//===================================================================
// TaskServer.java
// 	This is the server side code that listens for RESTful API
// 	commands from the task_manager interface. The code will
// 	translate the API calls and make the corresponding function
// 	calls to the database controller.
//===================================================================

package com.socialvagrancy.taskmanager.server;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

public class TaskServer
{
	//public static final String BASE_URI = "http://0.0.0.0:5050/taskmanager/";

	private HttpServer server;

	public TaskServer(String port)
	{
		String BASE_URI = "http://0.0.0.0:" + port + "/taskmanager/";

		try
		{
			server = startServer(BASE_URI);

			System.out.println("Press [enter] to stop the server...");
			System.in.read();

			server.shutdown();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static HttpServer startServer(String BASE_URI)
	{
		final ResourceConfig rc = new ResourceConfig().packages("com.socialvagrancy.taskmanager.server");

		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}

	public static void main(String[] args)
	{
		TaskServer server = new TaskServer(args[0]);
	}
}
