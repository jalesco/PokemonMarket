/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonmartjdbc;

import java.sql.*;
import java.util.Scanner;
import static pokemonmartjdbc.Customer.conn;

/**
 *
 * @author stephanieperez
 */
public class Product {
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/SeniorProject";

    //  Database credentials
    static final String USER = "jdbc";
    static final String PASS = "12345";
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    static Scanner input = new Scanner(System.in);
    
    private String name, productID, description;
    private double price;
    private int quanitityStock;
    
    public Product(String pName, String pID, String pDescription, double pPrice, int quanitityStock){
        name = pName;
        productID = pID;
        description = pDescription;
        price = pPrice;
        this.quanitityStock = quanitityStock;
    }// end constructor
    
    public String getName(){
        return name;
    }
    
    public String getProductID(){
        return productID;
    }
    
    public double getPrice(){
        return price;
    }
    
    public int getQuanitityStock(){
        return quanitityStock;
    }
    
    public static void addProduct(){
        PreparedStatement pstmt;
        //String sql = null;
        
        String inputName, inputID, inputDescription, inputPrice, inputQuantityStock;
        //List <String> newOrderHistory = null;
        
        try {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
            
        //input.nextLine();
        System.out.println("Enter the name of the product: ");
        inputName = input.nextLine();
        System.out.println("Enter the product id: ");
        inputID = input.nextLine();
        System.out.println("Enter the product description: ");
        inputDescription = input.nextLine();
        System.out.println("Enter the product price: ");
        inputPrice = input.nextLine();
        System.out.println("Enter the quantity stock of the product: ");
        inputQuantityStock = input.nextLine();
        String sql = "INSERT INTO product VALUES(?,?,?,?,?)"; 
        
        pstmt = conn.prepareStatement(sql);
         
        
        pstmt.setString(1, inputName);
        pstmt.setString(2, inputID);
        pstmt.setString(3, inputDescription);
        pstmt.setString(4, inputPrice);
        pstmt.setString(5, inputQuantityStock);
        pstmt.executeUpdate();
        
        
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }//end addProduct
    public static void removeProduct(){
        PreparedStatement pstmt;
        
        String inputID;        
        try {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        Scanner in = new Scanner(System.in);
        int menu = 0;

        System.out.println("Please Enter The ID of the Product You Wish To Remove: ");
        inputID = input.nextLine();
        String sql = "delete from product where pid = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,inputID);
        pstmt.executeUpdate();
        
        
        
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }//end removeProduct
    public static void modifyProduct(){// modifyProduct
        
        PreparedStatement pstmt;
        String inputName,inputID,inputDescription,inputPrice,inputQuantityStock;
        try {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        Scanner in = new Scanner(System.in);
        int menu = 0;
        String oldName = null;
        String oldDescription = null;
        String oldPrice = null;
        String oldQuantityStock = null;        

        System.out.println("Please Enter The ID of the Product You Wish To Modify: ");
        inputID = input.nextLine();
        String sql = "select * from product where pid = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,inputID);
        rs = pstmt.executeQuery();
        
        if(rs.next()== false ){
            System.out.println("Product does not exist");
        }else{
            do{
            System.out.println();
            System.out.println("Product Name: " + rs.getString("name"));
            System.out.println("Product ID: "+ rs.getString("pid"));
            System.out.println("Product Description: "+rs.getString("description"));
            System.out.println("Price: "+rs.getString("price"));
            System.out.println("Quantity Stock: "+rs.getString("quantitystock"));
            
            oldName = rs.getString("name");
            oldDescription = rs.getString("description");
            oldPrice = rs.getString("price");
            oldQuantityStock = rs.getString("quantitystock");            
        } while(rs.next());
        }
       
        
        
        System.out.println("What Do You Want to Modify:");
        System.out.println("1. Product Name");
        System.out.println("2. Product Description");
        System.out.println("3. Product Price");
        System.out.println("4. Product Quantity Stock");
        menu = input.nextInt();
        
        if(menu == 1){
         System.out.println("What do you want to change the name to?");
         input.nextLine();
         inputName = input.nextLine();
         sql = "update customer set name = ? where name = ?"; 
        
        pstmt = conn.prepareStatement(sql);
         
        
        pstmt.setString(1, inputName);
        pstmt.setString(2, oldName);
        pstmt.executeUpdate();
        
        } else if(menu == 2){
            System.out.println("What do you want to change the description to?");
            input.nextLine();
            inputDescription = input.nextLine();
            sql = "update product set description = ? where description = ?"; 
        
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setString(1, inputDescription);
            pstmt.setString(2, oldDescription);
            pstmt.executeUpdate();
        }else if(menu == 3){
            System.out.println("What do you want to change the price to?");
            input.nextLine();
            inputPrice = input.nextLine();
            sql = "update product set price = ? where price = ?"; 
        
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setString(1, inputPrice);
            pstmt.setString(2, oldPrice);
            pstmt.executeUpdate();
        }else if(menu == 4){
            System.out.println("What do you want to change the quantity stock to?");
            input.nextLine();
            inputQuantityStock = input.nextLine();
            sql = "update product set quantitystock = ? where quantitystock = ?"; 
      
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setString(1, inputQuantityStock);
            pstmt.setString(2, oldQuantityStock);
            pstmt.executeUpdate();
        }
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }finally {
           try {
           if(stmt != null) {
               stmt.close();
           }
           
           if(conn != null) {
               conn.close();
           }
           }catch(SQLException sqle2) {
               sqle2.printStackTrace();
           }
       
       }
    }//end modifyProduct
    public static void searchProduct(){// searchProduct
        
        PreparedStatement pstmt;
        String inputName,inputID,inputDescription,inputPrice,inputQuantityStock;
        try {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        Scanner in = new Scanner(System.in);
        int menu = 0;
        String oldName = null;
        String oldDescription = null;
        String oldPrice = null;
        String oldQuantityStock = null;        

        System.out.println("Please Enter The ID of the Product You Wish To Modify: ");
        inputID = input.nextLine();
        String sql = "select * from product where pid = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,inputID);
        rs = pstmt.executeQuery();
        
        if(rs.next()== false ){
            System.out.println("Product does not exist");
        }else{
            do{
            System.out.println();
            System.out.println("Product Name: " + rs.getString("name"));
            System.out.println("Product ID: "+ rs.getString("pid"));
            System.out.println("Product Description: "+rs.getString("description"));
            System.out.println("Price: "+rs.getString("price"));
            System.out.println("Quantity Stock: "+rs.getString("quantitystock"));
            
            oldName = rs.getString("name");
            oldDescription = rs.getString("description");
            oldPrice = rs.getString("price");
            oldQuantityStock = rs.getString("quantitystock");            
        } while(rs.next());
        }
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }finally {
           try {
           if(stmt != null) {
               stmt.close();
           }
           
           if(conn != null) {
               conn.close();
           }
           }catch(SQLException sqle2) {
               sqle2.printStackTrace();
           }
       
       }
    }//end searchProduct
    
}
