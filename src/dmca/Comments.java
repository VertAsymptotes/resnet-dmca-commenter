package dmca;

import java.util.Calendar;

public class Comments
{
	private static Calendar c = Calendar.getInstance();
	private static String date = (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR);
	public static String netappsComment(String user, String ticket)
	{
		return new String(
				"DMCA Copyright Violation - Please contact the ACMS Help Desk at 858-534-2267\r\n" +
				"Username: " + user + "\r\n" +
				"Ticket #: " + ticket);
	}
	public static String alenComment(String tech, String vio)
	{
		return new String("DMCA Block - ACMS; " + vio + " Vio - " + tech + " - " + date);
	}
	public static String uberComment(String tech, String vio)
	{
		return new String("DMCA " + vio + " Vio - " + tech + " " + date);
	}
}