FROM ghcr.io/navikt/baseimages/temurin:17

COPY target/pensjon-azuread-app-gateway-*.jar app.jar
