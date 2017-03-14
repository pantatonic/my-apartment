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
import my.apartment.model.Users;
import my.common.CommonString;
import my.common.Config;
import org.json.JSONObject;

public class LoginDaoImpl implements LoginDao {
    
    @Override
    public List<Users> loginProcess(Users user) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Users> userList = new ArrayList<Users>();
        
        try {
            Class.forName(Config.JDBC_DRIVER);
            
            con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            
            String stringQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
            
            ps = con.prepareStatement(stringQuery);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            rs = ps.executeQuery();
            
            if(rs.next()) {
                Users userReturn = new Users();
                
                userReturn.setId(rs.getInt("id"));
                userReturn.setEmail(rs.getString("email"));
                userReturn.setFirstname(rs.getString("firstname"));
                userReturn.setLastname(rs.getString("lastname"));
                userReturn.setIsAdmin(rs.getInt("is_admin"));
                userReturn.setStatus(rs.getInt("status"));
                
                userList.add(userReturn);
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
        
        return userList;
    }
    
}
