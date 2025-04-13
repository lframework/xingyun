FROM frolvlad/alpine-java:jdk8-slim

WORKDIR /opt

ARG JAR_FILE=@build.finalName@.jar
COPY ${JAR_FILE} /opt/app.jar

EXPOSE 8080

ENV JAVA_OPTS="-Dspring.profiles.active=prod"

# 在默认情况下
# /opt/data/tmp是tomcat临时文件目录
# /opt/data/upload是公开上传文件的存储目录
# /opt/data/security-upload是安全上传文件的存储目录
# /opt/logs是日志的存储目录
CMD ["sh", "-c", "java -Dspring.servlet.multipart.location=/opt/data/tmp -Djugg.upload.location=/opt/data/upload -Djugg.security-upload.location=/opt/data/security-upload $JAVA_OPTS -jar -server app.jar"]
