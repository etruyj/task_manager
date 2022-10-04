//===================================================================
// InitializeGroupTable.java
// 	Description:
// 		Creates the group table in the specified database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

public class InitializeGroupTable
{
	public static boolean createTable(PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Creating table: GROUP");

		String query = "CREATE TABLE IF NOT EXISTS account ("
			+ "id UUID PRIMARY KEY, "
			+ "name VARCHAR UNIQUE NOT NULL, "
			+ "account_id UUID, "
			+ "FOREIGN KEY (account_id) REFERENCES acount (id));";

		if(!psql.update(query, logbook))
		{
			logbook.ERR("Unable to create table: GROUP");
			return false;
		}

		return true;
	}
}
