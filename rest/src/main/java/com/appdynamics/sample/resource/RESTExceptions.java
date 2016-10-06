package com.appdynamics.sample.resource;

import com.appdynamics.sample.model.Product;
import com.sun.jersey.spi.inject.Errors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alanaanderson on 6/22/16.
 */
@Path("/")
public class RESTExceptions {

    @Inject
    protected EntityManager manager;

    @Inject
    private Logger log;

    @GET
    @Path("/java_error")
    public Response throwException() throws Exception {
        log.info("Throwing Exception...");
        try {
            throw new Exception("Forced Exception");
        } catch (Exception e) {
            // Ignore the Exception
        }
        //return Response.serverError().build();
        return Response.serverError()
                        .entity("Threw Java Exception")
                        .build();
    }

    @GET
    @Path("/slow/{delay}")
    public Response slowRequest(@PathParam("delay") int delay) throws Exception {
        log.info("Slow transaction: delay = " + delay + " seconds");

        try {
            for(int i = 0; i < delay; i++) Thread.sleep(1000);
        } catch(InterruptedException ex) {
        }

        return Response.ok()
                    .entity("Completed transaction with " + delay + " seconds delay")
                    .build();
    }

    @GET
    @Path("/sql_error")
    public Response throwSqlException() throws Exception {
        log.info("Throwing SQLException...");
        try {
            manager.createQuery("INSERT INTO non_existant_table (wrong_column) VALUES (1)").getResultList();
        } catch (Exception e) {
            //Ignore the Exception
        }
        return Response.serverError()
                        .entity("Threw SQL Exception")
                        .build();
    }
}
