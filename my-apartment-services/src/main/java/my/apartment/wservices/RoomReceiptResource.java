package my.apartment.wservices;

import java.io.InputStream;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import my.apartment.common.CommonString;
import my.apartment.common.CommonWsUtils;
import my.apartment.common.JsonObjectUtils;
import my.apartment.model.RoomInvoice;
import my.apartment.services.RoomInvoiceDao;
import my.apartment.services.RoomInvoiceDaoImpl;
import org.json.JSONObject;


@Path("room_receipt")
public class RoomReceiptResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RoomReceiptResource
     */
    public RoomReceiptResource() {
    }

    @Path("create")
    @POST
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String create(
            @FormParam("room_invoice_id") Integer roomInvoiceId
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RoomInvoiceDao roomInvoiceDaoImpl = new RoomInvoiceDaoImpl();
            
            List<RoomInvoice> roomInvoices = roomInvoiceDaoImpl.getById(roomInvoiceId);
            
            if(roomInvoices.isEmpty()) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        "Not found this invoice id");
            }
            else {
                //ทำถึงตรงนี้
            }
            
            
            
            System.out.println("-- ws --");
            System.out.println(roomInvoices.size());
            System.out.println(roomInvoices);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }

    /**
     * PUT method for updating or creating an instance of RoomReceiptResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
