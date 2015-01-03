// Problem 185: https://projecteuler.net/problem=185
// Requires input file Input185.txt
// Warning, this one is pretty memory-hungry. It may run better with -Xmx4g set,
// although I was able to run this version of the program without it.
/*
The theory here is that, although there are 10^16 possible guesses, if
you take two partial guesses where the same spots are filled in (eg, the first four
digits) and they each have the same number of matches on every input-guess,
then they're effectively equivalent, and will either both be right or both wrong
(and since there is one unique correct answer, they will always both be wrong).
My program iteratively fills in one digit at a time and records every possible
guess-matching state, where two states are equal if they have the same number of
matches on every input-guess. There are only ~9 billion possible states per iteration,
which narrows things down quite a bit. Furthermore, if the same state turns up twice,
it can be tossed out. However, even this is too memory-intensive to work. The final
key to solving this problem is realizing that if you generate two sets of states, one
where the first half of the digits have been filled in, and one where the second half of
the digits have been filled in, then you'll end up with two sets of states with an overlap
of exactly one - the correct answer. From there, it's a simple matter of concatenating the
two associated partial guesses.
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.FileReader;

public class Solution185
{
	public static void main(String[] args)
	{
		String[] input = readFile("Input185.txt");
		String[] guesses = new String[input.length];
		int[] correct = new int[input.length];
		for (int i = 0; i < input.length; i++)
		{
			String[] temp = input[i].split(";");
			guesses[i] = temp[0];
			correct[i] = Integer.parseInt(temp[1]);
		}
		Hashtable<Long, Long> states = new Hashtable<Long, Long>();
		int[] prevCorrect = new int[input.length];
		int[] nextCorrect = new int[prevCorrect.length];
		long startState = 0;
		for (int i = 0; i < prevCorrect.length; i++)
		{
			prevCorrect[i] = 0;
		}
		states.put(new Long(startState), new Long(0));
		for (int i = 0; i <= guesses[i].length() / 2 - 1; i++)
		{
			Hashtable<Long, Long> nextStates = new Hashtable<Long, Long>();
			Enumeration<Long> enumStates = states.keys();
			while (enumStates.hasMoreElements())
			{
				Long prevState = enumStates.nextElement();
				Long prevNum = states.get(prevState);
				states.remove(prevState);
				if (prevNum.longValue() < 0) continue;
				longToArray(prevState.longValue(), prevCorrect, correct);
				for (int j = 0; j <= 9; j++)
				{
					boolean mismatch = false;
					for (int k = 0; k < prevCorrect.length; k++)
					{
						nextCorrect[k] = prevCorrect[k];
					}
					for (int k = 0; k < guesses.length; k++)
					{
						if (guesses[k].charAt(i) - '0' == j)
						{
							nextCorrect[k]++;
							if (nextCorrect[k] > correct[k])
							{
								mismatch = true;
								break;
							}
						}
					}
					Long nextState = new Long(arrayToLong(nextCorrect, correct));
					int correctCount = 0;
					for (int k = 0; k < nextCorrect.length; k++)
					{
						correctCount += nextCorrect[k];
					}
					if (nextStates.containsKey(nextState) || mismatch)
					{
						nextStates.put(nextState, new Long(-1));
					}
					else
					{
						nextStates.put(nextState, prevNum * 10 + j);
					}
				}
			}
			int numSwept = 0;
			states.clear();
			states = nextStates;
			Enumeration<Long> deadSweep = states.keys();
			while (deadSweep.hasMoreElements())
			{
				Long check = deadSweep.nextElement();
				if (states.get(check).longValue() < 0)
				{
					states.remove(check);
					numSwept++;
				}
			}
			System.out.println("Pass " + i + ": " + states.size() + " states. " + numSwept + " dead end states removed.");
		}
		Hashtable<Long, Long> frontStates = states;
		states = new Hashtable<Long, Long>();
		states.put(new Long(arrayToLong(correct, correct)), new Long(0));
		for (int i = 0; i < correct.length; i++)
		{
			prevCorrect[i] = correct[i];
		}
		for (int i = guesses[0].length() - 1; i >= guesses[0].length() / 2; i--)
		{
			Hashtable<Long, Long> nextStates = new Hashtable<Long, Long>();
			Enumeration<Long> enumStates = states.keys();
			while (enumStates.hasMoreElements())
			{
				Long prevState = enumStates.nextElement();
				Long prevNum = states.get(prevState);
				states.remove(prevState);
				if (prevNum.longValue() < 0) continue;
				longToArray(prevState.longValue(), prevCorrect, correct);
				for (int j = 0; j <= 9; j++)
				{
					boolean mismatch = false;
					for (int k = 0; k < prevCorrect.length; k++)
					{
						nextCorrect[k] = prevCorrect[k];
					}
					for (int k = 0; k < guesses.length; k++)
					{
						if (guesses[k].charAt(i) - '0' == j)
						{
							nextCorrect[k]--;
							if (nextCorrect[k] < 0)
							{
								mismatch = true;
								break;
							}
						}
					}
					Long nextState = new Long(arrayToLong(nextCorrect, correct));
					int correctCount = 0;
					for (int k = 0; k < nextCorrect.length; k++)
					{
						correctCount += nextCorrect[k];
					}
					if (nextStates.containsKey(nextState) || mismatch)
					{
						nextStates.put(nextState, new Long(-1));
					}
					else
					{
						long concat = j;
						for (int k = i; k < guesses[0].length() - 1; k++)
						{
							concat *= 10;
						}
						nextStates.put(nextState, new Long(prevNum + concat));
					}
				}
			}
			int numSwept = 0;
			states.clear();
			states = nextStates;
			Enumeration<Long> deadSweep = states.keys();
			while (deadSweep.hasMoreElements())
			{
				Long check = deadSweep.nextElement();
				if (states.get(check).longValue() < 0)
				{
					states.remove(check);
					numSwept++;
				}
			}
			System.out.println("Pass " + i + ": " + states.size() + " states. " + numSwept + " dead end states removed.");
		}
		Hashtable<Long, Long> backStates = states;
		/*Enumeration<Long> frontKeys = frontStates.keys();
		Enumeration<Long> backKeys = backStates.keys();
		System.out.println("Front:");
		while (frontKeys.hasMoreElements())
		{
			Long key = frontKeys.nextElement();
			System.out.println(key + ":" + frontStates.get(key));
		}
		System.out.println("Back:");
		while (backKeys.hasMoreElements())
		{
			Long key = backKeys.nextElement();
			System.out.println(key + ":" + backStates.get(key));
		}*/
		Enumeration<Long> finalFront = frontStates.keys();
		while (finalFront.hasMoreElements())
		{
			Long finalState = finalFront.nextElement();
			if (backStates.containsKey(finalState))
			{
				System.out.println(frontStates.get(finalState).toString() + backStates.get(finalState).toString());
			}
		}
		
		/*Enumeration<Long> finalStates = states.keys();
		while (finalStates.hasMoreElements())
		{
			Long finalState = finalStates.nextElement();
			if (finalState.longValue() == arrayToLong(correct, correct))
			{
				System.out.println(states.get(finalState));
				System.exit(0);
			}
		}*/
	}
	
	// Takes a unique long, and an array to use as the base, and expands it into a list of digits
	// that it uses to populate an array.
	private static void longToArray(long source, int[] target, int[] base)
	{
		long value = 1;
		for (int i = 0; i < base.length; i++)
		{
			value *= base[i] + 1;
		}
		for (int i = target.length - 1; i >= 0; i--)
		{
			value /= base[i] + 1;
			int count = 0;
			while (source >= value)
			{
				source -= value;
				count++;
			}
			target[i] = count;
		}
	}
	
	// Takes an array containing digits, and an array to use as the base, and generates a unique long.
	private static long arrayToLong(int[] source, int[] base)
	{
		long value = 1;
		long num = 0;
		for (int i = 0; i < source.length; i++)
		{
			num += source[i] * value;
			value *= base[i] + 1;
		}
		return num;
	}
	
	// Takes a String containing digits, and uses it to populate an array.
	// Array is assumed to be of the same length as the string.
	private static void digitStringToArray(String digits, int[] target)
	{
		for (int i = 0; i < digits.length(); i++)
		{
			target[i] = digits.charAt(i) - '0';
		}
	}
	
	// Takes an array containing digits, and uses it to create a String of digits.
	private static String arrayToDigitString(int[] source)
	{
		String digits = "";
		for (int i = 0; i < source.length; i++)
		{
			digits += source[i];
		}
		return digits;
	}
	
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