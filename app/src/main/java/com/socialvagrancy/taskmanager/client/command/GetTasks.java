//===================================================================
// GetTasks.java
// 	Description:
// 		Returns a list of tasks for the specified start and
// 		end date.
//
// 	Functions:
// 		forDay(Date): converts date to UTC time range and submits
// 		request to the server.
//===================================================================

package com.socialvagrancy.taskmanager.client.command;

import com.socialvagrancy.taskmanager.client.utils.converter.UtcDate;
import com.socialvagrancy.taskmanager.client.utils.http.ApiUrls;
import com.socialvagrancy.taskmanager.structure.Task;
import com.socialvagrancy.utils.Logger;
import com.socialvagrancy.utils.http.RestApi;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;

public class GetTasks
{
	public static ArrayList<Task> forDay(String base_url, String contact, String date, String token, RestApi api, Logger logbook) throws Exception
	{
		Gson gson = new Gson();
                ArrayList<Task> task_list = new ArrayList<Task>();
                
                String range_start = UtcDate.localToUtc(date + "T00:00:00");
                String range_end = UtcDate.localToUtc(date + "T23:59:59");
                
                String api_url = ApiUrls.listTasks(base_url, contact, range_start, range_end);
                
                logbook.debug("GET: " + api_url);
                
                String response = api.get(api_url, token);
                
                try
                {
                    Task[] tasks = gson.fromJson(response, Task[].class);
                
                    for(int i=0; i<tasks.length; i++)
                    {
                        task_list.add(tasks[i]);
                    }
                    
                    logbook.info("Found (" + task_list.size() + ") tasks for date " + date);
                    
                    return task_list;
                }
                catch(JsonParseException e)
                {
                    logbook.error(e.getMessage());
                    
                    throw new Exception("Unable to parse returned payload for GetTasks()");
                }
	}
}
