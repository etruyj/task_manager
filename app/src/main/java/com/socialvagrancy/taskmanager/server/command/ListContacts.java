//===================================================================
// ListContacts.java
// 	Description:
// 		Returns a list of contacts.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Contact;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ListContacts
{
	public static ArrayList<Contact> forAccount(String account, String org_id, String active_status, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.INFO("Fetching list of users associated with account [" + account + "]...");

		ArrayList<Contact> contact_list = new ArrayList<Contact>();
		Contact contact;
		boolean state;

		if(active_status.equals("true"))
		{
			state = true;
		}
		else if(active_status.equals("false"))
		{
			state = false;
		}
		else
		{
			throw new Exception("Invalid contact state specified. Must be true or false.");
		}

		String query = "SELECT contact.id, first_name, last_name, phone, email, active, account_id, location_id, contact.text_id FROM contact "
			+ "INNER JOIN account ON account.id = contact.account_id "
			+ "WHERE account.name=? AND active=? AND contact.organization_id=?"
			+ "ORDER BY last_name, first_name ASC";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, account);
			pst.setBoolean(2, state);
			pst.setObject(3, UUID.fromString(org_id));

			ResultSet rs = pst.executeQuery();

			while(rs.next())
			{
				contact = new Contact();

				contact.setId(rs.getString(1));
				contact.setFirstName(rs.getString(2));
				contact.setLastName(rs.getString(3));
				contact.setPhone(rs.getString(4));
				contact.setEmail(rs.getString(5));
				contact.setActive(rs.getBoolean(6));
				contact.setAccountId(rs.getString(7));
				contact.setLocationId(rs.getString(8));
				contact.setTextId(rs.getString(9));

				contact_list.add(contact);
			}

			return contact_list;
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			
			throw new Exception("Unable to retrieve contact information.");
		}
	}
}
