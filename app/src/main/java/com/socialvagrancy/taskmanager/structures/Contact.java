//===================================================================
// Contact.java
// 	Description:
// 		A container class for the contact information.
//===================================================================

package com.socialvagrancy.taskmanager.structure;

public class Contact
{
	String id;
	String first_name;
	String last_name;
	String phone;
	String email;
	boolean active;
	String location_id;
       	String account_id;
	String text_id;
	String notes;

	public String account() { return account_id; }
	public String email() { return email; }
	public String firstName() { return first_name; }
	public String fullName() { return last_name + ", " + first_name; }
	public String id() { return id; }
	public String lastName() { return last_name; }
	public String location() { return location_id; }
	public String phone() { return phone; }
	public String notes() { return notes; }
	public boolean status() { return active; }
	public String textId() { return text_id; }

	public void setAccountId(String a) { account_id = a; }
	public void setActive(boolean a) { active = a; }
	public void setEmail(String e) { email = e; }
	public void setFirstName(String n) { first_name = n; }
	public void setLastName(String n) { last_name = n; }
	public void setLocationId(String l) { location_id = l; }
	public void setPhone(String p) { phone = p; }
	
	public void setId(String i) { id = i; } 
	public void setTextId(String nid)
	{
		text_id = nid;
		notes = null;
	}


}
