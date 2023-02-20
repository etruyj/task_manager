/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.socialvagrancy.taskmanager.client.command;

import com.socialvagrancy.taskmanager.client.utils.http.ApiUrls;
import com.socialvagrancy.taskmanager.structure.Project;
import com.socialvagrancy.utils.Logger;
import com.socialvagrancy.utils.http.RestApi;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;

/**
 *
 * @author seans
 */
public class GetProjects {
    public static ArrayList<Project> byStatus(String base_url, String account, boolean status, String token, RestApi api, Logger logbook) throws Exception
    {
        ArrayList<Project> project_list = new ArrayList<Project>();
        Gson gson = new Gson();
        
        String api_url = ApiUrls.listProjects(base_url, account, status);
        
        logbook.debug("GET " + api_url);
        
        String response = api.get(api_url, token);
        
        logbook.debug(response);
        
        try
        {
            Project[] projects = gson.fromJson(response, Project[].class);
            
            for(int i=0; i<projects.length; i++)
            {
                project_list.add(projects[i]);
            }
            
            logbook.info("Found (" + project_list.size() + ") projects");
        }
        catch(JsonParseException e)
        {
            logbook.error(e.getMessage());
            
            throw new Exception("Unable to fetch list of projects for account: " + account);
        }
        
        
        return project_list;
    }
}
