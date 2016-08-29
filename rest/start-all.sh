#! /bin/bash

if [ ! -s /env.sh ]; then
  echo "Environment not set for AppDynamics Agents: exiting..."
  exit
fi

start-machine-agent
echo "*****************************************************"
start-db-agent
echo "*****************************************************"
start-tomcat
echo "*****************************************************"

echo "The Sample App REST services are running at: http://192.168.99.100:8080/SampleApp/products"
echo "The Sample App web application is running at: http://192.168.99.100:3000/#"

exit 0
