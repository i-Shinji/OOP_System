
package org.inventorymanagement.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.inventorymanagement.stock.Stock;


public class StockUtil {
    
    static TextField txtName = new TextField();
    static TextField txtCategory = new TextField();
    static TextField txtQuantity = new TextField();
    
    
    static String query = null;
    static Connection connection = null;
    static PreparedStatement st = null;
    static ResultSet resultSet = null;
    static Stock stock = null;
    
    
    public static void clearFields() {
        txtName.setText("");
        txtQuantity.setText("");
        txtCategory.setText("");
    }
    
    public static void insert() {
        
        try {
            
            st = connection.prepareStatement(query);
            st.setString(1, null);
            st.setString(2, txtName.getText());
            st.setString(3, txtQuantity.getText());
            st.setString(4, txtCategory.getText());
            st.execute();
            
                Notifications.create()
                .title("Success")
                .text("Stock added successfully!")
                .position(Pos.CENTER)
                .hideAfter(Duration.seconds(3))
                .showConfirm();
                
                
            
        }catch (SQLException ex) {
                Notifications.create()
                .title("Error")
                .text("Failed to add stock. Check database connection.")
                .position(Pos.CENTER)
                .hideAfter(Duration.seconds(3))
                .showError();
        }
        
    }
    
    public static void getQuery() {
        String name = txtName.getText();
        String quantity = txtQuantity.getText();
        String category = txtCategory.getText();
        
        
        query = "INSERT INTO `stock`(`id`, `name`, `quantity`, `category`)"+ "VALUES (?,?,?,?)";
    }
}
