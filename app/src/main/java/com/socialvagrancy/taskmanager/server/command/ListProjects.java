//===================================================================
// ListProjects.java
// 	Descritpion:
// 		Lists the projects associated with the account.
//
// 	Options:
// 		listByStatus - list projects based on whether they are
// 		active or inactive.
//
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Project;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ListProjects
{
	public static ArrayList<Project> byStatus(String account_name, String org_id, String is_active, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.INFO("Fetching list of projects associated with account [" + account_name + "]...");
		
		ArrayList<Project> project_list = new ArrayList<Project>();
		Project project;

		// Assume is active is true. Not specified will result in true.
		boolean active = true;

		if(is_active != null && is_active.equals("false"))
		{
			active = false;
		}
	
		String query = "SELECT project.id, project.name, project.account_id, project.location_id, project.text_id, project.active FROM project "
				+ "INNER JOIN account ON account.id = project.account_id "
				+ "WHERE account.name=? AND project.organization_id=? AND active=? "
				+ "ORDER BY project.name ASC;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, account_name);
			pst.setObject(2, UUID.fromString(org_id));
			pst.setBoolean(3, active);

			ResultSet rs = pst.executeQuery();

			while(rs.next())
			{
				project = new Project();

				project.setId(rs.getString(1))
					.setName(rs.getString(2))
					.setAccountId(rs.getString(3))
					.setLocationId(rs.getString(4))
					.setDescriptionId(rs.getString(5))
					.setActive(rs.getBoolean(6));

				project_list.add(project);
			}

			return project_list;
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			throw new Exception("Unable to list projects for account [" + org_id + ":" + account_name + "]");
		}

	}
}
