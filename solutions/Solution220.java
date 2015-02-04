// Problem 220: https://projecteuler.net/problem=220
/*
This solution simply uses caching to make this
considerably faster. If you expand a given 'a' or 'b'
some number of times, it's exactly the same as any
other 'a' or 'b' expanded the same number of times.
The actual implementation of applying cached data
to a Cursor object is slightly ugly, but it's simple
in concept at least. There's also a bit of fiddling
to make sure I land on exactly the required number
of steps.
*/

public class Solution220
{
	public static final String A = "aRbFR";
	public static final String B = "LFaLb";

	public static void main(String[] args)
	{
		Cursor c = new Cursor();
		int size = 50;
		long steps = 1000000000000L;
		DragonCache cache = new DragonCache(size);
		genDragonCache(size, cache);
		heighwayDragon(size, "Fa", c, steps, cache);
		System.out.println(c.getX() + "," + c.getY());
	}
	
	public static void genDragonCache(int depth, DragonCache cache)
	{
		if (depth > 0)
		{
			genDragonCache(depth - 1, cache);
		}
		Cursor cursorA = new Cursor();
		for (int i = 0; i < A.length(); i++)
		{
			switch(A.charAt(i))
			{
				case 'F':
					cursorA.forward();
					break;
				case 'R':
					cursorA.right();
					break;
				case 'L':
					cursorA.left();
					break;
				case 'a':
					if (depth > 0)
					{
						cursorA.applyTransformation(cache.getACache(depth - 1));
					}
					break;
				case 'b':
					if (depth > 0)
					{
						cursorA.applyTransformation(cache.getBCache(depth - 1));
					}
					break;
			}
		}
		Cursor cursorB = new Cursor();
		for (int i = 0; i < B.length(); i++)
		{
			switch(B.charAt(i))
			{
				case 'F':
					cursorB.forward();
					break;
				case 'R':
					cursorB.right();
					break;
				case 'L':
					cursorB.left();
					break;
				case 'a':
					if (depth > 0)
					{
						cursorB.applyTransformation(cache.getACache(depth - 1));
					}
					break;
				case 'b':
					if (depth > 0)
					{
						cursorB.applyTransformation(cache.getBCache(depth - 1));
					}
					break;
			}
		}
		cache.setACache(depth, cursorA);
		cache.setBCache(depth, cursorB);
	}
	
	public static void heighwayDragon(int depth, String instructions, Cursor c, long steps, DragonCache cache)
	{
		for (int i = 0; i < instructions.length(); i++)
		{
			switch (instructions.charAt(i))
			{
				case 'F':
					c.forward();
					break;
				case 'R':
					c.right();
					break;
				case 'L':
					c.left();
					break;
				case 'a':
					if (depth > 0)
					{
						Cursor cacheC = cache.getACache(depth - 1);
						if (c.getSteps() + cacheC.getSteps() <= steps)
						{
							c.applyTransformation(cacheC);
						}
						else
						{
							heighwayDragon(depth - 1, A, c, steps, cache);
						}
					}
					break;
				case 'b':
					if (depth > 0)
					{
						Cursor cacheC = cache.getBCache(depth - 1);
						if (c.getSteps() + cacheC.getSteps() <= steps)
						{
							c.applyTransformation(cacheC);
						}
						else
						{
							heighwayDragon(depth - 1, B, c, steps, cache);
						}
					}
					break;
			}
			if (c.getSteps() == steps)
			{
				break;
			}
			else if (c.getSteps() > steps)
			{
				System.out.println("Error: Iterated through " + c.getSteps() + " out of " + steps + " steps.");
				System.exit(1);
			}
		}
	}
	
	private static class Cursor
	{
		private long x;
		private long y;
		private long faceX;
		private long faceY;
		private long steps;
		
		public Cursor()
		{
			x = 0;
			y = 0;
			faceX = 0;
			faceY = 1;
			steps = 0;
		}
		
		public long getX()
		{
			return x;
		}
		
		public long getY()
		{
			return y;
		}
		
		public long getSteps()
		{
			return steps;
		}
		
		public long getFaceX()
		{
			return faceX;
		}
		
		public long getFaceY()
		{
			return faceY;
		}
		
		public void forward()
		{
			x += faceX;
			y += faceY;
			steps++;
		}
		
		public void right()
		{
			if (faceX == 0 && faceY == 1)
			{
				faceX = 1;
				faceY = 0;
			}
			else if (faceX == 1 && faceY == 0)
			{
				faceX = 0;
				faceY = -1;
			}
			else if (faceX == 0 && faceY == -1)
			{
				faceX = -1;
				faceY = 0;
			}
			else if (faceX == -1 && faceY == 0)
			{
				faceX = 0;
				faceY = 1;
			}
		}
		
		public void left()
		{
			if (faceX == 0 && faceY == 1)
			{
				faceX = -1;
				faceY = 0;
			}
			else if (faceX == -1 && faceY == 0)
			{
				faceX = 0;
				faceY = -1;
			}
			else if (faceX == 0 && faceY == -1)
			{
				faceX = 1;
				faceY = 0;
			}
			else if (faceX == 1 && faceY == 0)
			{
				faceX = 0;
				faceY = 1;
			}
		}
		
		public void applyTransformation(Cursor trans)
		{
			long x = trans.getX();
			long y = trans.getY();
			if (this.faceX == 1 && faceY == 0)
			{
				long temp = x;
				x = y;
				y = -temp;
			}
			else if (this.faceX == 0 && faceY == -1)
			{
				x = -x;
				y = -y;
			}
			else if (this.faceX == -1 && faceY == 0)
			{
				long temp = x;
				x = -y;
				y = temp;
			}
			this.x += x;
			this.y += y;
			if (trans.getFaceX() == 1 || trans.getFaceY() == 0)
			{
				this.right();
			}
			else if (trans.getFaceX() == 0 || trans.getFaceY() == -1)
			{
				this.right();
				this.right();
			}
			else if (trans.getFaceX() == -1 || trans.getFaceY() == 0)
			{
				this.left();
			}
			this.steps += trans.getSteps();
		}
		
		public String toString()
		{
			return "Location: " + this.x + "," + this.y + "  Facing: " + this.faceX + "," + this.faceY + "  Steps: " + this.steps;
		}
	}
	
	private static class DragonCache
	{
		Cursor[] aCache;
		Cursor[] bCache;
		
		public DragonCache(int size)
		{
			aCache = new Cursor[size];
			bCache = new Cursor[size];
		}
		
		public Cursor getACache(int index)
		{
			if (index >= aCache.length || index < 0)
			{
				return null;
			}
			return aCache[index];
		}
		
		public void setACache(int index, Cursor c)
		{
			if (index >= aCache.length || index < 0 || aCache[index] != null)
			{
				return;
			}
			aCache[index] = c;
		}
		
		public Cursor getBCache(int index)
		{
			if (index >= bCache.length || index < 0)
			{
				return null;
			}
			return bCache[index];
		}
		
		public void setBCache(int index, Cursor c)
		{
			if (index >= bCache.length || index < 0 || bCache[index] != null)
			{
				return;
			}
			bCache[index] = c;
		}
	}
}