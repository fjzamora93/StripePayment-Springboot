# Usa una imagen de OpenJDK 19
FROM openjdk:19-jdk-slim

# Copia el archivo JAR generado a la imagen Docker
COPY target/SpringBootRetoDam-1.0-SNAPSHOT.jar /usr/app/SpringBootRetoDam-1.0-SNAPSHOT.jar

# Define el punto de entrada de la aplicación
ENTRYPOINT ["java", "-jar", "/usr/app/SpringBootRetoDam-1.0-SNAPSHOT.jar"]

# Exponer el puerto en el que tu app correrá
EXPOSE 8080
