import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Homework 4 - Swap Simulator
 * @author 
 * 
 * This program simulates memory swapping with first fit, next fit, and best fit 
 * memory allocation algorithms based on various parameters:
 * 
 *     -SwappingSimulator memory has 100MB swap space
 *     -Processes have randomly, evenly distributed sizes of  4, 8, 16, and 32 MB
 *     -Processes last 1, 2, 3, 4, or 5 seconds    
 *     -Process Scheduler uses FCFS
 *     -Simulation runs for "60 seconds" (sped up 10x)
 * 
 * Each algorithm is ran 5 times to get an average of the number of processes 
 * successfully swapped into memory (but not necessarily completed) and each time
 * a swap occurs the memory map is printed like 
 * AAAA....BBBBBBBB..CCCC with one letter / dot per MB of memory
 */

public class Simulator 
{
	public static final int SIM_RUNS = 1;
	public static final int SIM_TIME_MAX = 60; 
	public static final int MEMORY_SIZE = 100; 
	public static final int PROCESS_COUNT= 160;
	public static LinkedList<memoryPage> memory = new LinkedList<memoryPage>();
	public static double FIFO, LFU, LRU, MFU, RP;

	/** Simulate each algorithm SIM_RUNS times. Print out memory maps 
	 * each time memory is changed, and also print overall statistics 
	 */
	public static void main(String[] args) throws CloneNotSupportedException
	{
		int sim=1;
		while(sim <= SIM_RUNS) {
			generateMemory(100);
			System.out.println("Simulation Run: " + sim);
			System.out.println("------------------------------------------------");
			LinkedList<Process> q = ProcessFactory.generateProcesses(PROCESS_COUNT);

			System.out.println("Name    Arrival    Duration    Size");
			for (Process p : q)
				System.out.format("  %-8c %-10d %-8d %-8d\n", p.name, p.arrival, p.duration, p.size);

			System.out.println("------------------------------------------------");

			ArrayList<Pager> pager = new ArrayList<Pager>() {{
				add(new FIFO(memory, q)); 
				add(new LFU(memory, q)); 
				add(new LRU(memory, q)); 
				add(new LRU(memory, q)); 
				add(new MFU(memory, q)); 
				add(new RP(memory, q));
			}};

			for (Pager p : pager){
				System.out.println("Paging: " + p.getName());
				p.simulate();
				if (p.getName() == "FIFO"){
					FIFO += p.swapped;
				}
				if (p.getName() == "LFU"){
					LFU += p.swapped;
				}
				if (p.getName() == "LRU"){
					LRU += p.swapped;
				}
				if (p.getName() == "MFU"){
					MFU += p.swapped;
				}
				if (p.getName() == "Random Pick"){
					RP += p.swapped;
				}
				System.out.println("------------------------------------------------");
				System.out.println("------------------------------------------------");
				System.out.println();
			}
			

			System.out.println("\n\n");
			sim++;
			memory.clear();
		}
		
		System.out.println("FIFO: " +  FIFO/SIM_RUNS);
		System.out.println("LFU: " +  LFU/SIM_RUNS);
		System.out.println("LRU: " +  LRU/SIM_RUNS);
		System.out.println("MFU: " +  MFU/SIM_RUNS);
		System.out.println("Random Pick: " +  RP/SIM_RUNS);
	}

	public static void generateMemory(int n)
	{	
		for(int i = 0 ; i < n; i++) {
			memory.add(new memoryPage('.'));
		}
	}
}
