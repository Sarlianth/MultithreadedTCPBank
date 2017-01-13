package ie.gmit.sw;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{
	// main method 
	public static void main(String[] args) throws Exception{
		// declaring and initializing variables
		@SuppressWarnings("resource")
		ServerSocket socket = new ServerSocket(2004,10);
		int ClientID = 0;
		
		// while loop to keep the server running
		while(true){
			// displaying information on server
			System.out.println("Server listening on port 2004...");
			// declaring and accepting client socket
			Socket clientSocket = socket.accept();
			// new instance of ClientServiceThread with parameters
			ClientServiceThread clientThread = new ClientServiceThread(clientSocket, ClientID++);
			// starting a thread
			clientThread.start();
		}// while loop
	}// main
}// class

// thread class
class ClientServiceThread extends Thread{
	// declaring and initializing variables
	Socket clientSocket;
	int clientID = -1;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message = "Connection successfull";
	String passwordMessage = "";
	
	// declaring and initializing array of accounts and array of transactions
	ArrayList<ServerBankAccount> accountList = new ArrayList<ServerBankAccount>();
	ArrayList<String> transactions = new ArrayList<String>();
	
	// constructor with params
	ClientServiceThread(Socket socket, int id) {
	    clientSocket = socket;
	    clientID = id;
	}
	
