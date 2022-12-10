//===================================================================
// UpdateLocation.java
// 	Description:
// 		Updates the Location field in the the database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.server.command.AddText;
import com.socialvagrancy.taskmanager.structure.Location;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class UpdateLocation
{
	public static Location byUUID(Location location, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.INFO("Updating location [" + org_id + ":" + location.id() + "]: " + location.name());

		String query = "UPDATE location SET name=?, building=?, street_1=?, street_2=?, city=?, state=?, postal_code=?, country=?, text_id=? "
				+ "WHERE id=? AND organization_id=?;";


		// Update the text field.
		UUID text_id = null;

		if(location.notes() == null || location.notes().equals("") && location.notesId() != null)
		{
			AddText.deleteText(UUID.fromString(location.notesId()), UUID.fromString(org_id), psql, logbook);
		}
		else
		{
			text_id = AddText.updateText(location.notes(), UUID.fromString(location.notesId()), UUID.fromString(org_id), psql, logbook);
		
		}

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, location.name());
			pst.setString(2, location.building());
			pst.setString(3, location.street1());
			pst.setString(4, location.street2());
			pst.setString(5, location.city());
			pst.setString(6, location.state());
			pst.setString(7, location.postalCode());
			pst.setString(8, location.country());
			
			if(text_id == null)
			{
				pst.setObject(9, null);
			}
			else
			{
				pst.setObject(9, text_id);
			}

			pst.setObject(10, UUID.fromString(location.id()));
			pst.setObject(11, UUID.fromString(org_id));

			pst.executeUpdate();
			
			if(text_id != null)
			{
				location.setNotesId(text_id.toString());
			}
			else
			{
				location.setNotesId(null);
			}

			return location;
		}
		catch(SQLException e)
		{
			if(location.notesId() == null && text_id != null)
			{
				AddText.deleteText(text_id, UUID.fromString(org_id), psql, logbook);
			}

			logbook.ERR(e.getMessage());
			throw new Exception("Unable to update location [" + org_id + ":" + location.accountId() + ":" + location.id() + "]: " + location.name());
		}
	}

}
