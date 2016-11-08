/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seniorproject;

/**
 *
 * @author Richard
 * Employee/user class. This class should only be instantiated through
 * data retrieved from the database.
 */
public class Employee {
    private String mUsername, mPassword, mFName, mLName, mID;
    private boolean mTier;
    
    /**
     * Constructor for an employee/user
     * @param fname First name
     * @param lname Last name
     * @param uname username
     * @param pass password
     * @param ID user ID
     * @param tier the tier of the user
     */
    public Employee (String fname, String lname, String uname, String pass,
            String ID, boolean tier) 
    {
     mFName = fname;
     mLName = lname;
     mUsername = uname;
     mPassword = pass;
     mID = ID;
     mTier = tier;
    }
    
    
    /**
     * Accessor for an employees first name
     * @return mFName
     */
    public String getFName () {
        return mFName;
    }
    
    /**
     * Accessor for an employees last name
     * @return mLName
     */
    public String getLName () {
        return mLName;
    }    
}
