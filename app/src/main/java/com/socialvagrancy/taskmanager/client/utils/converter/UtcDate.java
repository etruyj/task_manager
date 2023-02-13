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
	public static String localToUtc(String timestamp)
	{
		String utc_zone = "UTC";
		LocalDateTime local = LocalDateTime.parse(timestamp);
		
		ZonedDateTime utc_time = ZonedDateTime.of(local, ZoneId.systemDefault());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return utc_time.withZoneSameInstant(ZoneId.of(utc_zone)).toLocalDateTime().format(formatter);
	}

	public static String utcToLocal(String timestamp)
	{
		String utc_zone = "UTC";
		LocalDateTime local = LocalDateTime.parse(timestamp);
		
		ZonedDateTime utc_time = ZonedDateTime.of(local, ZoneId.of(utc_zone));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return utc_time.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
	}

}

