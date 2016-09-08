package project;

/**
 * @author Richard
 *
 */
public abstract class User {
	
	protected String mFirstName, mLastName, mID, mUserName, mPassword;
	
	protected byte mTier;
	
	protected int yolo;
	
	public String getName(String ID) {
		return null;
		//PreparedStatement pstmt = conn.prepareStatement ("Select firstName from employees where ID = ?"
		//pstmt.setString(1, ID);
		//ResultSet rs = pstmt.executeQuery();
		//return rs.next();
	}
	
	public String getID (String firstName) {
		return null;
		//PreparedStatement pstmt = conn.prepareStatement ("Select ID from employees where firsName = ?"
		//pstmt.setString(1, firstName);
		//ResultSet rs = pstmt.executeQuery();
		//return rs.next();
	}
	
	public abstract void setUsername (String name);
	
	public abstract void setPassword (String pass);
}