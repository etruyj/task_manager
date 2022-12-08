//===================================================================
// InitializeTaskTable.java
// 	Description:
// 		Creates the task table in the database
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.TaskStatus;
import com.socialvagrancy.utils.Logger;

import java.lang.StringBuilder;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitializeTaskTable
{
	public static void createEnum(PostgresConnector psql, Logger logbook)
	{
		// Craft the Enum from the defined structure

	       	logbook.INFO("Creating enum: TASK_STATUS");

		StringBuilder query = new StringBuilder();
			
		query.append("CREATE TYPE task_status AS ENUM(");

		for(TaskStatus status : TaskStatus.values())
		{
			query.append("'" + status + "',");
		}

		// Remove the final comma
		query.deleteCharAt(query.length()-1);

		query.append(");");

		if(!psql.update(query.toString(), logbook))
		{
			logbook.ERR("Unable to create enum: TASK_STATUS");
		}
	}

	public static boolean createTable(PostgresConnector psql, Logger logbook)
	{
		createEnum(psql, logbook);

		logbook.INFO("Creating table: TASK");

		String query = "CREATE TABLE IF NOT EXISTS task ("
				+ "id UUID PRIMARY KEY, "
				+ "subject VARCHAR NOT NULL, "
				+ "text_id UUID, "
				+ "start_time TIMESTAMP WITH TIME ZONE NOT NULL, "
				+ "duration INT NOT NULL, "
				+ "status task_status, "
				+ "contact_id UUID NOT NULL, "
				+ "organization_ID UUID NOT NULL, "
				+ "account_id UUID NOT NULL, "
				+ "location_id UUID, "
				+ "project_id UUID, "
				+ "recurrance_id UUID, "
				+ "automate BOOLEAN, "
				+ "FOREIGN KEY (text_id) REFERENCES text(id), "
				+ "FOREIGN KEY (contact_id) REFERENCES contact(id), "
				+ "FOREIGN KEY (organization_id) REFERENCES organization(id), "
				+ "FOREIGN KEY (account_id) REFERENCES account(id), "
				+ "FOREIGN KEY (location_id) REFERENCES location(id), "
				+ "FOREIGN KEY (project_id) REFERENCES project(id));";
				// Add in v1.1
				// + "FOREIGN KEY (recurrance_id) REFERENCES task_recurrance(id));";
	
		if(!psql.update(query, logbook))
		{
			logbook.ERR("Unable to create table: TASK");
			return false;
		}

		return true;
	}
}
