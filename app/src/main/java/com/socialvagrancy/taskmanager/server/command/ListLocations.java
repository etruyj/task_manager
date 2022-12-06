//===================================================================
// ListLocations.java
// 	Description:
// 		Provide a list of locations 
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Location;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ListLocations
{
	public static ArrayList byStatus(String account_name, String org_id, boolean active_state, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.INFO("Fetching list of locations associated with account [" + account_name + "]...");

		ArrayList<Location> location_list = new ArrayList<Location>();
		Location location;

		String query = "SELECT location.id, location.name, building, street_1, street_2, city, state, postal_code, country, location.text_id FROM location "
			+ "INNER JOIN account ON account.id = location.account_id "
			+ "WHERE account.name=? AND active=? AND location.organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, account_name);
			pst.setBoolean(2, active_state);
			pst.setObject(3, UUID.fromString(org_id));

			ResultSet results = pst.executeQuery();

			while(results.next())
			{
				location = new Location();
				
				location.setId(results.getString(1));
				location.setName(results.getString(2));
				location.setBuilding(results.getString(3));
				location.setStreet1(results.getString(4));
				location.setStreet2(results.getString(5));
				location.setCity(results.getString(6));
				location.setState(results.getString(7));
				location.setPostalCode(results.getString(8));
				location.setCountry(results.getString(9));
				location.setNotesId(results.getString(10));

				location_list.add(location);
		
			}
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			throw new Exception("Failed to retrieve list of locations for account.");
		}
		
		return location_list;
	}
}
