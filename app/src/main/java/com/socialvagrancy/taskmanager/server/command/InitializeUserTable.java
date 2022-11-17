//===================================================================
// InitializeUserTable.java
// 	Description:
// 		Creates the user table in the specified database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

public class InitializeUserTable
{
	public static boolean createTable(PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Creating table: CLIENT_USER");

		String query = "CREATE TABLE IF NOT EXISTS client_user ("
			+ "id UUID PRIMARY KEY, "
			+ "name VARCHAR NOT NULL, "
			+ "password VARCHAR, "
			+ "salt VARCHAR, "
			+ "contact_id UUID, "
			+ "FOREIGN KEY (contact_id) REFERENCES contact (id));";

		if(!psql.update(query, logbook))
		{
			logbook.ERR("Unable to create table: CLIENT_USER");
			return false;
		}

		return true;
	}
}
