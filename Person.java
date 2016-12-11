package seniorproject;

/**
 * @author Richard
 *Person class sets foundation for employees and customers
 */

// May be useful in future expansion of the software
// NOTE: ID is a string for database purposes at the moment. Will be converted to
// int in the future.
public abstract class Person {
    private final String mID;
    private String mFname, mLname;
    
    /**
     * Constructor for a Person
     * @param firstName A persons first name
     * @param lastName A persons last name
     * @param ID a persons ID
     */
    public Person (String firstName, String lastName, String ID) {
        mFname = firstName;
        mLname = lastName;
        mID = ID;
    }
    
    /**
     * Setter for a persons first name
     * @param firstName a persons first name
     */
    public void setFirstName (String firstName) {
        mFname = firstName;
    }
    
    /**
     * Setter for a persons last name
     * @param lastName a persons last name
     */    
    public void setLastName (String lastName) {
        mLname = lastName;
    }    
    
    /**
     * Getter for a persons first name
     * @return the persons first name
     */
    public String getFirstName () {
        return mFname;
    }
    
    /**
     * Getter for a persons last name
     * @return the persons last name
     */
    public String getLastName () {
        return mLname;
    }
    
    /**
     * Getter for a persons ID
     * @return the persons ID
     */
    public String getID () {
        return mID;
    }
    
}
