//===================================================================
// InitializeTextTable.java
// 	Description:
// 		Creates the text table in the specified database to
// 		hold all large blocks of text.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

public class InitializeTextTable
{
	public static boolean createTable(PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Creating table: TEXT");

		String query = "CREATE TABLE IF NOT EXISTS text ("
			+ "id UUID PRIMARY KEY, "
			+ "text VARCHAR);";

		if(!psql.update(query, logbook))
		{
			logbook.WARN("Failed to create table: TEXT");
			return false;
		}

		return true;
	}
}
