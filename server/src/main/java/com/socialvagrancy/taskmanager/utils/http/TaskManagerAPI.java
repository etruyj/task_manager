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

import org.glassfish.grizzly.http.server.Request;

import com.socialvagrancy.taskmanager.server.command.CreateAccount;
import com.socialvagrancy.taskmanager.server.command.ListAccounts;
import com.socialvagrancy.taskmanager.server.structures.Account;
import com.socialvagrancy.taskmanager.server.structures.ServerResponse;
import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
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
		
		ServerResponse response;

		logbook.INFO("Servicing request: /account/" + name);
	
		if(body == null)
		{
			logbook.INFO("Creating an account without a description.");
			response = CreateAccount.nameOnly(name, psql, logbook);
		}
		else
		{
			logbook.INFO("Creating an account with description.");

			Account request = gson.fromJson(body, Account.class);	
		
			response = CreateAccount.nameWithDescription(name, request.description(), psql, logbook);
		}


		return gson.toJson(response);
	}
}


