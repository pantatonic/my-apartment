/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.ws.rs.core.MediaType;
import my.apartment.common.CommonString;
import my.apartment.common.CommonWsUtils;
import my.apartment.common.JsonObjectUtils;
import my.apartment.model.Building;
import my.apartment.model.ElectricityMeter;
import my.apartment.model.WaterMeter;
import my.apartment.services.BuildingDao;
import my.apartment.services.BuildingDaoImpl;
import my.apartment.services.RoomDao;
import my.apartment.services.RoomDaoImpl;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author user123
 */
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

            
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn = JsonObjectUtils.setServiceError(jsonObjectReturn);
        }
        
        return jsonObjectReturn.toString();
    }
    
    /**
     * 
     * @param electricityMeters
     * @return 
     */
    private List<ElectricityMeter> prepareElectricityMeterToSave(List<ElectricityMeter> electricityMeters) {
        RoomDao roomDaoImpl = new RoomDaoImpl();
        
        for(ElectricityMeter e : electricityMeters) {
            BigDecimal electricityChargePerUnit = roomDaoImpl.getElectricityChargePerUnitByRoomId(e.getRoomId());

            Integer usageUnit = this.calculateUsageUnit(e.getPreviousMeter(), e.getPresentMeter());
            
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
    private Integer calculateUsageUnit(String previousMeter, String presentMeter) {
        Integer previousMeterInteger = Integer.parseInt(previousMeter, 10);
        Integer presentMeterInteger = Integer.parseInt(presentMeter, 10);
        Integer usageUnitReturn = null;

        if(previousMeterInteger <= presentMeterInteger) {
            usageUnitReturn = presentMeterInteger - previousMeterInteger;
        }
        else {
            
        }

        return usageUnitReturn;
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

            electricityMeters.add(electricityMeter);
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

            waterMeters.add(waterMeter);
        }
        
        return waterMeters;
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
