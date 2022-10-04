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
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;

import org.glassfish.grizzly.http.server.Request;

import com.google.gson.Gson;
import com.socialvagrancy.utils.Logger;

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
}


