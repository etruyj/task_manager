//===================================================================
// ServerResponse.java
// 	Description:
// 		Error messaging from the server. This makes it easier
// 		to pass back more error information to the client.
//===================================================================

package com.socialvagrancy.taskmanager.server.structures;

public class ServerResponse
{
	String id;
	String message;

	public String getId() { return id; }
	public String getMessage() { return message; }
	
	public boolean success()
	{
		if(id != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void setId(String d) { id = d; }
	public void setMessage(String m) { message = m; }
}
