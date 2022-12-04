//===================================================================
// InitializeTokenTable.java
// 	Description:
// 		Creates the table for tracking approved access to the
// 		client.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

public class InitializeTokenTable
{
	public static boolean createTable(PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Creating table: ACCESS_TOKEN");

		String query = "CREATE TABLE IF NOT EXISTS access_token ("
			+ "id UUID PRIMARY KEY, "
			+ "token VARCHAR, "
			+ "user_id UUID NOT NULL, "
			+ "organization_id UUID NOT NULL, "
			+ "expires TIMESTAMP, "
			+ "FOREIGN KEY (user_id) REFERENCES client_user(id), "
			+ "FOREIGN KEY (organization_id) REFERENCES organization(id));";

		if(!psql.update(query, logbook))
		{
			logbook.ERR("Unabled to create table: ACCESS_TOKEN");
			return false;
		}
		

		return true;	
	}
}
