//===================================================================
// CreateAccount.java
// 	Description:
// 		Add account to database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Account;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class CreateAccount
{
	public static Account nameOnly(String name, PostgresConnector psql, Logger logbook) throws Exception
	{
		UUID uuid = UUID.randomUUID();

		logbook.INFO("Creating account: " + name);

		Account account = new Account();
		account.setId(uuid.toString());
		account.setName(name);
		account.setAbbreviation(name);

		String query = "INSERT INTO account (id, name, abbreviation) VALUES (?, ?, ?)";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);
			
			pst.setObject(1, account.id());
			pst.setString(2, account.name());
			pst.setString(3, account.abbreviation());

			pst.executeUpdate();

		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			logbook.ERR(e.getMessage());
			logbook.ERR("Failed to put account: " + name);

			throw new Exception(e.getMessage());
		}

		return account;
	}

	public static Account nameWithDescription(Account account, PostgresConnector psql, Logger logbook) throws Exception
	{
		UUID account_id = UUID.randomUUID();

		logbook.INFO("Creating account with text: " + account.name());

		UUID text_id = AddText.insertNew(account.description(), psql, logbook);

		if(account.abbreviation() == null)
		{
			account.setAbbreviation(account.name());
		}

		account.setId(account_id.toString());
		account.setDesc(text_id.toString());

		if(text_id == null)
		{
			logbook.WARN("Unable to add account text. Creating account without a description.");
			
			try
			{
				return nameOnly(account.name(), psql, logbook);
			}
			catch(Exception e)
			{
				throw new Exception(e.getMessage());
			}
		}
		else
		{
			try
			{
				String query = "INSERT INTO account (id, name, abbreviation, desc_text_id) VALUES (?, ?, ?, ?);";
				PreparedStatement pst = psql.prepare(query, logbook);
			
				pst.setObject(1, account.id());
				pst.setString(2, account.name());
				pst.setString(3, account.name());
				pst.setObject(4, account.description());

				pst.executeUpdate();

			}
			catch(SQLException e)
			{
				System.err.println(e.getMessage());
				logbook.ERR(e.getMessage());
				logbook.ERR("Failed to put account: " + account.name());

				throw new Exception(e.getMessage());
			}
		}
				
		return account;
	}
}
