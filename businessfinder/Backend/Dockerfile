# Imagen base
FROM openjdk:17-jdk-slim

# Carpeta de trabajo
WORKDIR /app

# Copiar el jar
COPY target/*.jar app.jar

# Exponer puerto
EXPOSE 8080

# Ejecutar app
ENTRYPOINT ["java", "-jar", "app.jar"]
