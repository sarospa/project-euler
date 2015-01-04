// Problem 215: https://projecteuler.net/problem=215

import java.util.ArrayList;

public class Solution215
{
	private static ArrayList<String> layouts = new ArrayList<String>();

	public static void main(String[] args)
	{
		int length = 32;
		int height = 10;
		horzLayouts(length, "");
		long[][] valid = new long[height][layouts.size()];
		for (int i = 0; i < valid[0].length; i++)
		{
			valid[0][i] = 1;
		}
		for (int i = 1; i < valid.length; i++)
		{
			for (int j = 0; j < valid[i].length; j++)
			{
				for (int k = 0; k < valid[i-1].length; k++)
				{
					if (crackFree(layouts.get(j), layouts.get(k)))
					{
						valid[i][j] += valid[i-1][k];
					}
				}
			}
		}
		long total = 0;
		for (int i = 0; i < valid[height-1].length; i++)
		{
			total += valid[height-1][i];
		}
		System.out.println(total);
	}
	
	private static boolean crackFree(String layout1, String layout2)
	{
		for (int i = 0; i < layout1.length(); i++)
		{
			if (layout1.charAt(i) == '|' && layout2.charAt(i) == '|')
			{
				return false;
			}
		}
		return true;
	}
	
	private static void horzLayouts(int length, String layout)
	{
		if (length == 2)
		{
			layouts.add(layout + "-");
		}
		else if (length == 3)
		{
			layouts.add(layout + "--");
		}
		else if (length > 3)
		{
			horzLayouts(length - 2, layout + "-|");
			horzLayouts(length - 3, layout + "--|");
		}
	}
}