package project;

/**
 * @author Richard
 *
 */
public class Staff extends User {

	
	public Staff () {
		mTier = 1;
	}
	
	@Override
	public void setUsername(String name) {
		mUserName = name;
		//execute sql code to update database, e.g. "Select from staff where firstName = ([java]this.mFirstName) yadda yadda..."
	}

	@Override
	public void setPassword(String pass) {
		mPassword = pass;
	}
}