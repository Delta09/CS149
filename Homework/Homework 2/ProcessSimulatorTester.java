import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Models a ProcessSimulatorTester class to test out all the scheduling algorithms 
 * @author yen_my_huynh
 *
 */
public class ProcessSimulatorTester{
	private static final int MAX_QUANTA = 99;
	private static final int MAX_PRIORITY = 4;
	
	// main method
	public static void main(String args[]){
		PriorityQueue<ProcessSimulator> processQueue = new PriorityQueue<ProcessSimulator>(50, new ArrivalTimeComparator());
		Random rand = new Random();
		boolean processCompleted = false;
		
		for (int i = 1; i < 2; i++) {
			System.out.println("Run: " + i);
			
			// Generator for priority queues
			System.out.println("Generating Queue");
			for (int k = 0; k < 50; k++){
				ProcessSimulator p = new ProcessSimulator(Integer.toString(k), randBetween(0,MAX_QUANTA), randBetween(0.1,10), rand.nextInt(MAX_PRIORITY) + 1, processCompleted);
				processQueue.add(p);
			}
			/*
			System.out.println("*********************************************Generated Queue********************************************");
			System.out.println(processQueue.toString());
			System.out.println("********************************************************************************************************");
			*/
			
			
			System.out.println("*****************************************First Come First Serve*****************************************");
			FirstComeFirstServed FCFS = new FirstComeFirstServed(processQueue);
			FCFS.runFCFS();
			for (int j = 0; j < FCFS.getOutputListing().size(); j++) {
				System.out.println(FCFS.getOutputListing().get(j));
			}
			
			System.out.println();
			
			System.out.println("***********************************************Round Robin**********************************************");
			RoundRobin RR = new RoundRobin(processQueue);
			RR.runRR();
			
			for (int j = 0; j < RR.getOutputListing().size(); j++) {
				System.out.println(RR.getOutputListing().get(j));
			}
			
			processQueue.clear();
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
