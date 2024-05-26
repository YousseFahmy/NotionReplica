FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ./src/main/resources/replica-notion-media-server-firebase-adminsdk-skn6b-0fc5e1b947.json config/firebase-key.json
COPY ./target/notesapp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]