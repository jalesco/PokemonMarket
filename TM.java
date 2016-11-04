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
public class TM extends Product{
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/SeniorProject";

    //  Database credentials
    static final String USER = "jdbc";
    static final String PASS = "12345";
    static Statement stmt = null;
    static ResultSet rs = null;
    
    private String ability; 
    
    public TM(String pName, String pID, String pDescription, int pPrice, int quanitityStock) {
        super(pName, pID, pDescription, pPrice, quanitityStock);
        this.ability = ability;
    }
    public static void addTM(Connection conn) {
        String inputName, inputID, inputDescription,inputAbility;
        int inputPrice, inputQuantityStock;
        Scanner input = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        
        PreparedStatement pstmt = null;                
        String sql = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Enter the name of the TM: ");
            inputName = input.nextLine();
            System.out.println("Enter the id of the TM: ");
            inputID = input.nextLine();
            System.out.println("Enter the TM description: ");
            inputDescription = input.nextLine();
            System.out.println("Enter the price: ");
            inputPrice = sc.nextInt();
            System.out.println("Enter the quantity in stock: ");
            inputQuantityStock = sc.nextInt();
            System.out.println("Enter the type of TM: ");
            inputAbility = input.nextLine();
            
            sql = "INSERT INTO product VALUES (?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputName);
            pstmt.setString(2, inputID);
            pstmt.setString(3, inputDescription);
            pstmt.setInt(4, inputPrice);
            pstmt.setInt(5, inputQuantityStock);
            pstmt.executeUpdate();
            
            sql = "INSERT INTO tm VALUES(?,?,?,?,?,?)";         
            pstmt = conn.prepareStatement(sql);                
            pstmt.setString(1, inputName);
            pstmt.setString(2, inputID);
            pstmt.setString(3, inputDescription);
            pstmt.setInt(4, inputPrice);
            pstmt.setInt(5, inputQuantityStock);
            pstmt.setString(6, inputAbility);
            pstmt.executeUpdate();         
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }                                 
    }//end addTM
    public static void removeTM(Connection conn) {
        String inputID;
        Scanner input = new Scanner(System.in);
        
        PreparedStatement pstmt = null;                
        String sql = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Enter the ID of the TM you want to remove: ");
            inputID = input.nextLine();
            
            sql = "delete from tm where tmid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputID);
            pstmt.executeUpdate();
            
            sql = "delete from product where pid = ?";         
            pstmt = conn.prepareStatement(sql);                
            pstmt.setString(1, inputID);
            pstmt.executeUpdate();         
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }                                 
    }//end removeTM
    public static void modifyTM(Connection conn){// modifyTM
        
        PreparedStatement pstmt;
        String inputName,inputID,inputDescription,inputAbility;
        int inputPrice,inputQuantityStock;
        Scanner input = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        
        try {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        int menu = 0;

        System.out.println("Please Enter The ID of the TM You Wish To Modify: ");
        inputID = input.nextLine();
        String sql = "select * from tm where tmid = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,inputID);
        rs = pstmt.executeQuery();
        
        if(rs.next()== false ){
            System.out.println("TM does not exist");
        }else{
            do{
            System.out.println();
            System.out.println("TM Name: " + rs.getString("name"));
            System.out.println("TM ID: "+ rs.getString("tmid"));
            System.out.println("Medicine Description: "+rs.getString("description"));
            System.out.println("Price: "+rs.getInt("price"));
            System.out.println("Quantity Stock: "+rs.getInt("quantitystock"));
            System.out.println("Medicine Type: "+rs.getString("ability"));

        } while(rs.next());
            
            System.out.println("What Do You Want to Modify:");
        System.out.println("1. TM Name");
        System.out.println("2. TM Description");
        System.out.println("3. TM Price");
        System.out.println("4. TM Quantity Stock");
        System.out.println("5. TM Type");
        menu = input.nextInt();
        
        if(menu == 1){
         System.out.println("What do you want to change the name to?");
         input.nextLine();
         inputName = input.nextLine();
         sql = "update tm set name = ? where tmid = ?"; 
        
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
            sql = "update tm set description = ? where tmid = ?"; 
        
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
            sql = "update tm set price = ? where tmid = ?"; 
        
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
            sql = "update tm set quantitystock = ? where tmid = ?"; 
      
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
            inputAbility = input.nextLine();
            sql = "update tm set ability = ? where tmid = ?"; 
        
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setString(1, inputAbility);
            pstmt.setString(2, inputID);
            pstmt.executeUpdate();
        }
        }

        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }//end modifyTM
    public static void searchTM(Connection conn){// searchTM
        Scanner input = new Scanner(System.in);
        PreparedStatement pstmt;
        String inputID;
        try {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        Scanner in = new Scanner(System.in);

        System.out.println("Please Enter The ID of the TM You're Looking For: ");
        inputID = input.nextLine();
        String sql = "select * from tm where tmid = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,inputID);
        rs = pstmt.executeQuery();
        
        if(rs.next()== false ){
            System.out.println("TM does not exist");
        }else{
            do{
            System.out.println();
            System.out.println("TM Name: " + rs.getString("name"));
            System.out.println("TM ID: "+ rs.getString("tmid"));
            System.out.println("TM Description: "+rs.getString("description"));
            System.out.println("Price: "+rs.getInt("price"));
            System.out.println("Quantity Stock: "+rs.getInt("quantitystock"));
            System.out.println("TM Ability: "+rs.getString("ability"));
        } while(rs.next());
        }
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }//end searchTM
    
}
