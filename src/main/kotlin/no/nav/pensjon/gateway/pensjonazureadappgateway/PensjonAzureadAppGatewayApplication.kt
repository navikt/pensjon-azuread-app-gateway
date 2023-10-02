package no.nav.pensjon.gateway.pensjonazureadappgateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


@SpringBootApplication(exclude = [ReactiveUserDetailsServiceAutoConfiguration::class])
class PensjonAzureadAppGatewayApplication {
	@Bean
	fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
		http
			.authorizeExchange { authorize: ServerHttpSecurity.AuthorizeExchangeSpec ->
				authorize
					.pathMatchers("/actuator/health/**").permitAll()
					.pathMatchers("/actuator/prometheus/**").permitAll()
					.anyExchange().authenticated()
			}
		return http.build()
	}
}
fun main(args: Array<String>) {
	runApplication<PensjonAzureadAppGatewayApplication>(*args)
}
