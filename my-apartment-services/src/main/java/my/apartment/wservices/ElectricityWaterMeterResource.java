package my.apartment.wservices;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import my.apartment.common.CommonString;
import my.apartment.common.CommonWsUtils;
import my.apartment.common.JsonObjectUtils;
import my.apartment.model.Building;
import my.apartment.model.ElectricityMeter;
import my.apartment.model.WaterMeter;
import my.apartment.services.BuildingDao;
import my.apartment.services.BuildingDaoImpl;
import my.apartment.services.ElectricityMeterDao;
import my.apartment.services.ElectricityMeterDaoImpl;
import my.apartment.services.RoomDao;
import my.apartment.services.RoomDaoImpl;
import my.apartment.services.WaterMeterDao;
import my.apartment.services.WaterMeterDaoImpl;
import org.json.JSONArray;
import org.json.JSONObject;


@Path("electricity_water_meter")
public class ElectricityWaterMeterResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ElectricityWaterMeterResource
     */
    public ElectricityWaterMeterResource() {
    }

    /**
     * Retrieves representation of an instance of my.apartment.wservices.ElectricityWaterMeterResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        return new JSONObject().toString();
    }
    
    
    @Path("save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String electricityWaterMeterSave(InputStream incomingData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            JSONObject jsonObjectReceive = CommonWsUtils.receiveJsonObject(incomingData);
            
            /** list of model ElectricityMeter for collect model to save */
            List<ElectricityMeter> electricityMeters = this.getElectricityMeterListFromJsonObjectReceive(jsonObjectReceive);
            
            /** list of model WaterMeter for collect mode to save */
            List<WaterMeter> waterMeters = this.getWaterMeterListFromJsonObjectReceive(jsonObjectReceive);
            
            /** prepare data for electricity meter */
            electricityMeters = this.prepareElectricityMeterToSave(electricityMeters);

            /** prepare data for water meter */
            waterMeters = this.prepareWaterMeterToSave(waterMeters);
            
            
            
            ElectricityMeterDao electricityMeterDaoImpl = new ElectricityMeterDaoImpl();
            Boolean resultSaveElectricityMeter = electricityMeterDaoImpl.save(electricityMeters);
            
            WaterMeterDao waterMeterDaoImpl = new WaterMeterDaoImpl();
            Boolean resultSaveWaterMeter = waterMeterDaoImpl.save(waterMeters);
            
            Boolean processSaveResult = Boolean.TRUE;
            String errorMessage = "";
            if(resultSaveElectricityMeter == Boolean.FALSE) {
                errorMessage += "Electricity meter save error ";
                processSaveResult = Boolean.FALSE;
            }
            if(resultSaveWaterMeter == Boolean.FALSE) {
                errorMessage += "Water meter save error ";
                processSaveResult = Boolean.FALSE;
            }
            
            if(processSaveResult == Boolean.FALSE) {
                jsonObjectReturn = JsonObjectUtils.setErrorWithMessage(jsonObjectReturn, errorMessage);
            }
            else {
                jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, 
                        CommonString.SAVE_DATA_SUCCESS_STRING);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    private Boolean checkAlreadyHaveData(Integer roomIdFromData, Integer month, Integer year) {
        return CommonWsUtils.checkAlreadyHaveRoomInvoiceData(roomIdFromData, month, year);
    }
    
    /**
     * 
     * @param electricityMeters
     * @return List
     */
    private List<ElectricityMeter> prepareElectricityMeterToSave(List<ElectricityMeter> electricityMeters) {
        RoomDao roomDaoImpl = new RoomDaoImpl();
        
        for(ElectricityMeter e : electricityMeters) {
            BigDecimal electricityChargePerUnit = roomDaoImpl.getElectricityChargePerUnitByRoomId(e.getRoomId());

            Integer usageUnit = this.calculateElectricityUsageUnit(e.getRoomId(), e.getPreviousMeter(), e.getPresentMeter());
            
            BigDecimal valueUsage = this.calculateElectricityValueUsage(e.getRoomId(), electricityChargePerUnit, usageUnit);
            
            Boolean useMinimunUnitCalculate = roomDaoImpl.getIsUseElectricityMinimunUnitCalculateByRoomId(e.getRoomId());
            
            e.setChargePerUnit(electricityChargePerUnit);
            e.setUsageUnit(usageUnit);
            e.setValue(valueUsage);
            e.setUseMinimunUnitCalculate(useMinimunUnitCalculate);
        }

        return electricityMeters;
    }
    
    /**
     * 
     * @param previousMeter
     * @param presentMeter
     * @return Integer
     */
    private Integer calculateElectricityUsageUnit(Integer roomId, String previousMeter, String presentMeter) {
        Integer previousMeterInteger = Integer.parseInt(previousMeter, 10);
        Integer presentMeterInteger = Integer.parseInt(presentMeter, 10);
        Integer usageUnitReturn = null;

        if(previousMeterInteger <= presentMeterInteger) {
            usageUnitReturn = presentMeterInteger - previousMeterInteger;
        }
        else {
            /** this scope is revers meter */
            
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            Building building = buildingDaoImpl.getByRoomId(roomId).get(0);
            
            Integer electricityMeterDigit = building.getElectricityMeterDigit();
            
            Integer maxMeterValue = this.getMaxMeterValue(electricityMeterDigit);
            
            Integer diffMax = (maxMeterValue - previousMeterInteger);
            usageUnitReturn = (diffMax + presentMeterInteger) + 1;
        }

        return usageUnitReturn;
    }
    
    /**
     * 
     * @param meterDigit
     * @return Integer
     */
    private Integer getMaxMeterValue(Integer meterDigit) {
        String maxMeterValueString = "";
        
        for(Integer i = 0; i < meterDigit; i++) {
            maxMeterValueString += "9";
        }
        
        return Integer.parseInt(maxMeterValueString, 10);
    }
    
    /**
     * 
     * @param roomId
     * @param electricityChargePerUnit
     * @param usageUnit
     * @return BigDecimal
     */
    private BigDecimal calculateElectricityValueUsage(Integer roomId,BigDecimal electricityChargePerUnit, Integer usageUnit) {
        BigDecimal valueUsageReturn = new BigDecimal("0");
        
        RoomDao roomDaoImpl = new RoomDaoImpl();
        
        Boolean useMinimunUnitCalculate = roomDaoImpl.getIsUseElectricityMinimunUnitCalculateByRoomId(roomId);
        
        if(useMinimunUnitCalculate == Boolean.TRUE) {
            /** to get min_electricity_unit and min_electricity_charge of table : building to calculate */
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            Building building = buildingDaoImpl.getByRoomId(roomId).get(0);
            
            Integer minElectricityUnit = building.getMinElectricityUnit();
            BigDecimal minElectricityCharge = building.getMinElectricityCharge();

            if(usageUnit < minElectricityUnit) {
                /** set minElectricityCharge to valueUsage*/
                valueUsageReturn = minElectricityCharge;
            }
            else {
                /** normal calculate */
                valueUsageReturn = electricityChargePerUnit.multiply(new BigDecimal(usageUnit));
            }
        }
        else {
            /** normal calculate */
            valueUsageReturn = electricityChargePerUnit.multiply(new BigDecimal(usageUnit));
        }

        return valueUsageReturn;
    }
    
    
    
    /**
     * 
     * @param waterMeters
     * @return List
     */
    private List<WaterMeter> prepareWaterMeterToSave(List<WaterMeter> waterMeters) {
        RoomDao roomDaoImpl = new RoomDaoImpl();
        
        for(WaterMeter w : waterMeters) {
            BigDecimal waterChargePerUnit = roomDaoImpl.getWaterChargePerUnitByRoomId(w.getRoomId());

            Integer usageUnit = this.calculateWaterUsageUnit(w.getRoomId(), w.getPreviousMeter(), w.getPresentMeter());
            
            BigDecimal valueUsage = this.calculateWaterValueUsage(w.getRoomId(), waterChargePerUnit, usageUnit);
            
            Boolean useMinimunUnitCalculate = roomDaoImpl.getIsUseWaterMinimunUnitCalculateByRoomId(w.getRoomId());
            
            w.setChargePerUnit(waterChargePerUnit);
            w.setUsageUnit(usageUnit);
            w.setValue(valueUsage);
            w.setUseMinimunUnitCalculate(useMinimunUnitCalculate);
        }
        
        return waterMeters;
    }
    
    /**
     * 
     * @param roomId
     * @param previousMeter
     * @param presentMeter
     * @return Integer
     */
    private Integer calculateWaterUsageUnit(Integer roomId, String previousMeter, String presentMeter) {
        Integer previousMeterInteger = Integer.parseInt(previousMeter, 10);
        Integer presentMeterInteger = Integer.parseInt(presentMeter, 10);
        Integer usageUnitReturn = null;
        
        if(previousMeterInteger <= presentMeterInteger) {
            usageUnitReturn = presentMeterInteger - previousMeterInteger;
        }
        else {
            /** this scope is revers meter */
            
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            Building building = buildingDaoImpl.getByRoomId(roomId).get(0);
            
            Integer waterMeterDigit = building.getWaterMeterDigit();
            
            Integer maxMeterValue = this.getMaxMeterValue(waterMeterDigit);
            
            Integer diffMax = (maxMeterValue - previousMeterInteger);
            usageUnitReturn = (diffMax + presentMeterInteger) + 1;
        }
        
        return usageUnitReturn;
    }
    
    /**
     * 
     * @param roomId
     * @param waterChargePerUnit
     * @param usageUnit
     * @return BigDecimal
     */
    private BigDecimal calculateWaterValueUsage(Integer roomId,BigDecimal waterChargePerUnit, Integer usageUnit) {
        BigDecimal valueUsageReturn = new BigDecimal("0");
        
        RoomDao roomDaoImpl = new RoomDaoImpl();
        
        Boolean useMinimunUnitCalculate = roomDaoImpl.getIsUseWaterMinimunUnitCalculateByRoomId(roomId);
        
        if(useMinimunUnitCalculate == Boolean.TRUE) {
            /** to get min_water_unit and min_electricity_charge of table : building to calculate */
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            Building building = buildingDaoImpl.getByRoomId(roomId).get(0);
            
            Integer minWaterUnit = building.getMinWaterUnit();
            BigDecimal minWaterCharge = building.getMinWaterCharge();
            
            if(usageUnit < minWaterUnit) {
                /** set minWaterCharge to valueUsage*/
                valueUsageReturn = minWaterCharge;
            }
            else {
                /** normal calculate */
                valueUsageReturn = waterChargePerUnit.multiply(new BigDecimal(usageUnit));
            }
        }
        else {
            /** normal calculate */
            valueUsageReturn = waterChargePerUnit.multiply(new BigDecimal(usageUnit));
        }
        
        return valueUsageReturn;
    }
    
    
    
    
    
    
    /**
     * 
     * @param jsonObjectReceive
     * @return List
     */
    private List<ElectricityMeter> getElectricityMeterListFromJsonObjectReceive(JSONObject jsonObjectReceive) {
        JSONArray jsonArrayReceive = new JSONArray(jsonObjectReceive.get(CommonString.DATA_STRING).toString());
        
        List<ElectricityMeter> electricityMeters = new ArrayList<ElectricityMeter>();
        
        for(Integer i = 0; i < jsonArrayReceive.length(); i++) {
            JSONObject jsonObject = jsonArrayReceive.getJSONObject(i);
            
            ElectricityMeter electricityMeter = new ElectricityMeter();
            
            electricityMeter.setRoomId(jsonObject.getInt("id"));
            electricityMeter.setPreviousMeter(jsonObject.getString("previous_electric"));
            electricityMeter.setPresentMeter(jsonObject.getString("present_electric_meter"));

            electricityMeter.setMonth(jsonObjectReceive.getInt("month"));
            electricityMeter.setYear(jsonObjectReceive.getInt("year"));
            
            Boolean alreadyHaveData 
                    = this.checkAlreadyHaveData(electricityMeter.getRoomId(), 
                            jsonObjectReceive.getInt("month"), 
                            jsonObjectReceive.getInt("year"));
            
            if(!alreadyHaveData) {
                electricityMeters.add(electricityMeter);
            }

        }
        
        return electricityMeters;
    }
    
    /**
     * 
     * @param jsonObjectReceive
     * @return List
     */
    private List<WaterMeter> getWaterMeterListFromJsonObjectReceive(JSONObject jsonObjectReceive) {
        JSONArray jsonArrayReceive = new JSONArray(jsonObjectReceive.get(CommonString.DATA_STRING).toString());
        
        List<WaterMeter> waterMeters = new ArrayList<WaterMeter>();
        
        for(Integer i = 0; i < jsonArrayReceive.length(); i++) {
            JSONObject jsonObject = jsonArrayReceive.getJSONObject(i);
            
            WaterMeter waterMeter = new WaterMeter();
            
            waterMeter.setRoomId(jsonObject.getInt("id"));
            waterMeter.setPreviousMeter(jsonObject.getString("previous_water"));
            waterMeter.setPresentMeter(jsonObject.getString("present_water_meter"));

            waterMeter.setMonth(jsonObjectReceive.getInt("month"));
            waterMeter.setYear(jsonObjectReceive.getInt("year"));
            
            Boolean alreadyHaveData 
                = this.checkAlreadyHaveData(waterMeter.getRoomId(), 
                        jsonObjectReceive.getInt("month"), 
                        jsonObjectReceive.getInt("year"));
            
            if(!alreadyHaveData) {
                waterMeters.add(waterMeter);
            }
        }
        
        return waterMeters;
    }
    
    @Path("get_electricity_water_meter_by_building_id_month_year/{building_id}/{month}/{year}")
    @GET
    @Produces(CommonWsUtils.MEDIA_TYPE_JSON)
    public String getElectricityWaterMeterByBuildingIdMonthYear(
            @PathParam("building_id") Integer building,
            @PathParam("month") Integer month,
            @PathParam("year") Integer year
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            ElectricityMeterDao electricityMeterDaoImpl = new ElectricityMeterDaoImpl();
            
            List<ElectricityMeter> electricityMeters 
                    = electricityMeterDaoImpl.getElectricityMeterByBuildingIdMonthYear(building, month, year);
            
            WaterMeterDao waterMeterDaoImpl = new WaterMeterDaoImpl();
            
            List<WaterMeter> waterMeters 
                    = waterMeterDaoImpl.getWaterMeterByBuildingIdMonthYear(building, month, year);
            
            JSONObject subJsonObject = new JSONObject();
            
            subJsonObject.put("electricityMeter", electricityMeters);
            subJsonObject.put("waterMeter", waterMeters);
            
            jsonObjectReturn = JsonObjectUtils.setSuccessWithMessage(jsonObjectReturn, "");
            jsonObjectReturn.put(CommonString.DATA_STRING, subJsonObject);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }

    /**
     * PUT method for updating or creating an instance of ElectricityWaterMeterResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
