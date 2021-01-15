/* Name: Drew King
   Course: CNT 4714 Fall 2019
   Assignment Title: Project 2 - Synchronized, Cooperating Threads Under Locking
   Due Date: October 6, 2019
*/

//java class that will implement the running of deposit threads
import java.util.Random;
import java.lang.Thread;

//Deposit class
public class Deposit implements Runnable
{
	//params that hold passed params from Deposit constructor call
	private String name = "";
	private BankAccount bankAccount = new BankAccount();

	//public constructor that takes passed params from Deposit call
	public Deposit (BankAccount bankAccount, String name)
	{
		this.bankAccount = bankAccount;
		this.name = name;
	}

	//run statement containing infinite loop
	public void run()
	{
		//calculates a random sleep value ranging from 3-8.49 seconds
		int randomSleep = (int)(Math.random() * 5500 + 3000);
		try
		{
			while(true)
			{
				bankAccount.Deposits(name);
				//put thread to sleep for randomSleep amount of time
				Thread.sleep(randomSleep);
			}
		}
		catch(InterruptedException e)
		{
			System.out.println("Interrupted Exception has been caught");
		}
	}
}
