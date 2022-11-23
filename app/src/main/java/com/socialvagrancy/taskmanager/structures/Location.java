//===================================================================
// Location.java
// 	Description:
// 		A container class for account location information.
//===================================================================

package com.socialvagrancy.taskmanager.structure;

public class Location
{
	String id;
	String name;
	String account_id;
	String building;
	String street_1;
	String street_2;
	String city;
	String state;
	String postal_code;
	String country;
	String notes_id;
	String notes;

	public String accountId() { return account_id; }
	public String building() { return building; }
	public String city() { return city; }
	public String country() { return country; }
	public String id() { return id; }
	public String name() { return name; }
	public String notes() { return notes; }
	public String notesId() { return notes_id; }
	public String postalCode() { return postal_code; }
	public String state() { return state; }
	public String street1() { return street_1; }
	public String street2() { return street_2; }

	public void setAccountId(String a) { account_id = a; }
	public void setBuilding(String b) { building = b; }
	public void setCity(String c) { city = c; }
	public void setCountry(String c) { country = c; }
	public void setId(String d) { id = d; }
	public void setName(String n) { name = n; }
	public void setNotes(String n) { notes = n; }
	public void setPostalCode(String p) { postal_code = p; }
	public void setState(String s) { state = s; }
	public void setStreet1(String s) { street_1 = s; }
	public void setStreet2(String s) { street_2 = s; }

	public void setNotesId(String nid)
	{
		notes_id = nid;
		notes = null;
	}

}
