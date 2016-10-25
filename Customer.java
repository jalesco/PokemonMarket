/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonmartjdbc;

import java.util.*;
import java.sql.*;
import static pokemonmartjdbc.PokemonMartJDBC.conn;

/**
 *
 * @author jalex_000
 */



public class Customer extends Person {
    private String type;
    private List <String> orderHistory;
    private static Connection conn = null;
    
    static Scanner input = new Scanner(System.in);
    
    public Customer(String cID, String cFName, String cLName, String cEmail, String cType, List <String> cOrderHistory) {
        super(cID, cFName, cLName, cEmail);
        type = cType;
        orderHistory = cOrderHistory;
    }//end customer
    
    public String getCustomerType() {return type;}
    
    public List<String> orderHistory() {return orderHistory;}
    
    public static void AddCustomer() {
        PreparedStatement pstmt = null;
        //String sql = null;
        
        String inputID, inputFName, inputLName, inputPhone, inputEmail, inputZipCode;
        //List <String> newOrderHistory = null;
        
        try {
            
        input.nextLine();
        System.out.println("Enter the first name of the customer: ");
        inputFName = input.nextLine();
        System.out.println("Enter the last name of the customer: ");
        inputLName = input.nextLine();
        System.out.println("Enter the ID of the customer: ");
        inputID = input.nextLine();
        System.out.println("Enter the phone number: ");
        inputPhone = input.nextLine();
        System.out.println("Enter the zipcode of customer: ");
        inputZipCode = input.nextLine();
        System.out.println("Enter the email of the customer: ");
        inputEmail = input.nextLine();
        //will enter a new list that's empty for a new customer.
        String sql = "INSERT INTO customer VALUES(?,?,?,?,?,?)"; 
        
        pstmt = conn.prepareStatement(sql);
         
        
        pstmt.setString(1, inputFName);
        pstmt.setString(2, inputLName);
        pstmt.setString(3, inputID);
        pstmt.setString(4, inputPhone);
        pstmt.setString(5, inputZipCode);
        pstmt.setString(6, inputEmail);
        pstmt.executeUpdate();
        
        
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        
        
        
        
        
        
        
        
        
               
        
        
        
    
    }//end AddCustomer
    
    
    
}//end Customers
