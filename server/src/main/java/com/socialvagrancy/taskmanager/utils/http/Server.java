//===================================================================
// Server.java
// 	Description:
// 		The Grizzly HTTP server
//===================================================================

package com.socialvagrancy.taskmanager.server.utils.http;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

public class Server
{
	private HttpServer server;

	public Server(String ip, String port, String api_prefix, PostgresConnector psql, Logger logbook)
	{
		String BASE_URI = "http://" + ip + ":" + port + "/" + api_prefix + "/";

		// Configure the properties that are going to be passed to the functions.
		HashMap<String, Object> property_map = new HashMap<String, Object>();
		property_map.put("database", psql);
		property_map.put("logger", logbook);

		try
		{
			logbook.INFO("Starting server and listening on " + BASE_URI);

			server = startServer(BASE_URI, property_map);

			System.err.println("Task server started and listening at " + BASE_URI);
			logbook.INFO("Server started successfully");

			System.err.println("Press [enter] to stop the server...");
			System.in.read();

			logbook.WARN("Shutting down task server.");
			server.shutdown();
			logbook.WARN("Server shutdown successfully.");
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			logbook.ERR(e.getMessage());
			logbook.ERR("Failed to start server.");
		}
	}

	public static HttpServer startServer(String BASE_URI, HashMap<String, Object> property_map)
	{
		final ResourceConfig config = new ResourceConfig().packages("com.socialvagrancy.taskmanager.server").addProperties(property_map);
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
	}
}
