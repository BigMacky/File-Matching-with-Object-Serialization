/*
	MARK ARTHUR A. CARO III
	ICST214 - N1
	03.09.16
	P2 - File Matching with Object Serialization

	Problem:
	Recreate your solution for Exercise 17.6 using object serialization. 
	Use the statements from Exercise 17.3 as your basis for this program. 
	You may want to create applications to read the data stored in 
	the .ser files—the code in Section 17.5.2 can be modified for this purpose.
*/
	
public class Caro_P1
{
	public static void main(String[] args)
	{
		CreateData maker = new CreateData();
		maker.openFile();
		maker.addAccountRecords();
		maker.addTransactionRecords();
		maker.closeFile();

		FileMatch matcher = new FileMatch();
		matcher.readBlock();
		matcher.closeFile();
	}
}