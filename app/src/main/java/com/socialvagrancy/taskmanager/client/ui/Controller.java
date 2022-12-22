//===================================================================
// Controller.java
// 	Description:
// 		The linker class between the GUI and the API calls.
//===================================================================

package com.socialvagrancy.taskmanager.client.ui;

import com.socialvagrancy.taskmanager.client.command.Login;
import com.socialvagrancy.utils.Logger;
import com.socialvagrancy.utils.http.RestApi;

public class Controller
{
	private Logger logbook;
	private RestApi api;
	private String base_url;
	private String token;

	public Controller(String url, boolean ignore_ssl, String log_path, int log_level, int log_size, int log_count)
	{
		base_url = url;
		api = new RestApi(ignore_ssl);

		logbook = new Logger(log_path, log_size, log_count, log_level);
	}

	public boolean login(String username, char[] password, String organization) throws Exception
	{
		try
		{
			token = Login.getToken(base_url, username, password, organization, api, logbook);
			// Clear from memory.
			password = null;

			return true;
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			throw new Exception("Unable to login with specified credentials.");
		}
	}
}
