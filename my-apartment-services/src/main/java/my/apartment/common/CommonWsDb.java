package my.apartment.common;

import java.sql.Connection;
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
    
}
