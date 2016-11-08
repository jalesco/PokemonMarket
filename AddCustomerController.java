/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addcustomer.controller;

import java.sql.*;
import addcustomer.model.AddCustomer;
/**
 *
 * @author patrickizawa
 */
public class AddCustomerController {
    
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/SeniorProject";
    static final String USER = "jdbc";
    static final String PASS = "12345";
    static Connection conn = null;
    static Statement stmt = null;
    public static int addCustomer(AddCustomer customer) throws ClassNotFoundException, SQLException {
//        Class.forName();
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        String query = "INSERT INTO customer values('" + customer.getfName() + "','" 
                + customer.getlName() + "','"+ customer.getID() + "','"+ customer.getPhone() + 
                "','" + customer.getAddress() + "','" + customer.getEmail() + "')";
        stmt = conn.createStatement();
        int exec = stmt.executeUpdate(query);
        //http://projectslanka.blogspot.com/2014/12/student-registration-formgui-with-mysql.html
        return exec;
    }
}
