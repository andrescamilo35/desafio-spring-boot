#
# Package stage
#
FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN chmod 644 /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
