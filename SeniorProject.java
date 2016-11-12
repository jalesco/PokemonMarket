/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seniorproject;
import java.awt.*;
import java.sql.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.*;

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
              case (0)://add (updated by Patrick)
                //Create JPanel to let user input data
                JPanel addPanel = new JPanel(new GridLayout(6,2));
                
                //Create JLabel and JFormattedTextField for each attribute
                JLabel fnameL = new JLabel("First Name:");
                JFormattedTextField firstName = new JFormattedTextField();
                firstName.setColumns(15);
                JLabel lnameL = new JLabel("Last Name:");
                JFormattedTextField lastName = new JFormattedTextField();
                lastName.setColumns(15);
                JLabel custIDL = new JLabel("Customer ID:");
                JFormattedTextField custID = new JFormattedTextField();
                custID.setColumns(10);
                JLabel phoneL = new JLabel("Phone Number:");
                JFormattedTextField phoneNum = new JFormattedTextField();
                phoneNum.setColumns(15);
                JLabel zipL = new JLabel("Zip Code:");
                JFormattedTextField zipcode = new JFormattedTextField();
                zipcode.setColumns(5);
                JLabel emailL = new JLabel("Email:");
                JFormattedTextField email = new JFormattedTextField();
                email.setColumns(30);
                
                //Add the labels and text fields into panel
                addPanel.add(fnameL);
                addPanel.add(firstName);
                addPanel.add(lnameL);
                addPanel.add(lastName);
                addPanel.add(custIDL);
                addPanel.add(custID);
                addPanel.add(phoneL);
                addPanel.add(phoneNum);
                addPanel.add(zipL);
                addPanel.add(zipcode);
                addPanel.add(emailL);
                addPanel.add(email);
                
                //Set the panel size
                addPanel.setPreferredSize(new Dimension(300, 150));
                
                //Display the add panel
                JOptionPane.showMessageDialog(null, addPanel);
                
                //SQL command to add customer
                //still need to figure out how to stop adding null info
                pstmt = conn.prepareStatement("insert into customer values(?,?,?,?,?,?)");
                pstmt.setString(1, firstName.getText());
                pstmt.setString(2, lastName.getText());
                pstmt.setString(3, custID.getText());
                pstmt.setString(4, phoneNum.getText());
                pstmt.setString(5, zipcode.getText());
                pstmt.setString(6, email.getText());
                pstmt.executeUpdate();
                break;
              
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
