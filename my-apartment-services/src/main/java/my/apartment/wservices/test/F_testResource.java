/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.apartment.wservices.test;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author OPECDEMO
 */
@Path("f_test")
public class F_testResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of F_testResource
     */
    public F_testResource() {
    }

    /**
     * Retrieves representation of an instance of my.apartment.wservices.test.F_testResource
     * @return an instance of java.lang.String
     */
    @Path("get_f_test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "This is f_test";
    }

    /**
     * PUT method for updating or creating an instance of F_testResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
