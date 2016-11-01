/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonmartjdbc;

import java.util.*;
import java.sql.*;

/**
 *
 * @author jalex_000
 */



public class Customer extends Person {
    private String type;
    
    static Scanner sc = new Scanner (System.in);
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String DB_URL = "jdbc:derby://localhost:1527/PokeMart";
    //  Database credentials (log-in to the database)
    private static final String USER = "jdbc";
    private static final String PASS = "12345";
    static Statement stmt = null;
    
    /**
     Argument constructor for the class, Customer.
     * String cID: Inherited ID from Person
     * String cFname: Inherited first name from Person
     * String cLname: Inherited last name from Person
     
     */
    public Customer(String cID, String cFName, String cLName, String cEmail, String cType) {
        super(cID, cFName, cLName, cEmail);
        type = cType;
    }//end customer
    
    public String getCustomerType() {return type;}
    
    public static void AddCustomer(Connection conn) {
        PreparedStatement pstmt = null;                
        String inputID, inputFName, inputLName, inputPhone, inputEmail, inputZipCode;
        String sql = null;
        try {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        sc.nextLine();
        System.out.println("Enter the first name of the customer: ");
        inputFName = sc.nextLine();
        System.out.println("Enter the last name of the customer: ");
        inputLName = sc.nextLine();
        System.out.println("Enter the ID of the customer: ");
        inputID = sc.nextLine();
        System.out.println("Enter the phone number: ");
        inputPhone = sc.nextLine();
        System.out.println("Enter the zipcode of customer: ");
        inputZipCode = sc.nextLine();
        System.out.println("Enter the email of the customer: ");
        inputEmail = sc.nextLine();
        //will enter a new list that's empty for a new customer.
        
        sql = "INSERT INTO customer VALUES(?,?,?,?,?,?)";         
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
    
    public static void ModifyCustomer(Connection conn) throws SQLException{
        //SELECT * FROM CUSTOMER WHERE FIRSTNAME = 'Steph'
        
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);            
            PreparedStatement pstmt = null;                
            String inputID, inputFirst, inputLast, inputPhone, inputZip, inputEmail;
            String oldName = null, oldLName = null, oldPhone = null, oldZip = null, oldEmail = null;
            String sql = null;   
            ResultSet rs = null;
            int menu = 0;          
            //Retrieve customer to be modified
            System.out.println("Enter the ID of the customer to modify: ");
            inputID = sc.nextLine();            
            sql = "SELECT * FROM customer WHERE cid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputID);
            rs = pstmt.executeQuery();
        
            if(rs.next() == false){
                System.out.println("Customer does not exist");            
            }else {
                do {
                    System.out.println("Customer retrieved: ");
                    System.out.println("First Name: "+rs.getString("fname"));
                    System.out.println("Last Name: " + rs.getString("lname"));
                    System.out.println("Customer ID: " + rs.getString("cid"));
                    System.out.println("Phone Number: : " + rs.getString("cphone"));
                    System.out.println("Zip Code: " + rs.getString("czipcode"));
                    System.out.println("Email: " + rs.getString("cemail"));
                    
                    oldName = rs.getString("fname");
                    oldLName = rs.getString("lname");
                    oldPhone = rs.getString("cPhone");
                    oldZip = rs.getString("czipcode");
                    oldEmail = rs.getString("lname");
                    
                }while(rs.next());
            }//end else 
            
            System.out.println("1) Modify first name" + "\n2) Modify last name" + "\n3) Phone Number" + "\n4) Zip Code" + "\n5) Email");
            menu = sc.nextInt();
            switch(menu) {
                case 1:
                    sc.nextLine();
                    System.out.println("Enter new first name: ");
                    inputFirst = sc.nextLine();
                    sql = "UPDATE customer SET fname = ? where fname = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, inputFirst);
                    pstmt.setString(2, oldName);
                    pstmt.executeUpdate();
                    System.out.println("First name changed.");
                    break;
                case 2:
                    sc.nextLine();
                    System.out.println("Enter new last name: ");
                    inputLast = sc.nextLine();
                    sql = "UPDATE customer SET lname = ? where lname = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, inputLast); //new last name
                    pstmt.setString(2, oldLName);
                    pstmt.executeUpdate();
                    System.out.println("Last name changed.");
                    break;
                case 3:
                    sc.nextLine();
                    System.out.println("Enter new phone number: ");
                    inputPhone = sc.nextLine();
                    sql = "UPDATE customer SET cphone = ? where cphone = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, inputPhone); //new phone number
                    pstmt.setString(2, oldPhone);
                    pstmt.executeUpdate();
                    System.out.println("Phone number changed.");
                    break;
                case 4:
                    sc.nextLine();
                    System.out.println("Enter new zipcode: ");
                    inputZip = sc.nextLine();
                    sql = "UPDATE customer SET czipcode = ? where czipcode = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, inputZip); //new zipcode
                    pstmt.setString(2, oldZip);
                    pstmt.executeUpdate();
                    System.out.println("Zip code changed.");
                    break;
                case 5:
                    sc.nextLine();
                    System.out.println("Enter new email: ");
                    inputEmail = sc.nextLine();
                    sql = "UPDATE customer SET cemail = ? where cemail = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, inputEmail); //new phone number
                    pstmt.setString(2, oldEmail);
                    pstmt.executeUpdate();
                    System.out.println("Email changed.");
                    break;            
            }
            
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }                              
    }//end modify 
    
    
    public static void RemoveCustomer(Connection conn) {
        
        PreparedStatement pstmt = null;                
        String inputID;
        String sql = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            sc.nextLine();
            System.out.println("Enter the ID of the customer to remove: ");
            inputID = sc.nextLine();
            
            sql = "DELETE FROM customer where cid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputID);          
            pstmt.executeUpdate();
            System.out.println("Customer deleted.");
               
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        
    }//end RemoveCustomer
    
    public static void SearchCustomer(Connection conn) {
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);            
            PreparedStatement pstmt = null; 
            String searchID = null;
            
            sc.nextLine();
            System.out.println("Enter the ID of the customer to search for: ");
            searchID = sc.nextLine();
            String sql = "SELECT * FROM customer WHERE cid=?";          
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,searchID);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next() == false) {
                System.out.println("Customer doesn't exist.");
            }else {
                do{
                    System.out.println("Customer retrieved: ");
                    System.out.println("First Name: "+rs.getString("fname"));
                    System.out.println("Last Name: " + rs.getString("lname"));
                    System.out.println("Customer ID: " + rs.getString("cid"));
                    System.out.println("Phone Number: : " + rs.getString("cphone"));
                    System.out.println("Zip Code: " + rs.getString("czipcode"));
                    System.out.println("Email: " + rs.getString("cemail"));                    
                }while(rs.next());
            }// end if/else                                                
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }//end try/catch
    
    }//end SearchCustomer
    
    
    
}//end Customers
