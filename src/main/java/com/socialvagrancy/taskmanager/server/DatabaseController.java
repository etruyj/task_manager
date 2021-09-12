//===================================================================
// DatabaseController.java
// 	This class interfaces between the API and the (database) 
// 	controller. The API initializes this class and calls functions.
// 	This class translates the passed json and the return json
// 	before handing it back to the class.
//
//
//===================================================================

package com.socialvagrancy.taskmanager.server;

import com.google.gson.Gson;
import com.socialvagrancy.taskmanager.jsonObjects.*;
import com.socialvagrancy.utils.Logger;

import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class DatabaseController
{
	private MySQLConnector db_conn;
	private Logger logbook;
	private Gson gson;
	private String api_root;

	
	//=======================================
	// Initialization	
	//=======================================
	
	
	public DatabaseController()
	{
		// Load configuration file.
		ServerConfiguration server_conf = loadConfiguration("task-server.conf");


		// Initialize components.
		logbook = new Logger(server_conf.log_path + server_conf.log_name, server_conf.log_size, server_conf.log_count, 1);
		db_conn = new MySQLConnector(server_conf.url, server_conf.database, server_conf.username, server_conf.password, logbook);
		gson = new Gson();
	
	}


	public ServerConfiguration loadConfiguration(String filePath)
	{
		// Load the YAML configuration document.

		try
		{
			
			Yaml yaml = new Yaml(new Constructor(ServerConfiguration.class));
			System.out.println(filePath);
			InputStream iStream = new FileInputStream(new File(filePath));
			
			ServerConfiguration conf = yaml.load(iStream);
			
			return conf;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ServerConfiguration();
		}
	}

	//=======================================
	// Authentication	
	//=======================================
	
	public String login(String source, String json)
	{
		UserCredentials credentials = gson.fromJson(json, UserCredentials.class);

		logbook.logWithSizedLogRotation("Login attempt from " + source + " with username "
				+ credentials.getUsername(), 3);
	
			

		return "success";
	}

	public String verifyDBCredentials(UserCredentials creds)
	{
		String api_query = "SELECT id, password FROM user "
					+ "WHERE name = ?";
			
	}

}

