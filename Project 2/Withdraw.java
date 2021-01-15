/* Name: Drew King
   Course: CNT 4714 Fall 2019
   Assignment Title: Project 2 - Synchronized, Cooperating Threads Under Locking
   Due Date: October 6, 2019
*/

//java class that will implement the frunning of withdraw threads
import java.util.Random;
import java.lang.Thread;

//Withdraw class
public class Withdraw implements Runnable
{
	//params that hold passed params from Withdraw constructor call
	private String name = "";
	private BankAccount bankAccount = new BankAccount();

	//public constructor that takes passed params from Withdraw call
	public Withdraw (BankAccount bankAccount, String name)
	{
		this.bankAccount = bankAccount;
		this.name = name;
	}

	//run statement containing infinite loop
	public void run()
	{
		//calculates a random sleep value ranging from .5-5 seconds
		int randomSleep = (int)(Math.random() * 3500 + 500);
		try
		{
			while(true)
			{
				bankAccount.Withdraws(name);
				//put thread to sleep for randomSleep amount of time
				Thread.sleep(randomSleep);
			}
		}
		catch(InterruptedException e)
		{
			System.out.println("Interrupted Exeception has been caught");
		}
	}
}
