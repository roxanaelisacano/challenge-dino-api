# Etapa 1: Build y compilación (incluye Maven para construir el JAR)
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copiar archivos de Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x ./mvnw

# Descargar dependencias (mejora cacheo de capas de Docker)
RUN ./mvnw dependency:go-offline

# Copiar el código fuente
COPY src src

# Compilar la aplicación y generar el JAR saltando tests para mayor rapidez
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen final lígera para correr la app
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto por el que corre Spring Boot
EXPOSE 8080

# Comando de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
