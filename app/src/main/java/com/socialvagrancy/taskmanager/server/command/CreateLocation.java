//===================================================================
// CreateLocation.java
// 	Descripton:
// 		Create an account location.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Location;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CreateLocation
{
	public static void deleteLocation(Location location, UUID org_id, PostgresConnector psql, Logger logbook)
	{
		logbook.WARN("Deleting location [" + location.name() + "]");

		String query = "DELETE FROM locaton "
			+ "WHERE id=? AND organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, UUID.fromString(location.id()));
			pst.setObject(2, org_id);

			pst.executeUpdate();
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			logbook.ERR("Unable to delete location [" + location.name() + "]");
		}
	}

	public static boolean isDuplicate(String name, UUID account_id, String org_id, PostgresConnector psql, Logger logbook)
	{
		String query = "SELECT id FROM location "
			+ "WHERE name=? AND account_id=? AND organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, name);
			pst.setObject(2, account_id);
			pst.setObject(3, UUID.fromString(org_id));

			ResultSet rs = pst.executeQuery();

			if(rs.next())
			{
				// Results mean the object exists in the table.
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			return true;
		}
	}

	public static Location withNameOnly(String name, UUID account_id, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		Location location = new Location();

		location.setName(name);
		location.setCity("");
		location.setCountry("");
		location.setAccountId(account_id.toString());

		try
		{
			return parseThenCreate(location, org_id, psql, logbook);
		}
		catch(Exception e)
		{
			throw e;
		}
		
	}

	public static Location parseThenCreate(Location location, String org_id, PostgresConnector psql, Logger logbook) throws Exception 
	{
		//===============================
		// Parse Exceptions
		// Requires:
		// 	- unique name
		// 	- account_id
		// 	- city
		// 	- country
		//===============================
		
		if(location.name() == null)
		{
			throw new Exception("Required Field: location name is not specified.");
		}
		else if(location.accountId() == null)
		{
			throw new Exception("Required Field: account is not specified.");
		}
		else if(location.city() == null)
		{
			throw new Exception("Required Field: city is not specified.");
		}
		else if(location.country() == null)
		{
			throw new Exception("Required Field: country is not specified.");
		}
		
		// Check for uniqueness
		String query = "SELECT id FROM location "
				+ "WHERE account_id=? AND name=? AND organization_id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);
			
			pst.setObject(1, UUID.fromString(location.accountId()));
			pst.setString(2, location.name());
			pst.setObject(3, UUID.fromString(org_id));

			ResultSet rs = pst.executeQuery();

			if(rs.next())
			{
				throw new Exception("Error: location name is not unique for this account.");
			}

		}
		catch(SQLException e)
		{
			throw new Exception(e.getMessage());
		}

		//===============================
		// Complete Fields
		//===============================
		
		logbook.INFO("Creating location: " + location.name());

		UUID uuid = UUID.randomUUID();

		location.setId(uuid.toString());


		//===============================
		// Create Notes
		//===============================

		if(location.notes() != null)
		{
			location.setNotes(" ");
		}

		UUID notes_id = AddText.insertNew(location.notes(), UUID.fromString(org_id), psql, logbook);

		if(notes_id != null)
		{
			location.setNotesId(notes_id.toString());
		}
		else
		{
			throw new Exception("Unabled to save notes.");
		}

		//===============================
		// Make Query
		//===============================
	
		Location created_location = insertNew(location, org_id, psql, logbook);

		if(created_location == null)
		{
			AddText.deleteText(UUID.fromString(location.notesId()), UUID.fromString(org_id), psql, logbook);

			throw new Exception("Unabled to create new location.");
		}

		return created_location;
	}

	public static Location insertNew(Location location, String org_id, PostgresConnector psql, Logger logbook)
	{
		String query = "INSERT INTO location (id, name, account_id, text_id, building, street_1, street_2, city, state, postal_code, country, active, organization_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);
			
			pst.setObject(1, UUID.fromString(location.id()));
			pst.setString(2, location.name());
			pst.setObject(3, UUID.fromString(location.accountId()));
			pst.setObject(4, UUID.fromString(location.notesId()));
			pst.setString(5, location.building());
			pst.setString(6, location.street1());
			pst.setString(7, location.street2());
			pst.setString(8, location.city());
			pst.setString(9, location.state());
			pst.setString(10, location.postalCode());
		       	pst.setString(11, location.country()); 	
			pst.setBoolean(12, true);
			pst.setObject(13, UUID.fromString(org_id));

			pst.executeUpdate();
		
			return location;	
		}
		catch(SQLException e)
		{
			// Delete corresponding text to keep the text table clean.
			AddText.deleteText(UUID.fromString(location.notesId()), UUID.fromString(org_id), psql, logbook);

			logbook.ERR(e.getMessage());
			logbook.ERR("Failed to put location: " + location.name());

			return null;
		}
	}
}
