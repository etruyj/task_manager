//===================================================================
// GetContacts.java
// 	Description:
// 		Retrieves a list of contacts from the task server.
//===================================================================

package com.socialvagrancy.taskmanager.client.command;

import com.socialvagrancy.taskmanager.client.utils.http.ApiUrls;
import com.socialvagrancy.taskmanager.structure.Contact;
import com.socialvagrancy.utils.Logger;
import com.socialvagrancy.utils.http.RestApi;

import com.google.gson.Gson;

import java.util.ArrayList;

public class GetContacts
{
    public static ArrayList<Contact> users(String base_url, String token, RestApi api, Logger logbook) throws Exception
    {
        Gson gson = new Gson();
        
        ArrayList<Contact> user_list = new ArrayList<Contact>();
        
        String api_url = ApiUrls.listUsers(base_url);
        
        logbook.debug("GET " + api_url);
        
        String response = api.get(api_url, token);
        
        logbook.debug(response);
        
        try
        {
            Contact[] users = gson.fromJson(response, Contact[].class);
            
            for(int i=0; i<users.length; i++)
            {
                user_list.add(users[i]);
            }
            
            logbook.info("Found (" + user_list.size() + ") users.");
            
            return user_list;
        }
        catch(Exception e)
        {
            logbook.error(e.getMessage());
            
            throw new Exception("Failed to find users.");
        }   
    }
}
