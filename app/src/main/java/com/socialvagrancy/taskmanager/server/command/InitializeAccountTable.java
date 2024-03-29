//===================================================================
// InitializeAccountTable.java
// 	Description:
// 		Creates the account table in the specified database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

public class InitializeAccountTable
{
	public static boolean createTable(PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Creating table: ACCOUNT");

		String query = "CREATE TABLE IF NOT EXISTS account ("
			+ "id UUID PRIMARY KEY, "
			+ "name VARCHAR NOT NULL, "
			+ "abbreviation VARCHAR, "
			+ "organization_id UUID, "
			+ "text_id UUID, "
			+ "FOREIGN KEY (organization_id) REFERENCES organization (id), "
			+ "FOREIGN KEY (text_id) REFERENCES text (id) ON DELETE CASCADE);";

		if(!psql.update(query, logbook))
		{
			logbook.ERR("Unable to create table: ACCOUNT");
			return false;
		}

		return true;
	}
}
