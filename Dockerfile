FROM openjdk:21
WORKDIR /app
ARG JAR_FILE=target/insure-me-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
CMD ["java", "-jar", "/app.jar"]
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
