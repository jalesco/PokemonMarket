package seniorproject;

import java.sql.SQLException;
import static seniorproject.SeniorProject.pstmt;

/**
 *
 * @author Richard
 * Product class sets foundation for Medicine, TM, Pokeball, and Potion
 */

// NOTE: ID is a string for database purposes at the moment. Will be converted to
// int in the future.
// subclasses will require unique sql commands in the future
public abstract class Product {
    private final String mID;
    private String mName, mDescription;
    private int mPrice, mStock;
    
    /**
     * Constructor for a product
     * @param name products name
     * @param ID products ID
     * @param description description of the product
     * @param price products price
     * @param stock products stock quantity
     */
    public Product (String name, String ID, String description, int price, int stock) {
        mName = name;
        mID = ID;
        mDescription = description;
        mPrice = price;
        mStock = stock;
    }
    
    /**
     * Getter for a products name
     * @return the products name
     */
    public String getProductName () {
        return mName;
    }
    
    /**
     * Getter for a products ID
     * @return the products ID
     */
    public String getProductID () {
        return mID;
    }
    
    
    /**
     * Getter for a products description
     * @return products description
     */
    public String getProductDescription () {
        return mDescription;
    }
    
    /**
     * Getter for a products price
     * @return products price
     */
    public int getProductPrice () {
        return mPrice;
    }
    
    /**
     * Getter for a products quantity in stock
     * @return amount in stock
     */
    public int getProductQuantity () {
        return mStock;
    }
    
    /**
     * Setter for a products name
     * @param name products new name
     */
    public void setName (String name) {
        mName = name;
    }
    
    /**
     * Setter for a products description
     * @param description products new description
     */
    public void setDescription (String description) {
        mDescription = description;
    }
    
    /**
     * Setter for a products price
     * @param price products new price
     */
    public void setPrice (int price) {
        mPrice = price;
    }
    
    /**
     * Setter for a products stock
     * @param stock products new stock
     */
    public void setStock (int stock) {
        mStock = stock;
    }
    
    /**
     * WIll add this product into the database
     * @throws SQLException Exception if error occurs from database
     */
    public void addProduct () throws SQLException {
        pstmt = SeniorProject.conn.prepareStatement("insert into product values(?,?,?,?,?)");
        pstmt.setString(1, this.getProductName());
        pstmt.setString(2, this.getProductID());
        pstmt.setString(3, this.getProductDescription());
        pstmt.setInt(4, this.getProductPrice());
        pstmt.setInt(5, this.getProductQuantity());
        pstmt.executeUpdate();
    }
    
    /**
     * Will remove this product from the database
     * NOTE: Removal is NOT normalized!
     * @throws SQLException Exception if error occurs in database
     */
    public void removeProduct () throws SQLException {
        SeniorProject.stmt.executeUpdate("delete from product where pID = '" + this.getProductID() + "'");
    }
    
    /**
     * Will return the number of purchases this product has
     * @return sum of this products purchases
     * @throws SQLException Exception if error occurs from database
     */
    public int getNumberOfPurchases () throws SQLException {
        SeniorProject.rs = SeniorProject.stmt.executeQuery("select sum(quantity)"
                + " from purchaseline where productID = '" + this.getProductID() + "'");
        SeniorProject.rs.next();
        return SeniorProject.rs.getInt(1);
    }
    
    /**
     * Views the customers that have bought this product
     * NOTE: needs to be integrated with UI
     * @throws SQLException Exception if error occurs from database
     */
    public void viewBuyers () throws SQLException {
        SeniorProject.rs = SeniorProject.stmt.executeQuery( "select  distinct fname,"
                + " lname from purchase inner join customer"
                + " on purchase.custID = customer.CID inner join"
                + " purchaseLine on purchase.purchaseID = purchaseLine.PURCHASEID"
                + "where purchaseLine.PRODUCTID = '" + this.getProductID() + "'");
        while (SeniorProject.rs.next()) {
            System.out.println(SeniorProject.rs.getString("fname"));
            System.out.println(SeniorProject.rs.getString("lname"));
        }
    }
}