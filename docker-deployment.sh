#!/bin/bash

set -e  # Detiene la ejecución si hay algún error

# Colores para mejorar la salida
green="\e[32m"
red="\e[31m"
reset="\e[0m"

# Paso 1: Compilar el proyecto con Maven
echo -e "${green}Compilando con Maven...${reset}"
if ! mvn clean install; then
    echo -e "${red}Error: Fallo en mvn clean install.${reset}"
    exit 1
fi

echo -e "${green}Compilación exitosa.${reset}"

# Paso 2: Construir la imagen Docker
echo -e "${green}Construyendo imagen Docker...${reset}"
if ! docker build -t retodam .; then
    echo -e "${red}Error: Fallo en docker build.${reset}"
    exit 1
fi

echo -e "${green}Imagen Docker creada con éxito.${reset}"

# Paso 3: Etiquetar la imagen Docker
echo -e "${green}Etiquetando imagen Docker...${reset}"
docker tag retodam fjzamora93/retodam:latest

echo -e "${green}Imagen etiquetada correctamente.${reset}"

# Paso 4: Subir la imagen a Docker Hub
echo -e "${green}Subiendo imagen a Docker Hub...${reset}"
if ! docker push fjzamora93/retodam:latest; then
    echo -e "${red}Error: Fallo en docker push. Verifica que estás autenticado.${reset}"
    exit 1
fi

echo -e "${green}Imagen subida con éxito a Docker Hub.${reset}"

echo -e "${green}Despliegue completado correctamente.${reset}"

