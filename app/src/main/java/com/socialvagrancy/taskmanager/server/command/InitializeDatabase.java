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
		InitializeOrganizationTable.createTable(psql, logbook); // no dependencies
		InitializeTextTable.createTable(psql, logbook); // no dependencies
		InitializeAccountTable.createTable(psql, logbook); // Depends on organization, text table
		InitializeLocationTable.createTable(psql, logbook); // Depends on account, organization, and text table.
		InitializeContactTable.createTable(psql, logbook); // Depends on account, location, organization, and text table
		InitializeUserTable.createTable(psql, logbook); // No dependencies
		InitializeProjectTable.createTable(psql, logbook); // Depends on account, location, organization, and text table.
		InitializeUserOrganizationTable.createTable(psql, logbook); // Depends on user, organization, contact
		InitializeTaskTable.createTable(psql, logbook); // Depends on text, org, account, location, project, contact
		//InitializeGroupTable.createTable(psql, logbook);
		InitializeTokenTable.createTable(psql, logbook); // Depends on user, organization
	}
}
