
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
        refreshTable(stockTable);
    }
}
