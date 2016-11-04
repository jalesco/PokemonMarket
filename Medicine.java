/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonmartjdbc;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author stephanieperez
 */
public class Medicine extends Product{
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/SeniorProject";

    //  Database credentials
    static final String USER = "jdbc";
    static final String PASS = "12345";
    static Statement stmt = null;
    static ResultSet rs = null;
    
    private String typeOfMedicine;
    
    public Medicine(String pName, String pID, String pDescription, int pPrice, int quanitityStock) {
        super(pName, pID, pDescription, pPrice, quanitityStock);
        this.typeOfMedicine = typeOfMedicine;
    }
    public static void addMedicine(Connection conn) {
        String inputName, inputID, inputDescription,inputType;
        int inputPrice, inputQuantityStock;
        Scanner input = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        
        PreparedStatement pstmt = null;                
        String sql = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Enter the name of the medicine: ");
            inputName = input.nextLine();
            System.out.println("Enter the id of the medicine: ");
            inputID = input.nextLine();
            System.out.println("Enter the medicine description: ");
            inputDescription = input.nextLine();
            System.out.println("Enter the price: ");
            inputPrice = sc.nextInt();
            System.out.println("Enter the quantity in stock: ");
            inputQuantityStock = sc.nextInt();
            System.out.println("Enter the type of medicine: ");
            inputType = input.nextLine();
            
            sql = "INSERT INTO product VALUES (?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputName);
            pstmt.setString(2, inputID);
            pstmt.setString(3, inputDescription);
            pstmt.setInt(4, inputPrice);
            pstmt.setInt(5, inputQuantityStock);
            pstmt.executeUpdate();
            
            sql = "INSERT INTO medicine VALUES(?,?,?,?,?,?)";         
            pstmt = conn.prepareStatement(sql);                
            pstmt.setString(1, inputName);
            pstmt.setString(2, inputID);
            pstmt.setString(3, inputDescription);
            pstmt.setInt(4, inputPrice);
            pstmt.setInt(5, inputQuantityStock);
            pstmt.setString(6, inputType);
            pstmt.executeUpdate();         
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }                                 
    }//end addMedicine
    public static void removeMedicine(Connection conn) {
        String inputID;
        Scanner input = new Scanner(System.in);
        
        PreparedStatement pstmt = null;                
        String sql = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Enter the ID of the medicine you want to remove: ");
            inputID = input.nextLine();
            
            sql = "delete from medicine where mid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputID);
            pstmt.executeUpdate();
            
            sql = "delete product where pid = ?";         
            pstmt = conn.prepareStatement(sql);                
            pstmt.setString(1, inputID);
            pstmt.executeUpdate();         
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }                                 
    }//end removeMedicine
    public static void modifyMedicine(Connection conn){// modifyMedicine
        
        PreparedStatement pstmt;
        String inputName,inputID,inputDescription,inputType;
        int inputPrice,inputQuantityStock;
        Scanner input = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        
        try {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        Scanner in = new Scanner(System.in);
        int menu = 0;

        System.out.println("Please Enter The ID of the Medicine You Wish To Modify: ");
        inputID = input.nextLine();
        String sql = "select * from medicine where mid = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,inputID);
        rs = pstmt.executeQuery();
        
        if(rs.next()== false ){
            System.out.println("Medicine does not exist");
        }else{
            do{
            System.out.println();
            System.out.println("Medicine Name: " + rs.getString("name"));
            System.out.println("Medicine ID: "+ rs.getString("mid"));
            System.out.println("Medicine Description: "+rs.getString("mdescription"));
            System.out.println("Price: "+rs.getInt("price"));
            System.out.println("Quantity Stock: "+rs.getInt("quantitystock"));
            System.out.println("Medicine Type: "+rs.getString("mtype"));

        } while(rs.next());
            
            System.out.println("What Do You Want to Modify:");
        System.out.println("1. Medicine Name");
        System.out.println("2. Medicine Description");
        System.out.println("3. Medicine Price");
        System.out.println("4. Medicine Quantity Stock");
        System.out.println("5. Medicine Type");
        menu = input.nextInt();
        
        if(menu == 1){
         System.out.println("What do you want to change the name to?");
         input.nextLine();
         inputName = input.nextLine();
         sql = "update medicine set name = ? where mid = ?"; 
        
        pstmt = conn.prepareStatement(sql);
         
        
        pstmt.setString(1, inputName);
        pstmt.setString(2, inputID);
        pstmt.executeUpdate();
        
        sql= "update product set name = ? where pid = ?";
        pstmt = conn.prepareStatement(sql);
         
        
        pstmt.setString(1, inputName);
        pstmt.setString(2, inputID);
        pstmt.executeUpdate();
        
        } else if(menu == 2){
            System.out.println("What do you want to change the description to?");
            input.nextLine();
            inputDescription = input.nextLine();
            sql = "update medicine set mdescription = ? where mid = ?"; 
        
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setString(1, inputDescription);
            pstmt.setString(2, inputID);
            pstmt.executeUpdate();
            
            sql = "update product set description = ? where pid = ?"; 
        
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setString(1, inputDescription);
            pstmt.setString(2, inputID);
            pstmt.executeUpdate();
        }else if(menu == 3){
            System.out.println("What do you want to change the price to?");
            inputPrice = sc.nextInt();
            sql = "update medicine set price = ? where mid = ?"; 
        
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setInt(1, inputPrice);
            pstmt.setString(2, inputID);
            pstmt.executeUpdate();
            
            sql = "update product set price = ? where pid = ?"; 
        
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setInt(1, inputPrice);
            pstmt.setString(2, inputID);
            pstmt.executeUpdate();
        }else if(menu == 4){
            System.out.println("What do you want to change the quantity stock to?");
            inputQuantityStock = sc.nextInt();
            sc.nextLine();
            sql = "update medicine set quantitystock = ? where mid = ?"; 
      
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setInt(1, inputQuantityStock);
            pstmt.setString(2, inputID);
            pstmt.executeUpdate();
            
            sql = "update product set quantitystock = ? where pid = ?"; 
      
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setInt(1, inputQuantityStock);
            pstmt.setString(2, inputID);
            pstmt.executeUpdate();
        } else if(menu == 5){
            System.out.println("What do you want to change the type to?");
            input.nextLine();
            inputType = input.nextLine();
            sql = "update medicine set mtype = ? where mid = ?"; 
        
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setString(1, inputType);
            pstmt.setString(2, inputID);
            pstmt.executeUpdate();
        }
        }

        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }//end modifyMedicine
    public static void searchMedicine(Connection conn){// searchMedicine
        
        PreparedStatement pstmt;
        String inputID;
        try {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        Scanner in = new Scanner(System.in);

        System.out.println("Please Enter The ID of the Medicine You Want To Display: ");
        inputID = in.nextLine();
        String sql = "select * from medicine where mid = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,inputID);
        rs = pstmt.executeQuery();
        
        if(rs.next()== false ){
            System.out.println("Medicine does not exist");
        }else{
            do{
            System.out.println();
            System.out.println("Medicine Name: " + rs.getString("name"));
            System.out.println("Medicine ID: "+ rs.getString("mid"));
            System.out.println("Medicine Description: "+rs.getString("mdescription"));
            System.out.println("Price: "+rs.getInt("price"));
            System.out.println("Quantity Stock: "+rs.getInt("quantitystock"));
            System.out.println("Medicine Type: "+rs.getString("mtype"));

        } while(rs.next());
        }
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }//end searchMedicine
}
