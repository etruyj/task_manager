//===================================================================
// ValidateToken.java
// 	Description:
// 		Verifies the token exists in the access_token table.
// 		if so and the expiration is after the current time,
// 		renew the access_token and produce the credentials.
// 		If not, prune all expired tokens from the database.
//===================================================================

package com.socialvagrancy.taskmanager.server.command;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.taskmanager.server.utils.security.AccessToken;
import com.socialvagrancy.taskmanager.structure.server.Credential;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class ValidateToken
{
	public static Credential generateCredentials(String token, PostgresConnector psql, Logger logbook) throws Exception
	{
		// Grab the token from the auth_token
		// It's three pieces separated by spaces
		// auth: Bearer TOKEN
		String[] parts = token.split(" ");
		token = parts[parts.length-1];

		Credential credential;

		String query = "SELECT client_user.id, client_user.name, user_organization.permissions, organization.id, access_token.expires, access_token.id FROM access_token "
				+ "INNER JOIN  client_user ON client_user.id = access_token.user_id "
				+ "INNER JOIN user_organization ON user_organization.user_id = client_user.id "
				+ "INNER JOIN organization ON organization.id = user_organization.organization_id "
				+ "WHERE access_token.token=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setString(1, token);

			ResultSet rs = pst.executeQuery();

			if(rs.next())
			{
				if(rs.getTimestamp(5).toLocalDateTime().isAfter(LocalDateTime.now()))
				{
					credential = new Credential()
							.setUserId(rs.getString(1))
							.setUsername(rs.getString(2))
							.setPermissions(rs.getString(3))
							.setOrganization(rs.getString(4));

					AccessToken.renewAccess(UUID.fromString(rs.getString(6)), psql, logbook);

					return credential;
				}
				else
				{
					// Token expired. No need to log.
					// There will probably be too many of these events.
					AccessToken.pruneExpired(psql, logbook);
					throw new Exception("Access token for user [" + rs.getString(2) + "] has expired.");
				}

			}
			else
			{
				System.err.println("QUERY: " + query);
				throw new Exception("Invalid token used to access the client.");
			}
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			
			throw new Exception("Unable to query database.");
		}
	}
}
