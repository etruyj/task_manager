//===================================================================
// SearchAccounts.java
// 	Description:
// 		Queries the API to perform an account name search
//===================================================================

package com.socialvagrancy.taskmanager.client.command;

import com.socialvagrancy.taskmanager.client.utils.http.ApiUrls;
import com.socialvagrancy.taskmanager.structure.Account;
import com.socialvagrancy.utils.Logger;
import com.socialvagrancy.utils.http.RestApi;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;

public class SearchAccounts
{
	public static ArrayList<Account> byName(String account, String base_url, String token, RestApi api, Logger logbook) throws Exception
	{
		ArrayList<Account> account_list = new ArrayList<Account>();
		Gson gson = new Gson();

		String api_url = ApiUrls.listAccounts(base_url, account);

		try
		{
			logbook.debug("GET " + api_url);

			String response = api.get(api_url, token);

			logbook.debug("Response: " + response);

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

			throw new Exception("Unable to fetch list of accounts.");
		}

	}
}
