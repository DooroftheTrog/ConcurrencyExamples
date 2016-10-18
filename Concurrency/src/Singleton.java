
//this is the basic singeton pattern
public class Singleton {
	
	private static Singleton instance ;
	
	private Singleton(){}
	
	//adding sychronized keeps this from being run by two different threads at the same time
	public static synchronized  Singleton getInstance()
	{
		// if two threads are calling getInstance() at the same time what happens
		// t1 and t2 both check the instance of the singleton and get a return of null so they
		// 		both enter the if statement and both create an instance of the singleton
		//		even though it should only be able to have one instance at a time.n 
		if(instance == null)
		{
			instance = new Singleton();
		}
		return instance;
	}

}
