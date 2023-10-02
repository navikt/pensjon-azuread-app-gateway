FROM gcr.io/distroless/java17-debian11:nonroot

WORKDIR /app

COPY target/pensjon-azuread-app-gateway-*.jar /app/app-all.jar

CMD ["/app/app-all.jar"]
