
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
		PriorityQueue<ProcessSimulator> processQueue1 = new PriorityQueue<ProcessSimulator>(100, new ArrivalTimeComparator());
		ArrayList<ProcessSimulator> processQueue2 = new ArrayList<ProcessSimulator>();
		PriorityQueue<ProcessSimulator> processQueue3 = new PriorityQueue<ProcessSimulator>(100, new RemainingRunTimeComparator());
		PriorityQueue<ProcessSimulator> processQueue4 = new PriorityQueue<ProcessSimulator>(100, new ArrivalTimeComparator());
		ArrayDeque<ProcessSimulator> processQueue5 = new ArrayDeque<ProcessSimulator>();
		ArrayDeque<ProcessSimulator> processQueue6 = new ArrayDeque<ProcessSimulator>();
		Random rand = new Random();
		boolean processCompleted = false;

		for (int i = 1; i < 6; i++) {
			System.out.println("Run: " + i);

			System.out.println("*********************************************Generated Queue********************************************");
			// Generator for priority queues with different processes inside.
			for (int k = 0; k < 100; k++) {
				ProcessSimulator p = new ProcessSimulator(Integer.toString(k), randBetween(0, MAX_QUANTA),randBetween(0.1, 10), rand.nextInt(MAX_PRIORITY) + 1, processCompleted);
				
				processQueue1.add(p);
				processQueue2.add(p);
				processQueue3.add(p);
				processQueue4.add(p);
				processQueue5.add(p);
				processQueue6.add(p);
				System.out.println(p);
			}
			System.out.println("********************************************************************************************************");
			
			System.out.println();
			
			System.out.println("*****************************************First Come First Serve*****************************************");
			FirstComeFirstServed FCFS = new FirstComeFirstServed(processQueue1);
			FCFS.runFCFS();
			for (int j = 0; j < FCFS.getOutputListing().size(); j++) {
				System.out.println(FCFS.getOutputListing().get(j));
			}

			System.out.println();
			
			System.out.println(
					"******************************************Shortestest Job First********************************************");
			ShortestJobFirst SJF = new ShortestJobFirst(processQueue2);
			SJF.runSJF();
			for (int j = 0; j < SJF.getOutputListing().size(); j++) {
				System.out.println(SJF.getOutputListing().get(j));
			}

			System.out.println();


			System.out.println(
					"***************************************Shortest Remaining Run Time***************************************");
			ShortestRemainingTime SRT = new ShortestRemainingTime(processQueue3);
			SRT.runSRT();
			for (int j = 0; j < SRT.getOutputListing().size(); j++) {
				System.out.println(SRT.getOutputListing().get(j));
			}

			System.out.println();

			System.out.println(
					"*************************************************Round Robin***********************************************");
			RoundRobin RR = new RoundRobin(processQueue4, 1);
			RR.runRR();
			for (int j = 0; j < RR.getOutputListing().size(); j++) {
				System.out.println(RR.getOutputListing().get(j));
			}
			System.out.println();
			
			
			 System.out.println("******************************Highest Priority First Non-Preemptive******************************");
			 HighestPriorityFirstNP HPFNP = new HighestPriorityFirstNP(processQueue5);
			 HPFNP.runHPFNP();
			 for (int j = 0; j < HPFNP.getOutputListing().size(); j++) {
			 System.out.println(HPFNP.getOutputListing().get(j));
			 }
			
			 System.out.println();
			
			 System.out.println("********************************Highest Priority First Preemptive********************************");
			 HighestPriorityFirstP HPFP = new HighestPriorityFirstP(processQueue6);
			 HPFP.runHPFP();	 
			 for (int j = 0; j < HPFP.getOutputListing().size(); j++) {
			 System.out.println(HPFP.getOutputListing().get(j));
			 }
			
			 System.out.println();
			 System.out.println();
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
	
	
	
}
