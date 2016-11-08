/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seniorproject;

import java.sql.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 *
 * @author Richard
 */
public class SeniorProject {
   static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
   static final String DB_URL = "jdbc:derby://localhost:1527/SeniorProject";

   //  Database credentials
   static final String USER = "jdbc";
   static final String PASS = "12345";
   static Connection conn = null;
   
   //Universal SQL holders
   static Statement stmt;
   static PreparedStatement pstmt;
   static ResultSet rs;

   //SQL commands
   static String GET_CUSTOMERS = "Select * from products orderby pID";
   static String GET_EMPLOYEES = "Select * from employee";
   static Scanner sc = new Scanner (System.in);
   static Employee usr;
   public static void main(String [] args) {
       try {
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        System.out.println("Connected succesfully");
        while ( !(login()))
            JOptionPane.showMessageDialog(null, "Incorrect username or password");
        JOptionPane.showMessageDialog(null, "Welcome " + usr.getFName() + " " +usr.getLName());
        boolean cont = true;
        while(cont) {
            select();
        }

       }
       
       catch (SQLException se) {
           se.printStackTrace();
           //goto main
       }
   }
   
   
   
    private static boolean login () throws SQLException {
       String username = JOptionPane.showInputDialog(null, "Enter username");
       String password = JOptionPane.showInputDialog(null, "Enter password");
       pstmt = conn.prepareStatement("Select *from employee where username = ?"
               + " AND password = ?");
       pstmt.setString(1, username);
       pstmt.setString(2, password);
       rs = pstmt.executeQuery();
       if (rs.next()) {
           usr = new Employee (rs.getString("fname"), rs.getString("lname"),
           rs.getString("username"), rs.getString("password"), rs.getString("eID"), 
           rs.getBoolean("tier"));
           return true;
       }
       else 
           return rs.next();
   }
   
    private static void select () throws SQLException {
                Object[] options = {"Customers", "Products", "Users"};
        int n = JOptionPane.showOptionDialog(null, "What would you like to do?", "Pokemart (Beta)"
                , JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[2]);
        switch(n) {
            case 0:
                customers();
        }
    }
    
    private static void customers() throws SQLException {
        Object [] options = {"Add Customer", "Modify Customer", "Remove Customer"
        , "View Customers", "Cancel"};
        int n = JOptionPane.showOptionDialog(null, "Customers", "Pokemart (Beta)"
                    , JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[2]);    
        switch (n) {
            case (2):
                String ID = JOptionPane.showInputDialog(null, "enter ID to remove");
                pstmt = conn.prepareStatement("Select fname, lname from customer where cid = ?");
                pstmt.setString(1, ID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    //this will not work at the moment. Remember for consistency
                    //that you have to delete the dependent entities as well.
                    JOptionPane.showMessageDialog(null, "Remove " + rs.getString("fname") + " " + rs.getString("lname"));
                    pstmt = conn.prepareStatement("delete from customer where cid = ?");
                    pstmt.setString(1, ID);
                    pstmt.executeUpdate();
                }
                break;

                
            
            case (3):
                stmt = conn.createStatement();
                rs = stmt.executeQuery("Select fname, lname, cid from customer");
                while (rs.next()) {
                    System.out.println(rs.getString("fname") + " " + rs.getString("lname")
                    + " " + rs.getString("cid"));
                }
                break;
            case (4):
                select();
                break;
        }
    }
}