FROM openjdk:21
WORKDIR /app
COPY target/insure-me-1.0.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
