package seniorproject;

/**
 *
 * @author Richard
 * Parent class of all human entities in the database
 */
public abstract class Person {
    private String mFName, mLName, mID, mEmail;
    
    /**
     * Accessor for a persons ID
     * @return a String of a persons ID
     */
    public String getID () {
        return mID;
    }
    
    /**
     * Accessor for a persons first name
     * @return String of a persons first name
     */
    public String getFirstName () {
        return mFName;
    }
    
    /**
     * Accessor for a persons last name
     * @return String of a persons last name
     */
    public String getLastName () {
        return mLName;
    }
    
    /**
     * Accessor for a persons email
     * @return String of a persons email
     */
    public String getEmail () {
        return mEmail;
    }
}
