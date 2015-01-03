// Just a list of useful functions that might go in multiple solutions.

import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;

public class Toolbox
{
	public static void main(String[] args)
	{
	
	}

	/* Requires:
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.io.BufferedReader;
	import java.io.FileReader;
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
}