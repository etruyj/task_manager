//===================================================================
// GenerateToken.java
// 	Description:
// 		Queries the user database and creates an access token
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.structures.server.Credential;
import com.socialvagrancy.utils.Logger;

import org.mindrot.jbcrypt.BCrypt;

import java.lang.StringBuilder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

public class GenerateToken
{
	public static Credential authenticate(String username, String password, String organization, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.INFO("Authenticating user [" + username + "] for organization [" + organization + "]");

		Credential creds = new Credential();

		String query = "SELECT client_user.name, client_user.password, organization.id, user_organization.permissions FROM client_user "
			+ "INNER JOIN user_organization ON user_organizatin.user_id = client_user.id "
			+ "INNER JOIN organization ON organization.id = user_organization.organization_id "
			+ "WHERE client_user.name=? AND organization.name=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			throw new Exception("Unable to query database for user [" + organization + ":" + username + "]");
		}	
	}

	public static String randomToken(int length)
	{
		StringBuilder token = new StringBuilder();

		Random rand = new Random();
		int random_number;

		for(int i=0; i<length; i++)
		{
			random_number = rand.nextInt(100) + 21;
			
			token.append((char) random_number);
		}

		return token;
	}

}

