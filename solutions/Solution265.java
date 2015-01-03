// Problem 265: https://projecteuler.net/problem=265
// Takes roughly a minute to run.

public class Solution265
{
	public static void main(String[] args)
	{
		// baseLength is N in S(N). Changing this variable should
		// solve the problem for that value of N.
		int baseLength = 5;
		int fullLength = (int)Math.pow(2, baseLength);
		long total = 0;
		String head = "1";
		for (int i = 0; i < baseLength; i++)
		{
			head += "0";
		}
		head += "1";
		for (int i = 0; i < (int)Math.pow(2, fullLength - head.length()); i++)
		{
			String digits = head + longToBinaryString(i, fullLength - head.length());
			if (checkDigits(baseLength, digits))
			{
				digits = digits.substring(1, digits.length()) + digits.charAt(0);
				total += binaryStringToLong(digits);
			}
		}
		System.out.println(total);
	}
	
	private static boolean checkDigits(int baseLength, String digits)
	{
		int reqLength = (int)Math.pow(2, baseLength);
		if (digits.length() != reqLength) return false;
		boolean[] sequences = new boolean[reqLength];
		for (int i = 0; i <= reqLength - baseLength; i++)
		{
			String sequence = digits.substring(i, i + baseLength);
			int decSequence = (int)binaryStringToLong(sequence);
			if (sequences[decSequence])
			{
				return false;
			}
			else
			{
				sequences[decSequence] = true;
			}
		}
		for (int i = reqLength - baseLength + 1; i < reqLength; i++)
		{
			int endLength = reqLength - i;
			int startLength = baseLength - endLength;
			String sequence = digits.substring(i, reqLength) + digits.substring(0, startLength);
			int decSequence = (int)binaryStringToLong(sequence);
			if (sequences[decSequence])
			{
				return false;
			}
			else
			{
				sequences[decSequence] = true;
			}
		}
		return true;
	}
	
	// Converts a String of 0s and 1s to an equivalent long in decimal.
	private static long binaryStringToLong(String binary)
	{
		long num = 0;
		long value = 1;
		for (int i = binary.length() - 1; i >= 0; i--)
		{
			num += value * (binary.charAt(i) - '0');
			value *= 2;
		}
		return num;
	}
	
	// Converts a long to an equivalent binary number, stored as a String.
	private static String longToBinaryString(long decimal, int padding)
	{
		String binary = "";
		long value = 1;
		while (decimal > 0)
		{
			long digit = decimal % 2;
			decimal = (decimal - digit) / 2;
			binary = digit + binary;
		}
		for (int i = binary.length(); i < padding; i++)
		{
			binary = "0" + binary;
		}
		return binary;
	}
}