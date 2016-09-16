Docker based Sample Application with Java REST server, MySQL database and Node.js frontend.

### Project Setup
1. Copy [docker-compose.yml](https://github.com/Appdynamics/SampleApp/blob/master/docker-compose.yml) and save to local
2. Fill in environment variables:
     - **CONTROLLER_URL**  :  For example: xxx.saas.appdynamics.com
     - **CONTROLLER_PORT** : 443 (SaaS controller), 8090 (or whichever port you set for on-prem controller)
     - **CONTROLLER_ACCOUNT_NAME** : Get from License information
     - **CONTROLLER_ACCESS_KEY**: Get from License information
     - **PORTAL_USERNAME**: Your appdynamics.com account (required for downloading agents)
     - **PORTAL_PASSWORD**: Your appdynamics.com password (required for downloading agents)
     - **AGENT_VERSION**: Your 4 digit controller version, for example: 4.2.3.2.
     - **APP_ID**: Not applicable if trying from GitHub source. Leave empty. (Note, some features are not availble with APP_ID left empty)

> **Note**: If you want to build Docker images from source code, clone the project and uncomment line 7 and line 34 in docker-compose.yml.

### Deploy

1. Start the application from docker-compose.yml directory by running: 

    ```docker-compose up -d``` 
2. Once you see "... Creating web", run this command to install AppDynamics agents:

    ```docker exec -it rest install-appdynamics; docker exec rest start-all```
3. Now your app is running (your default Docker host may not be 192.168.99.100):
    * Java REST server: [192.168.99.100:8080/SampleApp/products](http://192.168.99.100:8080/SampleApp/products)
    * Node.js web frontend: [192.168.99.100:3000](http://192.168.99.100:3000/#)

Open frontend in browser, you will see this:

![alt tag](https://github.com/Appdynamics/SampleApp/blob/master/web/src/public/img/sampleapp.png)
### Clean up
* Stop and remove all the running containers: 
 
    ```docker-compose down```
* Remove all the images: 

    ``` docker rmi -f appdynamics/sample-app-rest appdynamics/sample-app-web mysql```
* Delete docker-compose.yml
* Your enviroment is clean now!
