//===================================================================
// ServerConfiguration.java
// 	This class holds the configuration file when loaded from the
// 	the task-server.conf file.
//===================================================================

package com.socialvagrancy.taskmanager.jsonObjects;

public class ServerConfiguration
{
	public	String url; // database location.
	public String database; // database name
	public String username; // mysql user
	public String password; // mysql password

	public String log_path;
	public String log_name;
	public int log_size; // Target size of log
	public int log_count; // number of log files to keep.
	public int log_level; // log level

	public ServerConfiguration()
	{
		url = "127.0.0.1";
		database = "default";
		username = "none";
		password = "password";

		log_path = "./";
		log_name = "log.log";
		log_size = 1024;
		log_count = 0;
	}

	public String getURL() { return url; }
	public String getDatabase() { return database; }
	public String getUsername() { return username; }
	public String getPassword() { return password; }

	public String getLogFile() { return log_path + log_name; }
	public int getLogSize() { return log_size; }
	public int getLogCount() { return log_count; }
	public int getLogLevel() { return log_level; }
}
