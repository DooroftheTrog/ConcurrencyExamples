package org.paumard.runnable;

public class longwrapper {
	private long l;
	private Object key = new Object();
	public longwrapper(long l)
	{
		this.l = l;
	}
	
	public long getValue()
	{
		return l;
	}
	
	public void incerementValue()
	{
		synchronized(key)
		{
			l++;
		}		
	}
}
