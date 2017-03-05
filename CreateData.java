import java.io.FileNotFoundException;
import java.lang.SecurityException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CreateData
{
	private static ObjectOutputStream outOldMaster, outTransaction; // object used to output text to file
	// enable user to open file
	public void openFile()
	{
		try
		{
			outOldMaster = new ObjectOutputStream(new FileOutputStream("oldmast.ser"));
			outTransaction = new ObjectOutputStream(new FileOutputStream("trans.ser"));
		}
		catch (IOException io)
		{
			System.out.println("IO Error: Error opening the file");
		}
	} // end method openFile
	
	// add records to file
	public void addAccountRecords()
	{
		// object to be written to file
		AccountRecord acct = new AccountRecord();
		Scanner input = new Scanner( System.in );
		
		System.out.printf( "%s\n%s\n\n",
			"To terminate input on UNIX/Linux/Mac OS X type <ctrl> d then press Enter",
			"To terminate input on Windows type <ctrl> z then press Enter" );
		
		System.out.printf( "%s\n",
			"Enter account number (should be greater than 0), first name, last name and balance.");

		while ( input.hasNext() ) // loop until end-of-file indicator
		{
			try // output values to file
			{
				// retrieve data to be output
				acct.setAccount( input.nextInt() ); // read account number
				acct.setFirstName( input.next() ); // read first name
				acct.setLastName( input.next() ); // read last name
				acct.setBalance( input.nextDouble() ); // read balance
				if ( acct.getAccount() > 0 )
				{
					// write new record to file
					outOldMaster.writeObject( new AccountRecord( acct.getAccount(), acct.getFirstName(), acct.getLastName(), acct.getBalance() ) );
				}
				else
				{
					System.out.println(
					"Input Error: Account number must be greater than 0." );
				}
			} // end try
			catch ( IOException io )
			{
				System.err.println( "Error reading or writing to file." );
				return;
			} // end catch
			catch ( NoSuchElementException elementException )
			{
				System.err.println( "Invalid input. Please try again." );
				input.nextLine(); // discard input so user can try again
			} // end catch
			System.out.printf( "%s\n",
				"Enter account number (should be greater than 0), first name, last name and balance.");
		} // end while
	} // end method addAccountRecords
	
	public void addTransactionRecords()
	{
		// object to be written to file
		TransactionRecord trans = new TransactionRecord();
		Scanner input = new Scanner( System.in );
		
		System.out.printf( "%s\n%s\n\n",
			"To terminate input on UNIX/Linux/Mac OS X type <ctrl> d then press Enter",
			"To terminate input on Windows type <ctrl> z then press Enter" );
		
		System.out.printf( "%s\n",
			"Enter account number(should be greater than 0), and balance.");

		while ( input.hasNext() ) // loop until end-of-file indicator
		{
			try // output values to file
			{
				// retrieve data to be output
				trans.setAccount( input.nextInt() ); // read account number
				trans.setBalance( input.nextDouble() ); // read balance
				if ( trans.getAccount() > 0 )
				{
					// write new record to file
					outTransaction.writeObject( new TransactionRecord( trans.getAccount(), trans.getBalance() ) );
				}
				else
				{
					System.out.println(
					"Input Error: Account number must be greater than 0." );
				}
			} // end try
			catch ( IOException io )
			{
				System.err.println( "Error reading or writing to file." );
				return;
			} // end catch
			catch ( NoSuchElementException elementException )
			{
				System.err.println( "Invalid input. Please try again." );
				input.nextLine(); // discard input so user can try again
			} // end catch
			System.out.printf( "%s\n",
				"Enter account number(should be greater than 0), and balance.");
		} // end while
	} // end method addAccountRecords

	// close file
	public void closeFile()
	{
		outTransaction.close();
		outOldMaster.close();
	} // end method closeFile
} // end class CreateTextFile
