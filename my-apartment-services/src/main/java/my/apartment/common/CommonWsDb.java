package my.apartment.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CommonWsDb {
    
    public static Boolean optimizeTable(Connection con, PreparedStatement ps, String tableName) {
        Boolean result = Boolean.TRUE;
        try {
            ps = con.prepareStatement("OPTIMIZE TABLE " + tableName);
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(CommonWsDb.class.getName()).log(Level.SEVERE, null, ex);
            
            result = Boolean.FALSE;
        }
        
        return result;
    }
    
    public static String getNowDateTimeString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        return dateFormat.format(new Date());
    }
    
    public static String getNowDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        return dateFormat.format(new Date());
    }
    
    public static Boolean getBooleanFromInt(Integer integer) throws Exception {
        if(null != integer) switch (integer) {
            case 0:
                return Boolean.FALSE;
            case 1:
                return Boolean.TRUE;
            default:
                throw new Exception("Input must 0 or 1 only");
        }
        
        return null;
    }
    
    public static void closeFinally(PreparedStatement ps, Connection con, String className) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(className).log(Level.SEVERE, null, ex);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(className).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static Connection getDbConnection() throws ClassNotFoundException, SQLException {
        Class.forName(Config.JDBC_DRIVER);
        
        Connection con = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
        
        return con;
    }
    
}
