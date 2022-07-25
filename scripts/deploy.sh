#!/bin/bash

source ~/.bash_profile

APP_NAME=webboard
REPOSITORY=`pwd`
RELEASE=release

# .jar 파일 타겟팅
JAR_NAME=$(ls $REPOSITORY | grep jar)

# 현재 실행중인 서버가 있으면 잡아서 종료 시킵니다.
CURRENT_PID=$(pgrep -f $APP_NAME)
if [ -z $CURRENT_PID ]
then
  echo ">>>> java process not found."
else
  echo ">>>> PID: $CURRENT_PID kill."
  kill -15 $CURRENT_PID
  sleep 30
fi

# .jar 파일 java 실행합니다.
echo ">>>> $JAR_NAME java execute."
nohup java -jar ./$JAR_NAME --spring.config.location=classpath:/application.properties --spring.profiles.active=$RELEASE > /dev/null 2> /dev/null < /dev/null &
sleep 15
CURRENT_PID=$(pgrep -f $APP_NAME)
echo ">>>> New PID: $CURRENT_PID"
