package org.paumard.runnable;

public class ProduceConsume {
	
	private static Object lock = new Object();
	
	private static int[] buffer;
	private static int count;
	
	static class Producer {
		
		void produce()
		{
			synchronized(lock)
			{
				if(isFull(buffer))
				{
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
				buffer[count++] = 1;
				lock.notifyAll();
			}
		}
	}
	
	static class Consumer {

		void consume()
		{
			synchronized(lock)
			{
				if(isEmpty(buffer))
				{
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
				buffer[--count] = 0;
				lock.notifyAll();
			}
		}
	}
	
	static boolean isEmpty(int[] buffer)
	{
		return (count == 0);
	}
	
	static boolean isFull(int[] buffer)
	{
		return (count == buffer.length);
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		buffer = new int[100];
		count = 0;
		
		Producer p = new Producer();
		Consumer c = new Consumer();
		
		Runnable produceTask = () -> {
			
			for(int i = 0; i < 50; i++)
			{
				p.produce();
			}
			System.out.println("Done Producing");
		};
		
		Runnable consumeTask = () -> {
			
			for(int i = 0; i < 15; i++)
			{
				c.consume();
			}
			System.out.println("Done Consuming");
		};
		
		Thread consumerThread = new Thread(consumeTask);
		Thread producerThread = new Thread(produceTask);
		
		consumerThread.start();
		producerThread.start();
		  
		consumerThread.join();
		producerThread.join();
		
		System.out.println("Items in buffer: " + count);
	}

}
