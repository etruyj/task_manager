//===================================================================
// UpdateTask.java
// 	Description:
// 		Updates the task field in the the database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.server.command.AddText;
import com.socialvagrancy.taskmanager.structure.Task;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.UUID;

public class UpdateTask
{
	public static Task byUUID(Task task, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.INFO("Updating task [" + org_id + ":" + task.accountId() + ":task/" + task.id() + "]: " + task.subject());

		String query = "UPDATE task SET subject=?, text_id=?, start_time=?, duration=?, status=?, account_id=?, contact_id=?, location_id=?, project_id=?, recurrance_id=?, automate=? "
				+ "WHERE id=? AND organization_id=?;";


		// Update the text field.
		UUID text_id = null;
		
		if((task.description() == null || task.description().equals("")) && task.descriptionId() != null)
		{
			AddText.deleteText(UUID.fromString(task.descriptionId()), UUID.fromString(org_id), psql, logbook);
		}
		else if(task.description() != null && task.description().length() > 0)
		{
			text_id = AddText.updateText(task.description(), UUID.fromString(task.descriptionId()), UUID.fromString(org_id), psql, logbook);
		
		}

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, task.subject());

			// Allow for no description to exist
			if(text_id == null)
			{
				pst.setObject(2, null);
			}
			else
			{
				pst.setObject(2, text_id);
			}

			pst.setObject(3, Timestamp.valueOf(task.startTime()));
			pst.setInt(4, task.duration());
			pst.setObject(5, task.status(), Types.OTHER);
			pst.setObject(6, UUID.fromString(task.accountId()));
			pst.setObject(7, UUID.fromString(task.contactId()));

			// Allow for no location to be assigned.
			if(task.locationId() == null)
			{
				pst.setObject(8, null);
			}
			else
			{
				pst.setObject(8, task.locationId());
			}

			// Allow for no project to be assigned.
			if(task.projectId() == null)
			{
				pst.setObject(9, null);
			}
			else
			{
				pst.setObject(9, task.projectId());
			}
		
			// Allow for no recurrance id
			if(task.recurranceId() == null)
			{
				pst.setObject(10, null);
			}
			else
			{
				pst.setObject(10, task.recurranceId());
			}

			pst.setBoolean(11, task.automate());

			pst.setObject(12, UUID.fromString(task.id()));
			pst.setObject(13, UUID.fromString(org_id));

			pst.executeUpdate();
			
			if(text_id != null)
			{
				task.setDescriptionId(text_id.toString());
			}
			else
			{
				task.setDescriptionId(null);
			}

			return task;
		}
		catch(SQLException e)
		{
			if(task.descriptionId() == null && text_id != null)
			{
				AddText.deleteText(text_id, UUID.fromString(org_id), psql, logbook);
			}

			logbook.ERR(e.getMessage());
			throw new Exception("Unable to update task [" + org_id + ":" + task.accountId() + ":task/" + task.id() + "]: " + task.subject());
		}
	}

}
