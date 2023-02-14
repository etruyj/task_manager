//===================================================================
// Controller.java
// 	Description:
// 		The linker class between the GUI and the API calls.
//===================================================================

package com.socialvagrancy.taskmanager.client.ui;

import com.socialvagrancy.taskmanager.client.command.GetContacts;
import com.socialvagrancy.taskmanager.client.command.GetTasks;
import com.socialvagrancy.taskmanager.client.command.Login;
import com.socialvagrancy.taskmanager.structure.Contact;
import com.socialvagrancy.taskmanager.structure.Task;
import com.socialvagrancy.utils.Logger;
import com.socialvagrancy.utils.http.RestApi;

import java.util.ArrayList;

public class Controller
{
	private Logger logbook;
	private RestApi api;
	private String base_url;
	private String token;

	public Controller(String url, boolean ignore_ssl, String log_path, int log_level, int log_size, int log_count)
	{
		base_url = url;
		api = new RestApi(ignore_ssl);

		logbook = new Logger(log_path, log_size, log_count, log_level);
                logbook.info("Starting task manager java client");
	}

        public ArrayList<Task> getTasks(String date, String owner)
        {
            try
            {
                ArrayList<Task> task_list = GetTasks.forDay(base_url, owner, date, token, api, logbook);
         
                return task_list;
            }
            catch(Exception e)
            {
                logbook.error(e.getMessage());
            
                return new ArrayList<Task>();
            }
        }
        
        public ArrayList<Contact> getUsers()
        {
            try
            {
                ArrayList<Contact> user_list = GetContacts.users(base_url, token, api, logbook);
                
                return user_list;
            }
            catch(Exception e)
            {
                logbook.error(e.getMessage());
                
                return new ArrayList<Contact>();
            }
        }
        
	public boolean login(String username, char[] password, String organization) throws Exception
	{
		try
		{
			token = Login.getToken(base_url, username, password, organization, api, logbook);
			// Clear from memory.
			password = null;
			
			return true;
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			throw new Exception("Unable to login with specified credentials.");
		}
	}
}
