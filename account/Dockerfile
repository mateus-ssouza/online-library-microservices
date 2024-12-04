# Use a imagem do JDK 17 como base
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o JAR gerado pela aplicação para o contêiner
COPY target/*.jar app.jar

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]