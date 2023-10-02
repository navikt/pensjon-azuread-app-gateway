package no.nav.pensjon.gateway.pensjonazureadappgateway

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.security.config.Customizer
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.transport.ProxyProvider.Proxy.HTTP
import java.net.URI

@Configuration
class SecurityConfiguration {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange { authorize: ServerHttpSecurity.AuthorizeExchangeSpec ->
            authorize
                .pathMatchers("/actuator/health/**").permitAll()
                .pathMatchers("/actuator/prometheus/**").permitAll()
                .anyExchange().authenticated()
        }
        http.oauth2Login(Customizer.withDefaults())
        http.oauth2Client(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun reactiveJwtDecoder(
        @Value("\${AZURE_APP_CLIENT_ID}") clientId: String,
        @Value("\${AZURE_OPENID_CONFIG_ISSUER}") issuer: String,
        @Value("\${AZURE_OPENID_CONFIG_JWKS_URI}") jwkSetUri: String,
    ): ReactiveJwtDecoder {
        val jwtDecoder = NimbusReactiveJwtDecoder
            .withJwkSetUri(jwkSetUri)
            .webClient(webClientProxy())
            .build()

        jwtDecoder.setJwtValidator(
            DelegatingOAuth2TokenValidator(
                JwtTimestampValidator(),
                JwtIssuerValidator(issuer),
                JwtClaimValidator<Collection<String>>("aud") { clientId in it },
            )
        )
        return jwtDecoder
    }

    fun webClientProxy(): WebClient =
        System.getenv("HTTP_PROXY")
            ?.let { URI(it) }
            ?.run {
                WebClient.builder().clientConnector(
                    ReactorClientHttpConnector(
                        HttpClient.create().proxy {
                            it
                                .type(HTTP)
                                .host(host)
                                .port(port)
                        }
                    )
                ).build()
            }
            ?: WebClient.create()
}
