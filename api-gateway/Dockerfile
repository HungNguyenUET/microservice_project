FROM openjdk:17-jdk

WORKDIR /app

COPY target/gateway-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8762

CMD ["java", "-jar", "app.jar"]