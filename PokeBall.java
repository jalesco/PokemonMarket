package pokemonmartjdbc;

import java.util.*;
import java.sql.*;
import static pokemonmartjdbc.Customer.sc;

public class PokeBall extends Product {
    private int catchRate;
    
    //Variables for database access
    static Scanner sc = new Scanner (System.in);
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String DB_URL = "jdbc:derby://localhost:1527/PokeMart";
    //  Database credentials (log-in to the database)
    private static final String USER = "jdbc";
    private static final String PASS = "12345";
    static Statement stmt = null;
    
    public PokeBall(String name, String ballID, String ballDescription, int ballPrice, int ballQuantity, int catchRate) {
        super(name, ballID, ballDescription, ballPrice, ballQuantity);
        this.catchRate = catchRate;        
    }//end Pokeball
    
    /**
     * This method is in charge of adding a Poke Ball to the "Poke Ball" table.
     * Steps: Get the user's input for the poke ball, add the appropriate information to the Product table, add the new Poke Ball to the Poke Ball table
     */
    public static void AddPokeBall(Connection conn) {
        String name, id, description;
        int price, quantity, catchRate;
        
        PreparedStatement pstmt = null;                
        String sql = null;
        try {
            //PROMPTS TO ASK FOR USER INPUT
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            sc.nextLine();
            System.out.println("Enter the name of the poke ball: ");
            name = sc.nextLine();
            System.out.println("Enter the id of the pokeball: ");
            id = sc.nextLine();
            System.out.println("Enter a short description of the poke ball: ");
            description = sc.nextLine();
            System.out.println("Enter the price: ");
            price = sc.nextInt();
            System.out.println("Enter the quantity in stock: ");
            quantity = sc.nextInt();
            System.out.println("Enter the catch rate of the pokeball: ");
            catchRate = sc.nextInt();
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
            sql = "INSERT INTO pokeball VALUES(?,?,?,?,?,?)";         
            pstmt = conn.prepareStatement(sql);                
            pstmt.setString(1, name);
            pstmt.setString(2, id);
            pstmt.setString(3, description);
            pstmt.setInt(4, price);
            pstmt.setInt(5, quantity);
            pstmt.setInt(6, catchRate);
            pstmt.executeUpdate();         
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }                                 
    }//end AddPokeball()
    
    /**
     * 
     */
    public static void ModifyPokeBall(Connection conn) {
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);            
            PreparedStatement pstmt = null;      
            
            String name, id, description;
            int price, quantity, catchRate;
            
            String oldName = null, oldDescription = null;
            int oldPrice = 0, oldQuantity = 0, oldCatchRate = 0;
            String sql = null;   
            ResultSet rs = null;
            int menu = 0;          
            //Retrieve customer to be modified
            sc.nextLine();
            System.out.println("Enter the ID of the poke ball to modify: ");
            id = sc.nextLine();
            sql = "SELECT * FROM pokeball WHERE ballid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
        
            if(rs.next() == false){
                System.out.println("Product does not exist");
                return;
            }else {
                do {
                    System.out.println("Product retrieved: ");
                    System.out.println("Poke Ball: "+rs.getString("name"));
                    System.out.println("Product ID: " + rs.getString("ballID"));
                    System.out.println("Description: " + rs.getString("balldescription"));
                    System.out.println("Price: : " + rs.getInt("price"));
                    System.out.println("Quantity in Stock: " + rs.getInt("quantitystock"));
                    System.out.println("Email: " + rs.getInt("catchrate"));
                    
                    oldName = rs.getString("name");
                    oldDescription = rs.getString("balldescription");
                    oldPrice = rs.getInt("price");
                    oldQuantity = rs.getInt("quantitystock");
                    oldCatchRate = rs.getInt("catchrate");
                    
                }while(rs.next());
            }//end else 
            System.out.println("Modify Menu: ");
            System.out.println("1) Product name" + "\n2) Ball Description" + "\n3) Price" + "\n4) Quantity" + "\n5) Catch Rate");
            menu = sc.nextInt();
            switch(menu) {
                case 1:
                    sc.nextLine();
                    System.out.println("Enter new product name: ");
                    name = sc.nextLine();
                    sql = "UPDATE pokeball SET name = ? where name = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setString(2, oldName);
                    pstmt.executeUpdate();
                    System.out.println("Product name changed.");
                    
                    //Update Product table for data integrity
                    sql = "UPDATE product SET name=? where name=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setString(2, oldName);
                    pstmt.executeUpdate();                                                          
                    System.out.println("Poke Ball name changed.");
                    break;
                case 2:
                    sc.nextLine();
                    System.out.println("Enter new description: ");
                    description = sc.nextLine();
                    sql = "UPDATE pokeball SET balldescription = ? where balldescription = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, description); //new last name
                    pstmt.setString(2, oldDescription);
                    pstmt.executeUpdate();
                    System.out.println("Ball description changed.");
                    
                    sql = "UPDATE product SET description=? where description=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, description);
                    pstmt.setString(2, oldDescription);
                    pstmt.executeUpdate();                                                          
                    System.out.println("Poke Ball description changed.");
                    break;
                case 3:
                    sc.nextLine();
                    System.out.println("Enter new price: ");
                    price = sc.nextInt();
                    sql = "UPDATE pokeball SET price = ? where price = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, price); //new phone number
                    pstmt.setInt(2, oldPrice);
                    pstmt.executeUpdate();
                    System.out.println("Price changed.");
                    
                    //Update product table for data integrity
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
                    sql = "UPDATE pokeball SET quantitystock = ? where quantitystock = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, quantity); //new zipcode
                    pstmt.setInt(2, oldQuantity);
                    pstmt.executeUpdate();
                    System.out.println("Quantity changed.");
                    
                    //Update product table for data integrity
                    sql = "UPDATE product SET quantitystock=? where quantitystock=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, quantity);
                    pstmt.setInt(2, oldQuantity);
                    pstmt.executeUpdate();                                                          
                    System.out.println("Quantity changed.");
                    break;
                case 5:
                    sc.nextLine();
                    System.out.println("Enter new catch rate: ");
                    catchRate = sc.nextInt();
                    sql = "UPDATE pokeball SET catchrate = ? where catchrate = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, catchRate); //new phone number
                    pstmt.setInt(2, oldCatchRate);
                    pstmt.executeUpdate();
                    System.out.println("Catch rate changed.");                                 
                    break;            
            }
            
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        
    }//end ModifyPokeBall
    
    public static void RemovePokeBall(Connection conn) {
        PreparedStatement pstmt = null;                
        String inputID;
        String sql = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            sc.nextLine();
            System.out.println("Enter the ID of the poke ball product to remove: ");
            inputID = sc.nextLine();            
            sql = "DELETE FROM pokeball where ballid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputID);          
            pstmt.executeUpdate();
            
            sql = "DELETE FROM product where pid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputID);
            pstmt.executeUpdate();
            
            System.out.println("Poke Ball product deleted.");
                       
               
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        
    
    }//end RemovePokeBall
    
    
    
    
}//end class Pokeball
