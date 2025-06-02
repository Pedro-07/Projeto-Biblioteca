# Usa uma imagem base com o Java 17
FROM openjdk:17

# Copia o .jar gerado do Maven para dentro da imagem
COPY target/*.jar app.jar

# Informa como a aplicação será iniciada
ENTRYPOINT ["java", "-jar", "/app.jar"]
