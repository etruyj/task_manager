//===================================================================
// Account.java
// 	Description:
// 		A container class for account information.
//===================================================================

package com.socialvagrancy.taskmanager.server.structures;

public class Account
{
	String name;
	String id;
	String description;

	public String id() { return id; }
	public String name() { return name; }
	public String description() { return description; }

	public void setId(String d) { id = d; }
	public void setName(String n) { name = n; }
	public void setDesc(String d) { description = d; }
}
