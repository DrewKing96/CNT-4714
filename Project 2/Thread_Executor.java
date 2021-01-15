/* Name: Drew King
   Course: CNT 4714 Fall 2019
   Assignment Title: Project 2 - Synchronized, Cooperating Threads Under Locking
   Due Date: October 6, 2019
*/

//java class that will create and start all deposit and withdraw threads needed
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Thread_Executor
{
	/*main function*/
	public static void main(String[] args)
	{
		BankAccount bankAccount = new BankAccount();

		//creating threads using-> RunnableTester.java as example/template
		Deposit deposit1 = new Deposit(bankAccount, "D1");
		Deposit deposit2 = new Deposit(bankAccount, "D2");
		Deposit deposit3 = new Deposit(bankAccount, "D3");
		Deposit deposit4 = new Deposit(bankAccount, "D4");
		Withdraw withdraw1 = new Withdraw(bankAccount, "W1");
		Withdraw withdraw2 = new Withdraw(bankAccount, "W2");
		Withdraw withdraw3 = new Withdraw(bankAccount, "W3");
		Withdraw withdraw4 = new Withdraw(bankAccount, "W4");
		Withdraw withdraw5 = new Withdraw(bankAccount, "W5");
		Withdraw withdraw6 = new Withdraw(bankAccount, "W6");
		Withdraw withdraw7 = new Withdraw(bankAccount, "W7");
		Withdraw withdraw8 = new Withdraw(bankAccount, "W8");

		//Printing the intial column headings
		System.out.println("Deposit Threads" + "\t\t" + " Withdrawal Threads" + "\t\t\t" + "Balance" + "\n" + "---------------" + "\t" + " -------------" + "\t\t\t" + " ------------");

		//creating executor thread pool that will manage the threads
		ExecutorService threadExecutor = Executors.newCachedThreadPool();

		//Starting threads
		threadExecutor.execute(deposit1);
		threadExecutor.execute(withdraw1);
		threadExecutor.execute(withdraw2);
		threadExecutor.execute(deposit2);
		threadExecutor.execute(withdraw3);
		threadExecutor.execute(withdraw4);
		threadExecutor.execute(deposit3);
		threadExecutor.execute(withdraw5);
		threadExecutor.execute(withdraw6);
		threadExecutor.execute(deposit4);
		threadExecutor.execute(withdraw7);
		threadExecutor.execute(withdraw8);
	}
}
