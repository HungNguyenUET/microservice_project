# Sử dụng image JDK 17 chính thức
FROM openjdk:17-jdk


WORKDIR /app

COPY target/apiGateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8888
CMD ["java", "-jar", "app.jar"]