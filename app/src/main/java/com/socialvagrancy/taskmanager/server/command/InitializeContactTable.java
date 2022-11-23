//===================================================================
// InitializeContactTable.java
// 	Description:
// 		Creates the contact table in the specified database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

public class InitializeContactTable
{
	public static boolean createTable(PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Creating table: CONTACT");

		String query = "CREATE TABLE IF NOT EXISTS contact ("
			+ "id UUID PRIMARY KEY, "
			+ "first_name VARCHAR NOT NULL, "
			+ "last_name VARCHAR NOT NULL, "
			+ "role VARCHAR, "
			+ "phone VARCHAR, "
			+ "email VARCHAR, "
			+ "active BOOLEAN, "
			+ "location_id UUID, "
			+ "account_id UUID, "
			+ "text_id UUID, "
			+ "organization_id UUID, "
			+ "FOREIGN KEY (location_id) REFERENCES location (id), "
			+ "FOREIGN KEY (account_id) REFERENCES account (id), "
			+ "FOREIGN KEY (organization_id) REFERENCES organization (id), "
			+ "FOREIGN KEY (text_id) REFERENCES text (id));";

		if(!psql.update(query, logbook))
		{
			logbook.ERR("Unable to create table: CONTACT");
			return false;
		}

		return true;
	}
}
