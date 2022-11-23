//===================================================================
// TaskManagerAPI.java
// 	Description:
// 		The API listeners for the server.
//===================================================================

package com.socialvagrancy.taskmanager.server.utils.http;

import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.glassfish.grizzly.http.server.Request;

import com.socialvagrancy.taskmanager.server.command.CreateAccount;
import com.socialvagrancy.taskmanager.server.command.CreateContact;
import com.socialvagrancy.taskmanager.server.command.CreateLocation;
import com.socialvagrancy.taskmanager.server.command.ListAccounts;
import com.socialvagrancy.taskmanager.server.command.ListContacts;
import com.socialvagrancy.taskmanager.server.command.ListLocations;
import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Account;
import com.socialvagrancy.taskmanager.structure.Contact;
import com.socialvagrancy.taskmanager.structure.Location;
import com.socialvagrancy.taskmanager.structure.ServerResponse;
import com.socialvagrancy.utils.Logger;

import com.google.gson.Gson;

@Path("api")
public class TaskManagerAPI
{
	@Context
	Configuration config;


	@GET
	@Path("/test")
	@Produces("application/json")
	public String test()
	{
		Logger logbook = (Logger) config.getProperty("logger");
		logbook.INFO("Servicing request: /test");

		String message = "Hello World";
		Gson gson = new Gson();
		return gson.toJson(message);
	}

	@GET
	@Path("/accounts")
	@Produces("application/json")
	public String listAccounts(@QueryParam("name") String name)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");
		Gson gson = new Gson();

		logbook.INFO("Servicing request: /accounts");

		ArrayList<Account> account_list;

		if(name == null)
		{
			account_list = ListAccounts.all(psql, logbook);
		}
		else
		{
			account_list = ListAccounts.search(name, psql, logbook);
		}

		return gson.toJson(account_list);
	}

	@PUT
	@Path("/account/{name}")
	@Consumes("application/json")
	@Produces("application/json")
	public String createAccount(@PathParam("name") String name, String body)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");
		Gson gson = new Gson();
		
		ServerResponse response = new ServerResponse();
		Account account;
		UUID org_id = null;

		logbook.INFO("Servicing request: /account/" + name);
	
		if(body == null)
		{
			logbook.INFO("Creating an account without a description.");
			try
			{
				account = CreateAccount.nameOnly(name, org_id, psql, logbook);
				return gson.toJson(account);
			}
			catch(Exception e)
			{
				response.setMessage(e.getMessage());
			}
		}
		else
		{
			logbook.INFO("Creating an account with description.");

			Account request = gson.fromJson(body, Account.class);	
			// Build the variable for passing to the function.
			request.setName(name);

			try
			{
				account = CreateAccount.nameWithDescription(request, org_id, psql, logbook);
				
				return gson.toJson(account);
			}
			catch(Exception e)
			{
				response.setMessage(e.getMessage());
			}
		}


		return gson.toJson(response);
	}
	
	@GET
	@Path("/account/{name}/contacts")
	@Consumes("application/json")
	@Produces("application/json")
	public String listAccountContacts(@PathParam("name") String account_name, @QueryParam("active") String active_state)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");

		Gson gson = new Gson();
		ServerResponse response = new ServerResponse();

		logbook.INFO("Servicing request: /account/" + account_name + "/contacts/active=" + active_state);

		try
		{
			ArrayList<Contact> contact_list = ListContacts.forAccount(account_name, active_state, psql, logbook);

			logbook.INFO("Found (" + contact_list.size() + ") contacts.");

			return gson.toJson(contact_list);
		}
		catch(Exception e)
		{
			response.setMessage(e.getMessage());

			return gson.toJson(response);
		}
	}


	@PUT
	@Path("/account/{account}/contact/")
	@Consumes("application/json")
	@Produces("application/json")
	public String createContact(@PathParam("account") String account_name, String body)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");

		Gson gson = new Gson();
		
		ServerResponse response = new ServerResponse();

		logbook.INFO("Servicing requrest: /account/" + account_name + "/contact");

		if(body != null)
		{
			try
			{
				Contact contact = gson.fromJson(body, Contact.class);
				contact = CreateContact.parseThenCreate(contact, null, psql, logbook);

				return gson.toJson(contact);

			}
			catch(Exception e)
			{
				logbook.ERR(e.getMessage());
				response.setMessage(e.getMessage());
			}
		}
		else
		{
			logbook.ERR("Unable to create contact. No contact information provided.");
			response.setMessage("Unable to create contact. No contact information provided.");

		}

		return gson.toJson(response);	
	}

	@GET
	@Path("/account/{name}/locations")
	@Consumes("application/json")
	@Produces("application/json")
	public String listLocations(@PathParam("name") String account_name, @QueryParam("active") String active_state)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");

		Gson gson = new Gson();

		logbook.INFO("Servicing request: /account/" + account_name + "/locations/active=" + active_state);

		boolean state;

		if(active_state.equals("true"))
		{
			state = true;
		}
		else if(active_state.equals("false"))
		{
			state = false;
		}
		else
		{
			// Catch all. If state isn't specified or specified
			// incorrectly, only active accounts are returned.
			logbook.WARN("Improper active state specified. Setting to true.");
			state = true;
		}

		ArrayList<Location> location_list = ListLocations.byStatus(account_name, state, psql, logbook);

		logbook.INFO("Found (" + location_list.size() + ") locations.");

		return gson.toJson(location_list);
	}

	@PUT
	@Path("/account/{account}/location/{location}")
	@Consumes("application/json")
	@Produces("application/json")
	public String createLocation(@PathParam("account") String account_name, @PathParam("location") String name, String body)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");
		Gson gson = new Gson();

		ServerResponse response = new ServerResponse();
		UUID org_id = null;

		logbook.INFO("Servicing request: /account/" + account_name + "/location/" + name);

		if(body != null)
		{
			try
			{
				logbook.INFO("Creating location...");

				Location location = gson.fromJson(body, Location.class);

				location = CreateLocation.parseThenCreate(location, org_id, psql, logbook);
			
				return gson.toJson(location);
			}
			catch(Exception e)
			{
				logbook.ERR(e.getMessage());
				response.setMessage(e.getMessage());
			}
		
		}
		else
		{
			logbook.ERR("Unable to create location. No information available.");
			response.setMessage("Unable to create location. No information available.");	
		}

		return gson.toJson(response);
	}
}


