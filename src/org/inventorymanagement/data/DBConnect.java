
package org.inventorymanagement.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnect {
    private static String DB_URL = "jdbc:mysql://localhost:3306/stockinventory";
    private static String USERNAME = "root";
    private static String PASSWORD = ""; 
    private static Connection connection;
    
    public static Connection getConnect() {
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
                
}
