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
import my.apartment.model.RoomCurrentCheckIn;
import my.apartment.model.RoomInvoice;
import my.apartment.model.RoomReceipt;
import my.apartment.services.RoomCurrentCheckInDao;
import my.apartment.services.RoomCurrentCheckInDaoImpl;
import my.apartment.services.RoomInvoiceDao;
import my.apartment.services.RoomInvoiceDaoImpl;
import my.apartment.services.RoomReceiptDao;
import my.apartment.services.RoomReceiptDaoImpl;
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
            
            RoomReceiptDao roomReceiptDaoImpl = new RoomReceiptDaoImpl();
            
            List<RoomInvoice> roomInvoices = roomInvoiceDaoImpl.getById(roomInvoiceId);
            
            if(roomInvoices.isEmpty()) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        "Not found this invoice id");
            }
            else {
                /** roomInvoice data */
                RoomInvoice roomInvoice = roomInvoices.get(0);
                
                /** check invoice id already receipt */
                if(roomReceiptDaoImpl.isAreadyReceiptOfInvoice(roomInvoiceId)) {
                    jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        "This invoice already receipt");
                }
                else {
                    /** this scope is create receipt process */
                    
                    /** roomReceipt object to save */
                    RoomReceipt roomReceipt = new RoomReceipt();


                    RoomCurrentCheckInDao rccid = new RoomCurrentCheckInDaoImpl();

                    List<RoomCurrentCheckIn> roomCurrentCheckIns = rccid.getCurrentByRoomId(roomInvoice.getRoomId());
                    if(roomCurrentCheckIns.isEmpty()) {
                        roomReceipt.setPayer("");
                    }
                    else {
                        roomReceipt.setPayer(roomCurrentCheckIns.get(0).getName() + " " + roomCurrentCheckIns.get(0).getLastname());
                    }

                    roomReceipt.setInvoiceId(roomInvoice.getId());
                    roomReceipt.setStatus(1); //force status 1 when create

                    Boolean resultSave = roomReceiptDaoImpl.create(roomReceipt);

                    if(resultSave == Boolean.TRUE) {
                        jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, 
                                CommonString.SAVE_DATA_SUCCESS_STRING);
                    }
                    else {
                        jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                                CommonString.PROCESSING_FAILED_STRING);
                    }
                }                
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("cancel")
    @POST
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String cancel(
            @FormParam("room_receipt_id") Integer roomReceiptId,
            @FormParam("description") String description
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RoomReceiptDao roomReceiptDaoImpl = new RoomReceiptDaoImpl();
            
            RoomReceipt roomReceipt = new RoomReceipt();
            roomReceipt.setId(roomReceiptId);
            roomReceipt.setDescription(description);
            
            Boolean result = roomReceiptDaoImpl.cancel(roomReceipt);
            
            if(result == Boolean.TRUE) {
                jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, 
                        CommonString.DELETE_DATA_SUCCESS_STRING);
            }
            else {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        CommonString.PROCESSING_FAILED_STRING);
            }
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
