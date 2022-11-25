//====================================================================
// PostgresConnector.java
// 	Description:
// 		Handles connections to the postgres database.
//====================================================================

package com.socialvagrancy.taskmanager.server.utils.database;

import com.socialvagrancy.utils.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresConnector
{
	private String db_server;
	private String db_table;
	private String db_user;
	private String db_pass;

	public PostgresConnector(String server, String table, String user, String pass)
	{
		db_server = server;
		db_table = table;
		db_user = user;
		db_pass = pass;
	}

	public boolean update(String query, Logger logbook)
	{
		Connection cxn;
		Statement stmt;

		try
		{
			logbook.INFO("Connecting to database postgresql://" + db_server + ":5432/" + db_table + " with user " + db_user);

			Class.forName("org.postgresql.Driver");

			cxn = DriverManager.getConnection("jdbc:postgresql://" + db_server + ":5432/" + db_table, db_user, db_pass);

			stmt = cxn.createStatement();

			logbook.INFO("Querying database...");
			ResultSet results = stmt.executeQuery(query);

			results.close();
			stmt.close();
			cxn.close();
		}
		catch(Exception e)
		{
			if(!e.getMessage().equals("No results were returned by the query."))
			{
				System.err.println(e.getMessage());
				logbook.ERR(e.getMessage());
				logbook.ERR(query);
		
				return false;
			}
			else
			{
				// No results returned by the query.
				// This is expected behavior for update table
				// and true can be returned.
				return true;
			}
		}

		return true;
	}

	public PreparedStatement prepare(String query, Logger logbook)
	{
		Connection cxn;
		Statement stmt;

		try
		{
			Class.forName("org.postgresql.Driver");

			cxn = DriverManager.getConnection("jdbc:postgresql://" + db_server + ":5432/" + db_table, db_user, db_pass);

			return cxn.prepareStatement(query);
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			logbook.ERR(e.getMessage());
			logbook.ERR(query);
	
			return null;
		}
	}
}
