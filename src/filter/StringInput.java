package filter;

public class StringInput {
	
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
