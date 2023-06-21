FROM bellsoft/liberica-openjdk-alpine:17 AS build
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} weatherService.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "weatherService.jar"]