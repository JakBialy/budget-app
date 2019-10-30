FROM openjdk:8-slim

MAINTAINER Jakub Bialy

EXPOSE 8080

WORKDIR usr/local/bin

COPY build/libs/budget-app-0.0.1-SNAPSHOT.jar budget-app.jar

CMD ["java", "-jar", "budget-app.jar"]

# Before run make sure that Spring Boot properties are set for docker DB
