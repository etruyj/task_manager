//===================================================================
// Date.java
// 	Description:
// 		This class holds all the functions necessary for
// 		converting the dates to a format that works for the
// 		database.
//===================================================================

package com.socialvagrancy.taskmanager.server.utils.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date
{
	public static String timestampToPsql(String timestamp)
	{
		// Converts yyyy-MM-ddTHH:mm:ss to 
		// Postgres format yyyy-MM-dd HH:mm:ss
		LocalDateTime passed = LocalDateTime.parse(timestamp);
		DateTimeFormatter psql_format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return passed.format(psql_format);
	}
}
