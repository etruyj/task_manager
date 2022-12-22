//===================================================================
// ApiUrls.java
// 	Description: 
// 		The URLs for the API calls
//===================================================================

package com.socialvagrancy.taskmanager.client.utils.http;

public class ApiUrls
{
	public static String listTasks(String base_url, String contact, String start_date, String end_date)
	{
		return base_url + "api/tasks?assignedTo=" + contact + "&start=" + start_date + "&end=" + end_date;
	}

	public static String login(String base_url)
	{
		return base_url + "api/token"; 
	}


}
