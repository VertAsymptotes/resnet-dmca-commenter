package macformatter;
public class Formatter
{
	public static String[] format(String str) throws IllegalArgumentException
	{
		String orig = new String();
		for(char c : str.toCharArray())
			if(Character.isLetterOrDigit(c))
				orig += c;
		if(!isValid(orig))
			throw new IllegalArgumentException("MAC Address is invalid!");
		
		//first format: xxxx.xxxx.xxxx
		String split34 = orig.substring(0, 4) + "." + orig.substring(4, 8) + "." + orig.substring(8, 12);
		//second format: xx:xx:xx:xx:xx:xx
		String split2c6 = new String();
		for(int i = 0; i < 11; i += 2)
			split2c6 += orig.substring(i, i + 2) + ":";
		split2c6 = split2c6.substring(0, split2c6.length() - 1); // to remove the last colon
		//third format: xx-xx-xx-xx-xx-xx
		String split2d6 = split2c6.replace(":", "-");
		String [] rVal = {split34, split2c6, split2d6};
		return rVal;
	}
	
	private static boolean isValid(String str)
	{
		if(str.length() != 12)
			return false;
		for(char c : str.toCharArray())
			if(!Character.isDigit(c) && (c < 65 || c > 70) && (c < 97 || c > 102))
				return false;
		return true;
	}
}