package filter;

public class InsertProcessor implements Processor{

	private StringInput tobeDecided;
	public Result result;
	
	public static final int UNDECIDED = 0;
	public static final int READ = 1;
	public static final int INSERT = 2;
	
	public InsertProcessor(StringInput tbD)
	{
		tobeDecided = tbD;
		result = new Result();
		result.setMode(INSERT);
	}
	public String currentString(int pointer)
	{
		return tobeDecided.getSplittedString()[pointer];
	}
	@Override
	public void gotoProcess() {
		// TODO Auto-generated method stub
		int pointer = 2 ;//INSERT INTO "TABLE" VALUE ()
		result.setTablename(currentString(pointer));
		pointer++;
		//check if there is a "("
		if (currentString(pointer).contains("("))
		{
			//there are selected targets
			String temp = currentString(pointer);
			while(!currentString(pointer).contains(")"))
			{
				temp = temp + currentString(++pointer);
			}
			temp = temp.replaceFirst("\\(", "").replaceFirst("\\)","");
			System.out.println(temp);
			String target[] = temp.split("\\,|\\ ");
			result.setTarget(target);
			System.out.println(target.length);
			System.out.println(target[0]);
		}
		else
		{
			String alltarget[]={"all"};
			result.setTarget(alltarget);
		}
		//check if is right
		if(!currentString(++pointer).equalsIgnoreCase("values"))
			System.err.println("WRONG POINTER!!!!Check the program");
		pointer++;
		String temp = currentString(pointer);
		while(!currentString(pointer).contains(")"))
		{
			temp = temp + currentString(++pointer);
		}
		temp = temp.replaceFirst("\\(", "").replaceFirst("\\)","");
		String target[] = temp.split("\\,| ");
		//check if it is legible
		if(target.length != result.target.length)
		{
			System.err.println("not matched");
		}
		result.setValue(target);
	}

	public void printResult()
	{
		this.gotoProcess();
		System.out.println("target: "+result.value[1]);
	}
}
