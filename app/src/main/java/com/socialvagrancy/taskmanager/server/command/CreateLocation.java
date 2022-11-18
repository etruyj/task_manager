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
	public static Location parseThenCreate(Location location, PostgresConnector psql, Logger logbook) throws Exception 
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
				+ "WHERE account_id=? AND name=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);
			
			pst.setObject(1, UUID.fromString(location.accountId()));
			pst.setString(2, location.name());

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

		if(location.notes() != null)
		{
			UUID notes_id = AddText.insertNew(location.notes(), psql, logbook);

			if(notes_id != null)
			{
				location.setNotesId(notes_id.toString());
			}
			else
			{
				location.setNotesId("");
			}
		}
		else
		{
			location.setNotesId("");
		}

		location = insertNew(location, psql, logbook);

		if(location == null)
		{
			throw new Exception("Unabled to create new location.");
		}

		return location;
	}

	public static Location insertNew(Location location, PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Querying " + location.name());

		String query = "INSERT INTO location (id, name, account_id, notes_text_id, building, street_1, street_2, city, state, postal_code, country, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);
			
			logbook.INFO("Preparing " + location.name());

			pst.setObject(1, UUID.fromString(location.id()));
			logbook.INFO("Setting name");
			pst.setString(2, location.name());
			logbook.INFO("Setting account");
			pst.setObject(3, UUID.fromString(location.accountId()));
			logbook.INFO("Setting notes");
			pst.setObject(4, UUID.fromString(location.notesId()));
			logbook.INFO("Setting building");
			pst.setString(5, location.building());
			pst.setString(6, location.street1());
			pst.setString(7, location.street2());
			logbook.INFO("Setting city");
			pst.setString(8, location.city());
			pst.setString(9, location.state());
			pst.setString(10, location.postalCode());
		       	pst.setString(11, location.country()); 	
			logbook.INFO("Setting boolean");
			pst.setBoolean(12, true);

			logbook.INFO("Executing");

			pst.executeUpdate();
		
			return location;	
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			logbook.ERR(e.getMessage());
			logbook.ERR("Failed to put location: " + location.name());

			return null;
		}
	}
}
