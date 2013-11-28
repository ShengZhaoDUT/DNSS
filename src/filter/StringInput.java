package filter;

public class StringInput {
	public static void main(String args[])
	{
		String t = "SELECT a from table where a > 10";
		String i = "INSERT INTO table (A, B) VALUES (3, 8)";
		ReadProcessor rd = new ReadProcessor(new StringInput(t));
		InsertProcessor id = new InsertProcessor(new StringInput(i));
		id.printResult();
	}
	private String SqlString;
	private String splittedString[];
	/*
	 * default select *
	 */
	public StringInput()
	{
		SqlString="SELECT * FROM USERTABLE";
	}
	public StringInput(String i)
	{
		SqlString = i;
	}
	public String[] getSplittedString()
	{
		this.splittedString=this.SqlString.split(" ");
		if(splittedString.length < 4)
		{
			System.err.println("FATAL ERROR: NOT LENGTHY");
		}
		return splittedString;
	}
	
	
}
