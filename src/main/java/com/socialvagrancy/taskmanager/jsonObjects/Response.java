//===================================================================
//	Response.java
//		The basic server response. Includes status and message
//		fields. This is extended by the other objects to allow
//		encoding and decoding the json.
//===================================================================

package com.socialvagrancy.taskmanager.jsonObjects;

public class Response
{
	private String status_code;
	private String message;

	Response()
	{
		status_code = "none";
		message = "none";
	}

	//==============================================
	// Setters
	//==============================================

	public void setMessage(String msg) { message = msg; }
	public void setStatusCode(String code) { status_code = code; }
	public void setResponse(String code, String msg)
	{
		status_code = code;
		message = msg;
	}

	//==============================================
	// Getters
	//==============================================

	public String getMessage() { return message; }
	public String getStatusCode() { return status_code; }

	//==============================================
	// Functions
	//==============================================

	public boolean isValidResponse()
	{
		// Validate whether the call was successful.

		if(Integer.valueOf(status_code)>=200 && Integer.valueOf(status_code)<300)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
