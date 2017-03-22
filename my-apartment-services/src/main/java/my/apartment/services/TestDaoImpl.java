package my.apartment.services;

import java.math.BigInteger;
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
import my.apartment.model.Test;
import my.apartment.common.Config;


public class TestDaoImpl implements TestDao {
    
    @Override
    public List<Test> getAllTests() {               
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        
        List<Test> tests = new ArrayList<Test>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
         
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            statement = con.createStatement();
            
            String queryString = "SELECT * FROM test";
            
            rs = statement.executeQuery(queryString);
            
            while(rs.next()) {
                Test test = new Test();
                
                BigInteger id = new BigInteger(rs.getObject("id").toString());
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                
                test.setId(id);
                test.setName(name);
                test.setSurname(surname);
                
                tests.add(test);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TestDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TestDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return tests;
    }
    
    @Override
    public List<Test> getTestById(BigInteger id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Test> tests = new ArrayList<Test>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String queryString = "SELECT * FROM test WHERE id = ?";
            
            ps = con.prepareStatement(queryString);
            ps.setInt(1, Integer.parseInt(id.toString()));
            rs = ps.executeQuery();
            if(rs.next()) {
                Test test = new Test();
                
                test.setId(id);
                test.setName(rs.getString("name"));
                test.setSurname(rs.getString("surname"));
                
                tests.add(test);
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
                    Logger.getLogger(TestDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TestDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return tests;
    }
    
}
