# Sử dụng image JDK 17 chính thức
FROM openjdk:17-jdk

# Thong tin tac gia
LABEL authors="npv0710"

# Tạo thư mục ứng dụng trong container
WORKDIR /app

# Sao chép file JAR vào thư mục làm việc trong container
COPY target/department-service-0.0.1-SNAPSHOT.jar /app/department-service-0.0.1-SNAPSHOT.jar

# EXPOSE PORT UNG DUNG
# Chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "/app/department-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080

# Chạy ứng dụng khi container khởi động
CMD ["java", "-jar", "department-service-0.0.1-SNAPSHOT.jar"]

