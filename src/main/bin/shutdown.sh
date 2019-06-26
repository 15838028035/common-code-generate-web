#!/bin/bash
  pid=$(ps -ef | grep  common-code-generate-web-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}')
if [ -z "$pid" ]
then
  echo Application is already stopped
else
  echo kill $pid
  kill  $pid
fi
