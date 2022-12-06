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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CreateAccount
{
	public static void deleteAccount(Account account, UUID org_id, PostgresConnector psql, Logger logbook)
	{
		logbook.WARN("Deleting account [" + account.name() + "]");
		
		String query = "DELETE FROM account "
			+ "WHERE id=? AND organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, UUID.fromString(account.id()));
			pst.setObject(2, org_id);

			pst.executeUpdate();

		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			logbook.ERR("Unable to delete account [" + account.name() + "]");
		}
	}

	public static boolean isDuplicate(String name, UUID org_id, PostgresConnector psql, Logger logbook)
	{
		String query = "SELECT id FROM account "
			+ "WHERE name=? AND organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, name);
			pst.setObject(2, org_id);

			ResultSet rs = pst.executeQuery();

			if(rs.next())
			{
				// Results returned = account/org pair exists.
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			logbook.ERR("Unabled to determine if account [" + name + "] exists.");
			return true;
		}
	}

	public static Account nameOnly(String name, UUID org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		UUID uuid = UUID.randomUUID();

		if(isDuplicate(name, org_id, psql, logbook))
		{
			throw new Exception("Account [" + name + "] already exists.");
		}

		Account account = new Account();
		account.setId(uuid.toString());
		account.setName(name);
		account.setAbbreviation(name);

		String query = "INSERT INTO account (id, name, abbreviation, text_id, organization_id) VALUES (?, ?, ?, ?, ?)";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);
			
			pst.setObject(1, UUID.fromString(account.id()));
			pst.setString(2, account.name());
			pst.setString(3, account.abbreviation());
			pst.setObject(4, null);
			pst.setObject(5, org_id);

			pst.executeUpdate();

		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			logbook.ERR(e.getMessage());

			throw new Exception("Failed to create account: " + name);
		}

		return account;
	}

	public static Account nameWithDescription(Account account, UUID org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		UUID account_id = UUID.randomUUID();

		if(isDuplicate(account.name(), org_id, psql, logbook))
		{
			throw new Exception("Account [" + account.name() + "] already exists.");
		}
		
		UUID text_id = null;

		if(account.description() != null)
		{
			text_id = AddText.insertNew(account.description(), org_id, psql, logbook);
		}

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
				return nameOnly(account.name(), org_id, psql, logbook);
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
				String query = "INSERT INTO account (id, name, abbreviation, text_id, organization_id) VALUES (?, ?, ?, ?, ?);";
				PreparedStatement pst = psql.prepare(query, logbook);
			
				pst.setObject(1, UUID.fromString(account.id()));
				pst.setString(2, account.name());
				pst.setString(3, account.name());
				pst.setObject(4, UUID.fromString(account.description()));
				pst.setObject(5, org_id);

				pst.executeUpdate();

			}
			catch(SQLException e)
			{
				AddText.deleteText(UUID.fromString(account.description()), org_id, psql, logbook);

				System.err.println(e.getMessage());
				logbook.ERR(e.getMessage());
				logbook.ERR("Failed to put account: " + account.name());

				throw new Exception(e.getMessage());
			}
		}
				
		return account;
	}
}
