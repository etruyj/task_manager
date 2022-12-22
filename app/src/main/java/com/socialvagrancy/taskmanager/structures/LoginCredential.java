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

	public LoginCredential()
	{
		password = "";
	}

	public String organization() { return organization; }
	public String password() { return password; }
	public String username() { return username; }

	public void setOrganization(String o) { organization = o; }

	public void setPassword(char[] p) 
	{ 
		for(int i=0; i<p.length; i++)
		{
			password += p[i];
		}	
	}

	public void setUsername(String u) { username = u; }
}
