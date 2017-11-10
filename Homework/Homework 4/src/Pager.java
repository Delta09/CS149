import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 
 * 
 * Simulates Paging Algorithm
 */
public abstract class Pager 
{
	public LinkedList<Process> q,  waiting;
	public LinkedList<memoryPage> memory;
	public static final int MEMORY_SIZE_MB = 100;
	public int swapped = 0;
	public int hit = 0;
	public int miss = 0;
	public double ratio = 0;
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
		while (time <= 60) {

			if(!firstTime) {
				update();
				free += deallocateProcess(time);
			}



			while (q.element().arrival <= time && free >= 4) {
				waiting.add(q.removeFirst());
			}

			firstTime=false;
			for (Process p : waiting) {
				int frame = p.NextPage();

				if (free >= 4) {//add existing or new process from waiting queue
					if (checkProcess(p, frame)) { //hit
						
						update2(p, frame);
						hit++;
					}

					else {
						miss++;
						if(frame == 0) {						
							p.start=time;
						}
						addProcess(p, frame);
						free--;
						System.out.println("Time:" + time + ",\t Name:" + p.name + ",\t Enter,\t\t Size:" + p.size + ",\tService Duration:" + p.duration);
					}


				} 
				else if(free > 0){
					if (checkProcess(p, frame)) {
						
						memory.indexOf(p.name);
						update2(p, frame);
						hit++;
					}

					else {
						if(frame == 0) {						
							p.start=time;
						}
						addProcess(p, frame);
						free--;
						miss++;
					}


				}
				else if( free == 0 ){
					int index = run();
					replace(index, p, frame);
					swapped++;
					System.out.println("Time:" + time + ",\t Name:" + p.name + ",\t Exit,\t\t Size:" + p.size + ",\t Service Duration:" + p.duration);
					
				}
				
			}
			time = (double) (time + 0.10);
			BigDecimal bd = new BigDecimal(time);
			bd=bd.round(new MathContext(3));
			printMemoryMap();
			time = bd.doubleValue();
		}
		
		ratio = (double) hit/miss;
	}

	private void update2(Process p, int frame) {//LFU MFU
		for (memoryPage m : memory){
			if ((m.name == p.name) && (m.processPage == frame)) {
				
				m.lastAccessed = 0;
				m.frequency+= 1;
			}
		}

	}

	private void update() {//LRU FIFO
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
		HashSet<Process> pSet = new HashSet<Process>();
		ArrayList<Integer> remove = new ArrayList<Integer>();
		boolean removed = false;
		for (memoryPage m : memory){   /* Remove the finished process and replace with available space ('.' process)
               NOTE: I could not make the LinkedList access efficient due to 
               ConcurrentModificationExceptions while using iterator to modify, and 
               Java does not give direct access to the list pointers for manual traversal
		 */
			if (m.name != '.') {
				if (time == (m.process.start + m.process.duration))
				{
					remove.add(i);
					pSet.add(m.process);
					count++;
					removed =true;
				}
			}
			i++;
		}
		
		for(Process p : pSet) {
			waiting.remove(p);
			System.out.println("Time:" + time + ",\t Name:" + p.name + ",\t Exit,\t\t Size:" + p.size + ",\t Service Duration:" + p.duration);
		}
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
