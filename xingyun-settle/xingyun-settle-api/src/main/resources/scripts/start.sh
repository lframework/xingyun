#!/bin/sh

export BUILD_ID=dontkillme
if [ -z $JAVA_HOME ] ;
then
echo "JAVA_HOME is not exist, please check"
exit 1
fi
export APP_HOME_BIN_PATH=$(cd `dirname $0`; pwd)
export APP_HOME=${APP_HOME_BIN_PATH%/bin}

$JAVA_HOME/bin/java -jar -server -Xmx256m -XX:MaxPermSize=32m -XX:MaxNewSize=32m $APP_HOME/@build.finalName@.jar >/dev/null 2>&1 &