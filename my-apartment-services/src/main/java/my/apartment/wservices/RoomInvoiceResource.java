package my.apartment.wservices;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import my.apartment.common.CommonString;
import my.apartment.common.CommonWsUtils;
import my.apartment.common.JsonObjectUtils;
import my.apartment.model.ElectricityMeter;
import my.apartment.model.Room;
import my.apartment.model.RoomInvoice;
import my.apartment.model.RoomInvoicePdf;
import my.apartment.model.WaterMeter;
import my.apartment.services.RoomDao;
import my.apartment.services.RoomDaoImpl;
import my.apartment.services.RoomInvoiceDao;
import my.apartment.services.RoomInvoiceDaoImpl;
import my.apartment.services.RoomReceiptDao;
import my.apartment.services.RoomReceiptDaoImpl;
import org.json.JSONArray;
import org.json.JSONObject;


@Path("room_invoice")
public class RoomInvoiceResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RoomInvoiceResource
     */
    public RoomInvoiceResource() {
    }

    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String create(InputStream incomingData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            JSONObject jsonObjectReceive = CommonWsUtils.receiveJsonObject(incomingData);
            
            List<RoomInvoice> roomIdStringList = this.getRoomInvoiceListFromJsonObjectReceive(jsonObjectReceive);
            
            RoomInvoiceDao  roomInvoiceDaoImpl = new RoomInvoiceDaoImpl();
            
            Integer allDataSet = roomIdStringList.size();
            Integer countSuccess = 0;
            
            for(RoomInvoice r : roomIdStringList) {
                Boolean roomInvoiceResult = roomInvoiceDaoImpl.create(r);
                
                if(roomInvoiceResult) {
                    countSuccess = countSuccess + 1;
                }
            }

            if(allDataSet == countSuccess) {
                jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, 
                        CommonString.SAVE_DATA_SUCCESS_STRING);
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
    
    /**
     * 
     * @param jsonObjectReceive
     * @return List
     */
    private List<RoomInvoice> getRoomInvoiceListFromJsonObjectReceive(JSONObject jsonObjectReceive) {
        JSONArray jsonArrayReceive = new JSONArray(jsonObjectReceive.get(CommonString.DATA_STRING).toString());
        
        Integer month = jsonObjectReceive.getInt("month");
        Integer year = jsonObjectReceive.getInt("year");

        List<RoomInvoice> roomInvoices = new ArrayList<RoomInvoice>();
        
        for(Integer i = 0; i < jsonArrayReceive.length(); i++) {
            Integer roomIdFromData = Integer.parseInt(jsonArrayReceive.getString(i), 10);
            
            ElectricityMeter electricityMeterOfRoom = this.getElectricityDataOfRoom(roomIdFromData, month, year);
            WaterMeter waterMeterOfRoom = this.getWaterMeterDataOfRoom(roomIdFromData, month, year);
            Room roomDataForRoomPrice = this.getRoomData(roomIdFromData);
            
            /** if electricityMeterOfRoom or waterMeterOfRoom 
             * null then bypass to create room invoice */
            if(electricityMeterOfRoom != null && waterMeterOfRoom != null) {
                
                Boolean alreadyHaveData = this.checkAlreadyHaveData(roomIdFromData, month, year);
                
                if(!alreadyHaveData) {
                    RoomInvoice roomInvoice = this.prepareRoomInvoiceForCreate(roomIdFromData, month, year, 
                    roomDataForRoomPrice, electricityMeterOfRoom, waterMeterOfRoom);
                
                    roomInvoices.add(roomInvoice);
                }
            }
        }
        
        return roomInvoices;
    }
    
    /**
     * 
     * @param roomIdFromData
     * @return Room
     */
    private Room getRoomData(Integer roomIdFromData) {
        RoomDao roomDaoImpl = new RoomDaoImpl();
        
        List<Room> rooms = roomDaoImpl.getById(roomIdFromData);
        
        return rooms.isEmpty() ? null : rooms.get(0);
    }
    
    /**
     * 
     * @param roomIdFromData
     * @param month
     * @param year
     * @return Boolean
     */
    private Boolean checkAlreadyHaveData(Integer roomIdFromData, Integer month, Integer year) {
        return CommonWsUtils.checkAlreadyHaveRoomInvoiceData(roomIdFromData, month, year);
    }
    
    /**
     * 
     * @param roomIdFromData
     * @param month
     * @param year
     * @param electricityMeterOfRoom
     * @param waterMeterOfRoom
     * @return RoomInvoice
     */
    private RoomInvoice prepareRoomInvoiceForCreate(
            Integer roomIdFromData, Integer month, Integer year,
            Room roomDataForRoomPrice,
            ElectricityMeter electricityMeterOfRoom,
            WaterMeter waterMeterOfRoom
    ) {
            RoomInvoice roomInvoice = new RoomInvoice();
        
            roomInvoice.setRoomId(roomIdFromData);
            roomInvoice.setRoomPricePerMonth(roomDataForRoomPrice.getPricePerMonth());
            roomInvoice.setMonth(month);
            roomInvoice.setYear(year);
            
            roomInvoice.setElectricityPreviousMeter(electricityMeterOfRoom.getPreviousMeter());
            roomInvoice.setElectricityPresentMeter(electricityMeterOfRoom.getPresentMeter());
            roomInvoice.setElectricityChargePerUnit(electricityMeterOfRoom.getChargePerUnit());
            roomInvoice.setElectricityUsageUnit(electricityMeterOfRoom.getUsageUnit());
            roomInvoice.setElectricityValue(electricityMeterOfRoom.getValue());
            roomInvoice.setElectricityUseMinimunUnitCalculate(electricityMeterOfRoom.getUseMinimunUnitCalculate());
            
            roomInvoice.setWaterPreviousMeter(waterMeterOfRoom.getPreviousMeter());
            roomInvoice.setWaterPresentMeter(waterMeterOfRoom.getPresentMeter());
            roomInvoice.setWaterChargePerUnit(waterMeterOfRoom.getChargePerUnit());
            roomInvoice.setWaterUsageUnit(waterMeterOfRoom.getUsageUnit());
            roomInvoice.setWaterValue(waterMeterOfRoom.getValue());
            roomInvoice.setWaterUseMinimunUnitCalculate(waterMeterOfRoom.getUseMinimunUnitCalculate());
            
            /** when create status must be 1 only (1=active, 2=inactive, 3=receipt) */
            roomInvoice.setStatus(1); 
            roomInvoice.setDescription(null);
            roomInvoice.setReceiptId(null);
            
            return roomInvoice;
    }
    
    /**
     * 
     * @param roomId
     * @param month
     * @param year
     * @return ElectricityMeter
     */
    private ElectricityMeter getElectricityDataOfRoom(Integer roomId, Integer month, Integer year) {
        ElectricityMeter electricityMeter = null;
        
        RoomDao roomDaoImpl = new RoomDaoImpl();
        
        List<ElectricityMeter> electricityMeters = roomDaoImpl.getElectricityMeterByRoomIdMonthYear(roomId, month, year);
        
        if(!electricityMeters.isEmpty()) {
            electricityMeter = electricityMeters.get(0);
        }
        
        return electricityMeter;
    }
    
    /**
     * 
     * @param roomId
     * @param month
     * @param year
     * @return WaterMeter
     */
    private WaterMeter getWaterMeterDataOfRoom(Integer roomId, Integer month, Integer year) {
        WaterMeter waterMeter = null;
        
        RoomDao roomDaoImpl = new RoomDaoImpl();
        
        List<WaterMeter> waterMeters = roomDaoImpl.getWaterMeterByRoomIdMonthYear(roomId, month, year);
        
        if(!waterMeters.isEmpty()) {
            waterMeter = waterMeters.get(0);
        }

        return waterMeter;
    }
    
    

    @Path("get_room_invoice_month_year/{building_id}/{month}/{year}")
    @GET
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String getRoomInvoiceMonthYear(
            @PathParam("building_id") Integer buildingId,
            @PathParam("month") Integer month,
            @PathParam("year") Integer year
    ) {
        JSONObject jsonObjectReturn = new JSONObject();

        try {
            RoomInvoiceDao roomInvoiceDaoImpl = new RoomInvoiceDaoImpl();
            
            List<RoomInvoice> roomInvoices = roomInvoiceDaoImpl.getRoomInvoiceMonthYear(buildingId, month, year);
            
            jsonObjectReturn = JsonObjectUtils.setSuccessWithDataList(jsonObjectReturn, roomInvoices);
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
            @FormParam("room_invoice_id") Integer roomInvoiceId,
            @FormParam("description") String description
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            RoomReceiptDao roomReceiptDaoImpl = new RoomReceiptDaoImpl();
             
            RoomInvoiceDao roomInvoiceDaoImpl = new RoomInvoiceDaoImpl();
            
            RoomInvoice roomInvoice = new RoomInvoice();
            roomInvoice.setId(roomInvoiceId);
            roomInvoice.setDescription(description);
            
            if(roomReceiptDaoImpl.isAreadyReceiptOfInvoice(roomInvoiceId)) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, 
                            CommonString.INVOICE_HAS_RECEIPT_DATA_STRING);
            }
            else {
                Boolean result = roomInvoiceDaoImpl.cancel(roomInvoice);
            
                if(result == Boolean.TRUE) {
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
    
    /**
     * 
     * @param incomingData
     * @return String
     */
    @Path("post_get_room_invoice_by_id")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String postGetRoomInvoiceById(InputStream incomingData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            JSONObject jsonObjectReceive = CommonWsUtils.receiveJsonObject(incomingData);
            
            JSONArray jsonArrayReceive = new JSONArray(jsonObjectReceive.get(CommonString.DATA_STRING).toString());
            
            RoomInvoiceDao roomInvoiceDaoImpl = new RoomInvoiceDaoImpl();
            
            List<RoomInvoicePdf> roomInvoicePdfsReturn = new ArrayList<RoomInvoicePdf>();
            
            for(Integer i = 0; i < jsonArrayReceive.length(); i++) {
                Integer roomInvoiceIdFromData = Integer.parseInt(jsonArrayReceive.getString(i), 10);

                List<RoomInvoicePdf> roomInvoicePdfs = roomInvoiceDaoImpl.getById(roomInvoiceIdFromData);
                
                if(!roomInvoicePdfs.isEmpty()) {
                    roomInvoicePdfsReturn.add(roomInvoicePdfs.get(0));
                }
            }
            
            jsonObjectReturn = JsonObjectUtils.setSuccessWithDataList(jsonObjectReturn, roomInvoicePdfsReturn);
        }
        catch(Exception e) {
            e.printStackTrace();
        
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("get_all_room_invoice_month_year/{building_id}/{month}/{year}")
    @GET
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String getAllRoomInvoiceMonthYear(
            @PathParam("building_id") Integer buildingId,
            @PathParam("month") Integer month,
            @PathParam("year") Integer year
    ) {
        JSONObject jsonObjectReturn = new JSONObject();

        try {
            RoomInvoiceDao roomInvoiceDaoImpl = new RoomInvoiceDaoImpl();
            
            List<RoomInvoice> roomInvoices = roomInvoiceDaoImpl.getAllRoomInvoiceMonthYear(buildingId, month, year);
            
            jsonObjectReturn = JsonObjectUtils.setSuccessWithDataList(jsonObjectReturn, roomInvoices);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("get_all_unpaid_invoice")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String getAllUnpaidInvoice(InputStream incomingData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            JSONObject jsonObjectReceive = CommonWsUtils.receiveJsonObject(incomingData);

            Integer start = Integer.parseInt(jsonObjectReceive.getString("start"), 10);
            Integer length = Integer.parseInt(jsonObjectReceive.getString("length"), 10);
            
            RoomInvoiceDao roomInvoiceDaoImpl = new RoomInvoiceDaoImpl();
            
            Object[] resultObject = roomInvoiceDaoImpl.getAllUnpaidInvoice(start, length);
            
            jsonObjectReturn = JsonObjectUtils.setSuccessWithDataList(jsonObjectReturn, (List<RoomInvoice>) resultObject[0]);

            jsonObjectReturn.put("totalRecords", resultObject[1]);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    

    /**
     * PUT method for updating or creating an instance of RoomInvoiceResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
