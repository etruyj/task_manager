//===================================================================
// Account.java
// 	Description:
// 		A container class for account information.
//===================================================================

package com.socialvagrancy.taskmanager.structure;

public class Account
{
	String name;
	String id;
	String abbreviation;
	String description;
	String description_id;

	public String abbreviation() { return abbreviation; }
	public String id() { return id; }
	public String name() { return name; }
	public String description() { return description; }
	public String descriptionId() { return description_id; }

	public void setAbbreviation(String a) { abbreviation = a; }
	public void setId(String d) { id = d; }
	public void setName(String n) { name = n; }
	public void setDesc(String d) { description = d; }
	public void setDescriptionId(String d)
	{
		description_id = d;
		description = null;
	}
}
