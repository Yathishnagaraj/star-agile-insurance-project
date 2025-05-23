FROM openjdk:21-slim
WORKDIR /app
COPY selenium-insure-me-runnable.jar app.jar
EXPOSE 9095
ENTRYPOINT ["java", "-jar", "app.jar"]
