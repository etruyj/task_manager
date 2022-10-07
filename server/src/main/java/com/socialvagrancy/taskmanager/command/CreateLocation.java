//===================================================================
// CreateLocation.java
// 	Descripton:
// 		Create an account location.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

import java.util.UUID;

public class CreateLocation
{
	public static UUID parseThenCreate(String account_id, String name, String building, String street_1, String street_2, String city, String state, String postal_code, String country, String notes, PostgresConnector psql, Logger logbook)
	{
		logbook.INFO("Creating location: " + name);

		// Parse variables
		if(account_id == null)
		{
			logbook.ERR("No account id specified. Cancelling create account.");
		}
	
		String query = "INSERT INTO location (id, name, account_id, notes_text_id, building, street_1, street_2, city, state, postal_code, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		return null;
	}
}
