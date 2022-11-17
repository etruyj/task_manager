//===================================================================
// InitializeOrganizationTable.java
// 	Description:
// 		Creates the organization table in the specified
// 		database. The organziation table segments data
// 		by different groups.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

public class InitializeOrganizationTable
{
	public static boolean createTable(PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Creating table: Organization");

		String query = "CREATE TABLE IF NOT EXISTS organization ("
			+ "id UUID PRIMARY KEY, "
			+ "name VARCHAR UNIQUE NOT NULL);";

		if(!psql.update(query, logbook))
		{
			logbook.ERR("Unable to create table: ORGANIZATION");
			return false;
		}

		return true;
	}
}
