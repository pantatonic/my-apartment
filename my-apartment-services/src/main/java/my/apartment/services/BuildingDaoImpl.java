package my.apartment.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.apartment.common.Config;
import my.apartment.model.Building;


public class BuildingDaoImpl implements BuildingDao {
    
    public Building save(Building building) {
        Connection con = null;
        PreparedStatement ps = null;
        
        Building buildingReturn = null;
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            if(building.getId() == null) {
                String querysString = "INSERT INTO building ("
                        + "name, "
                        + "address, "
                        + "tel, "
                        + "electricity_charge_per_unit, "
                        + "min_electricity_unit, "
                        + "min_electricity_charge, "
                        + "water_charge_per_unit, "
                        + "min_water_unit, min_water_charge) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                ps = con.prepareStatement(querysString, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, building.getName());
                ps.setString(2, building.getAddress());
                ps.setString(3, building.getTel());
                ps.setBigDecimal(4, building.getElectricityChargePerUnit());
                ps.setInt(5, building.getMinElectricityUnit());
                ps.setBigDecimal(6, building.getMinElectricityCharge());
                ps.setBigDecimal(7, building.getWaterChargePerUnit());
                ps.setInt(8, building.getMinWaterUnit());
                ps.setBigDecimal(9, building.getMinWaterCharge());
                
                Integer effectRow = ps.executeUpdate();
                if(effectRow != 0) {
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        buildingReturn = new Building();
                        buildingReturn.setId(generatedKeys.getInt(1));
                    }
                }
            }
            else {
                System.out.println("UPDATE");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        
        return buildingReturn;
    }
}
