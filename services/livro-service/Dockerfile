FROM openjdk:17
COPY target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "sleep 10 && java -jar /app.jar"]