FROM openjdk:17-jdk-slim

ARG ROOT_DIRECTORY=../..

COPY $ROOT_DIRECTORY/gradlew .
COPY $ROOT_DIRECTORY/gradle gradle
COPY $ROOT_DIRECTORY/build.gradle .
COPY $ROOT_DIRECTORY/settings.gradle .
COPY $ROOT_DIRECTORY/src src
COPY $ROOT_DIRECTORY/feasta-backend-config feasta-backend-config

RUN chmod +x ./gradlew
RUN ./gradlew tasks
RUN ./gradlew bootjar

RUN cp ./build/libs/*.jar app.jar
RUN chmod +x ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]