	// sendMessage method to pass messages around
	void sendMessage(String message){
		try{
			// sending message
			out.writeObject(message);
			//flushing buffer
			out.flush();
			// displaying information in servers console
			System.out.println("<Client ID: " + clientID + " > " + message);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	 
	// run method to control the thread
	public void run(){
		//try to operate all server, or catch if exception occurs
		try{
			//initializing the output and input streams
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			
			//displaying information to servers console once client connected and displays its host/IP
			System.out.println("======================================");
			System.out.println("Client accepted\tID: " + clientID + " \tAddress: " + clientSocket.getInetAddress().getHostName());
			System.out.println("======================================");
			//sending message
			sendMessage(message);
		
		// do while loop to keep the client alive
		do{
			// reading in a message from the client
			message = (String)in.readObject();
			// try and catch to try and proceed the message from client, so basically to get to menu
			try{	
				switch(message){
					//creating new account on menu option 1
					case "1":
						System.out.println("<Client ID: " + clientID + "> requesting new bank account");
						System.out.println("---------------------------------------------------------");
						
						//user name
						sendMessage("\tPlease enter username:");
						message = (String)in.readObject();
						String username = message;
						
						//password 
						sendMessage("\tPlease enter password:");
						message = (String)in.readObject();
						String password = message;
						
						//account number
						sendMessage("\tPlease enter account number:");
						message = (String)in.readObject();
						int accountNumber = new Integer(message);
						
						//first name
						sendMessage("\tPlease enter your First Name:");
						message = (String)in.readObject();
						String firstName = message;
						
						//last name
						sendMessage("\tPlease enter your Surname:");
						message = (String)in.readObject();
						String lastName = message;
						
						//address
						sendMessage("\tPlease enter your Address:");
						message = (String)in.readObject();
						String address = message;
						
						// declaring and initializing balance to 0 instead of allowing user to choose his initial balance
						int balance = 0;
						
						// adding new account to list of all accounts using information provided from client
						accountList.add(new ServerBankAccount(username, password, accountNumber, firstName, lastName, address, balance));
						//sending back all information about the account that has just been created to client
						sendMessage(accountList.get(accountList.size()-1).toString());
						break;
						
					// sign in to an existing account on menu option 2
					case "2":
						//user name
						sendMessage("\tPlease enter username:");
						message = (String)in.readObject();
						System.out.println(message);
						
						//password
						sendMessage("\tPlease enter password:");
						passwordMessage = (String)in.readObject();
						System.out.println(passwordMessage);
						
						// for loop that validates the user name and password and allows to proceed 
						for(int i=0; i< accountList.size();i++){
							
							// if account found
							if(accountList.get(i).getUserName().equals(message) && accountList.get(i).getPassword().equals(passwordMessage)){
								System.out.println("Authentication successful");
								
								// creating an instance of an Account and assigns its value to current user so we can manipulate it
								ServerBankAccount temp = accountList.get(i);
								
								// welcome message sent to client 
								sendMessage("\n\tWelcome " + temp.getName() + temp);
								
								// do while loop to display menu for authenticated user until option 5 is selected (to exit to main menu)
								do{
									// sending message containing menu and expecting user to reply
									sendMessage("\n\t\tPlease select one of the following: "
											+ "\n\t\t[1] - To Change Account Details"
											+ "\n\t\t[2] - To Make a Lodgement"
											+ "\n\t\t[3] - To Make Withdrawal"
											+ "\n\t\t[4] - To Preview Latest Transactions"
											+ "\n\t\t[5] - To Exit to Main Menu"
											+ "\n\t\t[x] - Your current balance is: " + temp.getBalance());
									
									// waiting for user to select an option, then reading it in into message variable
									message = (String)in.readObject();
									
									// switch to control the options from the menu
									switch(message){
										case "1":
											// displaying log on servers console
											System.out.println("\tRequested change of details for the following account number: " + temp.getBankAccountNumber());
											// sending message to user providing current user name and asking for new one
											sendMessage("Your current username: " + temp.getUserName() + "\nPlease enter new username: ");
											// reading in the message from user and using it to set new user name
											message = (String)in.readObject();
											// displaying log on servers console
											System.out.println("Username has been changed from " + temp.getUserName() + " to " + message);
											// changing user name
											temp.setUserName(message);
											
											// sending message to user providing current information and asking for new one
											sendMessage("Your current password: " + temp.getPassword() + "\nPlease enter new password: ");
											// reading in the message from user and using it to proceed 
											message = (String)in.readObject();
											// displaying log on servers console
											System.out.println("Password has been changed from " + temp.getPassword() + " to " + message);
											// changing password
											temp.setPassword(message);
											
											// sending message to user providing current information and asking for new one
											sendMessage("Your current first name: " + temp.getName() + "\nPlease enter new first name: ");
											// reading in the message from user and using it to proceed 
											message = (String)in.readObject();
											// displaying log on servers console
											System.out.println("First name has been changed from " + temp.getName() + " to " + message);
											// changing first name
											temp.setName(message);
											
											// sending message to user providing current information and asking for new one
											sendMessage("Your current surname: " + temp.getSurname() + "\nPlease enter new surname: ");
											// reading in the message from user and using it to proceed 
											message = (String)in.readObject();
											// displaying log on servers console
											System.out.println("Surname has been changed from " + temp.getSurname() + " to " + message);
											// changing last name
											temp.setSurname(message);
											
											// sending message to user providing current information and asking for new one
											sendMessage("Your current address: " + temp.getAddress() + "\nPlease enter new address: ");
											// reading in the message from user and using it to proceed 
											message = (String)in.readObject();
											// displaying log on servers console
											System.out.println("Address has been changed from " + temp.getAddress() + " to " + message);
											// changing address
											temp.setAddress(message);
											break;
											
										case "2":
											// displaying log on servers console
											System.out.println("Requested Lodgement...");
											// sending message asking for the amount to lodge
											sendMessage("\tPlease enter amount to lodge in: ");
											// reading in the message from user and using it to proceed 
											message = (String)in.readObject();
											
											// displaying information onto servers console (previous and after balance)
											System.out.println("BALANCE BEFORE: " + temp.getBalance());
											// lodging money into account
											temp.lodgement(Float.valueOf(message));
											// add information about the transaction you have just completed into the array list
											transactions.add("Account number: " + temp.getBankAccountNumber() + " | Amount lodged: " + Float.valueOf(message));
											// displaying information onto servers console (previous and after balance)
											System.out.println("BALANCE AFTER: " + temp.getBalance());
											break;
											
										case "3":
											// displaying log on servers console
											System.out.println("Requested Withdrawal...");
											// sending message asking for the amount to withdraw
											sendMessage("\tPlease enter amount to withdraw: ");
											// reading in the message from user and using it to proceed 
											message = (String)in.readObject();
											
											// displaying information onto servers console (previous and after balance)
											System.out.println("BALANCE BEFORE: " + temp.getBalance());
											// withdrawing money from account
											temp.withdrawal(Float.valueOf(message));
											// add information about the transaction you have just completed into the array list
											transactions.add("Account#: " + temp.getBankAccountNumber() + " | Withdrawn: " + Float.valueOf(message));
											// displaying information onto servers console (previous and after balance)
											System.out.println("BALANCE AFTER: " + temp.getBalance());
											break;
											
										case "4":
											// displaying log on servers console
											System.out.println("Requested 10 recent transactions preview for account #" + temp.getBankAccountNumber());
											// sending message to client with the recent transactions that he requested
											sendMessage("" + temp.getTransactions());
											break;
											
										case "5":
											// sending message to client to confirm if he wants to exit to main menu
											message = "Are you sure? [y/n]";
											sendMessage(message);
											// reading in the message from user
											message = (String) in.readObject();
											// if user sent "y" then change message to "return" so it will terminate current do while loop
											if(message.equalsIgnoreCase("y")){
												// if user sent "y" then change message to "return" so it will terminate current do while loop
												message = "return";
												sendMessage(message);
											}//if
											// if user sent anything else than "y", just print menu again
											else{
												break;
											}//else
									}//switch
								}while(!message.equals("return"));
							}// End of if
							
							// Else if account information do not match just ignore and go back to menu
							else if(accountList.get(i).getUserName()!=(message) && accountList.get(i).getPassword()!=(passwordMessage)){
								//sendMessage("Account doesn't exist, please try again");
							}// End of else if
							
							// Else if system couldn't process the data
							else{
								sendMessage("Sorry. Could not process.");
							}// else
						}// for
						break;
					// if 3 has been selected terminate the client
					case "3":
						message = "bye";
						// End of case 3
						break;
				}// switch
			}//  try
			catch(ClassNotFoundException classnot){
				System.err.println("Data has been received in unknown format");
			}// catch
			// printing menu again until menu option 3 will be chosen by client 
			sendMessage("Printing menu");
		}while(!message.equals("3"));
			  
		// after user terminates, displaying information on severs console that the user with given ID and host/IP has ended session
		System.out.println("======================================");
		System.out.println("Client terminated\tID: " + clientID + " \tAddress: " + clientSocket.getInetAddress().getHostName());
		System.out.println("======================================");
		
		} catch (Exception e) {
			e.printStackTrace();
		}// catch
	}// run
}//class