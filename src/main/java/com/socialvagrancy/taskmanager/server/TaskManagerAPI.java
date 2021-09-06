//===================================================================
// TaskManagerAPI.java
//	This is the different API paths available for the task manager.
//	Incoming calls are listened to on the Grizzly HTTP server and
//	translated into these API calls.
//===================================================================

package com.socialvagrancy.taskmanager.server;

import com.google.gson.Gson;

import com.socialvagrancy.utils.Logger;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.Request;

@Path("api")
public class TaskManagerAPI
{
	private DatabaseController c = new DatabaseController();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test()
	{
		return "success!";
	}

	@GET
	@Path("/test")
	@Produces("application/json")
	public String test2()
	{
		Gson gson = new Gson();
		String message = "Hello World!";
		return gson.toJson(message);
	}

	//==============================================
	// AUTHENTICATION API CALL
	//==============================================
	
	@POST
	@Path("/login")
	@Produces("application/json")
	public String login(@Context Request request, String json)
	{
		c.login(request.getRemoteAddr() + ":" + request.getRemotePort(), json);

		System.out.println("Hi, " + request.getRemoteAddr() + " " + json.toString());
		return json.toString();
	}
}
