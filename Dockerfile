FROM openjdk:17
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} app.war
EXPOSE 10050
ENTRYPOINT ["java", "-jar", "app.war"]
