
package org.inventorymanagement.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class DashboardAboutController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void performClickGeneralInventory(ActionEvent event) throws IOException {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardInvGeneral.fxml"));
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
        }
    
    public void performClickDashboard(ActionEvent event) throws IOException {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardHome.fxml"));
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
        }
    
    public void performClickAbout(ActionEvent event) throws IOException {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardAbout.fxml"));
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
        }
    
    public void performClickLogout(ActionEvent event) throws IOException {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/loginView.fxml"));
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
        }

     
    
}
