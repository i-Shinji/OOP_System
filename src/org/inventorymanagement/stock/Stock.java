
package org.inventorymanagement.stock;

import javafx.scene.control.Button;

public class Stock {
    String id;
    String name;
    String category;
    String quantity;
    

    public Stock(String id, String name, String quantity, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
    }
    
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getQuantity() {
        return quantity;
    }
    
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    

  
}
