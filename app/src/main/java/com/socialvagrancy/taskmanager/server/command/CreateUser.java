//===================================================================
// CreateUser.java
// 	Description:
// 		Creates a user for accessing the database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Account;
import com.socialvagrancy.taskmanager.structure.Contact;
import com.socialvagrancy.taskmanager.structure.Location;
import com.socialvagrancy.taskmanager.structure.server.PermissionLevel;
import com.socialvagrancy.utils.Logger;

import org.mindrot.jbcrypt.BCrypt;

import java.io.Console;
import java.lang.StringBuilder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

public class CreateUser
{
	public static void deleteUser(UUID user_id, PostgresConnector psql, Logger logbook)
	{
		String query = "SELECT name FROM client_user WHERE id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, user_id);

			ResultSet rs = pst.executeQuery();

			while(rs.next())
			{
				logbook.WARN("Deleting user [" + rs.getString(1) + "]");

				query = "DELETE FROM client_user WHERE id=?;";

				pst = psql.prepare(query, logbook);

				pst.setObject(1, user_id);

				pst.executeUpdate();
			}
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			logbook.ERR("Unable to delete user [" + user_id + "]");
		}
	}

	public static UUID insertNew(String username, char[] password, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.WARN("Creating username [" + username + "]");

		UUID id = UUID.randomUUID();
		
		String hash = BCrypt.hashpw(String.valueOf(password), BCrypt.gensalt());

		String query = "INSERT INTO client_user (id, name, password, active, reset_pass_on_login) VALUES (?, ?, ?, ?, ?);";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, id);
			pst.setString(2, username);
			pst.setString(3, hash);
			pst.setBoolean(4, true);
			pst.setBoolean(5, true);

			pst.execute();

			return id;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			logbook.ERR(e.getMessage());
			throw new Exception("Could not create user [" + username + "].");
		}

	}

	public static void mapToContact(UUID user_id, UUID organization_id, UUID contact_id, PermissionLevel permission_level, PostgresConnector psql, Logger logbook) throws Exception
	{
		UUID id = UUID.randomUUID();

		logbook.INFO("Mapping user to contact.");

		String query = "INSERT INTO user_organization "
			+ "(id, user_id, organization_id, contact_id, permissions) "
			+ "VALUES (?, ?, ?, ?, ?);";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, id);
			pst.setObject(2, user_id);
			pst.setObject(3, organization_id);
			pst.setObject(4, contact_id);
			pst.setObject(5, permission_level);

			pst.executeUpdate();
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			logbook.ERR("Unable to map user to organization and contact.");
			throw new Exception("Unable to map user to organization and contact.");
		}
	}

	public static void withPrompts(PostgresConnector psql, Logger logbook)
	{
		Console cons;
		
		if((cons = System.console()) != null)
		{
			//=======================
			// User
			//=======================

			System.out.print("Please enter admin username [admin]: ");
			String user = cons.readLine();

			if(user.equals("") || user == null)
			{
				user = "admin";
			}
			
			System.out.print("Please enter password for [" + user + "]: ");
			char[] pass = cons.readPassword();

			String first_name = "";

			while(first_name.equals("") || first_name == null)
			{
				System.out.print("First name: ");
				first_name = cons.readLine();			
			}

			String last_name = "";

			while(last_name.equals("") || last_name == null)
			{
				System.out.print("Last name: ");
				last_name = cons.readLine();
			}

			//=======================
			// Organization
			//=======================

			System.out.print("Organization [default]: ");
			String org = cons.readLine();

			if(org.equals("") || org.equals(" ") || org == null)
			{
				org = "default";
			}

			//=======================
			// Account
			//=======================

			System.out.print("Account [home]: ");
			String account = cons.readLine();

			if(account.equals("") || account == null)
			{
				account = "home";
			}

			System.out.print("Account Location [none]: " );
			String location = cons.readLine();

			if(location.equals("") || location == null)
			{
				location = "[none]";
			}

			// Initialize as null and then test if they are edited.
			// This allows the script to back out if any of the queries faile.
			UUID user_id = null;
			UUID org_id = null;
			Account act = null;
			Location loc = null;
			Contact contact = null;

			try
			{
				user_id = insertNew(user, pass, psql, logbook);	
				org_id = CreateOrganization.insertNew(org, psql, logbook);
				act = CreateAccount.nameOnly(account, org_id, psql, logbook);
				loc = CreateLocation.withNameOnly(location, UUID.fromString(act.id()), org_id.toString(), psql, logbook);
			
				contact = CreateContact.withNameOnly(first_name, last_name, UUID.fromString(act.id()), UUID.fromString(loc.id()), org_id.toString(), psql, logbook);
			
				mapToContact(user_id, org_id, UUID.fromString(contact.id()), PermissionLevel.DATABASE_ADMIN, psql, logbook);

			}
			catch(Exception e)
			{
				System.err.println("ERROR: " + e.getMessage());
				logbook.ERR(e.getMessage());

				if(contact != null)
				{
					CreateContact.deleteContact(contact, org_id, psql, logbook);
				}

				if(loc != null)
				{
					CreateLocation.deleteLocation(loc, org_id, psql, logbook);
				}

				if(act != null)
				{
					CreateAccount.deleteAccount(act, org_id, psql, logbook);
				}

				if(org_id != null)
				{
					CreateOrganization.deleteOrganization(org_id, psql, logbook);
				}

				if(user_id != null)
				{
					deleteUser(user_id, psql, logbook);
				}
			}

		}
		else
		{
			logbook.ERR("Unable to open system console for input.");
			System.err.println("ERROR: Unable to open system console for input.");
		}
	}
}
