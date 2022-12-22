//===================================================================
// Login.java
// 	Description:
// 		Attempts to login to the server and generate a JWT
//===================================================================

package com.socialvagrancy.taskmanager.client.command;

import com.socialvagrancy.taskmanager.client.utils.http.ApiUrls;
import com.socialvagrancy.taskmanager.structure.LoginCredential;
import com.socialvagrancy.taskmanager.structure.Token;
import com.socialvagrancy.utils.Logger;
import com.socialvagrancy.utils.http.RestApi;

import com.google.gson.Gson;

import java.lang.StringBuilder;

public class Login
{
	public static String getToken(String base_url, String username, char[] password, String organization, RestApi api, Logger logbook) throws Exception
	{
		LoginCredential creds = new LoginCredential();
		Gson gson = new Gson();

		String url = ApiUrls.login(base_url);

		creds.setUsername(username);
		creds.setPassword(password);
		creds.setOrganization(organization);

		try
		{
			logbook.info("Logging in for user " + organization + ":" + username);
			logbook.debug("POST: " + url);
			String response = api.authenticate(url, gson.toJson(creds));
			Token token = gson.fromJson(response, Token.class);

			logbook.info("Login successful for " + organization + ":" + username);

			return token.get();
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());

			throw new Exception("Connection refused.");
		}
	}	
}
