import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 
 * 
 * Class representing a Memory Swapping Algorithm. Extended by 
 * BestFit, NextFit, and FirstFit
 * 
 * Simulates the algorithm and prints a memory map every time memory is 
 * allocated or deallocated.
 */
public abstract class Pager 
{
	public LinkedList<Process> q,  waiting;
	public LinkedList<memoryPage> memory;
	public static final int MEMORY_SIZE_MB = 100;
	public int swapped = 0;
	/**
	 * Use a particular swapping algorithm to get the index of the next allocation
	 * @param memory LinkedList<Process> representing the memory space
	 * @param size int : size of the process to be allocated
	 * @param start int: index to start searching (ignored by FirstFit, BestFit)
	 * @return int : the index that the next process will be allocated to
	 */
	public Pager(LinkedList<memoryPage> memory, LinkedList<Process> process) {
		this.memory = new LinkedList<memoryPage>( memory);
		this.q = new LinkedList<Process>(process);
		this.waiting = new LinkedList<Process>();
	}

	public abstract int run();
	public abstract String getName();

	/**
	 * Simulate swapping using the given Swapping Algorithm and collect statistics
	 * @param processQueue Queue<Process> in FCFS order 
	 * @return int : number of processes that were successfully swapped in 
	 */
	public void simulate() throws CloneNotSupportedException {
		int free = memory.size();
		double time = 0.0;
		boolean firstTime = true;
		while (time < 60) {

			if(!firstTime) {
				update();
				free += deallocateProcess(time);
			}
			


			while (q.element().arrival == time) {
				waiting.add(q.removeFirst());
			}


			for (Process p : waiting) {
				firstTime=false;
				int frame = p.NextPage();
				if (free >= 4) {//add existing or new process from wiaitng que
					if (checkProcess(p, frame)) { //hit
						memory.indexOf(p.name);
						update2(p, frame);
					}

					else {
						if(frame == 0) {						
							p.start=time;
						}
						addProcess(p, frame);
						free--;
						System.out.println(time + ", " + p.name + ",  Enter,  " + p.size + ", " + p.duration);
					}

					
				} 
				else if(free > 0){//add existing Procdxx
					if (checkProcess(p, frame)) { //hit
						memory.indexOf(p.name);
						update2(p, frame);
					}

					else {
						if(frame == 0) {						
							p.start=time;
						}
						addProcess(p, frame);
						free--;
					}

					
				}
				else if( free == 0 ){
					int index = run();
					System.out.println(time + ", " + p.name + ",  Enter,  " + frame + ", " + index + ", " + p.name +", "+ frame);
					System.out.println(index);
					System.out.println(getName());
					replace(index, p, frame);
					swapped++;
				}
				
			}
			time = (double) (time + 0.10);
			BigDecimal bd = new BigDecimal(time);
			bd=bd.round(new MathContext(3));
			
			time = bd.doubleValue();
			if ((time == Math.floor(time)) && !Double.isInfinite(time)) {
				System.out.println("Time:"+ time);
				printMemoryMap();
			}

		}
	}

private void update2(Process p, int frame) {
	for (memoryPage m : memory){
		if ((m.name == p.name) && (m.processPage == frame)) {
			m.lastAccessed = 0;
			m.frequency+= 1;
		}
	}

}

private void update() {
	// TODO Auto-generated method stub
	for (memoryPage m : memory){
		if (m.name != '.') {
			m.runTime += 0.1;
			m.lastAccessed += 0.1;
		}
	}
}



private boolean checkProcess(Process p, int frame) {
	boolean ans = false;
	for (memoryPage m : memory)
	{ 
		if((m.name ==p.name) && (m.processPage ==frame)){
			ans =  true;
			break;
		}
	}
	return ans;
}

private void replace(int index, Process p, int page) {
	// TODO Auto-generated method stub
	memory.set(index, new memoryPage(p, page, 0, 0, 0));
}

/**
 * Find the memory for the process that finished and deallocate it
 * @param memory LinkedList<Process> : the memory to search for deallocation
 * @param time int : the current time for use in printing the memory map
 */
public int deallocateProcess(double time)
{
	int i = 0;
	int count = 0;
	Process p = new Process(0, 0, '.',  0, 0);
	ArrayList<Integer> remove = new ArrayList<Integer>();
	for (memoryPage m : memory){   /* Remove the finished process and replace with available space ('.' process)
               NOTE: I could not make the LinkedList access efficient due to 
               ConcurrentModificationExceptions while using iterator to modify, and 
               Java does not give direct access to the list pointers for manual traversal
	 */
		if (m.name != '.') {
			if (time == (m.process.start + m.process.duration))
			{
				remove.add(i);
				p=m.process;
				count++;
			}
		}
		i++;
	}
	waiting.remove(p);

	for(int z : remove) {
		memory.set(z, new memoryPage('.'));
	}
	remove.clear();
	return count;
}    



public void printMemoryMap()
{
	System.out.print("<");
	for (memoryPage m : memory)
		System.out.print(m.name);
	System.out.println(">");
}



public void addProcess(Process p, int page)
{
	memoryPage blank =  new memoryPage('.');
	int i = memory.indexOf(blank);
	memory.set(i, new memoryPage(p, page, 0, 0, 0));
	
}

}
