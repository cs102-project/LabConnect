FROM openjdk:11.0.10-jdk-buster
RUN apt-get -y update && apt-get -y install zip
RUN useradd spring
#USER spring:spring
COPY --chown=spring:spring labconnect-dummyfs /dummy
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]