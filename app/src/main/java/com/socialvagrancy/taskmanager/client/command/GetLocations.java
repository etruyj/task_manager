/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.socialvagrancy.taskmanager.client.command;

import com.socialvagrancy.taskmanager.client.utils.http.ApiUrls;
import com.socialvagrancy.taskmanager.structure.Location;
import com.socialvagrancy.utils.Logger;
import com.socialvagrancy.utils.http.RestApi;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;

/**
 *
 * @author seans
 */
public class GetLocations {
    public static ArrayList<Location> byStatus(String base_url, String account, boolean status, String token, RestApi api, Logger logbook) throws Exception
    {
        ArrayList<Location> location_list = new ArrayList<Location>();
        Gson gson = new Gson();
        
        String api_url = ApiUrls.listLocations(base_url, account, status);
        
        logbook.debug("GET " + api_url);
        
        String response = api.get(api_url, token);
        
        logbook.debug(response);
        
        try
        {
            Location[] locations = gson.fromJson(response, Location[].class);
            
            for(int i=0; i<locations.length; i++)
            {
                location_list.add(locations[i]);
            }
            
            logbook.info("Found (" + location_list.size() + ") locations");
        }
        catch(JsonParseException e)
        {
            logbook.error(e.getMessage());
            
            throw new Exception("Unable to fetch list of locations for account: " + account);
        }
        
        
        return location_list;
    }
}
