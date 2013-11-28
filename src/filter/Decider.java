package filter;

public class Decider {
	private Result result;
	public Decider(Result r)
	{
		result = r;
	}
	public Decider()
	{
		
	}
	public Decider(ReadProcessor rp)
	{
		result = rp.result;
	}
	public Decider(InsertProcessor ip)
	{
		result = ip.result;
	}
	
}
