//===================================================================
// ServerController.java
// 	Description:
// 		Cordinates all the subclasses.
//===================================================================

package com.socialvagrancy.taskmanager.server.ui;

import com.socialvagrancy.taskmanager.server.command.InitializeDatabase;
import com.socialvagrancy.taskmanager.server.command.StartServer;
import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

public class ServerController
{
	private PostgresConnector psql;
	private Logger logbook;

	public ServerController(String address, String table, String user, String pass)
	{
		psql = new PostgresConnector(address, table, user, pass);
		logbook = new Logger("../log/server-main.log", 10240, 10, 1);
	}

	public void initializeServer()
	{
		InitializeDatabase.initializeTables(psql, logbook);
	}

	public void startServer(String ip, String port, String api_prefix)
	{
		StartServer.initialize(ip, port, api_prefix, psql, logbook);
	}
}
