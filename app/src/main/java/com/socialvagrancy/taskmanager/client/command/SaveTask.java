//===================================================================
// SaveTask.java
// 	Description:
// 		Saves task information by passing the task to the API
//===================================================================

package com.socialvagrancy.taskmanager.client.command;

import com.socialvagrancy.taskmanager.client.utils.http.ApiUrls;
import com.socialvagrancy.taskmanager.structure.Task;
import com.socialvagrancy.utils.Logger;
import com.socialvagrancy.utils.http.RestApi;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class SaveTask
{
	public static Task task(Task task, String base_url, String token, RestApi api, Logger logbook) throws Exception
	{
		logbook.info("Saving task [" + task.subject() + "]");

		Gson gson = new Gson();
		String api_url = ApiUrls.putTask(base_url, task.account());

		logbook.debug("POST " + api_url);

		try
		{
                    logbook.debug("BODY: " + gson.toJson(task));
                    
                    String response = api.put(api_url, token, gson.toJson(task));

			logbook.debug("Response: " + response);

			Task saved = gson.fromJson(response, Task.class);

			logbook.info("Successfully saved task: " + saved.id());

			return saved;
		}
		catch(JsonParseException e)
		{
			logbook.error(e.getMessage());
			throw new Exception("Unable to save task");
		}
	}
}
