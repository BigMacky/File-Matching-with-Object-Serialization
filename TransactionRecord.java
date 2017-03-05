import java.io.Serializable;

// a class that represents one transaction record
public class TransactionRecord implements Serializable
{
	private int account;
	private double balance;

	// non-argument constructor calls other constructor with defaut values
	public TransactionRecord()
	{
		this(0, 0.0);
	}

	//initialize a record
	public TransactionRecord(int acctNo, double bal)
	{
		setAccount(acctNo);
		setBalance(bal);
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