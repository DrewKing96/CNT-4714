/*Name: Drew King
  Course: CNT 4714 - Fall 2019
  Assignment title: Program 1 - Event-driven Programming
  Date: Sunday September 22, 2019
*/

import javax.swing.*;				//imports all aspects of javax.swing
import javax.swing.border.EmptyBorder;

import java.awt.*;					//imports all aspects of java.awt
import java.io.*;					//imports all aspects of java.io
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;			//imports java scanner
import java.awt.event.*;			//imports all aspects of java.awt.event


public class bookStore
{
	String filePath = "inventory.txt";
	private static Scanner csvReader;
	//formats decimals to have a ones, tenths, and hundreths place
	DecimalFormat twoPlaces = new DecimalFormat("0.00");
	//formats discount decimal to a minimum of a ones and tenths places, and any non zero hundreths place
	DecimalFormat transactionFileDiscount = new DecimalFormat("0.0#");
	String bookSearch = "";
	String title = "";
	String price = "";
	String viewOrderString = "";
	String invoice = "";
	String currentDate = "";
	String transactionString = "";
	String transactionHelperString = "";
	String permutation = "";
	Integer numOrderTotal = 0;
	Integer numOrderCurrent = 0;
	Integer numSpecificBook = 0;
	Integer tax = 6;
	Double subTotal = 0.0;
	Double discount = 0.0;
	Double pWithDiscount = 0.0;
	JTextField bookInfoField;
	JTextField bookIDField;
	JTextField totalSpecificField;
	JButton processItem;
	JButton confirmItem;
	//private static Scanner lineReader;
	
	public static void main(String[] args)
	{
		new bookStore();		
	}

