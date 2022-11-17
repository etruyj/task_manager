//===================================================================
// DatabaseConnector.java
//	This class handles all connections and queires to the
//	mySQL database. This will only handle the connection.
//	The DatabaseController handles formatting calls and 
//	processing the replies.
//===================================================================

package com.socialvagrancy.taskmanager.server.utils.database;

import com.socialvagrancy.utils.Logger;

import java.sql.Connection;
import java.sql.DriverManager;


public class MySQLConnector
{
	private Connection conn;
	private Logger logs;

	public MySQLConnector(String url, String database, String user, String password, Logger logger)
	{
		logs = logger;

		try
		{
			String driver = "org.gjt.mm.mysql.Driver";
			Class.forName(driver);

			conn = DriverManager.getConnection("jbdc:mysql://" + url+ ":" + database, user, password);
		}
		catch(Exception e)
		{
			logs.log(e.getMessage(), 3);
		}

	}
}
