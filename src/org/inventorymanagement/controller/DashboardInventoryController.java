package org.inventorymanagement.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.inventorymanagement.data.DBConnect;
import org.inventorymanagement.stock.Stock;
import org.inventorymanagement.utils.TableUtil;

public class DashboardInventoryController implements Initializable {

    private ObservableList<Stock> stockList = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Stock> stockTable;
    
    @FXML
    private TableColumn<Stock, String> colId;
    
    @FXML
    private TableColumn<Stock, String> colCategory;
    
    @FXML
    private TableColumn<Stock, String> colQuantity;
    
    @FXML
    private TableColumn<Stock, String> colName;
    
    @FXML
    private TextField searchFilter;
    
    @FXML
    private AnchorPane mainPane;
    
    String query = null;
    Connection connection = null;
    PreparedStatement st = null;
    ResultSet resultSet = null;
    Stock stock = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeTableEditable();
        TableUtil.loadData(stockTable, colId, colName, colQuantity, colCategory);
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
    public void performClickLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/loginView.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void performClickAddStock(ActionEvent event) throws IOException {
        Stage stage = new Stage(StageStyle.DECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardAddStock.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
        stage.setResizable(false); // Disable window resizing
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void performClickRefreshTable(ActionEvent event) throws IOException {
        TableUtil.refreshTable(stockTable);
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
} 