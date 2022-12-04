//===================================================================
// JWT.java
// 	Description:
// 		Implements Auth0.com's JWT library to build and validate
// 		JWT tokens.
//===================================================================

package com.socialvagrancy.taskmanager.server.utils.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class Token
{
	public static String generateJWT() throws Exception
	{
		try
		{
			Algorithm algorithm = Algorithm.RSA256(RsaKeys.importPublicKey(), RsaKeys.importPrivateKey());
		
			String jwt = JWT.create()
					.withIssuer("socailvagrancy")
					.sign(algorithm);

			return jwt;

		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
