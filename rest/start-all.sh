#! /bin/bash

if [ ! -s /env.sh ]; then
  echo "Environment not set for AppDynamics Agents: exiting..."
  exit
fi

start-machine-agent
echo 
start-db-agent
echo
start-tomcat

exit 0
