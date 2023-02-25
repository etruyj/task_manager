//===================================================================
// UtcDate.java
// 	Description:
// 		This code converts dates to and from UTC to the current
// 		time zone.
//===================================================================

package com.socialvagrancy.taskmanager.client.utils.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UtcDate
{
	private static DateTimeFormatter client_format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mma");
        private static DateTimeFormatter psql_format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        public static String localToUtc(String timestamp, String format)
	{
		String utc_zone = "UTC";
                DateTimeFormatter passed_format = DateTimeFormatter.ofPattern(format);
                LocalDateTime local = LocalDateTime.parse(timestamp, passed_format);
		
		ZonedDateTime utc_time = ZonedDateTime.of(local, ZoneId.systemDefault());

//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return utc_time.withZoneSameInstant(ZoneId.of(utc_zone)).toLocalDateTime().format(psql_format);
	}

	public static String utcToLocal(String timestamp, String format)
	{
		String utc_zone = "UTC";
                LocalDateTime utc_time = LocalDateTime.parse(timestamp, psql_format);
                
		ZonedDateTime local = ZonedDateTime.of(utc_time, ZoneId.of(utc_zone));

                DateTimeFormatter client_desired_format = DateTimeFormatter.ofPattern(format);
                
                return local.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().format(client_desired_format);
	}

}

