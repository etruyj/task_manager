//===================================================================
// Controller.java
// 	Description:
// 		The linker class between the GUI and the API calls.
//===================================================================

package com.socialvagrancy.taskmanager.client.ui;

import com.socialvagrancy.taskmanager.client.command.GetAccounts;
import com.socialvagrancy.taskmanager.client.command.GetContacts;
import com.socialvagrancy.taskmanager.client.command.GetLocations;
import com.socialvagrancy.taskmanager.client.command.GetProjects;
import com.socialvagrancy.taskmanager.client.command.GetTasks;
import com.socialvagrancy.taskmanager.client.command.Login;
import com.socialvagrancy.taskmanager.client.command.SaveTask;
import com.socialvagrancy.taskmanager.client.command.SearchAccounts;
import com.socialvagrancy.taskmanager.structure.Account;
import com.socialvagrancy.taskmanager.structure.Contact;
import com.socialvagrancy.taskmanager.structure.Location;
import com.socialvagrancy.taskmanager.structure.Project;
import com.socialvagrancy.taskmanager.structure.Task;
import com.socialvagrancy.taskmanager.structure.Token;
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

	public ArrayList<Account> getAccounts()
	{
		try
		{
			ArrayList<Account> account_list = GetAccounts.all(base_url, token, api, logbook);

			return account_list;
		}
		catch(Exception e)
		{
			logbook.error(e.getMessage());

			return new ArrayList<Account>();
		}
	}

	public ArrayList<Account> getAccounts(String account)
	{
		try
		{
			return SearchAccounts.byName(account, base_url, token, api, logbook);
		}
		catch(Exception e)
		{
			logbook.error(e.getMessage());

			return new ArrayList<Account>();
		}
	}

        public ArrayList<Contact> getContacts(String account, boolean status)
        {
            try
            {
                ArrayList<Contact> contact_list = GetContacts.byStatus(base_url, account, status, token, api, logbook);
         
                return contact_list;
            }
            catch(Exception e)
            {
                logbook.error(e.getMessage());
                
                return new ArrayList<Contact>();
            }
        }
        
        public ArrayList<Location> getLocations(String account, boolean status)
        {
            try
            {
                ArrayList<Location> location_list = GetLocations.byStatus(base_url, account, status, token, api, logbook);
                
                return location_list;
            }
            catch(Exception e)
            {
                logbook.error(e.getMessage());
                
                return new ArrayList<Location>();
            }
        }
        
        public ArrayList<Project> getProjects(String account, boolean status)
        {
            try
            {
                ArrayList<Project> project_list = GetProjects.byStatus(base_url, account, status, token, api, logbook);
                
                return project_list;
            }
            catch(Exception e)
            {
                logbook.error(e.getMessage());
                
                return new ArrayList<Project>();
            }
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
        
        public Task getSingleTask(String id)
        {
            try
            {
                Task task = GetTasks.byId(base_url, id, token, api, logbook);
                
                return task;
            }
            catch(Exception e)
            {
                logbook.error(e.getMessage());
                
                return new Task();
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
        
	public String login(String username, char[] password, String organization) throws Exception
	{
		try
		{
			Token t = Login.getToken(base_url, username, password, organization, api, logbook);
			// Clear from memory.
			password = null;
			token = t.get();
                        
			return t.name();
		}
		catch(Exception e)
		{
			logbook.ERR(e.getMessage());
			throw new Exception("Unable to login with specified credentials.");
		}
	}
        
        public String saveTask(Task task) throws Exception
        {
            try
            {
                Task saved = SaveTask.task(task, base_url, token, api, logbook);
                
                return saved.id();
            }
            catch(Exception e)
            {
                logbook.error(e.getMessage());
                
                return "";
            }
        }
}
