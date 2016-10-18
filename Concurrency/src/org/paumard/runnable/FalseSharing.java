package org.paumard.runnable;

public class FalseSharing {
	
	public static int NUM_THREADS_MAX = 4;
	public final static long ITERATIONS = 50_000_000L;
	
	private static VolatileLongPadded[] paddedLongs;
	private static VolatileLongUnPadded[] unPaddedLongs;
	
	//the reason you instantiate longs before and afterwards is so that the 
	//instantiated long variable is the only one on the caches line in the CPU's memory
	//this is to prevent false sharing called, Variable Padding
	//you should generally not use variable padding
	public final static class VolatileLongPadded{
		public long q1, q2, q3, q4, q5, q6;
		public volatile long value = 0L;
		public long q11, q12, q13, q14, q15, q16;
	}
	
	public final static class VolatileLongUnPadded{
		public volatile long value = 0L;
	}
	
	static{
		paddedLongs = new VolatileLongPadded[NUM_THREADS_MAX];
		for(int i = 0; i < paddedLongs.length; i++)
		{
			paddedLongs[i] = new VolatileLongPadded();
		}
		
		unPaddedLongs = new VolatileLongUnPadded[NUM_THREADS_MAX];
		for(int i = 0; i < paddedLongs.length; i++)
		{
			unPaddedLongs[i] = new VolatileLongUnPadded();
		}
	}
	
	public static void main(final String[] args) throws Exception{
		runBenchmark();
	}

	private static void runBenchmark() throws InterruptedException{
		long begin, end;
		
		for(int i = 1; i <= NUM_THREADS_MAX; i++)
		{
			Thread[] threads = new Thread[i];
			
			for(int j = 0; j < threads.length; j++)
			{
				threads[j] = new Thread(createPaddedRunnable(j));
			}
			
			begin = System.currentTimeMillis();
			for(Thread t : threads){t.start();}
			for(Thread t : threads){t.join();}
			end = System.currentTimeMillis();
			System.out.printf("	Padded # threads %d - T = %dms\n", i, end-begin);
			
			for(int j = 0; j < threads.length; j++)
			{
				threads[j] = new Thread(createUnpaddedRunnable(j));
			}
			begin = System.currentTimeMillis();
			for(Thread t : threads){t.start();}
			for(Thread t : threads){t.join();}
			end = System.currentTimeMillis();
			System.out.printf("	UnPadded # threads %d - T = %dms\n", i, end-begin);			
		}
	}
	
	private static Runnable createUnpaddedRunnable(final int k)
	{
		//lambda expression which returns the instance of the longs to use within the thread
		return () -> {
			long i = ITERATIONS + 1;
			while(0 != --i)
			{
				unPaddedLongs[k].value = i;
			}
		}; 
	}
	
	private static Runnable createPaddedRunnable(final int k)
	{
		//lambda expression which returns the instance of the longs to use within the thread
		Runnable paddedTouch = () -> {
			long i = ITERATIONS +1;
			while(0 != --i)
			{
				paddedLongs[k].value = i;
			}
		};
		
		return paddedTouch;
	}
}
