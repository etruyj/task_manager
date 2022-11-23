//===================================================================
// CreateContact.java
// 	Description:
// 		Adds a new contact to the contact table.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Contact;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class CreateContact
{
	public static Contact withNameOnly(String first_name, String last_name, UUID account_id, UUID location_id, UUID org_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		Contact contact = new Contact();
		
		contact.setFirstName(first_name);
		contact.setLastName(last_name);
		contact.setAccountId(account_id.toString());
		contact.setLocationId(location_id.toString());

		try
		{
			return parseThenCreate(contact, org_id, psql, logbook);
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	public static Contact parseThenCreate(Contact contact, UUID organization_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		//===============================
		// Parse Exceptions
		//===============================

		if(contact.firstName() == null)
		{
			throw new Exception("Required field: first_name is not set.");
		}
		else if(contact.lastName() == null)
		{
			throw new Exception("Required field: last_name is not set.");
		}
		else if(contact.account() == null)
		{
			throw new Exception("Required field: account_name is not set.");
		}
		
		//===============================
		// Parse Notes
		//===============================

		if(contact.notes() == null)
		{
			UUID text_id = AddText.insertNew("", psql, logbook);

			if(text_id != null)
			{
				contact.setTextId(text_id.toString());
			}
			else
			{
				throw new Exception("Unabled to create text for contact: " + contact.fullName());
			}
		}


		//===============================
		// Create Contact
		//===============================

		logbook.INFO("Creating contact: " + contact.fullName());

		UUID id = UUID.randomUUID();

		contact.setId(id.toString());

		Contact new_contact;

		try
		{
			new_contact = insertNew(contact, organization_id, psql, logbook);
			return new_contact;
		}
		catch(Exception e)
		{
			AddText.deleteText(UUID.fromString(contact.textId()), psql, logbook);

			logbook.ERR(e.getMessage());
			logbook.ERR("Unabled to create contact: " + contact.fullName());
			throw new Exception(e.getMessage());
		}
	}

	public static Contact insertNew(Contact contact, UUID organization_id, PostgresConnector psql, Logger logbook)
	{
		String query = "INSERT INTO contact (id, first_name, last_name, phone, email, active, account_id, location_id, text_id, organization_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, UUID.fromString(contact.id()));
			pst.setString(2, contact.firstName());
			pst.setString(3, contact.lastName());
			pst.setString(4, contact.phone());
			pst.setString(5, contact.email());
			pst.setBoolean(6, true);
			pst.setObject(7, UUID.fromString(contact.account()));
			pst.setObject(8, UUID.fromString(contact.location()));
			pst.setObject(9, UUID.fromString(contact.textId()));
			pst.setObject(10, organization_id);

			pst.execute();
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			logbook.ERR("Failed to put contact: " + contact.fullName());
			return null;
		}

		return contact;	
	}
}
