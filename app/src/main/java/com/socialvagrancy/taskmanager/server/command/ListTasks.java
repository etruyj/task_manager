//===================================================================
// ListTasks.java
// 	Description:
// 		Provides a list of tasks based on the specified 
// 		requirements.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

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
	public static ArrayList<Task> byStatus(String contact_id, String range_start, String range_end, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.INFO("Fetching tasks for user [" + contact_id + "] between range [" + range_start + " > " + range_end + "].");

		ArrayList<Task> task_list = new ArrayList<Task>();
		Task task;

		String query = "SELECT id, subject, text_id, status, start_time, duration, account_id, location_id, project_id FROM task "
		       		+ "WHERE contact_id=? AND organization_id=? AND start_time>=? AND start_time<=?"
				+ "ORDER BY start_time ASC;";

		try
		{
			String start = range_start;
			String end = range_end;

			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, UUID.fromString(contact_id));
			pst.setObject(2, UUID.fromString(org_id));
			pst.setTimestamp(3, Timestamp.valueOf(start));
			pst.setTimestamp(4, Timestamp.valueOf(end));

			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				task = new Task();

				task.setId(rs.getString(1))
					.setSubject(rs.getString(2))
					.setDescriptionId(rs.getString(3))
					.setStatus(rs.getString(4))
					.setStartTime(rs.getString(5))
					.setDuration(rs.getInt(6))
					.setAccountId(rs.getString(7))
					.setLocationId(rs.getString(8))
					.setProjectId(rs.getString(9));

				task_list.add(task);
			}

			return task_list;
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			throw new Exception("Unable to list tasks for user [" + contact_id + "]");
		}	
	}
}
