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
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
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
import com.socialvagrancy.taskmanager.server.command.GenerateToken;
import com.socialvagrancy.taskmanager.server.command.ListAccounts;
import com.socialvagrancy.taskmanager.server.command.ListContacts;
import com.socialvagrancy.taskmanager.server.command.ListLocations;
import com.socialvagrancy.taskmanager.server.command.ValidateToken;
import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Account;
import com.socialvagrancy.taskmanager.structure.Contact;
import com.socialvagrancy.taskmanager.structure.Location;
import com.socialvagrancy.taskmanager.structure.LoginCredential;
import com.socialvagrancy.taskmanager.structure.ServerResponse;
import com.socialvagrancy.taskmanager.structure.Token;
import com.socialvagrancy.taskmanager.structure.server.Credential;
import com.socialvagrancy.taskmanager.structure.server.PermissionLevel;
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
	public String listAccounts(@QueryParam("name") String name, @HeaderParam("Authorization") String auth_token)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");
		Gson gson = new Gson();
		ServerResponse response = new ServerResponse();
		PermissionLevel min_required = PermissionLevel.ORGANIZATION_MONITOR;

		logbook.INFO("Servicing request: /accounts");

		try
		{
			Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);
			if(min_required.checkPermission(creds.permissions()))
			{
			
				ArrayList<Account> account_list;

				if(name == null)
				{
					account_list = ListAccounts.all(creds.organization(), psql, logbook);
				}
				else
				{
					account_list = ListAccounts.search(creds.organization(), name, psql, logbook);
				}

				return gson.toJson(account_list);
			}
			else
			{
				logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to access resource (/accounts).");

				response.setMessage("Insufficient priviledges to access accounts.");
			}
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			
			response.setMessage("ERROR: Unable to retrieve account list.");

		}
		
		return gson.toJson(response);
	}

	@PUT
	@Path("/account/{name}")
	@Consumes("application/json")
	@Produces("application/json")
	public String createAccount(@PathParam("name") String name, @HeaderParam("Authorization") String auth_token, String body)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");
		PermissionLevel min_required = PermissionLevel.ORGANIZATION_USER;
		Gson gson = new Gson();
		
		ServerResponse response = new ServerResponse();
		Account account;

		logbook.INFO("Servicing request: /account/" + name);
		
		try
		{
			Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);

			if(min_required.checkPermission(creds.permissions()))
			{
				if(body == null)
				{
					logbook.INFO("Creating an account without a description.");
			
					account = CreateAccount.nameOnly(name, UUID.fromString(creds.organization()), psql, logbook);
					return gson.toJson(account);
			
				}
				else
				{
					logbook.INFO("Creating an account with description.");

					Account request = gson.fromJson(body, Account.class);	
					// Build the variable for passing to the function.
					request.setName(name);
					
					account = CreateAccount.nameWithDescription(request, UUID.fromString(creds.organization()), psql, logbook);
				
					return gson.toJson(account);
				}
			}
			else
			{
				logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to create account: " + name + ".");

				response.setMessage("Insufficient priviledges to create account.");
			}
		}
		catch(Exception e)
		{
			response.setMessage(e.getMessage());
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

	@POST
	@Path("/token")
	@Consumes("application/json")
	@Produces("application/json")
	public String authenticate(String body)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");

		Gson gson = new Gson();

		ServerResponse response = new ServerResponse();

		logbook.INFO("Servicing request: /token");

		if(body != null)
		{
			try
			{
				LoginCredential creds = gson.fromJson(body, LoginCredential.class);

				String jwt = GenerateToken.authenticate(creds.username(), creds.password(), creds.organization(), psql, logbook);

				Token token = new Token(jwt);

				return gson.toJson(token);
			}
			catch(Exception e)
			{
				logbook.ERR(e.getMessage());
				response.setMessage("Invalid credentials");
			}
		}
		else
		{
			logbook.ERR("No credentials present.");
			response.setMessage("No credentials present");
		}

		return gson.toJson(response);
	}
}


