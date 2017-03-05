import java.io.Serializable;

// a class that represets one account record
public class AccountRecord implements Serializable
{
	private int account;
	private String firstName;
	private String lastName;
	private double balance;

	//non-argument constructor that calls to the constructor with default values
	public AccountRecord()
	{
		this(0, "", "", 0.0);
	}

	//initializing a record
	public AccountRecord(int acctNo, String fName, String lName, double bal)
	{
		setAccount(acctNo);
		setFirstName(fName);
		setLastName(lName);
		setBalance(bal);
	}

	//add transaction record to an account record
	public AccountRecord combine(TransactionRecord transaction)
	{
		return new AccountRecord(account, firstName, lastName, balance + transaction.getBalance());
	}

	//set account number
	private void setAccount(int acctNo)
	{
		account = acctNo;
	}

	//get account number
	public int getAccount()
	{
		return account;
	}

	//set first name
	private void setFirstName(String fName)
	{
		firstName = fName;
	}

	//get first name
	public String getFirstName()
	{
		return firstName;
	}

	//set last name
	private void setLastName(String lName)
	{
		lastName = lName;
	}

	//get last name
	public String getLastName()
	{
		return lastName;
	}

	//set balance
	private void setBalance(double bal)
	{
		balance = bal;
	}

	//get balance
	public double getBalance()
	{
		return balance;
	}
}