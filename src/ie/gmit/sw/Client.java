package ie.gmit.sw;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client{
	// declaring and initializing class variables
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message="";
 	String ipaddress;
 	Scanner stdin;
 	int port = 2004;
 	
 	// null constructor
 	Client(){
 		
 	}
 	
 	// run method
	void run() throws ClassNotFoundException{
		stdin = new Scanner(System.in);
		try{
			// creating a socket to connect to the server
			System.out.println("Please Enter the servers IP Address");
			ipaddress = stdin.next();
			requestSocket = new Socket(ipaddress, port);
			System.out.println("Connected to " + ipaddress + " on port " + port);
			
			// get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			// communicating with the server
			do{
				// waiting for message from the server and then proceeds it
				message = (String)in.readObject();
				System.out.println(mainMenu());
				// waiting for clients input and sending it to the server to proceed
				message = stdin.next();
				sendMessage(message);
				
				// try to handle the switching operation
				try{
				
					// switch to handle first menu before user is logged in
					switch(message){
					
					case "1":
						// user name
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);
						
						// password
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);
						
						// account number
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);
						
						// first name
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);
						
						// last name
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);
						
						// address
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);
						
						// displaying users information after an account has been created successfully
						message = (String) in.readObject();
						System.out.println(message);
						break;
						
					case "2":
						// user name
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);
		
						// password
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);

						// welcome message 
						message = (String) in.readObject();
						System.out.println(message);
						
						// do while loop to handle the user menu after successfully being logged in
						do{
							// receiving message/response from server 
							message = (String)in.readObject();
							// displaying the menu sent by server to client
							System.out.println(message);
							// getting clients input
							message = stdin.next();
							// sending the message to the server
							sendMessage(message);
						
							//switch to control options in menu
							switch(message){
							
							//changing account information
							case "1":
								//changing user name
								message = (String) in.readObject();
								System.out.println(message);
								message = stdin.next();
								sendMessage(message);
								
								//changing password
								message = (String) in.readObject();
								System.out.println(message);
								message = stdin.next();
								sendMessage(message);
								
								//changing first name
								message = (String) in.readObject();
								System.out.println(message);
								message = stdin.next();
								sendMessage(message);
								
								//changing last name
								message = (String) in.readObject();
								System.out.println(message);
								message = stdin.next();
								sendMessage(message);
								
								//changing address
								message = (String) in.readObject();
								System.out.println(message);
								message = stdin.next();
								sendMessage(message);
								break;
								
							// sending lodgement to bank
							case "2":
								// receiving response from server
								message = (String) in.readObject();
								// prompt client to input lodgment amount
								System.out.println(message);
								message = stdin.next();
								// sending information to server 
								sendMessage(message);
								break;
							
							// withdrawing money from bank
							case "3":
								// receiving response from server
								message = (String) in.readObject();
								// prompt client to input withdrawal amount
								System.out.println(message);
								message = stdin.next();
								// sending information to server 
								sendMessage(message);
								break;
								
							// requesting last 10 transactions from server for clients account
							case "4":
								// receiving response from server (last 10 transactions)
								message = (String) in.readObject();
								// display the last 10 transaction to user
								System.out.println("Last 10 transactions: " + message);
								break;
								
							// going back to main menu
							case "5":
								// receiving response from server
								message = (String) in.readObject();
								// prompting user if he's sure he want's to exit
								System.out.println(message);
								// sending clients response to server
								message = stdin.next();
								sendMessage(message);
								// if client response was equal to "y" then log out to main menu
								if(message.equalsIgnoreCase("y")){
									// wait for response from the server and prompt to logout
									message = (String) in.readObject();
									System.out.println("You've been logged out..");
								}
								// if client response was anything different than "y", stay logged in
								else if(message.equalsIgnoreCase("n")){
									System.out.println("Staying in..");
								}
								break;
							}
						}while(!message.equals("return"));						
						break;
	
					// if option 3 from main menu has been selected, terminate client
					case "3":
						message = "bye";
					break; 
					
					}// switch
					
				}// try
				
				catch(ClassNotFoundException classNot){
					System.err.println("data received in unknown format");
				}// catch
				
			}while(!message.equals("bye"));
		}// try
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}// catch
		catch(IOException ioException){
			ioException.printStackTrace();
		}// catch
		finally{
			// Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}// catch
		}// finally
	}// run method
	
	// sendMessage method to pass messages around
	void sendMessage(String message){
		try{
			// sending message
			out.writeObject(message);
			//flushing buffer
			out.flush();
			// displaying information in servers console
			System.out.println("<Client Message >" + message + "");
		}// try
		catch(IOException ioException){
			ioException.printStackTrace();
		}// end of catch
	}// end of sendMessage function
	
	// main method to create client and start its thread
	public static void main(String args[]) throws ClassNotFoundException{
		Client client = new Client();
		client.run();
	}// main
	
	// method to print main menu
	public String mainMenu(){
		return "\nPlease select one of the following:"
				+ "\n\t[1] - Create new account"
				+ "\n\t[2] - Sign in to existing"
				+ "\n\t[3] - Terminate application";
	}// menu
}// class Client