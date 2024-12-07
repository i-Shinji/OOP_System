
package org.inventorymanagement.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.inventorymanagement.data.DBConnect;
import org.inventorymanagement.stock.Stock;
import org.inventorymanagement.utils.StockUtil;


public class DashboardAddStockController implements Initializable {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCategory;
    @FXML
    private JFXButton enter;
    @FXML
    private JFXButton enter1;
    
    
    
    
    String query = null;
    Connection connection = null;
    PreparedStatement st = null;
    ResultSet resultSet = null;
    Stock stock = null;
    
    private ObservableList<Stock> stockList = FXCollections.observableArrayList();
    @FXML
    private TextField txtQuantity;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    public void performClickClose(ActionEvent event) throws IOException {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardInventory.fxml"));
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
        }
    
    @FXML
    private void save(MouseEvent event) {
        
        connection = DBConnect.getConnect();
        String name = txtName.getText();
        String quantity = txtQuantity.getText();
        String category = txtCategory.getText();
        
        if (name.isEmpty()  || quantity.isEmpty() || category.isEmpty()) {
        // Show a notification if any field is empty
        Notifications.create()
                .title("Error")
                .text("All fields must be filled!")
                .position(Pos.CENTER)
                .hideAfter(Duration.seconds(3))
                .showError();
        return;
    } else {
            StockUtil.getQuery();
            StockUtil.insert();
            
          StockUtil.clearFields();

        }
        
    }
    

    
}
