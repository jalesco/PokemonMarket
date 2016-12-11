package seniorproject;

import java.sql.SQLException;

/**
 *
 * @author Richard
 * Employee/user class. This class should only be instantiated through
 * data retrieved from the database. Inherits from Person.
 */
public class Employee extends Person {
    private String mUsername, mPassword;
    private final boolean mTier;
    
    /**
     * Constructor for an employee/user
     * @param fname First name
     * @param lname Last name
     * @param uname username
     * @param pass password
     * @param ID user ID
     * @param tier the tier of the user (True = admin, False = regular)
     */
    public Employee (String fname, String lname, String uname, String pass,
            String ID, boolean tier) 
    {
     super(fname, lname, ID);
     mUsername = uname;
     mPassword = pass;
     mTier = tier;
    }
    
    /**
     * Getter for a users username
     * @return users username
     */
    public String getUsername () {
        return mUsername;
    }
    
    /**
     * Getter for a users password
     * @return users password
     */
    public String getPassword () {
        return mPassword;
    }
    /**
     * Accessor for an employees tier
     * @return mTier
     */
    public boolean getTier () {
        return this.mTier;
    }
    
    /**
     * Setter for a users username
     * @param username a users new username
     */
    public void setUsername (String username) {
        mUsername = username;
    }
    
    /**
     * Setter for a users password
     * @param password a users new password
     */
    public void setPassword (String password) {
        mPassword = password;
    }
    
    /**
     * Will add this employee to the database
     * NOTE: Only admins should be able to perform this function. Will need 
     * future modification.
     * @throws SQLException Exception if error occurs from database 
     */
    public void addEmployee () throws SQLException {
        SeniorProject.pstmt = SeniorProject.conn.prepareStatement("insert into Employee values(?,?,?,?,?,?)");
	SeniorProject.pstmt.setString(1, this.getFirstName());
	SeniorProject.pstmt.setString(2, this.getLastName());
	SeniorProject.pstmt.setString(3, this.getID());
        SeniorProject.pstmt.setString(4, this.getUsername());
	SeniorProject.pstmt.setString(5, this.getPassword());
	SeniorProject.pstmt.setString(6, "True");
        SeniorProject.pstmt.executeUpdate();
    }
    
    /**
     * Will remove this employee from the database
     * NOTE: Only admins should be able to perform this function. Will need
     * future modification.
     * @throws SQLException Exception if error occurs from database 
     */
    public void removeEmployee () throws SQLException {
        SeniorProject.stmt.executeUpdate("delete from Employee where eID = '" +
                this.getID() + "'");
    }
    
}