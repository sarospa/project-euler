// Problem 190: https://projecteuler.net/problem=190
/*
This solution is a bit brute force...basically, for
each set of numbers, I start out by setting all of them
to 1. Then I iterate over every single pair and adjust
each pair in turn until I've reached the maximum
product from only messing with those two numbers.
Then I repeat that until the numbers stop changing.
As much as that sounds like an abominable approach
on paper, there are relatively few pairs to iterate
over. However, this does reveal a clear pattern that
could form the basis for a much, much faster solution
if I was so inclined.
*/

import java.math.BigDecimal;

public class Solution190
{
	public static void main(String[] args)
	{
		long total = 0;
		for (int i = 2; i <= 15; i++)
		{
			BigDecimal max = maximize(i);
			total += max.intValue();
		}
		System.out.println(total);
	}
	
	public static BigDecimal maximize(int size)
	{
		if (size < 1)
		{
			return null;
		}
		BigDecimal[] terms = new BigDecimal[size];
		for (int i = 0; i < terms.length; i++)
		{
			terms[i] = BigDecimal.ONE;
		}
		boolean locked = false;
		while (!locked)
		{
			locked = true;
			for (int index1 = 0; index1 < size; index1++)
			{
				for (int index2 = index1 + 1; index2 < size; index2++)
				{
					BigDecimal increment = new BigDecimal("0.1");
					int count = 0;
					while (count < 10)
					{
						BigDecimal baseValue1 = terms[index1];
						BigDecimal baseValue2 = terms[index2];
						BigDecimal total = calcProduct(terms);
						terms[index1] = terms[index1].subtract(increment);
						terms[index2] = terms[index2].add(increment);
						BigDecimal nextTotal = calcProduct(terms);
						if (terms[index1].compareTo(BigDecimal.ZERO) <= 0 || nextTotal.compareTo(total) <= 0)
						{
							terms[index1] = baseValue1;
							terms[index2] = baseValue2;
							increment = increment.multiply(new BigDecimal("-0.1"));
							count++;
						}
						else
						{
							locked = false;
						}
					}
				}
			}
			/*for (int i = 0; i < terms.length; i++)
			{
				System.out.print(terms[i].toString() + "^" + (i+1) + " ");
			}
			System.out.println("= " + calcProduct(terms).intValue());*/
		}
		return calcProduct(terms);
	}
	
	public static BigDecimal calcProduct(BigDecimal[] terms)
	{
		BigDecimal total = BigDecimal.ONE;
		for (int i = 0; i < terms.length; i++)
		{
			total = total.multiply(terms[i].pow(i+1));
		}
		return total;
	}
}