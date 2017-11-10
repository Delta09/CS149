import java.util.LinkedList;


public class FIFO extends Pager
{
	
	public FIFO(LinkedList<memoryPage> memory, LinkedList<Process> process) {
		super(memory, process);
	}

	@Override
        //Page with the highest runTime should be removed because it probably is the process that came first
	public int run()
	{
            //Index in main memory to remove memoryPage. -1 means an error has occured.
            int remove = -1;
            double highest = 0;
            //Loops through all entry in main memory
            for (int x = 0; x < memory.size(); x++){
                //Takes memoryPage in memory[x]
                memoryPage temp = memory.get(x);
                //Checks if memoryPage is not empty
                if (temp.name != '.'){
                    //If runTime in memoryPage[x] is greater than highest, set highest to runTime. 
                    if(temp.runTime > highest){
                        highest = temp.runTime;
                        //Index to remove is x
                        remove = x;
                    }
                }
            }
            return remove;
	}

	@Override
	public String getName() {
		return "FIFO";
	};
}

