FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} enrollment-app.jar
ENTRYPOINT ["java","-jar","/enrollment-app.jar"]
