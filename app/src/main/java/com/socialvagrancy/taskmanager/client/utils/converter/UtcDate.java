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

public class UtcDate
{
	public static String localToUtc(String timestamp)
	{
		String utc_zone = "UTC";
		LocalDateTime local = LocalDateTime.parse(timestamp);
		
		ZonedDateTime utc_time = ZonedDateTime.of(local, ZoneId.systemDefault());

		return utc_time.withZoneSameInstant(ZoneId.of(utc_zone)).toLocalDateTime().toString();
	}

	public static String utcToLocal(String timestamp)
	{
		String utc_zone = "UTC";
		LocalDateTime local = LocalDateTime.parse(timestamp);
		
		ZonedDateTime utc_time = ZonedDateTime.of(local, ZoneId.of(utc_zone));

		return utc_time.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().toString();
	}

}

