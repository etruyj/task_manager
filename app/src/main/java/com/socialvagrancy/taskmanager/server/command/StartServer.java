//===================================================================
// StartServer.java
// 	Description:
// 		Start the HTTP server.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.server.utils.http.Server;
import com.socialvagrancy.taskmanager.server.utils.security.AccessToken;
import com.socialvagrancy.utils.Logger;

public class StartServer
{
	public static void initialize(String ip, String port, String api_prefix, PostgresConnector psql, Logger logbook)
	{
		AccessToken.revokeAll(psql, logbook);
		Server taskserver = new Server(ip, port, api_prefix, psql, logbook);
	}
}
