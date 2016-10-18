
package org.paumard.runnable;
import org.paumard.runnable.longwrapper;

public class RaceCondition {

	public static void main(String args[]) throws InterruptedException
	{
		//RACE CONDITION TESTING
		/*
		longwrapper l = new longwrapper(0L);
		
		Runnable r = () ->{
			for(int i = 0; i < 1_000; i++)
			{
				l.incerementValue();
			}
		};
		
		Thread[] t = new Thread[1_000];
		
		for(int i = 0; i < t.length; i++)
		{
			t[i] = new Thread(r);
			t[i].start();
		}
		
		for(int i = 0; i < t.length; i++)
		{
			t[i].join();
		}
		
		
		System.out.println("Value = " + l.getValue());
		*/
		
		
		//DEADLOCK TESTING
		KeySynchronizedTest a = new KeySynchronizedTest();
		
		Runnable r1 = () -> a.a();
		Runnable r2 = () -> a.b();
		
		Thread t1 = new Thread(r1);
		t1.start();
		
		Thread t2 = new Thread(r2);
		t2.start();
		
		t1.join();
		t2.join();
	}
}
