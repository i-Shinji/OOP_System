
package org.inventorymanagement.data;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.inventorymanagement.controller.DashboardHomeController;


public class FXMLloader {
    
    private Pane view;
    
    public Pane getPage(String fileName) {
        try {
            URL fileUrl = FXMLLoader.load(getClass().getResource("/org/inventorymanagement/view/"+fileName+".fxml"));
            
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }
            
            view = new FXMLLoader().load(fileUrl);
        } catch (Exception e) {
            System.out.println("No page" + fileName);
        }
        return view;
    }

    
}
