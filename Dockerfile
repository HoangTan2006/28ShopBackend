# Dockerfile


FROM openjdk:23
WORKDIR /app
COPY target/28ShopBE-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]