##Multi-threaded TCP Server Application
#Adrian Sypos – G00309646 (Operating Systems Project)

	My Multi-threaded TCP Server app allows multiple customers to log in to their bank accounts knowing their username and passwords. Once logged in, user can update their bank account (every information except an actual account number), as well as deposit the money in and withdraw the money out. 
	The service also allows users to register new account with the system, providing full name, address, bank account number, username and password. Log-in to the banking system from client app to the server app using a socket (port 2004). The service also allows client to change their details, make lodgements to their bank account, make withdrawals from their bank account and view the last ten transactions on their bank account.
	The server does not provide any service to a client application that can complete the authentication. The server also holds a list of valid users of the service and a list of the users’ previous transactions. Once the user logs in, they will receive their current balance along with a menu.
	To start the program, we need to run the server application first, which will wait for client connection on port 2004 (TCP port). After doing this, you need to run the client app, which will prompt for the IP address of the server (127.0.0.1). Once you did that server and client will accept their connection and on the client side, a menu will be prompt. 
	Once client has been connected to the server, you can either create new bank account selecting option [1], sign in into an existing account selecting option [2], or terminate the connection selecting option [3]. 
	If you select option [1] to create new bank account, you will be prompt to provide the following:
    •	Username
    •	Password
    •	Account number
    •	First name
    •	Last name	
    •	Address
 Once all that has been submitted, new account will successfully be created and information will be displayed.
	Selecting option [2] will prompt to ask for username and password, which if validated successfully will allow you to see your account balance and user menu.  Selecting option [5] in the personal menu after logging in, will bring you back to the main menu.
