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
public class Potion extends Product {
    //Variables for a Potion
    String potionID, potionName, potionDescription;
    int potionPrice, potionQuantity, healAmount;
    
    //Database Credentials    
    static Scanner sc = new Scanner (System.in);
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String DB_URL = "jdbc:derby://localhost:1527/PokeMart";
    //  Database credentials (log-in to the database)
    private static final String USER = "jdbc";
    private static final String PASS = "12345";
    static Statement stmt = null;
    
    
    //Constructor for Potion
    public Potion(String name, String pID, String description, int price, int quantity, int healAmount) {
        super(name, pID, description, price, quantity); //values inherited from product
        this.healAmount = healAmount; //healAmount is specific for Potion    
    }//end Potion constructor
    
    public static void AddPotion(Connection conn) {
        String name, id, description;
        int price, quantity, healAmount;
        
        PreparedStatement pstmt = null;                
        String sql = null;
        try {
            //PROMPTS TO ASK FOR USER INPUT
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            sc.nextLine();
            System.out.println("Enter the id of the potion: ");
            id = sc.nextLine();
            System.out.println("Enter the name of the potion: ");
            name = sc.nextLine();
            System.out.println("Enter a short description of the potion: ");
            description = sc.nextLine();
            System.out.println("Enter the price: ");
            price = sc.nextInt();
            System.out.println("Enter the quantity in stock: ");
            quantity = sc.nextInt();
            System.out.println("Enter the amount of HP a potion heals for: ");
            healAmount = sc.nextInt();
            //will enter a new list that's empty for a new customer.
            
            //INSERT INTO PRODUCT TABLE FOR DATA INTEGRITY
            sql = "INSERT INTO product VALUES (?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, id);
            pstmt.setString(3, description);
            pstmt.setInt(4, price);
            pstmt.setInt(5, quantity);
            pstmt.executeUpdate();
            
            //INSERT THE DATA INTO POKEBALL TABLE
            sql = "INSERT INTO potion VALUES(?,?,?,?,?,?)";         
            pstmt = conn.prepareStatement(sql);                
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, description);
            pstmt.setInt(4, price);
            pstmt.setInt(5, quantity);
            pstmt.setInt(6, healAmount);
            pstmt.executeUpdate();         
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }                   
    }//end AddPotion
    
    public static void ModifyPotion(Connection conn) {
         try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);            
            PreparedStatement pstmt = null;      
            
            String name, id, description;
            int price, quantity, healAmount;
            
            String oldName = null, oldDescription = null;
            int oldPrice = 0, oldQuantity = 0, oldHealAmount = 0;
            String sql = null;   
            ResultSet rs = null;
            int menu = 0;  
            
            //Retrieve customer to be modified
            sc.nextLine();
            System.out.println("Enter the ID of the potion to modify: ");
            id = sc.nextLine();
            sql = "SELECT * FROM potion WHERE pid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
        
            if(rs.next() == false){
                System.out.println("Product does not exist");
                return;
            }else {
                do {
                    System.out.println("Potion Product retrieved: ");
                    System.out.println("Potion ID: "+rs.getString("pid"));
                    System.out.println("Potion name: " + rs.getString("potionname"));
                    System.out.println("Potion Description: " + rs.getString("potiondescription"));
                    System.out.println("Price: : " + rs.getInt("potionprice"));
                    System.out.println("Quantity in Stock: " + rs.getInt("potionquantity"));
                    System.out.println("Heal Amount: " + rs.getInt("healamount"));
                    
                    //Assign all the data to different variables. These are used later on when modifiying
                    oldName = rs.getString("potionname");
                    oldDescription = rs.getString("potiondescription");
                    oldPrice = rs.getInt("potionprice");
                    oldQuantity = rs.getInt("potionquantity");
                    oldHealAmount = rs.getInt("healamount");
                    
                }while(rs.next());
            }//end else 
            System.out.println("Modify Menu: ");
            System.out.println("1) Potion name" + "\n2) Potion Description" + "\n3) Price" + "\n4) Quantity" + "\n5) Heal Amount");
            menu = sc.nextInt();
            switch(menu) {
                case 1:
                    sc.nextLine();
                    System.out.println("Enter new product name: ");
                    name = sc.nextLine();
                    sql = "UPDATE potion SET potionname = ? where potionname = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setString(2, oldName);
                    pstmt.executeUpdate();
                    
                    //Update product table for data integrity
                    sql = "UPDATE product SET name=? where name=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setString(2, oldName);
                    pstmt.executeUpdate();                                      
                    System.out.println("Potion name changed.");
                    break;
                case 2:
                    sc.nextLine();
                    System.out.println("Enter new description: ");
                    description = sc.nextLine();
                    sql = "UPDATE potion SET potiondescription = ? where potiondescription = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, description); //new last name
                    pstmt.setString(2, oldDescription);
                    pstmt.executeUpdate();
                    
                    //Update product's description on this potion for data integrity
                    sql = "UPDATE product SET description=? where description=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, description);
                    pstmt.setString(2, oldDescription);
                    pstmt.executeUpdate();                                    
                    System.out.println("Ball description changed.");
                    break;
                case 3:
                    sc.nextLine();
                    System.out.println("Enter new price: ");
                    price = sc.nextInt();
                    sql = "UPDATE potion SET potionprice = ? where potionprice = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, price); //new phone number
                    pstmt.setInt(2, oldPrice);
                    pstmt.executeUpdate();
                    
                    sql = "UPDATE product SET price=? where price=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, price);
                    pstmt.setInt(2, oldPrice);
                    pstmt.executeUpdate();
                    
                    System.out.println("Price changed.");
                    break;
                case 4:
                    sc.nextLine();
                    System.out.println("Enter new quantity: ");
                    quantity = sc.nextInt();
                    sql = "UPDATE potion SET potionquantity = ? where potionquantity = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, quantity); //new zipcode
                    pstmt.setInt(2, oldQuantity);
                    pstmt.executeUpdate();
                    
                    sql = "UPDATE product SET quantitystock=? where quantitystock=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, quantity);
                    pstmt.setInt(2, oldQuantity);
                    pstmt.executeUpdate();                                                          
                    System.out.println("Quantity changed.");
                    break;
                case 5:
                    sc.nextLine();
                    System.out.println("Enter new heal amount: ");
                    healAmount = sc.nextInt();
                    sql = "UPDATE potion SET healamount = ? where healamount = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, healAmount); //new phone number
                    pstmt.setInt(2, oldHealAmount);
                    pstmt.executeUpdate();                  
                    break;            
            }
            
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }                                                       
    }//end ModifyPotion
    
    public static void RemovePotion(Connection conn) {
        
        PreparedStatement pstmt = null;                
        String inputID;
        String sql = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            sc.nextLine();
            System.out.println("Enter the ID of the poke ball product to remove: ");
            inputID = sc.nextLine();            
            sql = "DELETE FROM potion where pid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputID);          
            pstmt.executeUpdate();
            
            sql = "DELETE FROM product where pid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputID);
            pstmt.executeUpdate();
            
            System.out.println("Potion product deleted.");
                       
               
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    
    
    }//end RemovePotion
    
    
}//end class Potion
