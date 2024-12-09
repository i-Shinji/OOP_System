package org.inventorymanagement.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
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

public class DashboardAddStockController implements Initializable {

    // Fields from the UI (text fields for input and buttons)
    @FXML
    private TextField txtName; // Text field to input the stock name
    @FXML
    private TextField txtCategory; // Text field to input the stock category
    @FXML
    private JFXButton enter; // Button to trigger saving stock
    @FXML
    private JFXButton enter1; // (Unused in this code snippet)
    @FXML
    private TextField txtQuantity; // Text field to input the stock quantity

    // Variables for database operations
    String query = null; // Holds the SQL query
    Connection connection = null; // Connection to the database
    PreparedStatement st = null; // Used to execute SQL queries
    ResultSet resultSet = null; // Stores data retrieved from the database
    Stock stock = null; // Represents a stock item
    private ObservableList<Stock> stockList = FXCollections.observableArrayList(); // Holds a list of stock items

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // This method runs automatically when the screen (FXML) is loaded
        // Nothing specific to initialize here
    }

    // Method to close the current window
    @FXML
    public void performClickClose(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current window
        stage.close(); // Close the window
    }

    // Method triggered when the "Save" button is clicked
    @FXML
    private void save(MouseEvent event) {
        connection = DBConnect.getConnect(); // Connect to the database

        // Retrieve values entered by the user
        String name = txtName.getText();
        String quantity = txtQuantity.getText();
        String category = txtCategory.getText();

        // Check if any field is empty
        if (name.isEmpty() || quantity.isEmpty() || category.isEmpty()) {
            // Show an error notification if fields are empty
            Notifications.create()
                    .title("Error")
                    .text("All fields must be filled!")
                    .position(Pos.CENTER)
                    .hideAfter(Duration.seconds(3))
                    .showError();
            return; // Stop further execution
        } else {
            // If all fields are filled, proceed with saving the stock
            getQuery(); // Prepare the SQL query
            insert(); // Insert the stock into the database
            clearFields(); // Clear input fields for the next entry
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current window
            stage.close(); // Close the window
        }
    }

    // Method to execute the SQL query and insert the stock into the database
    private void insert() {
        try {
            st = connection.prepareStatement(query); // Prepare the SQL query
            st.setString(1, null); // Automatically generate the stock ID
            st.setString(2, txtName.getText()); // Set the stock name
            st.setString(3, txtQuantity.getText()); // Set the stock quantity
            st.setString(4, txtCategory.getText()); // Set the stock category
            st.execute(); // Execute the query to insert the stock

            // Show a success notification
            Notifications.create()
                    .title("Success")
                    .text("Stock added successfully!")
                    .position(Pos.CENTER)
                    .hideAfter(Duration.seconds(3))
                    .showConfirm();
        } catch (SQLException ex) {
            // Show an error notification if the database operation fails
            Notifications.create()
                    .title("Error")
                    .text("Failed to add stock. Check database connection.")
                    .position(Pos.CENTER)
                    .hideAfter(Duration.seconds(3))
                    .showError();
        }
    }

    // Method to prepare the SQL query for inserting stock data
    private void getQuery() {
        query = "INSERT INTO `stock`(`id`, `name`, `quantity`, `category`) VALUES (?,?,?,?)";
        // This query inserts a new stock record into the database table
    }

    // Method to clear the input fields after saving the stock
    private void clearFields() {
        txtName.setText(""); // Clear the name field
        txtQuantity.setText(""); // Clear the quantity field
        txtCategory.setText(""); // Clear the category field
    }
}