//===================================================================
// CreateProject.java
// 	Description:
// 		Inserts a new project into the project table
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Project;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CreateProject
{
	public static void deleteProject(Project project, UUID org_id, PostgresConnector psql, Logger logbook)
	{
		logbook.WARN("Deleting project [" + project.name() + "]");

		String query = "DELETE FROM project "
				+ "WHERE id=? AND organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, UUID.fromString(project.id()));
			pst.setObject(2, org_id);

			pst.execute();
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			logbook.ERR("Unable to delete project [" + project.name() + "]");
		}
	}

	public static Project insertNew(Project project, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{

		String query = "INSERT INTO project (id, name, active, account_id, location_id, text_id, organization_id) VALUES (?, ?, ?, ?, ?, ?, ?);";

		try
		{
			project.setActive(true);

			PreparedStatement pst = psql.prepare(query, logbook);
			
			pst.setObject(1, UUID.fromString(project.id()));
			pst.setString(2, project.name());
			pst.setBoolean(3, project.active());
			pst.setObject(4, UUID.fromString(project.accountId()));

			// Allowing projects to exist without locations	
			if(project.locationId() == null)
			{
				pst.setObject(5, null);
			}
			else
			{
				pst.setObject(5, UUID.fromString(project.locationId()));
			}
			
			// Allowing projects to exist without descriptions.
			if(project.descriptionId() == null)
			{
				pst.setObject(6, null);
			}
			else
			{
				pst.setObject(6, UUID.fromString(project.descriptionId()));
			}		
			
			pst.setObject(7, UUID.fromString(org_id));

			pst.executeUpdate();

			return project;
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			throw new Exception("Failed to put project: " + project.name());
		}
	}

	public static boolean isDuplicate(String project_name, UUID account_id, UUID org_id, PostgresConnector psql, Logger logbook)
	{
		String query = "SELECT id FROM project "
				+ "WHERE name=? AND account_id=? AND organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, project_name);
			pst.setObject(2, account_id);
			pst.setObject(3, org_id);

			ResultSet rs = pst.executeQuery();

			if(rs.next())
			{
				// Results mean the object exists in the table.
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			logbook.ERR("Failed to check if project [" + project_name + "] is a duplicate.");
			return true;
		}
	}

	public static Project parseThenCreate(Project project, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		//===============================
		// Parse Exceptions:
		// Requires:
		// 	-- unique name
		// 	-- account_id
		// 	-- org_id
		//===============================
	
		if(project.name() == null)
		{
			throw new Exception("Required field: project name is not specified.");
		}
		else if(project.accountId() == null)
		{
			throw new Exception("Required field: no account specified for account.");
		}
		else if(org_id == null)
		{
			throw new Exception("Required field: organization id is not specified.");
		}
		else if(isDuplicate(project.name(), UUID.fromString(project.accountId()), UUID.fromString(org_id), psql, logbook))
		{
			throw new Exception("Project already exists.");
		}

		//===============================
		// Fill in the fields
		//===============================

		UUID id = UUID.randomUUID();

		project.setId(id.toString());

		//===============================
		// Create Project
		//===============================

		try
		{
			if(project.description() != null)
			{
				UUID text_id = AddText.insertNew(project.description(), UUID.fromString(org_id), psql, logbook);

				project.setDescriptionId(text_id.toString());
			}
		

			insertNew(project, org_id, psql, logbook);

			return project;
		}
		catch(Exception e)
		{
			if(project.descriptionId() != null)
			{
				AddText.deleteText(UUID.fromString(project.descriptionId()), UUID.fromString(org_id), psql, logbook);
			}

			logbook.ERR(e.getMessage());
			throw new Exception("Unable to create project: " + project.name());
		}
	}	
}
