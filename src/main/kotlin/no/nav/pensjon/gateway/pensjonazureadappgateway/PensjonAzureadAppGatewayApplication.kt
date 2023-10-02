package no.nav.pensjon.gateway.pensjonazureadappgateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.web.server.SecurityWebFilterChain


@SpringBootApplication(exclude = [ReactiveUserDetailsServiceAutoConfiguration::class])

class PensjonAzureadAppGatewayApplication {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange { authorize: AuthorizeExchangeSpec ->
            authorize
                .pathMatchers("/actuator/health/**").permitAll()
                .pathMatchers("/actuator/prometheus/**").permitAll()
                .anyExchange().authenticated()
        }
        http.oauth2Login(withDefaults())
        http.oauth2Client(withDefaults())
        return http.build()
    }
}

fun main(args: Array<String>) {
    runApplication<PensjonAzureadAppGatewayApplication>(*args)
}
