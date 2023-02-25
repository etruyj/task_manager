//===================================================================
// ProjectId.java
//	Description:
//		Convert projectt information to an project id.
//===================================================================

package com.socialvagrancy.taskmanager.server.utils.lookup;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProjectId
{
	public static String fromName(String project, String account_id, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.debug("Searching for project id for project: " + project);

		String query = "SELECT id FROM project "
			+ "WHERE name=? AND account_id=? AND organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, project);
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
			throw new Exception("Unable to find ID for project: " + project);
		}
	}
}
