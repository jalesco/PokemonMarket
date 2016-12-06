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

    // Database credentials
    static final String USER = "jdbc";
    static final String PASS = "12345";
    static Connection conn = null;

    // Universal SQL holders
    static Statement stmt;
    static PreparedStatement pstmt;
    static ResultSet rs;
    static ImageIcon icon = new ImageIcon("C:\\Users\\Richard\\My Documents\\NetBeansProjects\\SeniorProject\\src\\pokeball.png");
    // JLabels
    // Create JLabel and JFormattedTextField for each attribute
    static JLabel fnameL = new JLabel("First Name:");
    static JFormattedTextField firstName = new JFormattedTextField();
    static JLabel lnameL = new JLabel("Last Name:");
    static JFormattedTextField lastName = new JFormattedTextField();
    static JLabel custIDL = new JLabel("Customer ID:");
    static JFormattedTextField custID = new JFormattedTextField();
    static JLabel phoneL = new JLabel("Phone Number:");
    static JFormattedTextField phoneNum = new JFormattedTextField();
    static JLabel zipL = new JLabel("Zip Code:");
    static JFormattedTextField zipcode = new JFormattedTextField();
    static JLabel emailL = new JLabel("Email:");
    static JFormattedTextField email = new JFormattedTextField();

    // SQL commands
    static String GET_CUSTOMERS = "Select * from products orderby pID";
    static String GET_EMPLOYEES = "Select * from employee";
    static Scanner sc = new Scanner(System.in);
    static Employee usr;
    final static String APPNAME = "Pokemart (Beta)";

    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println("Connected succesfully");
            while (!(login())) {
                JOptionPane.showMessageDialog(null,"Incorrect username or password");
            }
            JOptionPane.showMessageDialog(null, "Welcome " + usr.getFName() + " " + usr.getLName());
            boolean cont = true;
            while (cont) {
                select();
            }
	}
	catch (SQLException se) {
            se.printStackTrace();
            // goto main
        }
    }

    private static boolean login() throws SQLException {
        String username = JOptionPane.showInputDialog(null, "Enter username");
	String password = JOptionPane.showInputDialog(null, "Enter password");
        pstmt = conn.prepareStatement("Select *from employee where username = ? AND password = ?");
	pstmt.setString(1, username);
	pstmt.setString(2, password);
        rs = pstmt.executeQuery();
	if (rs.next()) {
            usr = new Employee(rs.getString("fname"), rs.getString("lname"), rs.getString("username"), 
                    rs.getString("password"), rs.getString("eID"), rs.getBoolean("tier"));
            return true;
	} else
            return rs.next();
    }

    private static void select() throws SQLException {
	Object[] options = { "Customers", "Products", "Users" };
        int n = JOptionPane.showOptionDialog(null, "What would you like to do?", APPNAME,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                icon, options, options[2]);
	switch (n) {
            case (-1):
                System.exit(0);
            case (0):
		customers();
                break;
            case (1):
                products();
                break;
            case (2):
                if (usr.getTier())
                    users();
                else {
                    JOptionPane.showMessageDialog(null, "You do not have permission"
                            + " to access [users]! ", "Slow down there, kiddo!",
                            JOptionPane.WARNING_MESSAGE, icon);
                }
                break;
	}
    }

    private static void customers() throws SQLException {
        Object[] options = { "Add Customer", "Modify Customer", "Remove Customer", 
            "View Customers", "Cancel" };
	int n = JOptionPane.showOptionDialog(null, "Customers", APPNAME,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                icon, options, options[2]);
        switch (n) {
            case (0):// add 
                addCustomer();
		break;

            case (1):// modify
                modifyCustomer();
                break;
            
            case (2):// remove
                String rID = JOptionPane.showInputDialog(null, "Enter ID to remove");
                if (rID == null)
                    select();
                else
                    normalizedCustRemove(rID);
		break;
            
            case (3): //view
                rs = stmt.executeQuery("Select COUNT(*) from customer");
		rs.next();
		int count = rs.getInt(1);
		rs = stmt.executeQuery("Select * from customer");
                Object[] customers = new Object[count];
		int temp = 0;
		while (rs.next()) {
                    customers[temp] = rs.getString("fname") + " " + rs.getString("lname") 
                            + " " + rs.getString("cid");
                    ++temp;
		}
		String s = (String) JOptionPane.showInputDialog(null, "Select a customer", APPNAME, 
                        JOptionPane.PLAIN_MESSAGE, null, customers, "Select a customer");
                if (s == null || s.length() < 1) 
                    return;
		String[] iD = s.split(" ");
                rs = stmt.executeQuery("Select *from customer where cID = '" + iD[iD.length - 1] + "'");
                break;
            
            case (4):
                select();
                break;
        }
    }

    private static void products() throws SQLException {
        Object [] options = {"Add", "Remove", "Modify", "View/Select", "Cancel"};
        int n = JOptionPane.showOptionDialog(null, "Products", APPNAME, 
                JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE,
                icon, options, options[0]);
        switch (n){
            case (-1):
                System.exit(0);
            case (0):
                addProduct();
                break;
            case (1):
                normalizedProductRemove();
                break;
            case (2):
                break;
            case (3):
                productView();
                break;
            case (4):
                select();
                break;
        }
    }
    
    public static void users() throws SQLException {
        Object[] options = {"Add", "Remove", "Modify", "Cancel"};
        int n = JOptionPane.showOptionDialog(null, "Employee", APPNAME, 
                JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, 
                icon, options, options[0]);
        switch(n) {
            case (-1):
                break;
            case (3):
                select();
                break;
        }
    }
    
    private static void addCustomer() throws SQLException {
        JPanel addPanel = new JPanel(new GridLayout(6,2));
        firstName.setColumns(15);
        lastName.setColumns(15);
        custID.setColumns(10);
        phoneNum.setColumns(15);
        zipcode.setColumns(5);
        email.setColumns(30);
                
	// Add the labels and text fields into panel
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
        
        // Set the panel size
        addPanel.setPreferredSize(new Dimension(300, 150));

	// Display the add panel
        JOptionPane.showMessageDialog(null, addPanel);

	// SQL command to add customer
	// still need to figure out how to stop adding null info
        pstmt = conn.prepareStatement("insert into customer values(?,?,?,?,?,?)");
	pstmt.setString(1, firstName.getText());
	pstmt.setString(2, lastName.getText());
	pstmt.setString(3, custID.getText());
        pstmt.setString(4, phoneNum.getText());
	pstmt.setString(5, zipcode.getText());
	pstmt.setString(6, email.getText());
        pstmt.executeUpdate();
    }
    
    private static void modifyCustomer() throws SQLException {
        String mID = JOptionPane.showInputDialog(null, "Enter the ID of customer to modify:");
	rs = stmt.executeQuery("Select *from customer where cid = '" + mID + "'");
	if (rs.next()) {
            JPanel modPanel = new JPanel(new GridLayout(6, 2));
            
            // Create JLabel and JFormattedTextField for each attribute
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

            // Add the labels and text fields into panel
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

            // Set the panel size
            modPanel.setPreferredSize(new Dimension(300, 150));

            // Display the add panel
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
    }
    
    private static void addProduct () throws SQLException {
        //Create JPanel to let user input data
        Object [] possibilities = {"Medicine", "PokeBall", "Potion", "TM"};
        String choice = (String)JOptionPane.showInputDialog(null, 
                "What type of product?", APPNAME, 1, null, possibilities, 
                "Product Selection");
        if (choice == null)
            select();
        String uniqueAttribute = "";
        switch (choice) {
            case "Medicine":
                uniqueAttribute = "Stat";
                break;
            case "PokeBall":
                uniqueAttribute = "Catch Rate";
                break;
            case "Potion":
                uniqueAttribute = "Heal Amount";
                break;
            case "TM":
                uniqueAttribute = "Ability";
                break;
        }
                
        JPanel addPanel = new JPanel(new GridLayout(6,2));
                
        JLabel nameL = new JLabel("Name:");
        JFormattedTextField name = new JFormattedTextField();
        JLabel pIDL = new JLabel("ID:");
        JFormattedTextField pID = new JFormattedTextField();
        JLabel descriptionL = new JLabel("Description:");
        JFormattedTextField description = new JFormattedTextField();
        JLabel priceL = new JLabel("Price:");
        JFormattedTextField price = new JFormattedTextField();
        JLabel quantityStockL = new JLabel("Stock:");
        JFormattedTextField quantityStock = new JFormattedTextField();
        JLabel uniqueAttL = new JLabel (uniqueAttribute);
        JFormattedTextField uniqueAtt = new JFormattedTextField();
                
        name.setColumns(15);
        pID.setColumns(15);
        description.setColumns(100);
        price.setColumns(15);
        quantityStock.setColumns(5);
        uniqueAtt.setColumns(15);
        
        //Add the labels and text fields into panel
        addPanel.add(nameL);
        addPanel.add(name);
        addPanel.add(pIDL);
        addPanel.add(pID);
        addPanel.add(descriptionL);
        addPanel.add(description);
        addPanel.add(priceL);
        addPanel.add(price);
        addPanel.add(quantityStockL);
        addPanel.add(quantityStock);
        addPanel.add(uniqueAttL);
        addPanel.add(uniqueAtt);
                
        //Set the panel size
        addPanel.setPreferredSize(new Dimension(300, 150));
                
        //Display the add panel
        JOptionPane.showMessageDialog(null, addPanel);
        String pName = name.getText();
        String productID = pID.getText();
        String pDescription = description.getText();
        int pPrice = Integer.parseInt(price.getText());
        int pStock = Integer.parseInt(quantityStock.getText());
        String pUniqueAtt = uniqueAtt.getText();
                
        //SQL command to add customer   
        //still need to figure out how to stop adding null info
        pstmt = conn.prepareStatement("insert into product values(?,?,?,?,?)");
        pstmt.setString(1, pName);
        pstmt.setString(2, productID);
        pstmt.setString(3, pDescription);
        pstmt.setInt(4, pPrice);
        pstmt.setInt(5, pStock);
        pstmt.executeUpdate();
        pstmt = conn.prepareStatement("insert into " + choice + " values (?,?,?,?,?,?)");
                
        //pstmt.setString(1, choice);
        pstmt.setString(1, pName);
        pstmt.setString(2, productID);
        pstmt.setString(3, pDescription);
        pstmt.setInt(4, pPrice);
        pstmt.setInt(5, pStock);
        if (choice.equals("PokeBall") || choice.equals("Potion"))
            pstmt.setInt(6, Integer.parseInt(pUniqueAtt));
        else
            pstmt.setString(6, pUniqueAtt);
        pstmt.executeUpdate();
    }
    
    private static void normalizedProductRemove () throws SQLException {
        String pID = JOptionPane.showInputDialog(null, "Enter ID to remove");
        if (pID == null)
            return;
        String [] options = {"Medicine", "PokeBall", "Potion", "TM"};
        int option = 0;
        do {
            rs = stmt.executeQuery("Select * from " + options[option] + " where "
                    + "pID = '" + pID + "'");
            option++;
        } while (!rs.next() && option < 4);
        String choice = options[option - 1];
        stmt.executeUpdate("delete from " + choice + " where pID = '" + pID + "'");
        stmt.executeUpdate("delete from purchaseLine where productID = '" + pID + "'");
        stmt.executeUpdate("delete from product where pID = '" + pID + "'");
    }
    
    private static void productView() throws SQLException {
        rs = stmt.executeQuery("select count(*) from product");
        rs.next();
        int count = rs.getInt(1);
        rs = stmt.executeQuery("select * from product");
        Object[] products = new Object[count];
        int temp = 0;
        while(rs.next()) {
            products[temp] = rs.getString("name") + " " + rs.getString("pid");
            ++temp;
        }
        String s = (String) JOptionPane.showInputDialog(null, "Select a product", APPNAME,
                JOptionPane.PLAIN_MESSAGE, null, products, "Select a product");
        if (s == null)
            return;
        String[] prodID = s.split(" ");
        rs = stmt.executeQuery("Select * from product where pid = '" + prodID[prodID.length - 1] + "'");
    }        
    
    
    private static void normalizedCustRemove (String ID) throws SQLException {
        if (ID == null)
            select();
        else
            rs = stmt.executeQuery("Select fname, lname from customer where cid = '" + ID + "'");
        int selection = 1;
        if (rs.next()) {
            selection = JOptionPane.showConfirmDialog(null, "Remove " 
                    + rs.getString("fname") + " " + rs.getString("lname"));
        }
        if (selection == 0) {
            Statement stmtTemp = conn.createStatement();
            rs = stmtTemp.executeQuery("Select purchaseID from purchase "
                    + "where custID = '" + ID + "'");
            while (rs.next()) {
                stmt.executeUpdate("delete from purchaseLine where "
                        + "purchaseID = '" + rs.getString("purchaseID") + "'");
            }
            stmt.executeUpdate("delete from purchase where custID = '" + ID + "'" );
            stmt.executeUpdate("delete from customer where cID = '" + ID + "'");
        }
    }
}