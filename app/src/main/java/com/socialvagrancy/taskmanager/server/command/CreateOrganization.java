//===================================================================
// CreateOrganization.java
// 	Description:
// 		Inserts a new organization into the organization table.
// 		The organization is the segmentation unit allowing multiple
// 		groups to use the same task manager database without
// 		bleed through or using the server for multiple tasks (work
// 		and personal).
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class CreateOrganization
{
	public static UUID insertNew(String name, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.WARN("Creating new organziation [" + name + "]");

		UUID id = UUID.randomUUID();
		
		String query = "INSERT INTO organization (id, name) VALUES (?, ?);";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, id);
			pst.setString(2, name);

			pst.execute();

			return id;
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			logbook.ERR("Unable to create organization [" + name + "].");
			throw new Exception("Unable to create organization [" + name + "]");
		}
	}
}
