// Just a list of useful functions that might go in multiple solutions.

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class Toolbox
{
	public static void main(String[] args)
	{
	
	}

	/* Requires:
	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.util.ArrayList;
	import java.util.Arrays;
	*/
	private static String[] readFile(String fileName)
	{
		try
		{
			ArrayList<String> list = new ArrayList<String>();
			String current;
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((current = br.readLine()) != null) 
			{
				list.add(current);
			}
			return Arrays.copyOf(list.toArray(), list.size(), String[].class);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/* Requires:
	import java.math.BigInteger;
	*/
	public static boolean isTerminatingDecimal(BigInteger numer, BigInteger denom)
	{
		BigInteger gcd = numer.gcd(denom);
		numer = numer.divide(gcd);
		denom = denom.divide(gcd);
		while (denom.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
		{
			denom = denom.divide(BigInteger.valueOf(2));
		}
		while (denom.mod(BigInteger.valueOf(5)).equals(BigInteger.ZERO))
		{
			denom = denom.divide(BigInteger.valueOf(5));
		}
		return denom.equals(BigInteger.ONE);
	}
}