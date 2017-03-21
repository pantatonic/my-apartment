package my.apartment.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.apartment.model.Users;
import my.common.Config;

public class LoginDaoImpl implements LoginDao {
    
    //Add salt
    /*private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }*/
    
    private static String get_SHA_1_SecurePassword(String passwordToHash/*, byte[] salt*/) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            /*md.update(salt);*/
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
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
            
            String pwd = user.getPassword();
           // byte[] salt = getSalt();
           
            String securePassword = get_SHA_1_SecurePassword(pwd);
            //System.out.println(securePassword.compareTo(x));
            
            ps = con.prepareStatement(stringQuery);
            ps.setString(1, user.getEmail());
            ps.setString(2, securePassword);
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
