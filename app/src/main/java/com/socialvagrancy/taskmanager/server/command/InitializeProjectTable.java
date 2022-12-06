//===================================================================
// InitializeProjectTable.java
// 	Description:
// 		Creates the project table in the specified database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

public class InitializeProjectTable
{
	public static boolean createTable(PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Creating table: PROJECT");

		String query = "CREATE TABLE IF NOT EXISTS project ("
			+ "id UUID PRIMARY KEY, "
			+ "name VARCHAR NOT NULL, "
			+ "account_id UUID, "
			+ "location_id UUID, "
			+ "organization_id UUID, "
			+ "text_id UUID, "
			+ "active boolean, "
			+ "FOREIGN KEY (account_id) REFERENCES account (id), "
			+ "FOREIGN KEY (location_id) REFERENCES location (id), "
			+ "FOREIGN KEY (organization_id) REFERENCES organization (id), "
			+ "FOREIGN KEY (text_id) REFERENCES text (id));";

		if(!psql.update(query, logbook))
		{
			logbook.ERR("Unable to create table: PROJECT");
			return false;
		}

		return true;
	}
}
