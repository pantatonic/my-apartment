package my.apartment.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.apartment.common.CommonUtils;
import my.apartment.common.Config;
import my.apartment.model.Building;

public class BuildingDaoImpl implements BuildingDao {
    
    @Override
    public List<Building> get() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Building> buildings = new ArrayList<Building>();
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * FROM building";
            
            ps = con.prepareStatement(stringQuery);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Building building = new Building();

                building.setId(rs.getInt("id"));
                building.setName(rs.getString("name"));
                building.setAddress(rs.getString("address"));
                building.setTel(rs.getString("tel"));
                
                building.setElectricityMeterDigit(rs.getInt("electricity_meter_digit"));
                building.setElectricityChargePerUnit(rs.getBigDecimal("electricity_charge_per_unit"));
                building.setMinElectricityUnit(rs.getInt("min_electricity_unit"));
                building.setMinElectricityCharge(rs.getBigDecimal("min_electricity_charge"));
                
                building.setWaterMeterDigit(rs.getInt("water_meter_digit"));
                building.setWaterChargePerUnit(rs.getBigDecimal("water_charge_per_unit"));
                building.setMinWaterUnit(rs.getInt("min_water_unit"));
                building.setMinWaterCharge(rs.getBigDecimal("min_water_charge"));
                
                buildings.add(building);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return buildings;
    }
    
    @Override
    public List<Building> getById(Integer id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Building> buildings = new ArrayList<Building>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * FROM building WHERE id = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                Building building = new Building();
                
                building.setId(rs.getInt("id"));
                building.setName(rs.getString("name"));
                building.setAddress(rs.getString("address"));
                building.setTel(rs.getString("tel"));
                
                building.setElectricityMeterDigit(rs.getInt("electricity_meter_digit"));
                building.setElectricityChargePerUnit(rs.getBigDecimal("electricity_charge_per_unit"));
                building.setMinElectricityUnit(CommonUtils.integerZeroToNull(rs.getInt("min_electricity_unit")));
                building.setMinElectricityCharge(rs.getBigDecimal("min_electricity_charge"));

                building.setWaterMeterDigit(rs.getInt("water_meter_digit"));
                building.setWaterChargePerUnit(rs.getBigDecimal("water_charge_per_unit"));
                building.setMinWaterUnit(CommonUtils.integerZeroToNull(rs.getInt("min_water_unit")));
                building.setMinWaterCharge(rs.getBigDecimal("min_water_charge"));
                
                buildings.add(building);
            }            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return buildings;
    }
    
    @Override
    public Boolean deleteById(Integer id) {
        Boolean resultDelete = Boolean.TRUE;
        
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            Class.forName(Config.JDBC_DRIVER);

            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);

            String querysString = "DELETE FROM building WHERE id = ?";
            
            ps = con.prepareStatement(querysString);
            ps.setInt(1, id);
            
            Integer effectRow = ps.executeUpdate();

            if (effectRow == 0) {
                resultDelete = Boolean.FALSE;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            resultDelete = Boolean.FALSE;
        }
        finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return resultDelete;
    }

    @Override
    public Building save(Building building) {
        Connection con = null;
        PreparedStatement ps = null;

        Building buildingReturn = null;

        try {
            Class.forName(Config.JDBC_DRIVER);

            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);

            String querysString = "";
            
            if (building.getId() == null) {
                /** insert process */
                
                querysString = "INSERT INTO building ("
                        + "name, " //1
                        + "address, " //2
                        + "tel, " //3
                        + "electricity_meter_digit, " //4
                        + "electricity_charge_per_unit, " //5
                        + "min_electricity_unit, " //6
                        + "min_electricity_charge, " //7
                        + "water_meter_digit, " //8
                        + "water_charge_per_unit, " //9
                        + "min_water_unit, " //10
                        + "min_water_charge) " //11
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            } else {
                /** update process */
                
                querysString = "UPDATE building SET "
                        + "name = ?, " //1
                        + "address = ?, " //2
                        + "tel = ?, " //3
                        + "electricity_meter_digit = ?, " //4
                        + "electricity_charge_per_unit = ?, " //5
                        + "min_electricity_unit = ?, " //6
                        + "min_electricity_charge = ?, " //7
                        + "water_meter_digit = ?, " //8
                        + "water_charge_per_unit = ?, " //9
                        + "min_water_unit = ?, " //10
                        + "min_water_charge = ? " //11
                        + "WHERE id = ?"; //12
            }
            
            ps = con.prepareStatement(querysString, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, building.getName());
            ps.setString(2, building.getAddress());
            ps.setString(3, building.getTel());
            ps.setInt(4, building.getElectricityMeterDigit());
            ps.setBigDecimal(5, building.getElectricityChargePerUnit());
            
            if(building.getMinElectricityUnit() == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(6, building.getMinElectricityUnit());
            }

            ps.setBigDecimal(7, building.getMinElectricityCharge());
            ps.setInt(8, building.getWaterMeterDigit());
            ps.setBigDecimal(9, building.getWaterChargePerUnit());
            
            if(building.getMinWaterUnit() == null) {
                ps.setNull(10, java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(10, building.getMinWaterUnit());
            }
            
            ps.setBigDecimal(11, building.getMinWaterCharge());
            
            if (building.getId() != null) {
                /** insert process */
                
                ps.setInt(12, building.getId());
            }

            Integer effectRow = ps.executeUpdate();
            
            if (effectRow != 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                buildingReturn = new Building();
                
                if (building.getId() == null) {
                    /** insert process */
                    
                    if (generatedKeys.next()) {
                        buildingReturn.setId(generatedKeys.getInt(1));
                    }
                }
                else {
                    /** update process */
                    
                    buildingReturn.setId(building.getId());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (con != null) {
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
