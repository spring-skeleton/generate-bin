#!/bin/bash
currentTime=`date +%s`

if [ ! -n "$1" ]
then
  Xms=512m
else
  Xms=$1
fi

if [ ! -n "$2" ]
then
  Xmx=1024m
else
  Xmx=$2
fi

if [ ! -n "$3" ]
then
  profile=prod
else
  profile=$3
fi

java -server -Dspring.profiles.active=$profile -Xmx$Xmx -Xms$Xms -Dfile.encoding=UTF-8 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../heapdump$currentTime.hprof -jar ../{0}.jar >/dev/null 2>&1 &
