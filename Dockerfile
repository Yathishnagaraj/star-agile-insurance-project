FROM openjdk:21
WORKDIR /app
COPY target/target/insure-me-1.0.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
EXPOSE 9090
ENTRYPOINT ["java","-jar","/app.jar"]
