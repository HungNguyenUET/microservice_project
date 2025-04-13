FROM openjdk:17-jdk

WORKDIR /app

COPY target/api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway-0.0.1-SNAPSHOT.jar

EXPOSE 8762

CMD ["java", "-jar", "/app/api-gateway-0.0.1-SNAPSHOT.jar"]