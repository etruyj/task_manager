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

import com.socialvagrancy.taskmanager.client.utils.http.ApiUrls;
import com.socialvagrancy.taskmanager.structure.Task;
import com.socialvagrancy.utils.Logger;
import com.socialvagrancy.utils.http.RestApi;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GetTasks
{
	public static ArrayList<Task> forDay(String base_url, String date, RestApi api, Logger logbook) throws Exception
	{
		Gson gson = new Gson();

		
	}
}
