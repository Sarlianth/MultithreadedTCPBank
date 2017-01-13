package ie.gmit.sw;

public class ClientBankAccount{
	// Declaration of private variables
	private String clientUsername;
	private String clientPassword;
	private int clientAccountNumber;
	private String clientFirstName;
	private String clientLastName;
	private String clientAddress;
	private float clientBalance;
	
	// null constructor
	public ClientBankAccount(){
	}
	
	// constructor with params
	public ClientBankAccount(String username, String pass, int accountNumber, String firstName, String lastName, String address, float balance){
		super();
		this.clientUsername = username;
		this.clientPassword = pass;
		this.clientAccountNumber = accountNumber;
		this.clientFirstName = firstName;
		this.clientLastName = lastName;
		this.clientAddress = address;
		this.clientBalance = balance;
	}
	
	/////////////////////////////////////////////////
	/////////////GETTERS & SETTERS///////////////////
	/////////////////////////////////////////////////
	
	public String getUserName(){
		return clientUsername;
	}
	
	public void setUserName(String userName){
		this.clientUsername = userName;
	}

	public String getPassword() {
		return clientPassword;
	}

	public void setPassword(String password){
		this.clientPassword = password;
	}

	public String getName(){
		return clientFirstName;
	}

	public void setName(String name){
		this.clientFirstName = name;
	}

	public String getSurname(){
		return clientLastName;
	}

	public void setSurname(String Surname){
		this.clientLastName = Surname;
	}

	public String getAddress(){
		return clientAddress;
	}

	public void setAddress(String address){
		this.clientAddress = address;
	}

	public int getBankAccountNumber(){
		return clientAccountNumber;
	}

	public void setBankAccountNumber(int bankAccountNumber){
		this.clientAccountNumber = bankAccountNumber;
	}
	
	public float getBalance(){
		return clientBalance;
	}

	public void setBalance(float balance){
		this.clientBalance = balance;
	}

	public void lodgement(float lodge){
		clientBalance += lodge;
	}

	public void withdrawal(float withdraw){
		clientBalance -= withdraw;
	}
	
	/////////////////////////////////////////////////
	///////////////// METHODS ///////////////////////
	/////////////////////////////////////////////////

	//override toString function to print the details about the account
	@Override
	public String toString(){
		return "\n======================================================"
				+ "\n\t\tBank Account Details (" + clientAccountNumber + "):"
				+ "\n======================================================"
				+ "\n\t Username:\t\t" + clientUsername
				+ "\n\t Password:\t\t" + clientPassword
				+ "\n\t Account number:\t" + clientAccountNumber
				+ "\n\t First name:\t\t" + clientFirstName
				+ "\n\t Last name:\t\t" + clientLastName
				+ "\n\t Address:\t\t" + clientAddress
				+ "\n\t Account balance:\t" + clientBalance
				+ "\n======================================================";
	}//toString
}//class
