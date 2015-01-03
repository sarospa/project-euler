// Problem 183: https://projecteuler.net/problem=183
/*
Once you know the trick, it's incredibly simple.
k should always be the integer closest to N / e.
Would be cleaner to remove BigIntegers, which was originally a precaution
when I expected the numbers in the fractions to get very high.
However, there is no GCD function for primitives, so this is more convenient.
*/

import java.math.BigInteger;

public class Solution183
{
	public static void main(String[] args)
	{
		int total = 0;
		for (int i = 5; i <= 10000; i++)
		{
			if (isTerminatingDecimal(BigInteger.valueOf(i), BigInteger.valueOf((int)Math.rint(i / Math.E))))
			{
				total -= i;
			}
			else
			{
				total += i;
			}
		}
		System.out.println(total);
	}
	
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