//===================================================================
// Token.java
// 	Description:
// 		The class for passing the bearer token.
//===================================================================

package com.socialvagrancy.taskmanager.structure;

public class Token
{
	String token;
	String contact_name;

	public Token(String t, String first, String last) 
	{ 
		token = t;
	        contact_name = last + ", " + first;	
	}

	public String get() { return token; }
	public String name() { return contact_name; }
	public void set(String t) { token = t; }
	public void setName(String first, String last) { contact_name = last + ", " + last; }
}
