FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 10050
ENTRYPOINT ["java", "-jar", "app.jar"]
