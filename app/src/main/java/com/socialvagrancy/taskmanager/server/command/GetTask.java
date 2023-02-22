//===================================================================
// GetTask.java
// 	Description:
// 		Returns details on a specific task.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Task;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class GetTask
{
	public static Task withId(String id, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.info("Fetching details on task: " + id);

		String query = "SELECT task.id AS task_id, task.subject AS subject, task.start_time, task.status, task.duration, text.text AS description, account.name AS account, contact.first_name, contact.last_name, location.name AS location, project.name AS project FROM task " 
			+ "INNER JOIN account ON account.id = task.account_id "
			+ "INNER JOIN contact ON contact.id = task.contact_id "
			+ "INNER JOIN location ON location.id = task.location_id "
			+ "INNER JOIN project ON project.id = task.project_id "
			+ "INNER JOIN text ON text.id = task.text_id "
			+ "WHERE task.id=? AND organization_id=?;";

		try
		{
			Task task = new Task();

			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, UUID.fromString(id));
			pst.setObject(2, UUID.fromString(org_id));

			ResultSet rs = pst.executeQuery();

			if(rs.next())
			{
				task.setAccount(rs.getString("account"));
				task.setContact(rs.getString("last_name") + ", " + rs.getString("first_name"));
				task.setDuration(rs.getInt("duration"));
				task.setId(rs.getString("task_id"));
				task.setLocation(rs.getString("location"));
				task.setProject(rs.getString("project"));
				task.setStartTime(rs.getString("start_time"));
				task.setStatus(rs.getString("status"));
				task.setSubject(rs.getString("subject"));
				
			}

			return task;
		}
		catch(SQLException e)
		{
			logbook.error(e.getMessage());
			throw new Exception("Unable to fetch task [" + id + "].");
		}
	}
}
