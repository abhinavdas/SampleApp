package com.appdynamics.sample.resource;

import com.google.gson.GsonBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mark.prichard on 8/25/16.
 */

@Path("/controller")
public class Controller {

    // Used to parse return JSON: we only need the id element
    private class Application {
        private String id;

        public String getId() {
            return id;
        }
    }

    @GET
    @Path("/appid")
    public Response throwException() throws Exception {
        // These environment variables are set by docker-compose
        String controllerUrl = System.getenv("CONTROLLER_URL");
        String controllerPort = System.getenv("CONTROLLER_PORT");
        String controllerAccount = System.getenv("CONTROLLER_ACCOUNT_NAME");
        String controllerUsername = System.getenv("CONTROLLER_USERNAME");
        String controllerPassword = System.getenv("CONTROLLER_PASSWORD");
        String controllerProtocol = controllerPort.equalsIgnoreCase("443") ?  "https" : "http";

        URL baseUrl;
        Application[] app;

        // Return 500 Server Error if Controller base URL is invalid
        try {
            baseUrl = new URL(controllerProtocol + "://" + controllerUrl + ":" + controllerPort);
        } catch (MalformedURLException e) {
            return Response.serverError().build();
        }

        // Controller API should return an array with one JSON object
        try {
            String data = new ControllerRESTCall(baseUrl, controllerAccount, controllerUsername, controllerPassword)
                            .getRESTResponse("/controller/rest/applications/SampleApp?output=JSON");

            app = new GsonBuilder().create().fromJson(data, Application[].class);
        } catch (Exception e) {
            // Return 500 Server Error if Controller API call fails
            return Response.serverError().build();
        }

        // Return 404 Not Found if application doesn't exist
        // On success, return 200 OK with the appId as plain text
        if (app.length != 1)
            return Response.status(404).build();
        else
            return Response.ok().type("text/plain").entity(app[0].getId()).build();
    }
}
