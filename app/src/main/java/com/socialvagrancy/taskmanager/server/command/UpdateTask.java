//===================================================================
// UpdateTask.java
// 	Description:
// 		Updates the task by id and organization id
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.command.AddText;
import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.server.utils.lookup.AccountId;
import com.socialvagrancy.taskmanager.server.utils.lookup.ContactId;
import com.socialvagrancy.taskmanager.server.utils.lookup.LocationId;
import com.socialvagrancy.taskmanager.server.utils.lookup.ProjectId;
import com.socialvagrancy.taskmanager.structure.Task;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.UUID;

public class UpdateTask
{
	public static Task withId(Task task, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.info("Updating task: " + org_id + ":" + task.id());

		String query = "UPDATE task SET "
		 		+ "subject=?, "
				+ "start_time=?, "
				+ "duration=?, "
				+ "status=?, "
				+ "account_id=?, "
				+ "project_id=?, "
				+ "location_id=?, "
				+ "text_id=? "
				+ "WHERE id=? AND organization_id=?;";

		try
		{
			//=================================
			// Convert human readable names to
			// ids.
			//=================================

			task.setAccount(AccountId.fromName(task.account(), org_id, psql, logbook));
			task.setContact(ContactId.forOwner(task.contact(), org_id, psql, logbook));
			if(!task.project().equals("[none]"))
			{
				task.setProject(ProjectId.fromName(task.project(), task.account(), org_id, psql, logbook));
			}
			else
			{
				task.setProject(null);
			}

			if(!task.location().equals("[none]"))
			{
				task.setLocation(LocationId.fromName(task.location(), task.account(), org_id, psql, logbook));
			}
			else
			{
				task.setLocation(null);
			}

			if(task.description() != null)
			{
				if(task.descriptionId() == null)
				{
					UUID text_id = AddText.insertNew(task.description(), UUID.fromString(org_id), psql, logbook);
					task.setDescriptionId(text_id.toString());	
				}
				else
				{
					AddText.updateText(task.description(), UUID.fromString(task.descriptionId()), UUID.fromString(org_id), psql, logbook);
				}
			}
			else
			{
				task.setDescriptionId(null);
			}

			//=================================
			// Update the task.
			//=================================
			
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, task.subject());
			pst.setObject(2, Timestamp.valueOf(task.startTime()));
			pst.setInt(3, task.duration());
			pst.setObject(4, task.status(), Types.OTHER);
			pst.setObject(5, UUID.fromString(task.account()));
			
			if(task.project() == null)
			{
				pst.setObject(6, null);
			}
			else
			{
				pst.setObject(6, UUID.fromString(task.project()));
			}

			if(task.location() == null)
			{
				pst.setObject(7, null);
			}
			else
			{
				pst.setObject(7, UUID.fromString(task.location()));
			}

			if(task.descriptionId() == null)
			{
				pst.setObject(8, null);
			}
			else
			{
				pst.setObject(8, UUID.fromString(task.descriptionId()));
			}

			pst.setObject(9, UUID.fromString(task.id()));
			pst.setObject(10, UUID.fromString(org_id));

			logbook.debug(pst.toString());

			ResultSet rs = pst.executeQuery();

			if(rs.next())
			{
				logbook.info("Task updated successfully");
				return task;
			}

			return null;
		}
		catch(SQLException e)
		{
			logbook.error(e.getMessage());
			throw new Exception("Unabled to update task: " + task.subject());
		}
	}
}
