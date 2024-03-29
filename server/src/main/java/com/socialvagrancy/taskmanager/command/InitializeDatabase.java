//===================================================================
// InitializeDatabase.java
// 	Description:
// 		Performs the initial table creation for the database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

public class InitializeDatabase
{
	public static void initializeTables(PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Initializing postgres tables...");
		InitializeTextTable.createTable(psql, logbook);
		InitializeAccountTable.createTable(psql, logbook);
		InitializeContactTable.createTable(psql, logbook);
		InitializeGroupTable.createTable(psql, logbook);
		InitializeLocationTable.createTable(psql, logbook);
		InitializeProjectTable.createTable(psql, logbook);
		InitializeUserTable.createTable(psql, logbook);
	}
}
