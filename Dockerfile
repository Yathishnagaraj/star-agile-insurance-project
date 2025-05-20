FROM openjdk:21
WORKDIR /app
COPY target/insure-me-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
EXPOSE 9090
ENTRYPOINT ["java","-jar","/app.jar"]
