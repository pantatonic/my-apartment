package my.apartment.wservices;

import java.io.InputStream;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import my.apartment.common.CommonString;
import my.apartment.common.CommonUtils;
import my.apartment.model.Room;
import my.apartment.services.RoomDao;
import my.apartment.services.RoomDaoImpl;
import org.json.JSONObject;


@Path("room")
public class RoomResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RoomResource
     */
    public RoomResource() {
    }

    /**
     * Retrieves representation of an instance of my.apartment.wservices.RoomResource
     * @return an instance of java.lang.String
     */
    @Path("room_get_by_building_id/{building_id}")
    @GET
    @Produces(CommonUtils.MEDIA_TYPE_JSON)
    public String roomGetByBuildingId(
            @PathParam("building_id") Integer buildingId
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RoomDao roomDaoImpl = new RoomDaoImpl();
            
            List<Room> rooms = roomDaoImpl.getByBuildingId(buildingId);

            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                    .put(CommonString.DATA_STRING, rooms);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.SERVICE_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("room_get_by_id/{room_id}")
    @GET
    @Produces(CommonUtils.MEDIA_TYPE_JSON)
    public String roomGetById(
            @PathParam("room_id") Integer roomId
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RoomDao roomDaoImpl = new RoomDaoImpl();
            
            List<Room> rooms = roomDaoImpl.getById(roomId);
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                    .put(CommonString.DATA_STRING, rooms);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.SERVICE_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("room_save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(CommonUtils.MEDIA_TYPE_JSON)
    public String roomSave(InputStream incomingData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            JSONObject jsonObjectReceive = CommonUtils.receiveJsonObject(incomingData);

            RoomDao roomDaoImpl = new RoomDaoImpl();
            
            Room room = new Room();
            
            room.setId(CommonUtils.stringToInteger(jsonObjectReceive.getString("id")));
            room.setBuildingId(CommonUtils.stringToInteger(jsonObjectReceive.getString("building_id")));
            room.setFloorSeq(CommonUtils.stringToInteger(jsonObjectReceive.getString("floor_seq")));
            room.setRoomNo(jsonObjectReceive.getString("room_no"));
            room.setName(jsonObjectReceive.getString("name"));
            room.setPricePerMonth(
                    CommonUtils.stringToBigDecimal(jsonObjectReceive.getString("price_per_month"))
            );
            room.setRoomStatusId(
                    CommonUtils.stringToInteger(jsonObjectReceive.getString("room_status_id"))
            );
            
            Room resultSave = roomDaoImpl.save(room);
            
            if(resultSave != null) {
                jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                        .put(CommonString.MESSAGE_STRING, CommonString.SAVE_DATA_SUCCESS_STRING)
                        .put("id", resultSave.getId());
            }
            else {
                jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                        .put(CommonString.MESSAGE_STRING, CommonString.SAVE_DATA_ERROR_STRING);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return jsonObjectReturn.toString();
    }

    /**
     * PUT method for updating or creating an instance of RoomResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
