//===================================================================
// LoginCredential.java
// 	Description:
// 		Holds the information necessary to log into the server.
//===================================================================

package com.socialvagrancy.taskmanager.structure;

public class LoginCredential
{
	String username;
	String password;
	String organization;

	public String organization() { return organization; }
	public String password() { return password; }
	public String username() { return username; }
}
