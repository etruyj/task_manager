//===================================================================
// ApiUrls.java
// 	Description: 
// 		The URLs for the API calls
//===================================================================

package com.socialvagrancy.taskmanager.client.utils.http;

public class ApiUrls
{
	public static String getTask(String base_url, String id)
        {
            return base_url + "api/tasks/" + id;
        }
        
        public static String listAccounts(String base_url)
	{
		return base_url + "api/accounts";
	}

        public static String listContacts(String base_url, String account, boolean is_active)
        {
            String active = "true";
            
            if(!is_active)
            {
                active = "false";
            }
            
            return base_url + "api/account/" + account + "/contacts?active=" + active;
        }
        
        public static String listGroups(String base_url, String account, boolean is_active)
        {
            String active = "true";
            
            if(!is_active)
            {
                active = "false";
            }
            
            return base_url + "api/account/" + account + "/groups?active=" + active;
        }
        
        public static String listLocations(String base_url, String account, boolean is_active)
        {
            String active = "true";
            
            if(!is_active)
            {
                active = "false";
            }
            
            return base_url + "api/account/" + account + "/locations?active=" + active;
        }
        
        public static String listProjects(String base_url, String account, boolean is_active)
        {
            String active="true";
            
            if(!is_active)
            {
                active = "false";
            }
            
            return base_url + "api/account/" + account + "/projects?active=" + active;
        }
        
	public static String listTasks(String base_url, String contact, String start_date, String end_date)
	{
            return base_url + "api/tasks?assignedTo=" + contact + "&start=" + start_date + "&end=" + end_date;
	}

        public static String listUsers(String base_url)
        {
            return base_url + "api/contacts";
        }
        
	public static String login(String base_url)
	{
            return base_url + "api/token"; 
	}

        public static String putTask(String base_url, String account)
        {
            return base_url + "api/account/" + account + "/task";
        }

}
