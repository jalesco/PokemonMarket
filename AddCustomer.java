/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addcustomer.model;

/**
 *
 * @author patrickizawa
 */
public class AddCustomer {
    private String id;
    private String fName;
    private String lName;
    private String email;
    private String type;
    private String phone;
    private String address;
    
    public AddCustomer() {  
    }
    
    public AddCustomer(String id, String fName, String lName, String email, String type, String phone, String address) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.type = type;
        this.phone = phone;
        this.address = address;
    }
    
    public String getID() {
        return id;
    }
    
    public void setID(String id) {
        this.id = id;
    }
    
    public String getfName() {
        return fName;
    }
    
    public void setfName(String fName) {
        this.fName = fName;
    }
    
    public String getlName() {
        return lName;
    }
    
    public void setlName(String lName) {
        this.lName = lName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
}
