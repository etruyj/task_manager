//==================================================================
// AccessToken.java 
//	Description:
//		Commands for the access tokens.
//
//		create - creates a new access token
//		pruneExpired - deletes expired access tokens
//		RenewAccess - renew the specified access token
//		RevokeAll - revokes all access tokens.
//==================================================================

package com.socialvagrancy.taskmanager.server.utils.security;

import com.socialvagrancy.taskmanager.server.utils.database.PostgresConnector;
import com.socialvagrancy.utils.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class AccessToken
{
	public static void create(String jwt, UUID user_id, UUID org_id, PostgresConnector psql, Logger logbook)
	{
		UUID id = UUID.randomUUID();
		LocalDateTime expires = LocalDateTime.now().plusHours(1);

		String query = "INSERT INTO access_token "
				+ "(id, token, user_id, organization_id, expires) "
				+ "VALUES (?, ?, ?, ?, ?);";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, id);
			pst.setString(2, jwt);
			pst.setObject(3, user_id);
			pst.setObject(4, org_id);
			pst.setObject(5, expires);

			pst.executeUpdate();
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
		}

	}

	public static void pruneExpired(PostgresConnector psql, Logger logbook) throws Exception
	{
		LocalDateTime access_time = LocalDateTime.now();

		logbook.INFO("Pruning expired access tokens.");

		String query = "DELETE FROM access_token " 
				+ "WHERE expires<=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, access_time);

			pst.executeUpdate();
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());
			
			throw new Exception("Unabled to prune expired tokens.");
		}
	}

	public static void renewAccess(UUID token_id, PostgresConnector psql, Logger logbook) throws Exception
	{
		// Access is renewed to add an additional hour
		LocalDateTime access_time = LocalDateTime.now().plusHours(1);

		String query = "UPDATE access_token SET expires=? "
		       		+ "WHERE id=?;";

		try
		{
			PreparedStatement pst = psql.prepare(query, logbook);

			pst.setObject(1, access_time);
			pst.setObject(2, token_id);

			pst.executeUpdate();
		}
		catch(SQLException e)
		{
			logbook.ERR(e.getMessage());

			throw new Exception("Unable to update access time for token [" + token_id + "]");
		}

	}

	public static void revokeAll(PostgresConnector psql, Logger logbook)
	{
		logbook.WARN("REVOKING ACCESS FOR ALL ISSUED TOKENS");

		String query = "DELETE FROM access_token";

		psql.update(query, logbook);
	}
}
