package filter;

public class test {
	public static void main(String args[])
	{
		String t = "SELECT a from table where a > 10";
		String i = "INSERT INTO table (A, B) VALUES (3, 8)";
		ReadProcessor rd = new ReadProcessor(new StringInput(t));
		InsertProcessor id = new InsertProcessor(new StringInput(i));
		id.printResult();
	}
}
