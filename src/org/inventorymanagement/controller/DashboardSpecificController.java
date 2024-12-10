/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.inventorymanagement.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.inventorymanagement.data.DBConnect;
import org.inventorymanagement.stock.Stock;
import org.inventorymanagement.utils.TableUtil;

/**
 * FXML Controller class
 *
 * @author User
 */
public class DashboardSpecificController implements Initializable {

    @FXML
    private TableView<Stock> stockTable;
    @FXML
    private TableColumn<Stock, String> colCategory;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TableColumn<Stock, String> colId;
    @FXML
    private TableColumn<Stock, String> colName;
    @FXML
    private TableColumn<Stock, String
            > colQuantity;
    
        // Property to store the selected category
    private Stock selectedCategory;
    @FXML
    private Label item;

    
    
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        
        makeTableEditable();
        
    }
        

    @FXML
    public void performClickDashboard(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardHome.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show(); 
    }

    @FXML
    public void performClickAbout(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardAbout.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void performClickGeneralInventory(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardInvGeneral.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void performClickLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/loginView.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    
    private void makeTableEditable() {
        stockTable.setEditable(true); // Enable editing for the TableView

        // Make the Name column editable
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit(event -> {
            Stock stock = event.getRowValue();
            stock.setName(event.getNewValue());

            String query = "UPDATE stock SET name = ? WHERE id = ?";
            try (Connection connection = DBConnect.getConnect();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, event.getNewValue());
                statement.setString(2, stock.getId());
                statement.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            stockTable.refresh();
        });

        // Make the Quantity column editable
        colQuantity.setCellFactory(TextFieldTableCell.forTableColumn());
        colQuantity.setOnEditCommit(event -> {
            Stock stock = event.getRowValue();
            stock.setQuantity(event.getNewValue());

            String query = "UPDATE stock SET quantity = ? WHERE id = ?";
            try (Connection connection = DBConnect.getConnect();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, event.getNewValue());
                statement.setString(2, stock.getId());
                statement.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            stockTable.refresh();
        });

        // Make the Category column editable
        colCategory.setCellFactory(TextFieldTableCell.forTableColumn());
        colCategory.setOnEditCommit(event -> {
            Stock stock = event.getRowValue();
            stock.setCategory(event.getNewValue());

            String query = "UPDATE stock SET category = ? WHERE id = ?";
            try (Connection connection = DBConnect.getConnect();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, event.getNewValue());
                statement.setString(2, stock.getId());
                statement.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            stockTable.refresh();
        });
    }




    @FXML
    void removeStock(ActionEvent event) {
        // Get the selected item
        Stock selectedStock = stockTable.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            // Get the ID of the selected stock
            String selectedID = selectedStock.getId();

            // Remove the item from the TableView
            stockTable.getItems().remove(selectedStock);

            // Remove the item from the database
            String query = "DELETE FROM stock WHERE id = ?";
            try (Connection connection = DBConnect.getConnect();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, selectedID);
                statement.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace(); // Log or handle the error appropriately
            }
        } else {
            // Handle the case where no item is selected
            System.out.println("No stock selected for removal.");
        }
    }
    
        // Method to set the selected category
    public void setSelectedCategory(Stock selectedCategory) {
        this.selectedCategory = selectedCategory;

        // Populate the table with items from the selected category
        if (this.selectedCategory != null) {
            String category = selectedCategory.getCategory(); // Assuming getCategory() exists
            TableUtil.showItemsByCategory(stockTable, colId, colName, colQuantity, colCategory, category);
        }
    }
    

    
}
