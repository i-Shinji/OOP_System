package org.inventorymanagement.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;
import org.inventorymanagement.data.DBConnect;
import org.inventorymanagement.stock.Stock;
import org.inventorymanagement.utils.TableUtil;

public class DashboardCategoryController implements Initializable {

    // A column in the table to display stock categories
    @FXML
    private TableColumn<Stock, String> colCategory;

    // The table where stock data is displayed
    @FXML
    private TableView<Stock> stockTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // This method runs when the screen (FXML) is loaded

        // Load stock data into the table using a helper utility
        TableUtil.loadData(stockTable, colCategory);
        // Note: The helper utility handles database fetching and linking data to the table
    } 

    // Method to switch to the "General Inventory" screen
    @FXML
    public void performClickGeneralInventory(ActionEvent event) throws IOException {
        // Get the current stage (window) and load the new screen
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardInvGeneral.fxml"));
        Scene scene = new Scene(root);

        // Set the new scene to the stage and display it
        stage.setScene(scene);
        stage.show();
    }

    // Method to switch to the "Dashboard Home" screen
    @FXML
    public void performClickDashboard(ActionEvent event) throws IOException {
        // Load and display the dashboard home screen
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardHome.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    // Method to switch to the "About" screen
    @FXML
    public void performClickAbout(ActionEvent event) throws IOException {
        // Load and display the about screen
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardAbout.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    // Method to switch to the login screen (logout)
    @FXML
    public void performClickLogout(ActionEvent event) throws IOException {
        // Load and display the login screen
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/loginView.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}