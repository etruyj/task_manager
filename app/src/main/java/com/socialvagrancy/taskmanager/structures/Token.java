//===================================================================
// Token.java
// 	Description:
// 		The class for passing the bearer token.
//===================================================================

package com.socialvagrancy.taskmanager.structure;

public class Token
{
	String token;

	public Token(String t) { token = t; }

	public String get() { return token; }
	public void set(String t) { token = t; }
}
