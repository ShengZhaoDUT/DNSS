package filter;

public class Restricter {

	public String restricter;
	public String operations;
	public String values;
	
	public Restricter(){
		restricter = "";
		operations = "";
		values = "";
	}
	public Restricter setRestricter(String rest)
	{
		restricter = rest;
		return this;
	}
	public Restricter setOperations(String op)
	{
		operations = op;
		return this;
	}
	public Restricter setValues(String val)
	{
		values = val;
		return this;
	}
}
