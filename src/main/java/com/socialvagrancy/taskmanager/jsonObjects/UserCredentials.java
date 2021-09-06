//===================================================================
// UserCredentials.java
//	Holds the username and password information for json encoding
//	and decoding.		
//===================================================================

package com.socialvagrancy.taskmanager.jsonObjects;

public class UserCredentials extends Response
{
	private String password;
	private String username;

	public UserCredentials()
	{
		password = "none";
		username = "none";
	}

	//==============================================
	// Settors
	//==============================================
	
	public void setCredentials(String user, String pass)
	{
		password = pass;
		username = user;
	}
	public void setPassword(String pass) { password = pass; }
	public void setusername(String user) { username = user; }
	
	//==============================================
	// Gettors
	//==============================================

	public String getPassword() { return password; }
	public String getUsername() { return username; }
}

