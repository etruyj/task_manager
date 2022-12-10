//===================================================================
// UpdateAccount.java
// 	Description:
// 		Updates the account field in the the database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.server.command.AddText;
import com.socialvagrancy.taskmanager.structure.Account;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class UpdateAccount
{
	public static Account byUUID(Account account, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.INFO("Updating account [" + org_id + ":" + account.id() + "]: " + account.name());

		String query = "UPDATE account SET name=?, abbreviation=?, text_id=? "
				+ "WHERE id=? AND organization_id=?;";


		// Update the text field.
		UUID text_id = null;
		
		if(account.description() == null || account.description().equals("") && account.descriptionId() != null)
		{
			if(account.descriptionId() != null)
			{
				AddText.deleteText(UUID.fromString(account.descriptionId()), UUID.fromString(org_id), psql, logbook);
			}
		}
		else
		{
			text_id = AddText.updateText(account.description(), UUID.fromString(account.descriptionId()), UUID.fromString(org_id), psql, logbook);
		
		}

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, account.name());
			pst.setString(2, account.abbreviation());
			
			if(text_id == null)
			{
				pst.setObject(3, null);
			}
			else
			{
				pst.setObject(3, text_id);
			}
			
			pst.setObject(4, UUID.fromString(account.id()));
			pst.setObject(5, UUID.fromString(org_id));

			pst.executeUpdate();
			
			if(text_id != null)
			{
				account.setDescriptionId(text_id.toString());
			}
			else
			{
				account.setDescriptionId(null);
			}

			return account;
		}
		catch(SQLException e)
		{
			if(account.descriptionId() == null && text_id != null)
			{
				AddText.deleteText(text_id, UUID.fromString(org_id), psql, logbook);
			}

			logbook.ERR(e.getMessage());
			throw new Exception("Unable to update account [" + org_id + ":" + account.id() + "]: " + account.name());
		}
	}

}
