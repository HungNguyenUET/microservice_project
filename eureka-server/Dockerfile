FROM openjdk:17-jdk
LABEL authors="nhatvl"

WORKDIR /app

COPY target/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "eureka-server-0.0.1-SNAPSHOT.jar"]

EXPOSE 8761