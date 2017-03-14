/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.apartment.wservices;

import java.util.List;
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
import my.apartment.model.Users;
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
     * @param email
     * @param password
     * @return an instance of java.lang.String
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String loginProcess(
            @FormParam("email") String email,
            @FormParam("password") String password
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            LoginDao loginDaoImpl = new LoginDaoImpl();
            
            Users u = new Users();
            u.setEmail(email);
            u.setPassword(password);
            
            List<Users> resultUsers = loginDaoImpl.loginProcess(u);
             
            if(resultUsers.size() == 0) {
                jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                        .put(CommonString.MESSAGE_STRING, CommonString.DATA_NOT_FOUND_STRING);
            }
            else {
                if(resultUsers.get(0).getStatus() == 0) {
                    jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                        .put(CommonString.MESSAGE_STRING, "STATUS IS DISABLED");
                }
                else {
                    jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                        .put(CommonString.MESSAGE_STRING, "LOGIN SUCCESS")
                        .put(CommonString.DATA_STRING, resultUsers);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, "SERVICE ERROR");
        }
        
        return jsonObjectReturn.toString();                
    }

}
