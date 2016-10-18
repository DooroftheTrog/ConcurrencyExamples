package org.paumard.runnable;

public class ThreadsExample 
{
	public static void main(String args[])
	{
		Runnable run = () ->{
			System.out.println("I am running in "+ Thread.currentThread().getName());
		};
		
		Thread t = new Thread(run);
		t.setName("My Thread");
		
		//runs the new thread within the method
		t.start();
		//run in main methods thread
		//t.run(); 
		
	}
}
