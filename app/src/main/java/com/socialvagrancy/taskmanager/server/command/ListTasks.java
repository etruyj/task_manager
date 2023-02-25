//===================================================================
// ListTasks.java
// 	Description:
// 		Provides a list of tasks based on the specified 
// 		requirements.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.converter.ConvertContact;
import com.socialvagrancy.taskmanager.server.utils.converter.ConvertDate;
import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Task;
import com.socialvagrancy.taskmanager.structure.TaskStatus;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public class ListTasks
{
	public static ArrayList<Task> byStatus(String contact, String range_start, String range_end, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.INFO("Fetching tasks for user [" + contact + "] between range [" + range_start + " > " + range_end + "].");

		ArrayList<Task> task_list = new ArrayList<Task>();
		Task task;

		String query = "SELECT id, subject, text_id, status, start_time, duration, account_id, location_id, project_id FROM task "
		       		+ "WHERE contact_id=? AND organization_id=? AND start_time>=? AND start_time<=?"
				+ "ORDER BY start_time ASC;";

		try
		{
			// Convert the contact name to the UUID which is what is used to search for tasks.
			String contact_id = ConvertContact.fullnameToUUID(contact, org_id, psql, logbook);

			// Convert the yyyy-MM-ddTHH:mm:ss timestamp
			// passed by the client to a parseable format
			// yy-MM-dd HH:mm:ss
			/*
			 * Mark for deletion
			String start = ConvertDate.timestampToPsql(range_start);
			String end = ConvertDate.timestampToPsql(range_end);
			*/

			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, UUID.fromString(contact_id));
			pst.setObject(2, UUID.fromString(org_id));
			pst.setTimestamp(3, Timestamp.valueOf(range_start));
			pst.setTimestamp(4, Timestamp.valueOf(range_end));

			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				task = new Task();

				task.setId(rs.getString(1))
					.setSubject(rs.getString(2))
					.setDescription(rs.getString(3))
					.setStatus(rs.getString(4))
					.setStartTime(rs.getString(5))
					.setDuration(rs.getInt(6))
					.setAccount(rs.getString(7))
					.setLocation(rs.getString(8))
					.setProject(rs.getString(9));

				task_list.add(task);
			}

			return task_list;
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			throw new Exception("Unable to list tasks for user [" + contact + "]");
		}	
	}
}
