FROM openjdk:8-jdk-alpine
ARG JAR_FILE=@project.build.finalName@.jar
COPY ${JAR_FILE} /opt/app.jar
WORKDIR /opt
ENTRYPOINT exec java -jar -server -Xmx256m -XX:MaxPermSize=32m -XX:MaxNewSize=32m /opt/app.jar
