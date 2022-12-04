//===================================================================
// InitializeUserOrganizationTable.java
// 	Description:
// 		Initializes the User-Organization that links the user
// 		to an organization and an account contact.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.server.PermissionLevel;
import com.socialvagrancy.utils.Logger;

import java.lang.StringBuilder;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitializeUserOrganizationTable
{
	public static boolean createTable(PostgresConnector psql, Logger logbook)
	{
		createEnum(psql, logbook);

		logbook.INFO("Creating table: USER_ORGANIZATION");
		
		String query = "CREATE TABLE IF NOT EXISTS user_organization ("
				+ "id UUID PRIMARY KEY, "
				+ "user_id UUID NOT NULL, "
				+ "organization_id UUID NOT NULL, "
				+ "contact_id UUID NOT NULL, "
				+ "permissions permission_level, "
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

	public static void createEnum(PostgresConnector psql, Logger logbook)
	{
		// Craft the Enum from the defined structure/server/PermissionLevel.
	
		logbook.INFO("Creating enum: PERMISSION_LEVEL");
	
		StringBuilder query = new StringBuilder();
		PermissionLevel permissions;

		query.append("CREATE TYPE permission_level AS ENUM(");

		for(PermissionLevel privs : PermissionLevel.values())
		{
			query.append("'" + privs + "',");
		}
		
		// Remove the final comma
		query.deleteCharAt(query.length()-1);

		query.append(");");

		if(!psql.update(query.toString(), logbook))
		{
			logbook.ERR("Unable to create enum: PERMISSION_LEVEL");
		}
	}
}
