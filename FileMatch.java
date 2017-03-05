import java.io.*;

public class FileMatch
{
	private static ObjectInputStream inOldMaster, inTransaction;
	private static ObjectOutputStream outNewMaster;
	private static PrintStream logFile;
	private static TransactionRecord transaction;
	private static AccountRecord account;
	private int transactionNumber, accountNumber;

	public FileMatch()
	{
		try
		{
			//file streams for input & output files
			inOldMaster = new ObjectInputStream(new FileInputStream("oldmast.ser"));
			inTransaction = new ObjectInputStream(new FileInputStream("trans.ser"));
			outNewMaster = new ObjectOutputStream(new FileOutputStream("newmast.ser"));
			logFile = new PrintStream(new FileOutputStream("log.ser"));
		}
		catch(IOException io)
		{
			System.out.println("IO Exception ERROR: Cannot open the file.");
		}
	}

	//code block just for reading/writing all the records
	public void readBlock()
	{
		try
		{
			//get a transaction record and its account number
			transaction = getTransactionRecord();

			//if the transaction is null, the code block stops
			if(transaction == null)
				break readBlock;

			transactionNumber = transaction.getAccount();

			//get an account record and its account number
			account = getAccountRecord();

			//if the account is null, we are done
			if(account == null)
				break readBlock;

			accountNumber = account.getAccount();

			while(true)
			{
				while(accountNumber < transactionNumber)
				{
					//there is no transaction for this account
					outNewMaster.writeObject(account);

					account = getAccountRecord(); // get a new account
					if(account == null)
						break readBlock;

					accountNumber = account.getAccount();
				}

				//if there is a transaction for this account
				if (accountNumber == transactionNumber)
				{
					AccountRecord outputRecord = account;

					while(accountNumber == transactionNumber)
					{
						//combine the records
						outputRecord = outputRecord.combine(transaction);

						//get new transaction
						transaction = getTransactionRecord();
						if(transaction == null)
							break readBlock;

						transactionNumber = transaction.getAccount();
					}

					//write them to the master file
					outNewMaster.writeObject(outputRecord);

					//get a new account
					account = getAccountRecord();
					if(account == null)
						break readBlock;

					accountNumber = account.getAccount();
				}

				while(transactionNumber < accountNumber)
				{
					//there is no account for this transaction
					logFile.println("Unmatched transaction record for account number " + transactionNumber);

					//get a new transaction
					transaction = getTransactionRecord();
					if(transaction == null)
						break readBlock;

					transactionNumber = transaction.getAccount();
				}
			}//end while
		}//end try
		catch(IOException io)
		{
			System.out.println("IO ERROR: Error reading or writing the file.");
			System.exit(1);
		}
		catch(ClassNotFoundException noClass)
		{
			System.out.println("Invalid Class Name: Error reading or writing the file.");
			System.exit(1);
		}
	}//end readBlock

	//close the files
	public void closeFile()
	{
		try
		{
			inTransaction.close();
			outNewMaster.close();
			inOldMaster.close();
			logFile.close();
		}
		catch(IOException io)
		{
			System.out.println("IO ERROR: Error closing the file.");
			System.exit(1);
		}
	}

	//getting a transaction record
	private static TransactionRecord getTransactionRecord() throws IOException, ClassNotFoundException
	{
		TransactionRecord transaction;

		//try to read the whole record
		try
		{
			//the whole record is passed to transaction
			transaction = (TransactionRecord)inTransaction.readObject();
		}
		//if it reached the end of file
		catch(EOFException eof)
		{
			try
			{
				//read the remaining records from the old master file
				while(true)
					outNewMaster.writeObject(inOldMaster.readObject());
			}
			//if it reached the end of the old master file
			catch(EOFException eof2)
			{
				return null;
			}
		}

		//return a transaction if it was successfully read
		return transaction; 
	}//end getTransactionRecord

	//getting an account record
	private static AccountRecord getAccountRecord() throws IOException, ClassNotFoundException
	{
		AccountRecord account;

		//try to read the whole account record
		try
		{
			//the whole record is passed to account
			account = (AccountRecord)inOldMaster.readObject();
		}
		//if it reached the end of file
		catch(EOFException eof)
		{
			try
			{
				//read the remaining records from the old master file
				while(true)
					outNewMaster.writeObject(inOldMaster.readObject());
			}
			//if it reached the end of the old master file
			catch(EOFException eof2)
			{
				return null;
			}
		}

		//return a transaction if it was successfully read
		return account; 
	}//end getAccountRecord
}