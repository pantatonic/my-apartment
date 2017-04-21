package my.apartment.wservices;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import my.apartment.common.CommonString;
import my.apartment.common.CommonWsUtils;
import my.apartment.common.JsonObjectUtils;
import my.apartment.model.Room;
import my.apartment.model.RoomCurrentCheckIn;
import my.apartment.model.RoomReservation;
import my.apartment.services.RoomCurrentCheckInDao;
import my.apartment.services.RoomCurrentCheckInDaoImpl;
import my.apartment.services.RoomDao;
import my.apartment.services.RoomDaoImpl;
import my.apartment.services.RoomReservationDao;
import my.apartment.services.RoomReservationDaoImpl;
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
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String roomGetByBuildingId(
            @PathParam("building_id") Integer buildingId
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RoomDao roomDaoImpl = new RoomDaoImpl();
            
            List<Room> rooms = roomDaoImpl.getByBuildingId(buildingId);

            jsonObjectReturn = JsonObjectUtils.setSuccessWithDataList(jsonObjectReturn, rooms);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("room_get_by_id/{room_id}")
    @GET
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String roomGetById(
            @PathParam("room_id") Integer roomId
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RoomDao roomDaoImpl = new RoomDaoImpl();
            
            List<Room> rooms = roomDaoImpl.getById(roomId);
            
            jsonObjectReturn = JsonObjectUtils.setSuccessWithDataList(jsonObjectReturn, rooms);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("check_room_no_duplicated")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String checkRoomNoDuplicated(InputStream incomingData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            JSONObject jsonObjectReceive = CommonWsUtils.receiveJsonObject(incomingData);
            
            RoomDao roomDaoImpl = new RoomDaoImpl();
            
            Room room = new Room();
            
            room.setId(CommonWsUtils.stringToInteger(jsonObjectReceive.getString("id")));
            room.setRoomNo(jsonObjectReceive.getString("room_no"));
            room.setBuildingId(CommonWsUtils.stringToInteger(jsonObjectReceive.getString("building_id")));
            
            Boolean result = roomDaoImpl.checkRoomNoDuplicated(room);
            
            if(result == Boolean.FALSE) {
                jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, "");
            }
            else {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        CommonString.DATA_DUPLICATED_STRING);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("room_save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String roomSave(InputStream incomingData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            JSONObject jsonObjectReceive = CommonWsUtils.receiveJsonObject(incomingData);

            RoomDao roomDaoImpl = new RoomDaoImpl();
            
            Room room = new Room();
            
            room.setId(CommonWsUtils.stringToInteger(jsonObjectReceive.getString("id")));
            room.setBuildingId(CommonWsUtils.stringToInteger(jsonObjectReceive.getString("building_id")));
            room.setFloorSeq(CommonWsUtils.stringToInteger(jsonObjectReceive.getString("floor_seq")));
            room.setRoomNo(jsonObjectReceive.getString("room_no"));
            room.setName(jsonObjectReceive.getString("name"));
            room.setPricePerMonth(CommonWsUtils.stringToBigDecimal(jsonObjectReceive.getString("price_per_month"))
            );
            room.setRoomStatusId(CommonWsUtils.stringToInteger(jsonObjectReceive.getString("room_status_id"))
            );
            
            Room resultSave = roomDaoImpl.save(room);
            
            if(resultSave != null) {
                jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, 
                        CommonString.SAVE_DATA_SUCCESS_STRING);
                
                jsonObjectReturn.put("id", resultSave.getId());
            }
            else {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        CommonString.SAVE_DATA_ERROR_STRING);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("room_delete_by_id")
    @POST
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String roomDeleteById(
            @FormParam("room_id") Integer roomId
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RoomDao roomDaoImpl = new RoomDaoImpl();
            
            List<Room> rooms = roomDaoImpl.getById(roomId);
            
            if(rooms.isEmpty()) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        CommonString.DATA_ALREADY_DELETE_STRING);
            }
            else {
                /**
                 * TODO : check room data (example: invoice) 
                 * if have data not allow to delete
                 */
                
                Boolean resultDelete = roomDaoImpl.deleteById(roomId);
                
                if(resultDelete == Boolean.TRUE) {
                    jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, 
                            CommonString.DELETE_DATA_SUCCESS_STRING);
                }
                else {
                    jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                            CommonString.PROCESSING_FAILED_STRING);
                }
            }
            
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
        
    }
    
    @Path("get_current_reserve")
    @GET
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String getCurrentReserve() {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RoomReservationDao roomReservationDaoImpl = new RoomReservationDaoImpl();
            
            List<RoomReservation> roomReservations = roomReservationDaoImpl.getCurrentReserve();
            
            jsonObjectReturn = JsonObjectUtils.setSuccessWithDataList(jsonObjectReturn, roomReservations);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("get_current_check_in")
    @GET
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String getCurrentCheckIn() {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RoomCurrentCheckInDao roomCurrentCheckInDaoImpl = new RoomCurrentCheckInDaoImpl();
            
            List<RoomCurrentCheckIn> roomCurrentCheckIns = roomCurrentCheckInDaoImpl.getCurrentCheckIn();

            jsonObjectReturn = JsonObjectUtils.setSuccessWithDataList(jsonObjectReturn, roomCurrentCheckIns);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("get_room_manage/{room_id}")
    @GET
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String getRoomManage(
            @PathParam("room_id") Integer roomId
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RoomReservationDao roomReservationDaoImpl = new RoomReservationDaoImpl();
            RoomCurrentCheckInDao roomCurrentCheckInDaoImpl = new RoomCurrentCheckInDaoImpl();
            
            List<RoomReservation> roomReservations = roomReservationDaoImpl.getCurrentByRoomId(roomId);
            List<RoomCurrentCheckIn> currentCheckIns = roomCurrentCheckInDaoImpl.getCurrentByRoomId(roomId);

            jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, "");
            
            JSONObject jsonObjectData = new JSONObject();
            
            jsonObjectData.put("roomReservarion", roomReservations);
            jsonObjectData.put("currentCheckIn", currentCheckIns);
         
            jsonObjectReturn.put(CommonString.DATA_STRING, jsonObjectData);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("room_reservation_save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String roomReservationSave(InputStream incomingData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            JSONObject jsonObjectReceive = CommonWsUtils.receiveJsonObject(incomingData);

            RoomReservationDao roomReservationDaoImpl = new RoomReservationDaoImpl();
            
            RoomReservation roomReservation = new RoomReservation();
            
            roomReservation.setId(CommonWsUtils.stringToInteger(jsonObjectReceive.getString("id")));
            roomReservation.setReserveDateString(jsonObjectReceive.getString("reserve_date"));
            roomReservation.setReserveExpiredString(
                    CommonWsUtils.strToString(jsonObjectReceive.getString("reserve_expired"))
            );
            roomReservation.setRoomId(jsonObjectReceive.getInt("room_id"));
            roomReservation.setIdCard(jsonObjectReceive.getString("id_card"));
            roomReservation.setReserveName(jsonObjectReceive.getString("reserve_name"));
            roomReservation.setReserveLastname(jsonObjectReceive.getString("reserve_lastname"));
            roomReservation.setRemark(jsonObjectReceive.getString("remark"));
            roomReservation.setStatus(jsonObjectReceive.getInt("status"));

            RoomReservation resultSave = roomReservationDaoImpl.save(roomReservation);
            
            if(resultSave != null) {
                jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, 
                        CommonString.SAVE_DATA_SUCCESS_STRING);
                
                jsonObjectReturn.put("id", resultSave.getId());
                jsonObjectReturn.put("roomId", resultSave.getRoomId());
            }
            else {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        CommonString.SAVE_DATA_ERROR_STRING);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("room_check_in_save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String roomCheckInSave(InputStream incomingData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            JSONObject jsonObjectReceive = CommonWsUtils.receiveJsonObject(incomingData);
            
            RoomCurrentCheckInDao roomCurrentCheckInDaoImpl = new RoomCurrentCheckInDaoImpl();
            
            RoomCurrentCheckIn roomCurrentCheckIn = new RoomCurrentCheckIn();
            
            roomCurrentCheckIn.setRoomId(CommonWsUtils.stringToInteger(jsonObjectReceive.getString("room_id")));
            roomCurrentCheckIn.setCheckInDateString(jsonObjectReceive.getString("check_in_date"));
            roomCurrentCheckIn.setIdCard(jsonObjectReceive.getString("id_card"));
            roomCurrentCheckIn.setName(jsonObjectReceive.getString("name"));
            roomCurrentCheckIn.setLastname(CommonWsUtils.strToString(jsonObjectReceive.getString("lastname")));
            roomCurrentCheckIn.setAddress(CommonWsUtils.strToString(jsonObjectReceive.getString("address")));
            roomCurrentCheckIn.setRemark(CommonWsUtils.strToString(jsonObjectReceive.getString("remark")));
            roomCurrentCheckIn.setNumberCode(CommonWsUtils.strToString(jsonObjectReceive.getString("number_code")));
            
            RoomCurrentCheckIn resultSave = roomCurrentCheckInDaoImpl.save(roomCurrentCheckIn);
            
            if(resultSave != null) {
                jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, 
                        CommonString.SAVE_DATA_SUCCESS_STRING);
                
                jsonObjectReturn.put("roomId", resultSave.getRoomId());
                jsonObjectReturn.put("numberCode", resultSave.getNumberCode());
            }
            else {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                        CommonString.SAVE_DATA_ERROR_STRING);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("get_reservation_list")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String getReservationList(InputStream incomingData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            JSONObject jsonObjectReceive = CommonWsUtils.receiveJsonObject(incomingData);

            Integer start = Integer.parseInt(jsonObjectReceive.getString("start"), 10);
            Integer length = Integer.parseInt(jsonObjectReceive.getString("length"), 10);
            String searchString = jsonObjectReceive.getString("search");
            
            RoomReservationDao roomReservationDaoImpl = new RoomReservationDaoImpl();
            
            Object[] resultObject = roomReservationDaoImpl.getReservationList(
                    Integer.parseInt(jsonObjectReceive.getString("room_id"), 10), 
                    start, length, searchString);

            jsonObjectReturn = JsonObjectUtils.setSuccessWithDataList(jsonObjectReturn, (List<RoomReservation>) resultObject[0]);

            jsonObjectReturn.put("totalRecords", resultObject[1]);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
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
