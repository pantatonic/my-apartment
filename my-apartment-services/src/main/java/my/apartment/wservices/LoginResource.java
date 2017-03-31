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
import my.apartment.common.JsonObjectUtils;
import org.json.JSONObject;


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
                jsonObjectReturn = JsonObjectUtils.setDataNotFound(jsonObjectReturn);
            }
            else {
                if(resultUsers.get(0).getStatus() == 0) {
                    jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                            "STATUS IS DISABLED");
                }
                else {
                    jsonObjectReturn = JsonObjectUtils.setSuccessWithMessageDataList(
                            jsonObjectReturn, "LOGIN SUCCESS", resultUsers);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }

}
