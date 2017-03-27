package my.apartment.wservices;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import my.apartment.model.Building;
import my.apartment.services.BuildingDao;
import my.apartment.services.BuildingDaoImpl;
import my.apartment.common.CommonString;
import my.apartment.common.CommonUtils;
import org.json.JSONObject;


@Path("building")
public class BuildingResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BuildingResource
     */
    public BuildingResource() {
    }

    /**
     * Retrieves representation of an instance of my.apartment.wservices.BuildingResource
     * @return an instance of java.lang.String
     */
    @Path("building_get")
    @GET
    @Produces(CommonUtils.MEDIA_TYPE_JSON)
    public String getJson() {
        JSONObject jsonObjectReturn = new JSONObject();

        try {
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            List<Building> buildings = buildingDaoImpl.get();

            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                    .put(CommonString.DATA_STRING, buildings);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.SERVICE_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("building_get_by_id/{building_id}")
    @GET
    @Produces(CommonUtils.MEDIA_TYPE_JSON)
    @Consumes(CommonUtils.MEDIA_TYPE_JSON)
    public String buildingGetById(
            @PathParam("building_id") Integer buildingId
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            List<Building> buildings = buildingDaoImpl.getById(buildingId);

            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                    .put(CommonString.DATA_STRING, buildings);
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.SERVICE_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }
    
    @Path("building_delete_by_id")
    @POST
    @Produces(CommonUtils.MEDIA_TYPE_JSON)
    public String buildingDeleteById(
            @FormParam("building_id") Integer buildingId
    ) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            List<Building> buildings = buildingDaoImpl.getById(buildingId);
            
            if(buildings.isEmpty()) {
                jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.DATA_ALREADY_DELETE_STRING);
            }
            else {
                Boolean resultDelete = buildingDaoImpl.deleteById(buildingId);
                
                if(resultDelete == Boolean.TRUE) {
                    jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                        .put(CommonString.DATA_STRING, CommonString.DELETE_DATA_SUCCESS_STRING);
                }
                else {
                    jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                        .put(CommonString.DATA_STRING, CommonString.PROCESSING_FAILED_STRING);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.SERVICE_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }

    @Path("building_save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String buildingSave(InputStream incomingData) {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            JSONObject jsonObjectReceive = CommonUtils.receiveJsonObject(incomingData);
            
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            Building building = new Building();
            
            building.setId(CommonUtils.stringToInteger(jsonObjectReceive.getString("id")));
            building.setName(jsonObjectReceive.getString("name"));
            building.setAddress(jsonObjectReceive.getString("address"));
            building.setTel(jsonObjectReceive.getString("tel"));
            building.setElectricityChargePerUnit(
                    CommonUtils.stringToBigDecimal(jsonObjectReceive.getString("electricity_charge_per_unit"))
            );
            building.setMinElectricityUnit(
                    CommonUtils.stringToInteger(jsonObjectReceive.getString("min_electricity_unit"))
            );
            building.setMinElectricityCharge(
                    CommonUtils.stringToBigDecimal(jsonObjectReceive.getString("min_electricity_charge"))
            );
            
            building.setWaterChargePerUnit(
                    CommonUtils.stringToBigDecimal(jsonObjectReceive.getString("water_charge_per_unit"))
            );
            building.setMinWaterUnit(
                    CommonUtils.stringToInteger(jsonObjectReceive.getString("min_water_unit"))
            );
            building.setMinWaterCharge(
                    CommonUtils.stringToBigDecimal(jsonObjectReceive.getString("min_water_charge"))
            );

            Building resultSave = buildingDaoImpl.save(building);
            
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
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.ERROR_STRING)
                    .put(CommonString.MESSAGE_STRING, CommonString.SERVICE_ERROR_STRING);
        }
        
        return jsonObjectReturn.toString();
    }
}
