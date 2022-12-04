//===================================================================
// RsaKeys.java
// 	Description:
// 		This class allows importing of the server's public and
// 		private key for token signing. The keys are generated
// 		on server start and stored in ../lib/security/public
// 		and ../lib/security/private. The location of these keys
// 		will be hard coded in this version and until it's
// 		necessary to allow it to be modified.
//===================================================================

package com.socialvagrancy.taskmanager.server.utils.security;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class RsaKeys
{
	public static RSAPrivateKey importPrivateKey() throws Exception
	{
		String file_path = "../lib/security/private/private_key.pem";
	
		try
		{
			File file = new File(file_path);

			String priv_key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());
			priv_key = priv_key.replace("-----BEGIN PRIVATE KEY-----", "")
				.replaceAll(System.lineSeparator(), "")
				.replace("-----END PRIVATE KEY-----", "");

			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decoded_key = decoder.decode(priv_key);
	       
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded_key);
			KeyFactory kf = KeyFactory.getInstance("RSA");
		
			return (RSAPrivateKey) kf.generatePrivate(spec);
		}
		catch(Exception e)
		{
			throw e;
		}	
	}

	public static RSAPublicKey importPublicKey() throws Exception
	{
		String file_path = "../lib/security/public/public_key.pem";
		
		try
		{
			File file = new File(file_path);

			String pub_key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

			pub_key = pub_key.replace("-----BEGIN PUBLIC KEY-----", "")
				.replaceAll(System.lineSeparator(), "")
				.replace("-----END PUBLIC KEY-----", "");
	
			byte[] decoded_key = Base64.getDecoder().decode(pub_key);

			KeyFactory kf = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded_key);

			return (RSAPublicKey) kf.generatePublic(spec);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
