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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        JSONObject jsonObjectReturn = new JSONObject();
        
        try {
            BuildingDao buildingDaoImpl = new BuildingDaoImpl();
            
            List<Building> buildings = buildingDaoImpl.get();
            
            
            jsonObjectReturn.put(CommonString.RESULT_STRING, CommonString.SUCCESS_STRING)
                    .put(CommonString.MESSAGE_STRING, "Test building get");
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
                        .put(CommonString.MESSAGE_STRING, CommonString.SAVE_DATA_SUCCESS)
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
