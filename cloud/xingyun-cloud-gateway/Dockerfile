FROM openjdk:8-jre-slim

WORKDIR /opt

ARG JAR_FILE=@build.finalName@.jar
COPY ${JAR_FILE} /opt/app.jar

EXPOSE 15000

ENV JAVA_OPTS="-Dspring.profiles.active=prod"

CMD ["sh", "-c", "java $JAVA_OPTS -jar -server app.jar"]
