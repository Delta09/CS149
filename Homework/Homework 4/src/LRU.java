import java.util.LinkedList;

/**
 * @author  
 * 
 * Simulate memory swapping with the First Fit algorithm
 * Swaps process memory into the first available chunk in the memory map
 */
public class LRU extends Pager
{
    
	public LRU(LinkedList<memoryPage> memory, LinkedList<Process> process) {
		super(memory, process);
	}

	@Override
        public int run(){
            //Index in main memory to remove memoryPage. -1 means an error has occured.
            int remove = -1;
            double highest = Integer.MIN_VALUE;
            //Loops through all entry in main memory
            for (int x = 0; x < memory.size(); x++){
                //Takes memoryPage in memory[x]
                memoryPage temp = memory.get(x);
                //Checks if memoryPage is not empty
                if (temp.name != '.'){
                    //If lastAccessed in memoryPage[x] is greater than highest, set highest to lastAccessed. 
                    if(temp.lastAccessed > highest){
                        highest = temp.lastAccessed;
                        //Index to remove is x
                        remove = x;
                    }
                }
            }
            return remove;
        }

	@Override
	public String getName() {
		return "LRU";
	};
}
