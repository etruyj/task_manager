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
		InitializeOrganizationTable.createTable(psql, logbook);
		InitializeTextTable.createTable(psql, logbook);
		InitializeAccountTable.createTable(psql, logbook);
		InitializeLocationTable.createTable(psql, logbook);
		InitializeContactTable.createTable(psql, logbook);
		InitializeUserTable.createTable(psql, logbook);
		InitializeUserOrganizationTable.createTable(psql, logbook); // Depends on user, organization, contact
		//InitializeGroupTable.createTable(psql, logbook);
		//InitializeProjectTable.createTable(psql, logbook);
		InitializeTokenTable.createTable(psql, logbook); // Depends on user, organization
	}
}
