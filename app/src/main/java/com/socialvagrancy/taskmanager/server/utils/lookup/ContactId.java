//===================================================================
// ContacttId.java
//	Description:
//		Convert contact information to an contact id.
//===================================================================

package com.socialvagrancy.taskmanager.server.utils.lookup;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ContactId
{
	public static String fromName(String contact, String account_id, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		String[] name_parts = contact.split(", ");
		String first = name_parts[name_parts.length-1];
		String last = "";

		for(int i=0; i<name_parts.length-1; i++)
		{
			last += name_parts[i] + ", ";
		}

		last = last.substring(0, last.length()-2);

		logbook.debug("Searching for contact id for contact: " + last + ", " + first);


		String query = "SELECT id FROM contact "
			+ "WHERE first_name=? AND last_name=? AND account_id=? AND organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, first);
			pst.setString(2, last);
			pst.setObject(3, UUID.fromString(account_id));
			pst.setObject(4, UUID.fromString(org_id));

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
			throw new Exception("Unable to find ID for contact: " + contact);
		}
	}
}
