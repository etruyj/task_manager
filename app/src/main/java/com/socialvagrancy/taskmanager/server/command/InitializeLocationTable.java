//===================================================================
// InitializeLocationTable.java
// 	Description:
// 		Creates the account table in the specified database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

public class InitializeLocationTable
{
	public static boolean createTable(PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Creating table: LOCATION");

		String query = "CREATE TABLE IF NOT EXISTS location ("
			+ "id UUID PRIMARY KEY, "
			+ "name VARCHAR NOT NULL, "
			+ "building VARCHAR, "
			+ "street_1 VARCHAR, "
			+ "street_2 VARCHAR, "
			+ "city VARCHAR, "
			+ "state VARCHAR, "
			+ "postal_code VARCHAR, "
			+ "country VARCHAR, "
			+ "account_id UUID, "
			+ "text_id UUID, "
			+ "active BOOLEAN, "
			+ "organization_id UUID, "
			+ "FOREIGN KEY (account_id) REFERENCES account (id), "
			+ "FOREIGN KEY (organization_id) REFERENCES organization (id), "
			+ "FOREIGN KEY (text_id) REFERENCES text (id) ON DELETE CASCADE);";

		if(!psql.update(query, logbook))
		{
			logbook.ERR("Unable to create table: LOCATION");
			return false;
		}

		return true;
	}
}
