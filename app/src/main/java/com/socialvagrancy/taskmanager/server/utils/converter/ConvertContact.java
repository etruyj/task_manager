//===================================================================
// ConvertContact.java
// 	Description:
// 		Database queries required to convert the contact
// 		information received by the API to a different format.
//===================================================================

package com.socialvagrancy.taskmanager.server.utils.converter;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ConvertContact
{
	public static String fullnameToUUID(String name, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		// Split the information passed from fullname
		// to individual first name and last name.
		// fullname format is lastname, firstname
		// Split on ", ". The last item in the list
		// will be the first name. Everything else will
		// need to be reconsolidated to make the last name.
		String[] name_parts = name.split(", ");

		// Last index is the first name.
		String first_name = name_parts[name_parts.length-1];

		// All other indicies are last name.
		// Using string vs stringbuilder as I anticipate 
		// small array sizes if any.
		String last_name = "";

		// Use length - 1 to omit the last index: first name
		for(int i=0; i<name_parts.length-1; i++)
		{
			last_name += name_parts[i] + " ";
		}

		try
		{
			String query = "SELECT id FROM contact "
				+ "WHERE first_name=? AND last_name=? AND organization_id=?;";

			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, first_name);
			pst.setString(2, last_name);
			pst.setObject(3, UUID.fromString(org_id));

			ResultSet rs = pst.executeQuery();

			System.err.println("query: " + pst);

			if(rs.first())
			{
				String id = rs.getString(1);

				logbook.debug("Mapped contact [" + name + "] to UUID [" + id + "]");

				return id;
			}
			else
			{
				logbook.WARN("Unabled to find id for user [" + name + "]");
				return "";
			}
		}
		catch(SQLException e)
		{
			logbook.error(e.getMessage());
			throw new Exception("Unable to find UUID for contact: " + name);
		}
	}
}
