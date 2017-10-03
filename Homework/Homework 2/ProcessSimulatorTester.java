import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Models a ProcessSimulatorTester class to test out all the scheduling
 * algorithms
 * 
 * @author yen_my_huynh
 *
 */
public class ProcessSimulatorTester {
	private static final int MAX_QUANTA = 99;
	private static final int MAX_PRIORITY = 4;

	// main method
	public static void main(String args[]){
		PriorityQueue<ProcessSimulator> processQueue = new PriorityQueue<ProcessSimulator>(100, new ArrivalTimeComparator());
		ArrayList<ProcessSimulator> processQueue2 = new ArrayList<ProcessSimulator>();
		PriorityQueue<ProcessSimulator> processQueue3 = new PriorityQueue<ProcessSimulator>(100, new ArrivalTimeComparator());
		PriorityQueue<ProcessSimulator> processQueue4 = new PriorityQueue<ProcessSimulator>(100, new ArrivalTimeComparator());
		//PriorityQueue<ProcessSimulator> custom = new PriorityQueue<ProcessSimulator>(50, new ArrivalTimeComparator());
		Random rand = new Random();
		boolean processCompleted = false;

		for (int i = 1; i < 2; i++) {
			System.out.println("Run: " + i);

			// Generator for priority queues with different processes inside.
			System.out.println("Generating Queue");
			for (int k = 0; k < 100; k++) {
				ProcessSimulator p = new ProcessSimulator(Integer.toString(k), randBetween(0, MAX_QUANTA),
						randBetween(0.1, 10), rand.nextInt(MAX_PRIORITY) + 1, processCompleted);
				processQueue.add(p);
				processQueue2.add(p);
				processQueue3.add(p);
				processQueue4.add(p);
			}
			
			/*
			ProcessSimulator p1 = new ProcessSimulator("A", 0.0f, 8.0f, 1, false);
			ProcessSimulator p2 = new ProcessSimulator("B", 0.0f, 4.0f, 1, false);
			ProcessSimulator p3 = new ProcessSimulator("C", 0.0f, 9.0f, 1, false);
			ProcessSimulator p4 = new ProcessSimulator("D", 0.0f, 5.0f, 1, false);
			//ProcessSimulator p5 = new ProcessSimulator("4", 0.0f, 12.0f, 1, false);
			custom.add(p1);
			custom.add(p2);
			custom.add(p3);
			custom.add(p4);
			//custom.add(p5);
			
			
			System.out.println("*********************************************Generated Queue********************************************");
			System.out.println(custom.toString());
			System.out.println("********************************************************************************************************");
			*/

			
			System.out.println("*****************************************First Come First Serve*****************************************");

			FirstComeFirstServed FCFS = new FirstComeFirstServed(processQueue);
			FCFS.runFCFS();
			for (int j = 0; j < FCFS.getOutputListing().size(); j++) {
				System.out.println(FCFS.getOutputListing().get(j));
			}

			
			System.out.println();
			
			
			System.out.println("******************************************Shortestest Job First********************************************");
			ShortestJobFirst SJF = new ShortestJobFirst(processQueue2);
			SJF.runSJF();
			for (int j = 0; j < SJF.getOutputListing().size(); j++) {
				System.out.println(SJF.getOutputListing().get(j));
			}

			
			System.out.println();
			
			
			System.out.println("*****************************************Shortest Remaining Run Time*****************************************");
            ShortestRemainingTime SRT = new ShortestRemainingTime(processQueue3);
            SRT.runSRT();
            for (int j = 0; j < SRT.getOutputListing().size(); j++) {
            	System.out.println(SRT.getOutputListing().get(j));
            	}
			
            
            System.out.println();
            
            
			System.out.println("*************************************************Round Robin***********************************************");
			RoundRobin RR = new RoundRobin(processQueue4, 1);
			RR.runRR();
			for (int j = 0; j < RR.getOutputListing().size(); j++) {
				System.out.println(RR.getOutputListing().get(j));
			}
			System.out.println();
			
			
			
			

			// System.out.println("*************************************Highest
			// Priority First
			// Non-Preemptive*************************************");
			// HighestPriorityFirstNP HPFNP = new
			// HighestPriorityFirstNP(processQueue);
			// FCFS.runFCFS();
			// for (int j = 0; j < FCFS.getOutputListing().size(); j++) {
			// System.out.println(FCFS.getOutputListing().get(j));
			// }
			//
			// System.out.println();
			//
			// System.out.println("***************************************Highest
			// Priority First
			// Preemptive*****************************************");
			// HighestPriorityFirstP HPFP = new
			// HighestPriorityFirstP(processQueue);
			// FCFS.runFCFS();
			// for (int j = 0; j < FCFS.getOutputListing().size(); j++) {
			// System.out.println(FCFS.getOutputListing().get(j));
			// }
			//
			// System.out.println();

			processQueue.clear();
			processQueue2.clear();
			processQueue3.clear();
			processQueue4.clear();
		}
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
