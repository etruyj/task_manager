//===================================================================
// ArgParser.java
// 	Description:
// 		Parses function arguments and turns them into commands.
//===================================================================

package com.socialvagrancy.taskmanager.server.ui;

public class ArgParser
{
	private String db_address;
	private String db_table;
	private String db_user;
	private String db_pass;
	private String command;
	private String server_address;
	private String port;
	private String api_prefix;

	public String getCommand() { return command; }
	public String getDbAddress() { return db_address; }
	public String getPassword() { return db_pass; }
	public String getPort() { return port; }
	public String getPrefix() { return api_prefix; }
	public String getServerAddress() { return server_address; }
	public String getTable() { return db_table; }
	public String getUser() { return db_user; }

	public void parseArgs(String[] args)
	{
		if(args != null)
		{
			for(int i=0; i<args.length; i++)
			{
				switch (args[i])
				{
					case "-c":
					case "--command":
						if((i+1) < args.length)
						{
							i++;
							command = args[i];
						}
						break;
					case "-e":
					case "--endpoint":
						if((i+1) < args.length)
						{
							i++;
							db_address = args[i];
						}
						break;
					case "--listen":
						if((i+1) < args.length)
						{
							i++;
							server_address = args[i];
						}
						break;
					case "-p":
					case "--password":
						if((i+1) < args.length)
						{
							i++;
							db_pass = args[i];
						}
						break;
					case "--port":
						if((i+1) < args.length)
						{
							i++;
							port = args[i];
						}
						break;
					case "--prefix":
						if((i+1) < args.length)
						{
							i++;
							api_prefix = args[i];
						}
						break;
					case "-t":
					case "--table":
						if((i+1) < args.length)
						{
							i++;
							db_table = args[i];
						}
						break;
					case "-u":
					case "--user":
						if((i+1) < args.length)
						{
							i++;
							db_user = args[i];
						}
						break;
				}	
			}
		}
	}
}
