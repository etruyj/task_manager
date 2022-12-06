//===================================================================
// Project.java
// 	Desscription:
// 		A container class for project information.
//===================================================================

package com.socialvagrancy.taskmanager.structure;

public class Project
{
	String id;
	String name;
	boolean active;
	String account_id;
	String location_id;
	String description_id;
	String description;
	
	public String id() { return id; }
	public String name() { return name; }
	public boolean active() { return active; }
	public String accountId() { return account_id; }
	public String locationId() { return location_id; }
	public String description() { return description; }
	public String descriptionId() { return description_id; }

	//=======================================
	// Setters
	//=======================================

	public Project setAccountId(String a)
	{
		account_id = a;
		return this;
	}

	public Project setActive(boolean a)
	{
		active = a;
		return this;
	}

	public Project setDescriptionId(String d)
	{
		description_id = d;
		description = null;

		return this;
	}

	public Project setDescription(String d)
	{
		description = d;
		return this;
	}

	public Project setId(String d)
	{
		id = d;
		return this;
	}

	public Project setLocationId(String l)
	{
		location_id = l;
		return this;
	}

	public Project setName(String n)
	{
		name = n;
		return this;
	}
}
