# Usa una imagen base ligera de OpenJDK
FROM openjdk:19-jdk-slim

# Crea el directorio de la app
WORKDIR /usr/app

# Copia el JAR generado al contenedor
COPY target/StopMultas-SpringBoot-1.0-SNAPSHOT.jar app.jar

# Expone el puerto en el que tu app correrá
EXPOSE 8080

# Define el punto de entrada de la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]