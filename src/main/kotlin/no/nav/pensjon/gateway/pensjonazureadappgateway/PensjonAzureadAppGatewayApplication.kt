package no.nav.pensjon.gateway.pensjonazureadappgateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication


@SpringBootApplication(exclude = [ReactiveUserDetailsServiceAutoConfiguration::class])
class PensjonAzureadAppGatewayApplication
fun main(args: Array<String>) {
	runApplication<PensjonAzureadAppGatewayApplication>(*args)
}
