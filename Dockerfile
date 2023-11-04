FROM ghcr.io/navikt/baseimages/temurin:17

COPY target/pensjon-app-gateway-*.jar app.jar
