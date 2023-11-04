package no.nav.pensjon.gateway.pensjonazureadappgateway

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.session.CookieWebSessionIdResolver

import org.springframework.web.server.session.WebSessionIdResolver

@Configuration
class CookieConfiguration {
    @Bean
    fun webSessionIdResolver(): WebSessionIdResolver =
        CookieWebSessionIdResolver().apply {
            setCookieName("PROXYSESSION")
        }
}
