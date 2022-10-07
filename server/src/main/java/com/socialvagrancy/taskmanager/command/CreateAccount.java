//===================================================================
// CreateAccount.java
// 	Description:
// 		Add account to database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.structures.ServerResponse;
import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class CreateAccount
{
	public static ServerResponse nameOnly(String name, PostgresConnector psql, Logger logbook)
	{
		ServerResponse response = new ServerResponse();

		UUID uuid = UUID.randomUUID();

		logbook.INFO("Creating account: " + name);

		String query = "INSERT INTO account (id, name) VALUES (?, ?)";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);
			
			pst.setObject(1, uuid);
			pst.setString(2, name);

			pst.executeUpdate();

			response.setId(uuid.toString());
			response.setMessage("Successfully created account: " + name);

			return response;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			logbook.ERR(e.getMessage());
			logbook.ERR("Failed to put account: " + name);

			response.setMessage(e.getMessage());
			return response;
		}

		
	}

	public static ServerResponse nameWithDescription(String name, String text, PostgresConnector psql, Logger logbook)
	{
		ServerResponse response = new ServerResponse();

		UUID account_id = UUID.randomUUID();

		logbook.INFO("Creating account with text: " + name);

		UUID text_id = AddText.insertNew(text, psql, logbook);
	
		if(text_id == null)
		{
			logbook.WARN("Unable to add account text. Creating account with description.");
			return nameOnly(name, psql, logbook);
		}
		else
		{
			try
			{
				String query = "INSERT INTO account (id, name, desc_text_id) VALUES (?, ?, ?);";
				PreparedStatement pst = psql.prepare(query, logbook);
			
				pst.setObject(1, account_id);
				pst.setString(2, name);
				pst.setObject(3, text_id);

				pst.executeUpdate();

				response.setId(account_id.toString());
				response.setMessage("Successfully created account: " + name);

				return response;
			}
			catch(SQLException e)
			{
				System.err.println(e.getMessage());
				logbook.ERR(e.getMessage());
				logbook.ERR("Failed to put account: " + name);

				response.setMessage(e.getMessage());

				return response;
			}
		}
	}
}
