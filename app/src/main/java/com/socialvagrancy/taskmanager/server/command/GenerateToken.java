//===================================================================
// GenerateToken.java
// 	Description:
// 		Queries the user database and creates an access token
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.server.utils.security.AccessToken;
import com.socialvagrancy.taskmanager.server.utils.security.Token;
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
	public static String authenticate(String username, String password, String organization, PostgresConnector psql, Logger logbook) throws Exception
	{
		logbook.INFO("Authenticating user [" + username + "] for organization [" + organization + "]");

		String query = "SELECT client_user.id, client_user.password, organization.id FROM client_user "
			+ "INNER JOIN user_organization ON user_organization.user_id = client_user.id "
			+ "INNER JOIN organization ON organization.id = user_organization.organization_id "
			+ "WHERE client_user.name=? AND organization.name=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, username);
			pst.setString(2, organization);

			ResultSet rs = pst.executeQuery();

			if(rs.next())
			{
				if(BCrypt.checkpw(password, rs.getString(2)))
				{
					String jwt = Token.generateJWT();

					AccessToken.create(jwt, UUID.fromString(rs.getString(1)), UUID.fromString(rs.getString(3)), psql, logbook); 

					return jwt;
				}
				else
				{
					throw new Exception("Incorrect password entered for user [" + username + "]");
				}
			}
			else
			{
				throw new Exception("Unabled to find user [" + username + "] belonging to organization [" + organization + "]");
			}
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

		return token.toString();
	}

}

