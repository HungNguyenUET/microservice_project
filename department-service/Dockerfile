# Sử dụng image JDK 17 chính thức
FROM openjdk:17-jdk

# Tạo thư mục ứng dụng trong container
WORKDIR /app

# Sao chép file JAR vào thư mục làm việc trong container
COPY target/department-service-0.0.1-SNAPSHOT.jar /app/department-service-0.0.1-SNAPSHOT.jar

# Chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "/app/department-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
