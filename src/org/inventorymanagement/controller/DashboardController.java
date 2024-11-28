
package org.inventorymanagement.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.inventorymanagement.stock.Stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.inventorymanagement.data.DBConnect;


public class DashboardController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtCategory;
    @FXML
    private JFXButton reset;

    
    @FXML
    private TableView<Stock> stockTable;
    @FXML
    private TableColumn<Stock, String> colId;
    @FXML
    private TableColumn<Stock, String> colName;
    @FXML
    private TableColumn<Stock, String> colPrice;
    @FXML
    private TableColumn<Stock, String> colDescription;
    @FXML
    private TableColumn<Stock, String> colCategory;
    
    String query = null;
    Connection connection = null;
    PreparedStatement st = null;
    ResultSet resultSet = null;
    Stock stock = null;
    
    private ObservableList<Stock> stockList = FXCollections.observableArrayList();
    @FXML
    private JFXButton enter;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }   
    
    @FXML
    private void refreshTable() {
        try {
            stockList.clear();
            
            query = "SELECT * FROM `stock`";
            st = connection.prepareCall(query);
            resultSet = st.executeQuery();
            
            while (resultSet.next()) {
                stockList.add(new Stock(
                        resultSet.getString("id"), 
                        resultSet.getString("name"), 
                        resultSet.getString("price"),
                        resultSet.getString("description"), 
                        resultSet.getString("category")));
                
                stockTable.setItems(stockList);
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void logout(MouseEvent event) {
    }

    private void loadData() {
        connection = DBConnect.getConnect();
        refreshTable();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }
    
    

    @FXML
    private void save(MouseEvent event) {
        
        connection = DBConnect.getConnect();
        String name = txtName.getText();
        String price = txtPrice.getText();
        String description = txtDescription.getText();
        String category = txtCategory.getText();
        
        if (name.isEmpty() || price.isEmpty() || description.isEmpty() || category.isEmpty()) {
        // Show a notification if any field is empty
        Notifications.create()
                .title("Error")
                .text("All fields must be filled!")
                .position(Pos.CENTER)
                .hideAfter(Duration.seconds(3))
                .showError();
        return;
    } else {
            getQuery();
            insert();
            
          clearFields();

        }
        
    }
    
    private void clearFields() {
        txtName.setText("");
        txtPrice.setText("");
        txtDescription.setText("");
        txtCategory.setText("");
    }

    private void insert() {
        
        try {
            
            st = connection.prepareStatement(query);
            st.setString(1, null);
            st.setString(2, txtName.getText());
            st.setString(3, txtPrice.getText());
            st.setString(4, txtDescription.getText());
            st.setString(5, txtCategory.getText());
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

    private void getQuery() {
        String name = txtName.getText();
        String price = txtPrice.getText();
        String description = txtDescription.getText();
        String category = txtCategory.getText();
        
        
        query = "INSERT INTO `stock`(`id`, `name`, `price`, `description`, `category`)"+ "VALUES (?,?,?,?,?)";
    }
    
}
