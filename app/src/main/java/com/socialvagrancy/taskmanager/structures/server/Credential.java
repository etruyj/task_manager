//===================================================================
// Credential.java
//	Description:
//		A container class for holding the access credentials
//		and permissions for a client_user logged into the server
//===================================================================

package com.socialvagrancy.taskmanager.structure.server;

import java.time.LocalDateTime;

public class Credential
{
	String org_id;
	String user_id;
	String username;
	PermissionLevel permissions;


	//=======================================
	// Getters
	//=======================================

	public String organization() { return org_id; }
	public PermissionLevel permissions() { return permissions; }
	public String userId() { return user_id; }
	public String username() { return username; }
	
	//=======================================
	// Setters
	//=======================================

	public Credential setOrganization(String o) 
	{ 
		org_id = o; 
		return this;
	}


	public Credential setPermissions(String p) 
	{
		permissions = PermissionLevel.valueOf(p); 
		return this;
	}

	public Credential setUserId(String u) 
	{ 
		user_id = u; 
		return this;
	}

	public Credential setUsername(String u) 
	{ 
		username = u; 
		return this;
	}
}
