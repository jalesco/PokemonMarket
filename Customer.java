package seniorproject;

import java.sql.SQLException;

/**
 *
 * @author Richard
 * Customer is a buyer in the database. Inherits from Person.
 */
public class Customer extends Person {
    private String mPhone, mZipCode, mEmail;
    
    /**
     * Constructor for a Customer
     * @param fname first name
     * @param lname last name
     * @param ID ID
     * @param phone phone number
     * @param zipCode zip code
     * @param email e-mail
     */
    public Customer (String fname, String lname, String ID, String phone, String
            zipCode, String email) {
        super(fname, lname, ID);
        mPhone = phone;
        mZipCode = zipCode;
        mEmail = email;
    }
    
    /**
     * Getter for a customers zip code
     * @return customers zip code
     */
    public String getZipCode () {
        return mZipCode;
    }
    
    /**
     * Getter for a customers phone number
     * @return customers phone number
     */
    public String getPhone () {
        return mPhone;
    }
    
    /**
     * Getter for a customers email
     * @return customers email
     */
    public String getEmail () {
        return mEmail;
    }
    
    /**
     * Setter for a customers phone number
     * @param phone customers new phone number
     */
    public void setPhone (String phone) {
        mPhone = phone;
    }
    
    /**
     * Setter for a customers zip code
     * @param zipCode customers new zip code
     */
    public void setZipCode (String zipCode) {
        mZipCode = zipCode;
    }
    
    /**
     * Setter for a customers email
     * @param email customers new email
     */
    public void setEmail (String email) {
        mEmail = email;
    }
    
    /**
     * Will retrieve all of the attributes of the customer
     * @return String representation of all the customers attributes
     */
    public String infoRetrieve () {
        return getFirstName() + "\n" + getLastName()
            + "\n" + getID() + "\n" + getPhone() + "\n" + getZipCode()
            + "\n" + getEmail();
    }
    
    /**
     * Will add this customer into the database
     * @throws SQLException Exception if error occurs from database
     */
    public void addCustomer () throws SQLException {
        SeniorProject.pstmt = SeniorProject.conn.prepareStatement("insert into customer values(?,?,?,?,?,?)");
	SeniorProject.pstmt.setString(1, this.getFirstName());
	SeniorProject.pstmt.setString(2, this.getLastName());
	SeniorProject.pstmt.setString(3, this.getID());
        SeniorProject.pstmt.setString(4, this.getPhone());
	SeniorProject.pstmt.setString(5, this.getZipCode());
	SeniorProject.pstmt.setString(6, this.getEmail());
        SeniorProject.pstmt.executeUpdate();        
    }
    
    /**
     * Remove the customer from the database
     * NOTE: Method requires further development (removal is NOT normalized)
     * @throws SQLException Exception if error occurs from database
     */
    public void removeCustomer () throws SQLException {
        SeniorProject.stmt.executeUpdate("delete from customer where cID = '" + 
                this.getID() + "'");
    }
}
