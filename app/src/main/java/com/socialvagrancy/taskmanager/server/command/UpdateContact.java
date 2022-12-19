//===================================================================
// UpdateContact.java
// 	Description:
// 		Updates the Contact field in the the database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.server.command.AddText;
import com.socialvagrancy.taskmanager.structure.Contact;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class UpdateContact
{
	public static Contact byUUID(Contact contact, String org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.INFO("Updating contact [" + org_id + ":" + contact.account() + ":contact/" + contact.id() + "]: " + contact.fullName());

		String query = "UPDATE contact SET first_name=?, last_name=?, phone=?, email=?, active=?, account_id=?, location_id=?, text_id=? "
				+ "WHERE id=? AND organization_id=?;";


		// Update the text field.
		UUID text_id = null;

		if(contact.notes() == null || contact.notes().equals("") && contact.textId() != null)
		{
			AddText.deleteText(UUID.fromString(contact.textId()), UUID.fromString(org_id), psql, logbook);
		}
		else
		{
			text_id = AddText.updateText(contact.notes(), UUID.fromString(contact.textId()), UUID.fromString(org_id), psql, logbook);
		
		}

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, contact.firstName());
			pst.setString(2, contact.lastName());
			pst.setString(3, contact.phone());
			pst.setString(4, contact.email());
			pst.setBoolean(5, contact.status());
			pst.setObject(6, UUID.fromString(contact.account()));
			
			// Allow for contacts to be created without a location.
			if(contact.location() == null)
			{
				pst.setObject(7, null);
			}
			else
			{
				pst.setObject(7, UUID.fromString(contact.location()));
			}

			// Allow for contacts to be created without notes.
			if(text_id == null)
			{
				pst.setObject(8, null);
			}
			else
			{
				pst.setObject(8, text_id);
			}

			pst.setObject(9, UUID.fromString(contact.id()));
			pst.setObject(10, UUID.fromString(org_id));

			pst.executeUpdate();
			
			if(text_id != null)
			{
				contact.setTextId(text_id.toString());
			}
			else
			{
				contact.setTextId(null);
			}

			return contact;
		}
		catch(SQLException e)
		{
			if(contact.textId() == null && text_id != null)
			{
				AddText.deleteText(text_id, UUID.fromString(org_id), psql, logbook);
			}

			logbook.ERR(e.getMessage());
			throw new Exception("Unable to update contact [" + org_id + ":" + contact.account() + ":contact/" + contact.id() + "]: " + contact.fullName());
		}
	}

}
