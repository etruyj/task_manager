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
			+ "name VARCHAR UNIQUE NOT NULL, "
			+ "abbreviation VARCHAR, "
			+ "desc_text_id UUID, "
			+ "FOREIGN KEY (organziation_id) REFERENCES organization (id), "
			+ "FOREIGN KEY (desc_text_id) REFERENCES text (id));";

		if(!psql.update(query, logbook))
		{
			logbook.ERR("Unable to create table: ACCOUNT");
			return false;
		}

		return true;
	}
}
