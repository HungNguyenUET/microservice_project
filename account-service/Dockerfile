# Sử dụng image cơ sở Java
FROM openjdk:17-jdk

# Thông tin tác giả
LABEL authors="hungnv"

# Tạo thư mục làm việc trong container
WORKDIR /app

# Sao chép tệp JAR vào container
COPY target/account-service-0.0.1-SNAPSHOT.jar /app/account-service-0.0.1-SNAPSHOT.jar

# Expose port của ứng dụng
EXPOSE 8081

# Câu lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app/account-service-0.0.1-SNAPSHOT.jar"]