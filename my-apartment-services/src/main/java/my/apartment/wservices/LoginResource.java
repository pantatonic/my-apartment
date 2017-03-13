/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.apartment.wservices;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import my.apartment.services.LoginDao;
import my.apartment.services.LoginDaoImpl;
import my.common.CommonString;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author OPECDEMO
 */
@Path("login")
public class LoginResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LoginResource
     */
    public LoginResource() {
    }

    /**
     * Retrieves representation of an instance of my.apartment.wservices.LoginResource
     * @param user
     * @param password
     * @return an instance of java.lang.String
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(
            @FormParam("user") String user,
            @FormParam("password") String password
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            LoginDao loginDaoImpl = new LoginDaoImpl();
            Boolean result = loginDaoImpl.loginProcess(user, password);
            
            System.out.println("--------------");
            System.out.println(result);
            System.out.println("--------------");
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();                
    }

}
