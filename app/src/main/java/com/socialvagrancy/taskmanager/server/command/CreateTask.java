//===================================================================
// CreateTask.java
// 	Description:
// 		The functions associated with adding tasks to the
// 		task table.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Task;
import com.socialvagrancy.taskmanager.structure.TaskStatus;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.UUID;

public class CreateTask
{
	public static void deleteTask(Task task, UUID org_id, PostgresConnector psql, Logger logbook)
	{
		logbook.WARN("Deleting task [" + org_id + ":" + task.accountId() + ":task/" + task.id() +"]: " + task.subject());

		String query = "DELETE FROM task " 
				+ "WHERE id=? AND organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, UUID.fromString(task.id()));
			pst.setObject(2, org_id);

			pst.execute();
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			logbook.ERR("Unable to delete task [" + org_id + ":" + task.accountId() + ":task/" + task.id() + "]: " + task.subject());
		}
	}

	public static Task insertNew(Task task, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		String query = "INSERT INTO task (id, subject, text_id, start_time, duration, status, organization_id, account_id, contact_id, location_id, project_id, recurrance_id, automate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, UUID.fromString(task.id()));
			pst.setString(2, task.subject());
			
			// Allow for no assigned description
			if(task.descriptionId() == null)
			{
				pst.setObject(3, null);
			}
			else
			{
				pst.setObject(3, UUID.fromString(task.descriptionId()));
			}

			pst.setTimestamp(4, Timestamp.valueOf(task.startTime()));
			pst.setInt(5, task.duration());
			pst.setObject(6, task.status(), Types.OTHER);
			pst.setObject(7, UUID.fromString(org_id));
			pst.setObject(8, UUID.fromString(task.accountId()));
			pst.setObject(9, UUID.fromString(task.contactId()));
			
			// Allow for no assigned location
			if(task.locationId() == null)
			{
				pst.setObject(10, null);
			}
			else
			{
				pst.setObject(10, UUID.fromString(task.locationId()));
			}
			
			// Allow for no assigned project
			if(task.projectId() == null)
			{
				pst.setObject(11, null);
			}
			else
			{
				pst.setObject(11, UUID.fromString(task.projectId()));
			}

			// Allow for no assigned recurrance
			if(task.recurranceId() == null)
			{
				pst.setObject(12, null);
			}
			else
			{
				pst.setObject(12, UUID.fromString(task.recurranceId()));
			}

			pst.setBoolean(13, task.automate());
			
			pst.executeUpdate();
	
			return task;
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			throw new Exception("Failed to put task: " + task.subject());
		}
	}

	public static Task parseThenCreate(Task task, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		//===============================
		// Parse for exceptions
		// 
		// Requires
		// 	- org_id
		// 	- subject
		// 	- start time
		// 	- duration
		// 	- account_id
		// 	- contact_id
		//===============================

		if(org_id == null)
		{
			throw new Exception("Required field: organization_id is not specified.");
		}
		else if(task.subject() == null)
		{
			throw new Exception("Required field: subject is not specified.");
		}
		else if(task.startTime() == null)
		{
			throw new Exception("Required field: start_time is not specified.");
		}
		else if(task.duration() == null)
		{
			throw new Exception("Required field: duration is not specified.");
		}
		else if(task.accountId() == null)
		{
			throw new Exception("Required field: account_id is not specified.");
		}
		else if(task.contactId() == null)
		{
			throw new Exception("Required field: contact_id is not specified.");
		}

		//===============================
		// Fill Variables
		//===============================
		
		UUID id = UUID.randomUUID();

		task.setId(id.toString());

		if(task.status() == null)
		{
			task.setStatus(TaskStatus.READY);
		}

		//===============================
		// Create Task
		//===============================
		
		try
		{
			if(task.description() != null)
			{
				UUID text_id = AddText.insertNew(task.description(), UUID.fromString(org_id), psql, logbook);

				task.setDescriptionId(text_id.toString());
			}

			insertNew(task, org_id, psql, logbook);

			return task;
		}
		catch(Exception e)
		{
			if(task.descriptionId() != null)
			{
				AddText.deleteText(UUID.fromString(task.descriptionId()), UUID.fromString(org_id), psql, logbook);
			}

			logbook.ERR(e.getMessage());
			throw new Exception("Unable to create task [" + org_id + ":" + task.accountId() + ":" + task.id() + "]: " + task.subject());
		}
	}
}
