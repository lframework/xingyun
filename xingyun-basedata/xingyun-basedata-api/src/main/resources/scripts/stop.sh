#!/bin/sh

export APP_HOME_BIN_PATH=$(cd `dirname $0`; pwd)
export APP_HOME=${APP_HOME_BIN_PATH%/bin}

export C_PID=`ps -ef | grep java | grep $APP_HOME | grep -v grep | awk '{print $2}'`

if [ ! -z "$C_PID" ]; then
  kill -15 $C_PID;
fi