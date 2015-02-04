// Problem 357: https://projecteuler.net/problem=357
/*
This one is more or less just brute force, with the one
optimization being that I only check every 4th number
starting with 2. The reasoning for this is that a valid
number cannot be odd, because it must be divisible by 1,
so you will get n+1 which will be even. Thus they must all
be even, but then they're all divisible by 2, so n+2 must
be prime, so n+2 cannot be divisible by 4.
In any case, this one also runs a bit slow.
*/

public class Solution357
{
	public static void main(String[] args)
	{
		long total = 1;
		for (long i = 2; i <= 100000000; i += 4)
		{
			boolean allPrime = true;
			for (int j = 1; j*j <= i && allPrime; j++)
			{
				if (i % j == 0)
				{
					long checkPrime = j + (i / j);
					boolean isPrime = true;
					for (int k = 2; k*k <= checkPrime && allPrime; k++)
					{
						if (checkPrime % k == 0)
						{
							allPrime = false;
						}
					}
				}
			}
			if (allPrime)
			{
				total += i;
			}
		}
		System.out.println(total);
	}
}