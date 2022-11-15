FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/simpleAuth-1.0.jar
WORKDIR /opt/app
COPY ${JAR_FILE} auth.jar
ENTRYPOINT ["java", "-jar", "auth.jar"]