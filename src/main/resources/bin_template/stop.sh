#!/bin/bash

serverPort=`grep "server.port" ../config/application-prod.properties | cut -d'=' -f2 | sed 's/\r//'`
if [ ! -n "$serverPort" ]
then
  serverPort=8080
fi

curl -X POST http://localhost:$serverPort/actuator/shutdown