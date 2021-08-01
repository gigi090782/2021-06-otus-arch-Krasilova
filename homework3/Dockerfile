FROM openjdk:12-jdk-alpine

EXPOSE 8000

RUN mkdir /app

COPY build/libs/*.jar /app/homework2.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/homework2.jar"]