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
import java.util.UUID;

public class AddText
{
	public static UUID insertNew(String text, PostgresConnector psql, Logger logbook)
	{
		UUID uuid = UUID.randomUUID();

		logbook.INFO("Inserting text");

		String query = "INSERT INTO text (id, text) VALUES (?, ?)";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);
			
			pst.setObject(1, uuid);
			pst.setString(2, text);

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
}
