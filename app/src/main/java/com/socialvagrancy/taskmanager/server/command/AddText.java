//===================================================================
// AddText.java
// 	Description:
// 		Adds and updates text. Returns the UUID of the text
// 		entry if successful..
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class AddText
{
	public static boolean deleteText(UUID text_id, UUID org_id, PostgresConnector psql, Logger logbook)
	{
		// Delete text from the text tabled based on uuid.

		logbook.INFO("Deleting text with id [" + text_id.toString() + "]");

		String query = "DELETE FROM text WHERE id=? AND organization_id=?";

		try
		{
			PreparedStatement pst = psql. prepare(query, logbook);

			pst.setObject(1, text_id);
			pst.setObject(2, org_id);

			pst.executeUpdate();

			return true;
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			logbook.ERR("Failed to delete text.");

			return false;
		}
	}

	public static UUID insertNew(String text, UUID org_id, PostgresConnector psql, Logger logbook)
	{
		UUID uuid = UUID.randomUUID();

		logbook.INFO("Inserting text with id [" + uuid.toString() + "]");

		String query = "INSERT INTO text (id, text, organization_id) VALUES (?, ?, ?)";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);
			
			pst.setObject(1, uuid);
			pst.setString(2, text);
			pst.setObject(3, org_id);

			pst.executeUpdate();

			return uuid;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			logbook.ERR(e.getMessage());
			logbook.ERR("Failed to add text to database.");

			return null;
		}

		
	}

	public static UUID updateText(String text, UUID text_id, UUID org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		if(text_id == null)
		{
			return insertNew(text, org_id, psql, logbook);	
		}
		else
		{
			String query = "UPDATE text SET text=? "
			       		+ "WHERE id=? AND organization_id=?;";

			try
			{
				PreparedStatement pst = psql.prepare(query, logbook);

				pst.setString(1, text);
				pst.setObject(2, text_id);
				pst.setObject(3, org_id);

				pst.executeUpdate();

				return text_id;
			}	
			catch(SQLException e)
			{
				logbook.ERR(e.getMessage());
				throw new Exception("Unable to add text to text table.");
			}
		}
	}
}
