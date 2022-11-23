//===================================================================
// InitializeUserOrganizationTable.java
// 	Description:
// 		Initializes the User-Organization that links the user
// 		to an organization and an account contact.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitializeUserOrganizationTable
{
	public static boolean createTable(PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Creating table: USER_ORGANIZATION");

		String query = "CREATE TABLE IF NOT EXISTS user_organization ("
				+ "id UUID PRIMARY KEY, "
				+ "user_id UUID NOT NULL, "
				+ "organization_id UUID NOT NULL, "
				+ "contact_id UUID NOT NULL, "
				+ "permissions INT, "
				+ "FOREIGN KEY (user_id) REFERENCES client_user (id), "
				+ "FOREIGN KEY (organization_id) REFERENCES organization (id), "
				+ "FOREIGN KEY (contact_id) REFERENCES contact (id));";

		if(!psql.update(query, logbook))
		{
			logbook.ERR("Unable to create table: USER_ORGANIZATION");
			return false;
		}

		return true;
	}
}
