FROM openjdk:11.0.10-jdk-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]