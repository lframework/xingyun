FROM eclipse-temurin:21-jre-jammy

WORKDIR /opt

ARG JAR_FILE=@build.finalName@.jar
COPY ${JAR_FILE} /opt/app.jar

EXPOSE 15020

ENV SPRING_PROFILES_ACTIVE="prod" \
    JAVA_OPTS=""

CMD ["sh", "-c", "exec java -server -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} ${JAVA_OPTS} -jar /opt/app.jar"]
