FROM openjdk:17-jdk

WORKDIR /app

COPY target/config-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8763

CMD ["java", "-jar", "app.jar"]