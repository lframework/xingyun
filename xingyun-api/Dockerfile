FROM eclipse-temurin:21-jre-jammy

WORKDIR /opt

ARG JAR_FILE=@build.finalName@.jar
COPY ${JAR_FILE} /opt/app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE="prod" \
    XINGYUN_TMP_DIR="/opt/data/tmp" \
    XINGYUN_UPLOAD_DIR="/opt/data/upload" \
    XINGYUN_SECURITY_UPLOAD_DIR="/opt/data/security-upload" \
    XINGYUN_LOG_DIR="/opt/logs" \
    JAVA_OPTS=""

RUN mkdir -p /opt/data/tmp /opt/data/upload /opt/data/security-upload /opt/logs

# 在默认情况下
# /opt/data/tmp是tomcat临时文件目录
# /opt/data/upload是公开上传文件的存储目录
# /opt/data/security-upload是安全上传文件的存储目录
# /opt/logs是日志的存储目录
CMD ["sh", "-c", "exec java -server -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} ${JAVA_OPTS} -jar /opt/app.jar"]
