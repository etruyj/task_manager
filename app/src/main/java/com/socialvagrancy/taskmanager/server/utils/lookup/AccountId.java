//===================================================================
// AccountId.java
//	Description:
//		Convert account information to an account id.
//===================================================================

package com.socialvagrancy.taskmanager.server.utils.lookup;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AccountId
{
	public static String fromName(String account, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.debug("Searching for account id for account: " + account);

		String query = "SELECT id FROM account "
			+ "WHERE name=? AND organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, account);
			pst.setObject(2, UUID.fromString(org_id));

			logbook.debug(pst.toString());

			ResultSet rs = pst.executeQuery();

			if(rs.next())
			{
				return rs.getString("id");
			}
			
			return "";
		}
		catch(SQLException e)
		{
			logbook.error(e.getMessage());
			throw new Exception("Unable to find ID for account: " + account);
		}
	}
}
