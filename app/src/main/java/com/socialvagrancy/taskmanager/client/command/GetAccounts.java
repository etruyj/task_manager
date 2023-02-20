//===================================================================
// GetAccounts.java
// 	Description:
// 		Retrieves a list of accounts from the server.
//===================================================================

package com.socialvagrancy.taskmanager.client.command;

import com.socialvagrancy.taskmanager.client.utils.http.ApiUrls;
import com.socialvagrancy.taskmanager.structure.Account;
import com.socialvagrancy.utils.Logger;
import com.socialvagrancy.utils.http.RestApi;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;

public class GetAccounts
{
	public static ArrayList<Account> all(String base_url, String token, RestApi api, Logger logbook) throws Exception
	{
		ArrayList<Account> account_list = new ArrayList<Account>();
		Gson gson = new Gson();

		String api_url = ApiUrls.listAccounts(base_url);

		try
		{
			logbook.debug("GET " + api_url);

			String response = api.get(api_url, token);

			logbook.debug(response);

			Account[] accounts = gson.fromJson(response, Account[].class);

			for(int i=0; i<accounts.length; i++)
			{
				account_list.add(accounts[i]);
			}

			logbook.info("Found (" + account_list.size() + ") accounts.");

			return account_list;
		}
		catch(JsonParseException e)
		{
			logbook.error(e.getMessage());

			throw new Exception("Failed to retrieve account list.");
		}
	}
}
