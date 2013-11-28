package filter;

public class ReadProcessor implements Processor{
	private StringInput tobeDecided;
	private Result result;
	
	public static final int UNDECIDED = 0;
	public static final int READ = 1;
	public static final int INSERT = 2;
	
	public ReadProcessor(StringInput tbD)
	{
		tobeDecided = tbD;
		result = new Result();
		result.setMode(READ);

	}
	
	public void gotoDecide()
	{
		int pointer = 1; //Start from the 2nd word
		if (tobeDecided.getSplittedString()[pointer].equals("*"))
		{
			String allTargets[] = {"all"};
			result.setTarget(allTargets);
		}
		else 
		{
			String targets[] = tobeDecided.getSplittedString()[pointer].split(",");
			System.out.println(tobeDecided.getSplittedString()[pointer]);
			result.setTarget(targets);
		}
		pointer = pointer + 2;//change to table name
		result.setTablename(tobeDecided.getSplittedString()[pointer]);
		
		if (pointer != tobeDecided.getSplittedString().length)
		//where follows
		pointer = pointer + 2;
		//limited.
		//now only 1 set of instruction
		result.setRestrict(tobeDecided.getSplittedString()[pointer++],
				tobeDecided.getSplittedString()[pointer++], 
				tobeDecided.getSplittedString()[pointer]);
		
	}
		public void printResult()
		{
			this.gotoDecide();
			System.out.println("Restricter: "+result.restrict.restricter);
		}

	
		
		
	}

