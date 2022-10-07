//===================================================================
// ListAccounts.java
// 	Provides a list of accounts and ids from the account table
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.structures.Account;
import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListAccounts
{
	public static ArrayList all(PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Fetching list of accounts...");

		ArrayList<Account> account_list = new ArrayList<Account>();
		Account account;
	
		String query = "SELECT name, account.id, text.text FROM account "
			+ " LEFT JOIN text ON text.id = account.desc_text_id "
			+ " ORDER BY account.name DESC;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			ResultSet results = pst.executeQuery();

			while(results.next())
			{
				account = new Account();
				account.setName(results.getString(1));
				account.setId(results.getString(2));
				account.setDesc(results.getString(3));

				account_list.add(account);
			}

			logbook.INFO("Found (" + account_list.size() + ") accounts.");

		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			logbook.ERR("Failed to fetch list of accounts.");

		}
		
		return account_list;
	}
	
	public static ArrayList search(String search, PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Fetching list of accounts...");

		ArrayList<Account> account_list = new ArrayList<Account>();
		Account account;
	
		String query = "SELECT name, account.id, text.text FROM account "
			+ " LEFT JOIN text ON text.id = account.desc_text_id "
			+ " WHERE name LIKE ? "
			+ " ORDER BY account.name DESC;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			search += "%";

			pst.setString(1, search);

			query = pst.toString();

			ResultSet results = pst.executeQuery();

			while(results.next())
			{
				account = new Account();
				account.setName(results.getString(1));
				account.setId(results.getString(2));
				account.setDesc(results.getString(3));

				account_list.add(account);
			}

			logbook.INFO("Found (" + account_list.size() + ") accounts.");

		}
		catch(SQLException e)
		{
			logbook.ERR(query);
			logbook.ERR(e.getMessage());
			logbook.ERR("Failed to fetch list of accounts.");

		}
		
		return account_list;
	}
}
