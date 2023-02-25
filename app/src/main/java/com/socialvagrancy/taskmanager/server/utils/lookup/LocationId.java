//===================================================================
// LocationtId.java
//	Description:
//		Convert location information to an location id.
//===================================================================

package com.socialvagrancy.taskmanager.server.utils.lookup;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LocationId
{
	public static String fromName(String location, String account_id, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.debug("Searching for account id for location: " + location);

		String query = "SELECT id FROM location "
			+ "WHERE name=? AND account_id=? AND organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, location);
			pst.setObject(2, UUID.fromString(account_id));
			pst.setObject(3, UUID.fromString(org_id));

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
			throw new Exception("Unable to find ID for location: " + location);
		}
	}
}
