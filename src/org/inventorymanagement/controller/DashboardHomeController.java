/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.inventorymanagement.controller;

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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.inventorymanagement.data.DBConnect;
import org.inventorymanagement.data.FXMLloader;
import org.inventorymanagement.stock.Stock;


public class DashboardHomeController implements Initializable {

    @FXML
    private TableColumn<Stock, String> colId;
    @FXML
    private TableColumn<Stock, String> colQuantity;
    @FXML
    private TableColumn<Stock, String> colCategory;
    @FXML
    private TableColumn<Stock, String> colActions;
    @FXML
    private TableColumn<Stock, String> colName;
    @FXML
    private TableView<Stock> stockTable;
    
    String query = null;
    Connection connection = null;
    PreparedStatement st = null;
    ResultSet resultSet = null;
    Stock stock = null;
    private ObservableList<Stock> stockList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }
    



    @FXML
    public void performClickInventory(ActionEvent event) throws IOException {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardInventory.fxml"));
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
        }
    
    @FXML
    public void performClickDashboard(ActionEvent event) throws IOException {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardHome.fxml"));
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
        }
    
    @FXML
    public void performClickAbout(ActionEvent event) throws IOException {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardAbout.fxml"));
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
        }
    
    @FXML
    public void performClickLogout(ActionEvent event) throws IOException {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/loginView.fxml"));
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
        }
    
    
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
                        resultSet.getString("quantity"), 
                        resultSet.getString("category")));
                
                stockTable.setItems(stockList);
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadData() {
        connection = DBConnect.getConnect();
        refreshTable();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }
    }
    