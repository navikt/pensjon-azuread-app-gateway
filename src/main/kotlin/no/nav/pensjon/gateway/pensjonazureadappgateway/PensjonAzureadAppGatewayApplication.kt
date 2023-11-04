package no.nav.pensjon.gateway.pensjonazureadappgateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PensjonAzureadAppGatewayApplication

fun main(args: Array<String>) {
    runApplication<PensjonAzureadAppGatewayApplication>(*args)
}
