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
   
   //JLabels
                   //Create JLabel and JFormattedTextField for each attribute
static                JLabel fnameL = new JLabel("First Name:");
static                JFormattedTextField firstName = new JFormattedTextField();
static                JLabel lnameL = new JLabel("Last Name:");
static                JFormattedTextField lastName = new JFormattedTextField();
static                JLabel custIDL = new JLabel("Customer ID:");
static                JFormattedTextField custID = new JFormattedTextField();
static                JLabel phoneL = new JLabel("Phone Number:");
static                JFormattedTextField phoneNum = new JFormattedTextField();
static                JLabel zipL = new JLabel("Zip Code:");
static                JFormattedTextField zipcode = new JFormattedTextField();
static                JLabel emailL = new JLabel("Email:");
static                JFormattedTextField email = new JFormattedTextField();

   //SQL commands
   static String GET_CUSTOMERS = "Select * from products orderby pID";
   static String GET_EMPLOYEES = "Select * from employee";
   static Scanner sc = new Scanner (System.in);
   static Employee usr;
   final static String APPNAME = "Pokemart (Beta)";
   public static void main(String [] args) {
       try {
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
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
        int n = JOptionPane.showOptionDialog(null, "What would you like to do?", APPNAME
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
        int n = JOptionPane.showOptionDialog(null, "Customers", APPNAME
                    , JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[2]);    
        switch (n) {
              case (0)://add (updated by Patrick)
                //Create JPanel to let user input data
                JPanel addPanel = new JPanel(new GridLayout(6,2));

                firstName.setColumns(15);

                lastName.setColumns(15);

                custID.setColumns(10);

                phoneNum.setColumns(15);

                zipcode.setColumns(5);

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
                
              case (1):
                  String mID = JOptionPane.showInputDialog(null, "Enter the ID of customer to modify");
                  rs = stmt.executeQuery("Select *from customer where cid = '" + mID +"'");
                  if (rs.next()) {
                      JPanel modPanel = new JPanel(new GridLayout(6,2));
                
                //Create JLabel and JFormattedTextField for each attribute
                firstName.setText(rs.getString("fname"));
                firstName.setColumns(15);

                lastName.setText(rs.getString("lname"));
                lastName.setColumns(15);

                phoneNum.setText(rs.getString("cPhone"));
                phoneNum.setColumns(15);

                zipcode.setText(rs.getString("cZipCode"));
                zipcode.setColumns(5);

                email.setText(rs.getString("cEmail"));
                email.setColumns(30);
                
                //Add the labels and text fields into panel
                modPanel.add(fnameL);
                modPanel.add(firstName);
                modPanel.add(lnameL);
                modPanel.add(lastName);
                modPanel.add(phoneL);
                modPanel.add(phoneNum);
                modPanel.add(zipL);
                modPanel.add(zipcode);
                modPanel.add(emailL);
                modPanel.add(email);
                
                //Set the panel size
                modPanel.setPreferredSize(new Dimension(300, 150));
                
                //Display the add panel
                JOptionPane.showMessageDialog(null, modPanel);
                pstmt = conn.prepareStatement("Update customer set fname = ?, lname = ?,"
                        + " cPhone = ?, cZipCode = ?, cEmail = ? where cID = ?");
                pstmt.setString(1, firstName.getText());
                pstmt.setString(2, lastName.getText());
                pstmt.setString(3, phoneNum.getText());
                pstmt.setString(4, zipcode.getText());
                pstmt.setString(5, email.getText());
                pstmt.setString(6, rs.getString("cID"));
                pstmt.executeUpdate();
                  }

              break;
              
            case (2):
                String rID = JOptionPane.showInputDialog(null, "enter ID to remove");
                rs = stmt.executeQuery("Select fname, lname from customer where cid = '" + rID + "'");
                int selection = 1;
                if (rs.next()) {
                    selection = JOptionPane.showConfirmDialog(null, "Remove " + rs.getString("fname") + " " + rs.getString("lname"));
                }
                if (selection == 0) {
                    Statement stmtTemp = conn.createStatement();
                    rs = stmtTemp.executeQuery("Select purchaseID from purchase where custID = '" + rID + "'");
                    while (rs.next()) {
                        stmt.executeUpdate("delete from purchaseLine where purchaseID = '" + rs.getString("purchaseID") + "'");
                    }
                    stmt.executeUpdate("delete from purchase where custID = '" + rID + "'" );
                    stmt.executeUpdate("delete from customer where cID = '" + rID + "'");
                }
                break;               
            
            case (3):
                rs = stmt.executeQuery("Select COUNT(*) from customer"); 
                rs.next();
                int count = rs.getInt(1);
                rs = stmt.executeQuery("Select * from customer");
                Object [] customers = new Object[count];
                int temp = 0;
                while (rs.next()) {
                    customers[temp] = rs.getString("fname") + " " + rs.getString("lname")
                    + " " + rs.getString("cid");
                    ++temp;
                }
                String s = (String)JOptionPane.showInputDialog(
                    null,
                    "Select a customer",
                    APPNAME,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    customers,
                    "Select a customer");
                String [] iD = s.split(" ");
                rs = stmt.executeQuery("Select *from customer where cID = '" + iD[iD.length - 1] + "'");
                
                break;
            case (4):
                select();
                break;
        }
    }
    
    public static void normalizedRemove (String ID) throws SQLException {
        rs = stmt.executeQuery("Select purchaseID from purchase where custID = '" + ID + "'");
        while (rs.next()) {
            stmt.executeUpdate("delete from purchaseLine where purchaseID = '" + rs.getString("purchaseID") + "'");
        }
        stmt.executeUpdate("delete from purchase where custID = '" + ID + "'" );
        stmt.executeUpdate("delete from customer where cID = '" + ID + "'");
    }
}