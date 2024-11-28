
package org.inventorymanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.inventorymanagement.data.FXMLloader;


public class LoginController implements Initializable {
    
    @FXML
    private ImageView imgLogo;
    
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ImageView imgBackground;
    @FXML
    private JFXButton login;
    @FXML
    private JFXCheckBox checkBox;
    @FXML
    private TextField showPassword;

    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image img = new Image("/org/inventorymanagement/image/icon1.gif");
        Image panel = new Image("/org/inventorymanagement/image/panel.gif");
        imgLogo.setImage(img);
        imgBackground.setImage(panel);
    } 
    
    @FXML
    private void login(javafx.scene.input.MouseEvent event) throws IOException{
        performLogin(event.getSource());
    
    }

    @FXML
    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                performLogin(txtPassword); // Trigger login logic
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

    
    private void performLogin(Object eventSource) throws IOException {
        if (txtUser.getText().equals("admin") && txtPassword.getText().equals("admin")) {
            Stage stage = new Stage();
            FXMLloader object = new FXMLloader();
            Parent root = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/DashboardHome.fxml"));
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
            ((Node) eventSource).getScene().getWindow().hide();
        } else {
            error();
        }
    }
        
    public void error() {
            Notifications notification = Notifications.create();
            notification.title("Error");
            notification.text("Username or password is incorrent. Please try Again");
            notification.hideAfter(Duration.seconds(5));
            notification.position(Pos.BASELINE_RIGHT);
            notification.show();        
    }

    @FXML
    private void showPassword(ActionEvent event) {
        if (checkBox.isSelected()) {
            // Show the password as plain text
            showPassword.setText(txtPassword.getText()); // Sync the password text
            showPassword.setVisible(true);
            showPassword.setManaged(true); // Ensures layout updates for TextField
            txtPassword.setVisible(false);
            txtPassword.setManaged(false); // Hides PasswordField properly
        } else {
            // Mask the password again
            txtPassword.setText(showPassword.getText()); // Sync the password text
            txtPassword.setVisible(true);
            txtPassword.setManaged(true);
            showPassword.setVisible(false);
            showPassword.setManaged(false);
        }       
    }
}



