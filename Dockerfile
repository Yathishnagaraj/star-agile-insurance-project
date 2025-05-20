FROM openjdk:21
WORKDIR /app
COPY insure-me-1.0.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
EXPOSE 9090
ENTRYPOINT ["java","-jar","/app.jar"]
