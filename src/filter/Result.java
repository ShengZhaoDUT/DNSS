package filter;

public class Result {


	public String tablename;
	public int mode;
	public String[] target;
	public String[] value;
	public Restricter restrict;
	
	public Result()
	{
	 tablename = "";
	 mode = 0;
	 restrict = new Restricter();
	// target = new String[1];
	}
	public Result setTablename(String newTablename)
	{
		this.tablename = newTablename;
		return this;
	}
	public void setMode(int mode)
	{
		this.mode = mode;
		//return this;
	}
	public void setTarget(String[] newTarget)
	{
		this.target = newTarget;
		//return this;
	}
	public Result setRestrict(String re, String op, String val)
	{
		this.restrict=restrict.setRestricter(re).setOperations(op).setValues(val);
		return this;
	}
	public void setValue(String[] val)
	{
		value =val;
	}
}
