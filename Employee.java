/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonmartjdbc;

import java.sql.*;
import java.util.Scanner;
import static pokemonmartjdbc.Employee.conn;

/**
 *
 * @author stephanieperez
 */
public class Employee extends Person{
   static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
   static final String DB_URL = "jdbc:derby://localhost:1527/SeniorProject";

   //  Database credentials
   static final String USER = "jdbc";
   static final String PASS = "12345";
   static Connection conn = null;
   static Statement stmt = null;
   static Scanner input = new Scanner(System.in);
   static ResultSet rs = null;
   
   private String username;
   private String password;
   private boolean tier;
   
   

    public Employee(String FName, String LName, String ID, String Email, String uName, String password, boolean tier) {
        super(FName, LName, ID, Email);
        username = uName;
        this.password = password;
        this.tier = tier;
    }
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public boolean getTier(){
        return tier;
    }
 
    public static void addEmployee() {
        PreparedStatement pstmt;
        //String sql = null;
        
        String inputID, inputFName, inputLName, inputUsername, inputPassword, inputTier;
        //List <String> newOrderHistory = null;
        
        try {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
            
        //input.nextLine();
        System.out.println("Enter the first name of the employee: ");
        inputFName = input.nextLine();
        System.out.println("Enter the last name of the employee: ");
        inputLName = input.nextLine();
        System.out.println("Enter the ID of the employee: ");
        inputID = input.nextLine();
        System.out.println("Enter the username of the employee: ");
        inputUsername = input.nextLine();
        System.out.println("Enter the password of employee: ");
        inputPassword = input.nextLine();
        System.out.println("Enter the tier of employee: ");
        inputTier = input.nextLine();
        //will enter a new list that's empty for a new employee.
        String sql = "INSERT INTO employee VALUES(?,?,?,?,?,?)"; 
        
        pstmt = conn.prepareStatement(sql);
         
        
        pstmt.setString(1, inputFName);
        pstmt.setString(2, inputLName);
        pstmt.setString(3, inputID);
        pstmt.setString(4, inputUsername);
        pstmt.setString(5, inputPassword);
        pstmt.setString(6, inputTier);
        pstmt.executeUpdate();
        
        
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }//end AddEmployee
    public static void removeEmployee(){
        PreparedStatement pstmt;
        //String sql = null;
        
        String inputID;
        //List <String> newOrderHistory = null;
        
        try {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        Scanner in = new Scanner(System.in);
        int menu = 0;

        System.out.println("Please Enter The ID of the Employee You Wish To Remove: ");
        inputID = input.nextLine();
        String sql = "delete from employee where eid = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,inputID);
        pstmt.executeUpdate();
        
        
        
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }//end removeEmployee
    public static void modifyEmployee(){// modifyEmployee
        
        PreparedStatement pstmt;
        String inputID,inputfName,inputlName,inputUsername,inputPassword;
        boolean inputTier;
        try {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        Scanner in = new Scanner(System.in);
        int menu = 0;
        String oldfName = null;
        String oldlName = null;
        String oldUsername = null;
        String oldPassword = null;
        boolean oldTier = false;
        

        System.out.println("Please Enter The ID of the Employee You Wish To Modify: ");
        inputID = input.nextLine();
        String sql = "select * from employee where eid = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,inputID);
        rs = pstmt.executeQuery();
        
        if(rs.next()== false ){
            System.out.println("Employee does not exist");
        }else{
            do{
            System.out.println();
            System.out.println("First Name: " + rs.getString("fname"));
            System.out.println("Last Name: "+ rs.getString("lname"));
            System.out.println("Employee ID: "+rs.getString("eid"));
            System.out.println("Username: "+rs.getString("username"));
            System.out.println("Password: "+rs.getString("password"));
            System.out.println("Tier: "+ rs.getBoolean("tier"));
            
            oldfName = rs.getString("fname");
            oldlName = rs.getString("lname");
            oldUsername = rs.getString("username");
            oldPassword = rs.getString("password");
            oldTier = rs.getBoolean("tier");
            
        } while(rs.next());
        }
       
        
        
        System.out.println("What Do You Want to Modify:");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Username");
        System.out.println("4. Password");
        System.out.println("5. Tier");
        menu = input.nextInt();
        
        if(menu == 1){
         System.out.println("What do you want to change the name to?");
         input.nextLine();
         inputfName = input.nextLine();
         sql = "update employee set fname = ? where fname = ?"; 
        
        pstmt = conn.prepareStatement(sql);
         
        
        pstmt.setString(1, inputfName);
        pstmt.setString(2, oldfName);
        pstmt.executeUpdate();
        
        } else if(menu == 2){
            System.out.println("What do you want to change the last name to?");
            input.nextLine();
            inputlName = input.nextLine();
            sql = "update employee set lname = ? where lname = ?"; 
        
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setString(1, inputlName);
            pstmt.setString(2, oldlName);
            pstmt.executeUpdate();
        }else if(menu == 3){
            System.out.println("What do you want to change the username to?");
            input.nextLine();
            inputUsername = input.nextLine();
            sql = "update employee set username = ? where username = ?"; 
        
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setString(1, inputUsername);
            pstmt.setString(2, oldUsername);
            pstmt.executeUpdate();
        }else if(menu == 4){
            System.out.println("What do you want to change the password to?");
            input.nextLine();
            inputPassword = input.nextLine();
            sql = "update employee set password = ? where password = ?"; 
      
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setString(1, inputPassword);
            pstmt.setString(2, oldPassword);
            pstmt.executeUpdate();
        }else if (menu == 5){
            System.out.println("What do you want to change the tier to?");
            input.nextLine();
            inputTier = input.nextBoolean();
            sql = "update employee set tier = ? where tier = ?"; 
        
            pstmt = conn.prepareStatement(sql);
         
        
            pstmt.setBoolean(1, inputTier);
            pstmt.setBoolean(2, oldTier);
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
    }
    public static void searchEmployee() {
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);            
            PreparedStatement pstmt = null; 
            String searchID = null;
            
            System.out.println("Enter the ID of the employee to search for: ");
            searchID = input.nextLine();
            String sql = "select * from employee WHERE eid=?";          
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,searchID);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next() == false) {
                System.out.println("Employee doesn't exist.");
            }else {
                do{
                    System.out.println("Employee retrieved: ");
                    System.out.println("First Name: "+rs.getString("fname"));
                    System.out.println("Last Name: " + rs.getString("lname"));
                    System.out.println("Employee ID: " + rs.getString("eid"));
                    System.out.println("Username: : " + rs.getString("username"));
                    System.out.println("Password: " + rs.getString("password"));
                    System.out.println("Tier: " + rs.getBoolean("tier"));                    
                }while(rs.next());
            }// end if/else                                                
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }//end try/catch
    
    }//end searchEmployee
  
}
