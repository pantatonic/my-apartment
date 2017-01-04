/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.apartment.wservices;

import java.math.BigInteger;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import my.apartment.model.Test;
import my.apartment.services.TestDao;
import my.apartment.services.TestDaoImpl;
import my.common.CommonString;
import org.json.JSONObject;


@Path("test")
public class TestResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TestResource
     */
    public TestResource() {
    }

    /**
     * Retrieves representation of an instance of my.apartment.services.TestResource
     * @return an instance of java.lang.String
     */
    @Path("get_all_test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            TestDao testDaoImpl = new TestDaoImpl();
            List<Test> tests = testDaoImpl.getAllTests();
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                    .put(CommonString.DATA_STRING, tests);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("get_test_by_id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTestById(
            @PathParam(value = "id") BigInteger id
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            TestDao testDaoImpl = new TestDaoImpl();
            List<Test> tests = testDaoImpl.getTestById(id);
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                    .put(CommonString.DATA_STRING, tests);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }

    /**
     * PUT method for updating or creating an instance of TestResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
