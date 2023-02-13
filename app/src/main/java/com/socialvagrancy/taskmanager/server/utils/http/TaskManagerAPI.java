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
import com.socialvagrancy.taskmanager.server.command.CreateProject;
import com.socialvagrancy.taskmanager.server.command.CreateTask;
import com.socialvagrancy.taskmanager.server.command.GenerateToken;
import com.socialvagrancy.taskmanager.server.command.ListAccounts;
import com.socialvagrancy.taskmanager.server.command.ListContacts;
import com.socialvagrancy.taskmanager.server.command.ListLocations;
import com.socialvagrancy.taskmanager.server.command.ListProjects;
import com.socialvagrancy.taskmanager.server.command.ListTasks;
import com.socialvagrancy.taskmanager.server.command.UpdateAccount;
import com.socialvagrancy.taskmanager.server.command.UpdateLocation;
import com.socialvagrancy.taskmanager.server.command.ValidateToken;
import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structure.Account;
import com.socialvagrancy.taskmanager.structure.Contact;
import com.socialvagrancy.taskmanager.structure.Location;
import com.socialvagrancy.taskmanager.structure.LoginCredential;
import com.socialvagrancy.taskmanager.structure.Project;
import com.socialvagrancy.taskmanager.structure.ServerResponse;
import com.socialvagrancy.taskmanager.structure.Task;
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
		logbook.INFO("Servicing request: GET /test");

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

		logbook.INFO("Servicing request: GET /accounts");

		try
		{
			Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);
			if(min_required.checkPermissions(creds.permissions()))
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

				response.setMessage("Insufficient privileges to access accounts.");
			}
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			
			response.setMessage("ERROR: Unable to retrieve account list.");

		}
		
		return gson.toJson(response);
	}

	@POST
	@Path("/account")
	@Consumes("application/json")
	@Produces("application/json")
	public String updateAccount(@HeaderParam("Authorization") String auth_token, String body)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");

		ServerResponse response = new ServerResponse();
		Gson gson = new Gson();
		PermissionLevel min_required = PermissionLevel.ACCOUNT_ADMIN;

		logbook.INFO("Servicing requests: POST /account");

		try
		{
			Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);

			if(min_required.checkPermissions(creds.permissions()))
			{
				Account account = gson.fromJson(body, Account.class);

				account = UpdateAccount.byUUID(account, creds.organization(), psql, logbook);

				return gson.toJson(account);
			}
			else
			{
				logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to update resource (/account)");
				response.setMessage("Insufficient privileges to update account.");
			}
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			response.setMessage("Unable to update account.");
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

		logbook.INFO("Servicing request: PUT /account/" + name);

		try
		{
			Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);

			if(min_required.checkPermissions(creds.permissions()))
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

				response.setMessage("Insufficient privileges to create account.");
			}
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			
			if(e.getMessage().equals("Invalid token used to access the client."))
			{
				response.setMessage("Authorization is required to perform this request.");
			}
			else
			{
				response.setMessage("Failed to retrieve credentials for account: " + name);
			}
		}


		return gson.toJson(response);
	}
	
	@GET
	@Path("/account/{name}/contacts")
	@Consumes("application/json")
	@Produces("application/json")
	public String listAccountContacts(@PathParam("name") String account_name, @QueryParam("active") String active_state, @HeaderParam("Authorization") String auth_token)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");

		Gson gson = new Gson();
		ServerResponse response = new ServerResponse();
		PermissionLevel min_required = PermissionLevel.ACCOUNT_MONITOR;

		logbook.INFO("Servicing request: GET /account/" + account_name + "/contacts/active=" + active_state);

		try
		{
			Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);

			if(min_required.checkPermissions(creds.permissions()))
			{
	
				ArrayList<Contact> contact_list = ListContacts.forAccount(account_name, creds.organization(), active_state, psql, logbook);

				logbook.INFO("Found (" + contact_list.size() + ") contacts.");

				return gson.toJson(contact_list);
			}
			else
			{
				logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to lists contacts for account: " + account_name);

				response.setMessage("Insufficient privileges to create account.");
			}
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			response.setMessage("Failed to retrieve contact list for account: " + account_name);
		}
		
		return gson.toJson(response);
	}


	@PUT
	@Path("/account/{account}/contact/")
	@Consumes("application/json")
	@Produces("application/json")
	public String createContact(@PathParam("account") String account_name, @HeaderParam("Authorization") String auth_token, String body)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");

		Gson gson = new Gson();
		
		ServerResponse response = new ServerResponse();
		PermissionLevel min_required = PermissionLevel.ACCOUNT_USER;

		logbook.INFO("Servicing requrest: PUT /account/" + account_name + "/contact");
		Contact contact = gson.fromJson(body, Contact.class);

		if(body != null)
		{
			try
			{
				Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);

				if(min_required.checkPermissions(creds.permissions()))
				{
					contact = CreateContact.parseThenCreate(contact, creds.organization(), psql, logbook);

					return gson.toJson(contact);
				}
				else
				{
					logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to create user [" + contact.fullName() + "] account: " + account_name + ".");

					response.setMessage("Insufficient privileges to create user.");
				}

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
	public String listLocations(@PathParam("name") String account_name, @QueryParam("active") String active_state, @HeaderParam("Authorization") String auth_token)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");

		Gson gson = new Gson();
		ServerResponse response = new ServerResponse();
		PermissionLevel min_required = PermissionLevel.ACCOUNT_USER;

		logbook.INFO("Servicing request: GET /account/" + account_name + "/locations/active=" + active_state);

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

		try
		{
			Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);
			
			if(min_required.checkPermissions(creds.permissions()))
			{
				ArrayList<Location> location_list = ListLocations.byStatus(account_name, creds.organization(), state, psql, logbook);

				logbook.INFO("Found (" + location_list.size() + ") locations.");

				return gson.toJson(location_list);
			}
			else
			{
				logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to list locations for account: " + account_name + ".");

				response.setMessage("Insufficient privileges to list locations.");
			}
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			response.setMessage("Failed to retrieve locations for account: " + account_name);

		}

		return gson.toJson(response);
	}

	@POST
	@Path("/account/{account}/location")
	@Consumes("application/json")
	@Produces("application/json")
	public String updateLocation(@PathParam("account") String account_name, @HeaderParam("Authorization") String auth_token, String body)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");

		ServerResponse response = new ServerResponse();
		Gson gson = new Gson();
		PermissionLevel min_required = PermissionLevel.ACCOUNT_ADMIN;

		logbook.INFO("Servicing requests: POST /account/" + account_name + "/location");

		try
		{
			Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);

			if(min_required.checkPermissions(creds.permissions()))
			{
				Location location = gson.fromJson(body, Location.class);

				location = UpdateLocation.byUUID(location, creds.organization(), psql, logbook);

				return gson.toJson(location);
			}
			else
			{
				logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to update resource (/account/" + account_name + "/location)");
				response.setMessage("Insufficient privileges to update location.");
			}
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			response.setMessage("Unable to update location.");
		}

		return gson.toJson(response);
	}

	@PUT
	@Path("/account/{account}/location/{location}")
	@Consumes("application/json")
	@Produces("application/json")
	public String createLocation(@PathParam("account") String account_name, @PathParam("location") String name, @HeaderParam("Authorization") String auth_token, String body)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");
		Gson gson = new Gson();

		ServerResponse response = new ServerResponse();
		PermissionLevel min_required = PermissionLevel.ACCOUNT_USER;

		logbook.INFO("Servicing request: PUT /account/" + account_name + "/location/" + name);

		if(body != null)
		{
			try
			{
				Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);

				if(min_required.checkPermissions(creds.permissions()))
				{

					Location location = gson.fromJson(body, Location.class);

					location = CreateLocation.parseThenCreate(location, creds.organization(), psql, logbook);
			
					return gson.toJson(location);
				}
				else
				{
					logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to create location [" + name + "] for account: " + account_name + ".");

					response.setMessage("Insufficient privileges to create locations.");
				}
			}
			catch(Exception e)
			{
				logbook.ERR(e.getMessage());
				response.setMessage("Failed to create location: " + name);
			}

			return gson.toJson(response);
		}
		else
		{
			logbook.ERR("Unable to create location. No information available.");
			response.setMessage("Unable to create location. No information available.");	
		}

		return gson.toJson(response);
	}

	@GET
	@Path("/account/{account}/projects")
	@Produces("application/json")
	public String listProjects(@PathParam("account") String account_name, @QueryParam("active") String is_active, @HeaderParam("Authorization") String auth_token)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");
		Gson gson = new Gson();

		ServerResponse response = new ServerResponse();
		PermissionLevel min_required = PermissionLevel.ACCOUNT_MONITOR;

		logbook.INFO("Servicing request: GET /account/" + account_name + "/projects");

		try
		{
			Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);

			if(min_required.checkPermissions(creds.permissions()))
			{
				ArrayList<Project> project_list = ListProjects.byStatus(account_name, creds.organization(), is_active, psql, logbook);

				logbook.INFO("Found (" + project_list.size() + ") projects.");
				
				return gson.toJson(project_list);
			}
			else
			{
				logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to list locations for account: " + account_name + ".");
				response.setMessage("Insufficient privileges to list projects.");
			}
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			response.setMessage("Unable to retrieve list of projects for account [" + account_name + "].");
		}

		return gson.toJson(response);
	}

	@PUT
	@Path("/account/{account}/project/{project}")
	@Consumes("application/json")
	@Produces("application/json")
	public String createProject(@PathParam("account") String account_name, @PathParam("project") String project_name, @HeaderParam("Authorization") String auth_token, String body)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");
		Gson gson = new Gson();

		ServerResponse response = new ServerResponse();
		PermissionLevel min_required = PermissionLevel.ACCOUNT_USER;

		logbook.INFO("Servicing request: PUT /account/" + account_name + "/project/" + project_name);
		
		if(body != null)
		{
			try
			{
				Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);

				if(min_required.checkPermissions(creds.permissions()))
				{
					Project project = gson.fromJson(body, Project.class);

					project = CreateProject.parseThenCreate(project, creds.organization(), psql, logbook);

					return gson.toJson(project);
				}
				else
				{
					logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to create project [" + account_name + ":" + project_name + "]");

					response.setMessage("Insufficient privileges to create project.");
				}
			}
			catch(Exception e)
			{
				logbook.ERR(e.getMessage());
				response.setMessage("Failed to create project: " + project_name);
			}
		}
		else
		{
			logbook.ERR("Unable to create project. No information available.");
			response.setMessage("Unabled to create project. No information available.");
		}

		return gson.toJson(response);
	}

	@PUT
	@Path("/account/{account}/task")
	@Consumes("application/json")
	@Produces("application/json")
	public String createTask(@PathParam("account") String account_name, @HeaderParam("Authorization") String auth_token, String body)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");
		Gson gson = new Gson();

		ServerResponse response = new ServerResponse();
		PermissionLevel min_required = PermissionLevel.ACCOUNT_USER;

		logbook.INFO("Servicing request: PUT /account/" + account_name + "/task");

		if(body != null)
		{
			try
			{
				Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);

				if(min_required.checkPermissions(creds.permissions()))
				{
					Task task = gson.fromJson(body, Task.class);

					task = CreateTask.parseThenCreate(task, creds.organization(), psql, logbook);

					return gson.toJson(task);
				}
				else
				{
					logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to create a task for account: " + account_name);
					response.setMessage("Insufficient privileges to create task.");
				}
			}
			catch(Exception e)
			{
				logbook.ERR(e.getMessage());
				response.setMessage("Failed to create task");
			}
		}
		else
		{
			logbook.ERR("Unable to create task. No information available.");
			response.setMessage("Unable to create task. No information available.");
		}

		return gson.toJson(response);
	}

	@GET
	@Path("/contacts")
	@Consumes("application/json")
	@Produces("application/json")
	public String listUserContacts(@HeaderParam("Authorization") String auth_token)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");

		Gson gson = new Gson();
		ServerResponse response = new ServerResponse();
		PermissionLevel list_all = PermissionLevel.ORGANIZATION_USER;
		PermissionLevel min_required = PermissionLevel.ACCOUNT_USER;


		logbook.INFO("Servicing request: GET /contacts");
		
		ArrayList<Contact> contact_list;

		try
		{
			Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);

			if(list_all.checkPermissions(creds.permissions()))
			{
				// List all active organization client users.
				contact_list = ListContacts.activeOrganizationUsers(creds.organization(), psql, logbook);
				
				logbook.INFO("Found (" + contact_list.size() + ") user contacts.");

				return gson.toJson(contact_list);

			}
			else if(min_required.checkPermissions(creds.permissions()))
			{
				// List all active account client users.
				Account account = ListAccounts.findIdForUser(creds.organization(), creds.username(), psql, logbook);
			
				contact_list = ListContacts.activeAccountUsers(account.name(), creds.organization(), psql, logbook);

				logbook.INFO("Found (" + contact_list.size() + ") user contacts.");

				return gson.toJson(contact_list);
			}
			else
			{
				logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to list user contacts.");
				response.setMessage("Insufficient privileges to list user contacts.");
			}
		}
		catch(Exception e)
		{
			logbook.error(e.getMessage());
		}

		return gson.toJson(response);
	}


	@GET
	@Path("/tasks")
	@Consumes("application/json")
	@Produces("application/json")
	public String listTasks(@QueryParam("assignedTo") String contact_id, @QueryParam("start") String range_start, @QueryParam("end") String range_end, @HeaderParam("Authorization") String auth_token)
	{
		Logger logbook = (Logger) config.getProperty("logger");
		PostgresConnector psql = (PostgresConnector) config.getProperty("database");

		Gson gson = new Gson();
		ServerResponse response = new ServerResponse();
		PermissionLevel min_required = PermissionLevel.ACCOUNT_USER;

		logbook.INFO("Servicing request: GET /tasks");

		ArrayList<Task> task_list;
		
		if(contact_id != null && range_start != null && range_end != null)
		{
			try
			{
				Credential creds = ValidateToken.generateCredentials(auth_token, psql, logbook);

				if(min_required.checkPermissions(creds.permissions()))
				{
					// List tasks for user by date
					task_list = ListTasks.byStatus(contact_id, range_start, range_end, creds.organization(), psql, logbook);

					logbook.INFO("Found (" + task_list.size() + ") tasks.");
					
					return gson.toJson(task_list);
				}
				else
				{
					logbook.WARN("Restricted Access Attempt! User [" + creds.username() + "] attempted to list tasks.");
					response.setMessage("Insufficient privileges to list tasks.");
				}
			}
			catch(Exception e)
			{
				logbook.ERR(e.getMessage());
				response.setMessage("Failed to retrieve tasks.");
			}
		}
		else
		{
			logbook.ERR("Unable to list tasks. Not enough information specified.");
			response.setMessage("Unable to list tasks. Not enough information specified.");
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

		logbook.INFO("Servicing request: POST /token");

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


