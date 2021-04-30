FROM openjdk:11.0.10-jdk-buster
RUN useradd spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY --chown=spring:spring labconnect-dummyfs /dummy
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]