package dmca;

import java.util.*;
import java.text.*;

public class TimeConverter
{
	public static String parseUnix(long time)
	{
		//Date d = new Date(time * 1000);
		return new Date(time * 1000).toString();
	}
	
	@SuppressWarnings("finally")
	public static String parseOther(String str)
	{	
		String pattern = new String("dd MMM yyyy HH:mm:ss Z");
		SimpleDateFormat fmt = new SimpleDateFormat(pattern);//pattern);
		Date d = null;
		try
		{
			d = fmt.parse(str);
		}
		catch (ParseException e)
		{
			d = parseW3C(str);
		}
		finally
		{
			if(d == null)
				return null;
			return d.toString();
		}
	}
	
	private static Date parseW3C(String str)
	{
		if (str == null || str.length() == 0)
			return null;

		try
		{
			int year = Integer.parseInt(str.substring(0, 4));
			int month = Integer.parseInt(str.substring(5, 7));
			int day = Integer.parseInt(str.substring(8, 10));
			int hour = Integer.parseInt(str.substring(11, 13));
			int minute = Integer.parseInt(str.substring(14, 16));
			int second = Integer.parseInt(str.substring(17, 19));
			String zone = "GMT" + str.substring(19);

			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, day, hour, minute, second);
			cal.set(Calendar.MILLISECOND, 0);
			cal.setTimeZone(TimeZone.getTimeZone(zone));
			return cal.getTime();
		}
		catch (Exception e)
		{
			return null;
		}
	}
}