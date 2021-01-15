/* Name: Drew King
   Course: CNT 4714 Fall 2019
   Assignment Title: Project 2 - Synchronized, Cooperating Threads Under Locking
   Due Date: October 6, 2019
*/

//class that will keep track of the current balance of the bank account and contains the functionality of deposit and withdraw threads
import java.lang.Thread;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class BankAccount
{
	//int that holds the current balance
	private int currentBalance;
	//creating a lock
	private Lock lock = new ReentrantLock();
	//creating condition for overdrafts
	private Condition overDraft = lock.newCondition();

	//constructor to intialize beginning balance to zero
	public BankAccount()
	{
		currentBalance = 0;
	}

	//constructor for deposits
	public void Deposits(String name)
	{
		//generating a random deposit ranging from 1-250 dollars
		int deposit = (int)(Math.random() * 250 + 1);
		//obtaining the lock
		lock.lock();
		//attempting to make a deposit
		try
		{
			currentBalance = currentBalance + deposit;
			System.out.println("Thread " + name + " deposits $" + deposit + "\t\t\t\t\t\t" + " Balance is " + currentBalance);
			//signaling all blocked withdraw threads that a deposit has been made
			overDraft.signalAll();
		}
		catch(Exception e)
		{
			System.out.println("An Exception has been caught");
		}
		finally
		{
			//release lock
			lock.unlock();
		}
	}

	//constructor for withdraws
	public void Withdraws(String name)
	{
		//generating a random withdraw ranging from 1-50 dollars
		int withdraw = (int)(Math.random() * 50 + 1);
		//obtaining the lock
		lock.lock();
		//attempting to make a withdraw
		try
		{
			//checking if an overdraft will occur
			//if overdraft wont occur
			if(currentBalance >= withdraw)
			{
				currentBalance = currentBalance - withdraw;
				System.out.println("\t\t\t" + "Thread " + name + " withdraw $" + withdraw + "\t\t\t" + " Balance is " + currentBalance);
			}

			//if an overdraft will occur
			else
			{
				System.out.println("\t\t\t" + "Thread " + name + " withdraws $" + withdraw + "\t" + "Withdrawl - Blocked - Insufficent funds");
				overDraft.await();
			}
		}
		catch(InterruptedException e)
		{
			System.out.println("Interrupt Exception has been caught");
		}
		finally
		{
			//release lock
			lock.unlock();
		}
	}
}
