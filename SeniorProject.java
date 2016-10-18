/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.util.Scanner;
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
   static Statement stmt = null;
   
   //SQL commands
   static String GET_CUSTOMERS = "Select * from products orderby pID";
   static String GET_EMPLOYEES = "Select * from employee";
   public static void main(String [] args) {
       PreparedStatement pstmt;
       try {
        Scanner sc = new Scanner (System.in);
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
       /* String command = "create table employee (employeeID int not null)";
        stmt.executeUpdate(command);*/
        pstmt = conn.prepareStatement("select *from purchase inner join purchaseLine on purchase.PURCHASEID = purchaseLine.PURCHASEID\n" +
"inner join product on purchaseLine.PRODUCTID = product.PID\n" +
"where custID = (select cID from customer where fname = ?)");
        String name = sc.next();
        pstmt.setString(1, name);
        ResultSet rs =  pstmt.executeQuery();
        System.out.println("Purchases for " + name);
        while (rs.next()) {
            System.out.println(rs.getString("Name"));
            System.out.println(rs.getString("Quantity"));
        }
       }
       
       catch (SQLException se) {
           se.printStackTrace();
           //goto main
       }
   }
}