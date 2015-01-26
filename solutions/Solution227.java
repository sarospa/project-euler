// Problem 227: https://projecteuler.net/problem=227
/*
This one just iterates through every possible state
and calculates the average until it looks stable.
So, expect it to take a while to produce an answer.
Thus, in this one I included the debug output, though
commented out by default. This solution is a bit brute
force, so it wouldbe a good candidate for going back
later and finding a more elegant solution.
*/

import java.math.BigInteger;

public class Solution227
{
	public static void main(String[] args)
	{
		int players = 100;
		BigInteger[] prevStates = new BigInteger[players / 2 + 1];
		BigInteger[] nextStates = new BigInteger[players / 2 + 1];
		for (int i = 0; i < prevStates.length; i++)
		{
			prevStates[i] = BigInteger.ZERO;
			nextStates[i] = BigInteger.ZERO;
		}
		prevStates[players/2] = BigInteger.ONE;
		BigInteger avgSum = BigInteger.ZERO;
		BigInteger avgDiv = BigInteger.ZERO;
		int turn = 0;
		int endTimer = 0;
		String prevAvg = "";
		while (true)
		{
			turn++;
			for (int player = 0; player < prevStates.length; player++)
			{
				if (prevStates[player].compareTo(BigInteger.ZERO) > 0 && player != 0)
				{
					int left = player - 1;
					if (left < 0) left = (int)Math.abs(left);
					int twoLeft = player - 2;
					if (twoLeft < 0) twoLeft = (int)Math.abs(twoLeft);
					int right = player + 1;
					if (right >= prevStates.length) right = players - right;
					int twoRight = player + 2;
					if (twoRight >= prevStates.length) twoRight = players - twoRight;
					nextStates[player] = nextStates[player].add(prevStates[player].multiply(BigInteger.valueOf(18)));
					nextStates[left] = nextStates[left].add(prevStates[player].multiply(BigInteger.valueOf(8)));
					nextStates[right] = nextStates[right].add(prevStates[player].multiply(BigInteger.valueOf(8)));
					nextStates[twoLeft] = nextStates[twoLeft].add(prevStates[player].multiply(BigInteger.valueOf(1)));
					nextStates[twoRight] = nextStates[twoRight].add(prevStates[player].multiply(BigInteger.valueOf(1)));
				}
			}
			avgSum = avgSum.multiply(BigInteger.valueOf(36));
			avgDiv = avgDiv.multiply(BigInteger.valueOf(36));
			avgSum = avgSum.add(nextStates[0].multiply(BigInteger.valueOf(turn)));
			avgDiv = avgDiv.add(nextStates[0]);
			String avg = bigFractionToDecString(avgSum, avgDiv, 0);
			avg = bigFractionToDecString(avgSum, avgDiv, 11 - avg.length());
			if (avg.equals(prevAvg))
			{
				endTimer++;
				if (endTimer == 1000)
				{
					if (avg.length() == 12 && avg.charAt(avg.length() - 1) >= '5')
					{
						avg = avg.substring(0, avg.length() - 1);
						for (int i = avg.length() - 1; i >= 0; i--)
						{
							if (avg.charAt(i) == '9')
							{	
								avg = avg.substring(0, i) + "0" + avg.substring(i + 1, avg.length());
							}
							else if (avg.charAt(i) != '.')
							{
								avg = avg.substring(0, i) + (char)(avg.charAt(i) + 1) + avg.substring(i + 1, avg.length());
								break;
							}
						}
						if (avg.charAt(0) == '0')
						{
							avg = "1" + avg;
						}
					}
					while (avg.charAt(avg.length() - 1) == '0' || avg.charAt(avg.length() - 1) == '.')
					{
						avg = avg.substring(0, avg.length() - 1);
					}
					System.out.println(avg);
					return;
				}
			}
			else
			{
				endTimer = 0;
				prevAvg = avg;
			}
			//System.out.println("Turn " + turn + ": " + avg + " - Timer: " + endTimer);
			for (int i = 0; i < prevStates.length; i++)
			{
				prevStates[i] = nextStates[i];
				nextStates[i] = BigInteger.ZERO;
			}
		}
	}
	
	public static String bigFractionToDecString(BigInteger top, BigInteger bottom, int fractionalPlaces)
	{
		if (bottom.compareTo(BigInteger.ZERO) == 0)
		{
			return "NaN";
		}
		String dec = top.divide(bottom).toString();
		top = top.mod(bottom);
		if (top.compareTo(BigInteger.ZERO) == 0 || fractionalPlaces <= 0)
		{
			return dec;
		}
		dec += ".";
		for (int  i = 0; i < fractionalPlaces && top.compareTo(BigInteger.ZERO) > 0; i++)
		{
			top = top.multiply(BigInteger.TEN);
			dec += top.divide(bottom).toString();
			top = top.mod(bottom);
		}
		return dec;
	}
}