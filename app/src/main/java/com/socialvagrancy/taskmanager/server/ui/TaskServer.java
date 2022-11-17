//===================================================================
// TaskServer.java
// 	Description:
// 		The main class for the server client.
//===================================================================

package com.socialvagrancy.taskmanager.server.ui;

public class TaskServer
{
	public void executeCommand(ArgParser aparser)
	{
		ServerController server = new ServerController(aparser.getDbAddress(), aparser.getTable(), aparser.getUser(), aparser.getPassword());

		switch(aparser.getCommand())
		{
			case "initialize-server":
				server.initializeServer();
				break;
			case "start-server":
				server.startServer(aparser.getServerAddress(), aparser.getPort(), aparser.getPrefix());
				break;
			default:
				System.err.println("Invalid command selected [" + aparser.getCommand() + "]. Try --help to see a list of available commands.");
				break;
		}
	}

	public static void main(String[] args)
	{
		ArgParser aparser = new ArgParser();
		aparser.parseArgs(args);

		TaskServer server = new TaskServer();
		server.executeCommand(aparser);

	}
}
