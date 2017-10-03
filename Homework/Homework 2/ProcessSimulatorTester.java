
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Models a ProcessSimulatorTester class to test out all the scheduling
 * algorithms
 * 
 * @author yen_my_huynh
 *
 */
public class ProcessSimulatorTester {
	public static final int MAX_QUANTA = 99;
	public static final int MAX_PRIORITY = 4;

	// main method
	public static void main(String args[]) {
		PriorityQueue<ProcessSimulator> processQueue2 = new PriorityQueue<ProcessSimulator>(100,
				new RemainingRunTimeComparator());
		Random rand = new Random();
		boolean processCompleted = false;

		for (int i = 1; i < 6; i++) {
			System.out.println("Run: " + i);

			// Generator for priority queues with different processes inside.
			System.out.println("Generating Queue");
			for (int k = 0; k < 50; k++) {
				ProcessSimulator p = new ProcessSimulator(Integer.toString(k), randBetween(0, MAX_QUANTA),
						randBetween(0.1, 10), rand.nextInt(MAX_PRIORITY) + 1, processCompleted);
				
				processQueue2.add(p);
			}

			System.out.println(
					"*****************************************First Come First Serve*****************************************");
			FirstComeFirstServed FCFS = new FirstComeFirstServed(generateNewPriorityQueue());
			FCFS.runFCFS();
			for (int j = 0; j < FCFS.getOutputListing().size(); j++) {
				System.out.println(FCFS.getOutputListing().get(j));
			}

			System.out.println();

			System.out.println(
					"***************************************Shortest Remaining Run Time***************************************");
			ShortestRemainingTime SRT = new ShortestRemainingTime(processQueue2);
			SRT.runSRT();
			for (int j = 0; j < SRT.getOutputListing().size(); j++) {
				System.out.println(SRT.getOutputListing().get(j));
			}

			System.out.println();

			System.out.println(
					"*************************************************Round Robin***********************************************");
			RoundRobin RR = new RoundRobin(generateNewPriorityQueue());
			RR.runRR();

			for (int j = 0; j < RR.getOutputListing().size(); j++) {
				System.out.println(RR.getOutputListing().get(j));
			}
			System.out.println();
			
			System.out.println(
					"******************************************Shortestest Job First********************************************");
			ShortestJobFirst SJF = new ShortestJobFirst(generateNewAList());
			SJF.runSJF();
			for (int j = 0; j < SJF.getOutputListing().size(); j++) {
				System.out.println(SJF.getOutputListing().get(j));
			}

			System.out.println();

			 System.out.println("******************************Highest Priority First Non-Preemptive******************************");
			 HighestPriorityFirstNP HPFNP = new HighestPriorityFirstNP(generateNewArrayDeque());
			 HPFNP.runHPFNP();
			 for (int j = 0; j < HPFNP.getOutputListing().size(); j++) {
			 System.out.println(HPFNP.getOutputListing().get(j));
			 }
			
			 System.out.println();
			
			 System.out.println("********************************Highest Priority First Preemptive********************************");
			 HighestPriorityFirstP HPFP = new HighestPriorityFirstP(generateNewArrayDeque());
			 HPFP.runHPFP();	 
			 for (int j = 0; j < HPFP.getOutputListing().size(); j++) {
			 System.out.println(HPFP.getOutputListing().get(j));
			 }
			
			 System.out.println();
		}
		processQueue2.clear();
	}

	/**
	 * Randomizes a number between max and min values.
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return the random number
	 */
	public static float randBetween(double min, float max) {
		return (float) (min + Math.round(Math.random() * (max - min)));
	}
	
	/**
	 * Creating a new array deque with different processes
	 * @return a new array deque with different processes
	 */
	private static ArrayDeque<ProcessSimulator> generateNewArrayDeque() {
		ArrayList<ProcessSimulator> processList = new ArrayList<>();
		ArrayDeque<ProcessSimulator> processQueue = new ArrayDeque<>();
		boolean processCompleted = false;
		Random rand = new Random();
		for (int i = 0; i < 100; i++) 
		{
			ProcessSimulator p = new ProcessSimulator(Integer.toString(i), randBetween(0, MAX_QUANTA),
					randBetween(0.1, 10), rand.nextInt(MAX_PRIORITY) + 1, processCompleted);
			processList.add(p);
		}

		Collections.sort(processList, new Comparator<ProcessSimulator>() {
			public int compare(ProcessSimulator o1, ProcessSimulator o2) {
				return (int) (o1.getArrivalTime() - o2.getArrivalTime());
			}
		});

		for (ProcessSimulator p : processList) {
			processQueue.add(p);
		}
		return processQueue;
	}
	
	/**
	 * Creating a new array list with different processes
	 * @return a new array list with different processes
	 */
	private static ArrayList<ProcessSimulator> generateNewAList() {
		// For other processing scheduling algorithms
		ArrayList<ProcessSimulator> processes = new ArrayList<ProcessSimulator>();
		boolean processCompleted = false; 

		// Create 100 processes
		for (int i = 0; i < 100; i++) {
			Random rand = new Random();
			ProcessSimulator p = new ProcessSimulator(Integer.toString(i), randBetween(0, MAX_QUANTA),
					randBetween(0.1, 10), rand.nextInt(MAX_PRIORITY) + 1, processCompleted);
			processes.add(p);
		}
		Collections.sort(processes, new ArrivalTimeComparator());
		return processes;
	}
	
	/**
	 * Creating a new priority queue with different processes
	 * @return a new priority queue with different processes
	 */
	private static PriorityQueue<ProcessSimulator> generateNewPriorityQueue() {
		PriorityQueue<ProcessSimulator> processQueue = new PriorityQueue<>(100, new ArrivalTimeComparator());
		boolean processCompleted = false;
		Random rand = new Random();
		for (int i = 0; i < 100; i++) 
		{
			ProcessSimulator p = new ProcessSimulator(Integer.toString(i), randBetween(0, MAX_QUANTA),
					randBetween(0.1, 10), rand.nextInt(MAX_PRIORITY) + 1, processCompleted);
			processQueue.add(p);
		}
		return processQueue;
	}
	
}