	public bookStore()
	{
		//Create Frame
		JFrame bookStoreFrame = new JFrame();
		//closing frame when program is exited
		bookStoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bookStoreFrame.setTitle("BookStore");
		bookStoreFrame.setSize(900,200);
		bookStoreFrame.setLocationRelativeTo(null);
		//Create Panel
		JPanel bookStorePanel = new JPanel();
		bookStorePanel.setLayout(new GridLayout(5,1,10,0));
		bookStorePanel.setBorder(new EmptyBorder(10,10,0,10));
		//Create Button Panel
		JPanel bookStoreButtonsPanel = new JPanel();
		//# of Items in order label and text field
		JLabel totalItemsLabel = new JLabel("Enter number of items in this order: ");
		JTextField totalItemsField = new JTextField(30);
		//Book ID label and text field
		JLabel bookIDLabel = new JLabel("Enter Book ID for Item #1: ");
		bookIDField = new JTextField(30);
		//# of specific books label and text field
		JLabel totalSpecificLabel = new JLabel("Enter quantity for Item #1: ");
		totalSpecificField = new JTextField(30);
		//item information label and text field
		JLabel bookInfoLabel = new JLabel("Item #1 info: ");
		bookInfoField = new JTextField(30);
		//sub total label and text field
		JLabel subtotalLabel = new JLabel("Order subtotal for 0 item(s): ");
		JTextField subtotalField = new JTextField(30);
		//Process Item
		processItem = new JButton("Process Item 1");
		//Confirm Item
		confirmItem = new JButton("Confirm Item 1");
		//View Order
		JButton viewOrder = new JButton("View Order");
		//Finish Order
		JButton finishOrder = new JButton("Finish Order");
		//New Order
		JButton newOrder = new JButton("New Order");
		//Exit
		JButton exit = new JButton("Exit");

		//Add all bookStorePanel content to the Panel
		bookStorePanel.add(totalItemsLabel);
		bookStorePanel.add(totalItemsField);
		bookStorePanel.add(bookIDLabel);
		bookStorePanel.add(bookIDField);
		bookStorePanel.add(totalSpecificLabel);
		bookStorePanel.add(totalSpecificField);
		bookStorePanel.add(bookInfoLabel);
		bookStorePanel.add(bookInfoField);
		bookStorePanel.add(subtotalLabel);
		bookStorePanel.add(subtotalField);

		//Add all bookStoreButtonsPanel content to Panel
		bookStoreButtonsPanel.add(processItem);
		bookStoreButtonsPanel.add(confirmItem);
		bookStoreButtonsPanel.add(viewOrder);
		bookStoreButtonsPanel.add(finishOrder);
		bookStoreButtonsPanel.add(newOrder);
		bookStoreButtonsPanel.add(exit);

		//Add all Panels to Frame
		bookStoreFrame.add(bookStorePanel, BorderLayout.NORTH);
		bookStoreFrame.add(bookStoreButtonsPanel, BorderLayout.SOUTH);

		//Setting Frame to visible
		bookStoreFrame.setVisible(true);

		//Disable buttons and Textfields that are required on start-up
		confirmItem.setEnabled(false);
		bookInfoField.setEditable(false);
		subtotalField.setEditable(false);
		finishOrder.setEnabled(false);
		
		//Process Item ActionListener
		processItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				numSpecificBook = Integer.parseInt(totalSpecificField.getText());	//obtaining the number of specific books for the current item
				numOrderTotal = Integer.parseInt(totalItemsField.getText());		//obtaining the number of items that are in the order
				
				if(numSpecificBook < 5)
				{
					discount = 1.0;
				}
				
				if(numSpecificBook >= 5 && numSpecificBook <= 9)
				{
					discount = 0.90;
				}
				
				if(numSpecificBook >= 10 && numSpecificBook <= 14)
				{
					discount = 0.85;
				}
				
				if(numSpecificBook >= 15)
				{
					discount = 0.80;
				}
				
				csvSearch(bookIDField.getText(), filePath);
				totalItemsField.setEditable(false);		//disabling total items text field
			}

		});
		//Confirm Item ActionListener
		confirmItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				finishOrder.setEnabled(true);
				numOrderCurrent++;
				//string that constantly has book info of current item append to it as each item is confirmed
				viewOrderString = viewOrderString + numOrderCurrent + ". " + bookInfoField.getText() + "\n";
				
				
				subTotal = subTotal + pWithDiscount;
				subtotalField.setText("$" + twoPlaces.format(subTotal));
				if(numOrderCurrent < numOrderTotal)
				{
					processItem.setEnabled(true);
					confirmItem.setEnabled(false);
					processItem.setText("Process Item " + (numOrderCurrent+1));
					confirmItem.setText("Confirm Item " + (numOrderCurrent+1));
					bookIDLabel.setText("Enter Book ID for Item #" + (numOrderCurrent+1) + ": ");
					totalSpecificLabel.setText("Enter quantity for Item #" + (numOrderCurrent+1) + ": ");
					bookInfoLabel.setText("Item #" + (numOrderCurrent+1) + " info: ");
					subtotalLabel.setText("Order subtotal for " + numOrderCurrent + " item(s): ");
					bookIDField.setText("");
					totalSpecificField.setText("");
				}
				else
				{
					confirmItem.setEnabled(false);
					subtotalLabel.setText("Order subtotal for " + numOrderCurrent + " item(s): ");
					bookIDField.setText("");
					bookIDField.setEditable(false);
					bookIDLabel.setText("");
					totalSpecificField.setText("");
					totalSpecificField.setEditable(false);
					totalSpecificLabel.setText("");
				}
				//confirmation message of the item being confirmed to the order
				JOptionPane.showMessageDialog(null, "Item #" + numOrderCurrent + " accepted");
			}
		});
		//View Order ActionListener
		viewOrder.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//create pane to display up-to-date items in cart
				//populates a message box that shows the current items that have already been confirmed
				JOptionPane.showMessageDialog(null, viewOrderString);
			}
		});
		//Finish Order ActionListener
		finishOrder.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				finishOrder.setEnabled(false);	//disabling finish order button once it is pressed
				viewOrder.setEnabled(false);	//disabling view order button once finish order has been pressed
				processItem.setEnabled(false);
				currentDate = new SimpleDateFormat("MM/dd/YY, h:mm:ss a zzz").format(new Date());		//date format for invoice
				permutation = new SimpleDateFormat("ddmmyyyyhhmmss").format(new Date());				//permutation format for transactions file
				invoice = "Date: " + currentDate 
						+ "\n\n" 
						+ "Number of line items: " 
						+ numOrderCurrent + "\n\n" 
						+ " Item# / ID / Title / Price / Qty / Disc % / Subtotal:" 
						+ "\n\n" + viewOrderString + "\n\n\n" 
						+ "Order subtotal: $" + twoPlaces.format(subTotal) + "\n\n" 
						+ "Tax rate: 	" + tax + "%" + "\n\n" 
						+ "Tax amount:  $" + twoPlaces.format(((subTotal * tax)/100)) 
						+ "\n\n" + "Order total:  $" 
						+ twoPlaces.format((subTotal + ((subTotal * tax)/100))) + "\n\n" 
						+ "Thanks for shopping at the Book Store!"; 
				//populate message box with invoice
				JOptionPane.showMessageDialog(null, invoice);
				
					String [] tL = transactionHelperString.split("\n");
					
					for(Integer x = 0; x < numOrderCurrent; x++)
					{
						transactionString = transactionString + permutation + ", " + tL[x] + " " + currentDate + "\n";
					}
					
					//System.out.print(transactionString);
				
				//complete the order by adding all contents to transaction log file
				try {
					FileWriter fW = new FileWriter("transactions.txt", true);
					BufferedWriter bW = new BufferedWriter(fW);
					PrintWriter out = new PrintWriter(bW);
					
					out.print(transactionString);
					
					out.close();
					
					} catch (IOException e1) {
					//Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		//New Order ActionListener
		newOrder.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//clear fields
				totalItemsField.setText("");
				bookIDField.setText("");
				totalSpecificField.setText("");
				bookInfoField.setText("");
				subtotalField.setText("");
				//clear view order
				viewOrderString = "";
				//clear invoice
				invoice = "";
				//clear transaction string
				transactionString = "";
				//clear transaction helper string
				transactionHelperString = "";
				//reset buttons and labels to first item
				processItem.setText("Process Item 1");
				confirmItem.setText("Confirm Item 1");
				bookIDLabel.setText("Enter Book ID for Item #1: ");
				totalSpecificLabel.setText("Enter quantity for Item #1: ");
				bookInfoLabel.setText("Item #1 info: ");
				subtotalLabel.setText("Order subtotal for 0 item(s): ");
				//reset counters to 1 or 0 depending on what it is
				numOrderTotal = 0;
				numOrderCurrent = 0;
				//enable and disable needed buttons, fields and labels
				processItem.setEnabled(true);
				confirmItem.setEnabled(false);
				totalItemsField.setEditable(true);
				bookIDField.setEditable(true);
				totalSpecificField.setEditable(true);
				finishOrder.setEnabled(false);
				viewOrder.setEnabled(true);
				subTotal = 0.0;
				pWithDiscount = 0.0;
			}
		});
		//Exit ActionListener
		exit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});


	}
	//TODO: Remove global varibles for this function and add them to variables being passed to this function
	public void csvSearch(String bookID, String filePath)
	{
		boolean exists = false;
		String fileLine = "";
		

		try
		{
			csvReader = new Scanner(new File(filePath));

			while(csvReader.hasNextLine() && !exists)
			{
				fileLine = csvReader.nextLine();										//reading passed file line by line
				
				String[] tokens = fileLine.split(",");									//splits the passed line into separate strings using a comma as the delimiter
				
				bookSearch = tokens[0];													//the first string of each entry is the book ID which is equal to the first array element

				if(bookSearch.equals(bookID))											//checks if the passed lines first element is equal to the desired book id passed
				{
					exists = true;
					title = tokens[1];
					price = tokens[2];
					//Make pWithDiscount limited to two decimal places
					pWithDiscount = (numSpecificBook) * (discount) * (Double.parseDouble(price));
					//pWithDiscount = (double) (Math.round(pWithDiscount));
					bookInfoField.setText(bookSearch + title + price + " " + numSpecificBook +" " + (Math.round(100 * (1 - discount))) +"% " + "$" + twoPlaces.format(pWithDiscount));
					//change discount from percentage to decimal in transactionHelperString
					transactionHelperString = transactionHelperString + bookSearch + ", " + title + ", " + price + ", " + numSpecificBook + ", " + transactionFileDiscount.format((1 - discount)) + ", " + "$" + twoPlaces.format(pWithDiscount) +","+ "\n";
					//System.out.print(transactionHelperString);
					processItem.setEnabled(false);
					confirmItem.setEnabled(true);
				}
			}
		}

		catch(Exception e)
		{

		}
		
		if(exists == false)
		{
			//System.out.print("Book ID " + bookID + " not in file");
			JOptionPane.showMessageDialog(null, "Book ID " + bookID + " not in file");
			bookIDField.setText("");
			totalSpecificField.setText("");
		}
	}
}
