import java.util.LinkedList;

public class LFU extends Pager
{   
	public LFU(LinkedList<memoryPage> memory, LinkedList<Process> process) {
		super(memory, process);
	}

	@Override
	public int run(){
            //Index in main memory to remove memoryPage. -1 means an error has occured.
            int remove = -1;
            double lowest = Integer.MAX_VALUE;
            //Loops through all entry in main memory
            for (int x = 0; x < memory.size(); x++){
                //Takes memoryPage in memory[x]
                memoryPage temp = memory.get(x);
                //Checks if memoryPage is not empty
                if (temp.name != '.'){
                    //If frequency in memoryPage[x] is lower than lowest, set lowest to frequency. 
                	if(temp.frequency < lowest){
                        lowest = temp.frequency;
                        //Index to remove is x
                        remove = x;
                    }
                }
            }
            return remove;
	}

	@Override
	public String getName() {
		return "LFU";
	};
}