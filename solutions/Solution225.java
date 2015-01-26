// Problem 225: https://projecteuler.net/problem=225
/*
The solution to this problem is based on the fact that
if you take mod X of every term in the tribonacci
sequence, where X is the number being tested, the
sequence will eventually loop. If the loop contains 0,
then there is a term that is divisible by X. If not,
it will eventually loop back to the first three terms,
which is a pretty convenient condition to check for.
*/

import java.math.BigInteger;

public class Solution225
{
	public static void main(String[] args)
	{
		int count = 0;
		for (int i = 3; ; i += 2)
		{
			int tri1 = 1;
			int tri2 = 1;
			int tri3 = 1;
			while (true)
			{
				int temp = (tri1 + tri2 + tri3) % i;
				tri1 = tri2;
				tri2 = tri3;
				tri3 = temp;
				if (tri3 == 0)
				{
					break;
				}
				else if (tri1 * tri2 * tri3 == 1)
				{
					count++;
					if (count == 124)
					{
						System.out.println(i);
						System.exit(0);
					}
					break;
				}
			}
		}
	}
}