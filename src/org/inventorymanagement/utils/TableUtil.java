
package org.inventorymanagement.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.inventorymanagement.data.DBConnect;
import org.inventorymanagement.stock.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.util.Callback;

public class TableUtil {

    
    public static ObservableList<Stock> refreshTable(TableView<Stock> stockTable) {
        ObservableList<Stock> stockList = FXCollections.observableArrayList();
        String query = "SELECT * FROM `stock`";
        try (Connection connection = DBConnect.getConnect();
             PreparedStatement st = connection.prepareCall(query);
             ResultSet resultSet = st.executeQuery()) {

            while (resultSet.next()) {
                stockList.add(new Stock(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("quantity"),
                        resultSet.getString("category")));
            }
            stockTable.setItems(stockList);

        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle or log the error appropriately
        }
        return stockList;
    }
    
        public static void load(TableView<Stock> stockTable) {
        ObservableList<Stock> stockList = FXCollections.observableArrayList();
        String query = "SELECT * FROM `stock`";

        // Use a Set to track unique categories
        Set<String> uniqueCategories = new HashSet<>();

        try (Connection connection = DBConnect.getConnect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String category = resultSet.getString("category");

                // Only add the stock item if the category is not already in the set
                if (!uniqueCategories.contains(category)) {
                    uniqueCategories.add(category); // Mark category as added
                    stockList.add(new Stock(
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("quantity"),
                            category
                    ));
                }
            }

            // Update the TableView with unique categories
            stockTable.setItems(stockList);

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as appropriate
        }
    }
    
    

    public static void loadData(TableView<Stock> stockTable,
                            TableColumn<Stock, String> colId,
                            TableColumn<Stock, String> colName,
                            TableColumn<Stock, String> colQuantity,
                            TableColumn<Stock, String> colCategory) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        refreshTable(stockTable);
}

    public static void loadData(TableView<Stock> stockTable, TableColumn<Stock, String> colCategory) {
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        load(stockTable);
    }

    // Method to load data into the TableView (for stock items filtered by category)
    public static void loadDataForCategory(TableView<Stock> stockTable,
                                       TableColumn<Stock, String> colId,
                                       TableColumn<Stock, String> colName,
                                       TableColumn<Stock, String> colQuantity,
                                       TableColumn<Stock, String> colCategory,
                                       String category) {
    ObservableList<Stock> stockList = FXCollections.observableArrayList();
    String query = "SELECT * FROM `stock` WHERE category = ?";
    try (Connection connection = DBConnect.getConnect();
         PreparedStatement st = connection.prepareStatement(query)) {
        st.setString(1, category); // Pass the category parameter
        ResultSet resultSet = st.executeQuery();

        while (resultSet.next()) {
            stockList.add(new Stock(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("quantity"),
                    resultSet.getString("category")));
        }
        stockTable.setItems(stockList);

    } catch (SQLException ex) {
        ex.printStackTrace(); // Handle or log the error appropriately
    }
}
    
    // Method to show items based on category (this is used to show only the items for the selected category)
    public static void showItemsByCategory(TableView<Stock> stockTable,
                                       TableColumn<Stock, String> colId,
                                       TableColumn<Stock, String> colName,
                                       TableColumn<Stock, String> colQuantity,
                                       TableColumn<Stock, String> colCategory,
                                       String category) {
    // Set the cell value factories
    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

    // Load data for the given category
    loadDataForCategory(stockTable, colId, colName, colQuantity, colCategory, category);
}


    

    
  
    
    
    
    
    

}
