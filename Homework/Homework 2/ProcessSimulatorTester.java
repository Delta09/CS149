import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Models a ProcessSimulatorTester class to test out all the scheduling algorithms 
 * @author yen_my_huynh
 *
 */
public class ProcessSimulatorTester {
	private static final int MAX_QUANTA = 99;
	private static final int MAX_PRIORITY = 4;
	
	// main method
	public static void main(String args[]){
		PriorityQueue<ProcessSimulator> processQueue = new PriorityQueue<ProcessSimulator>(50, new ArrivalTimeComparator());
		Random rand = new Random();
		boolean processCompleted = false;
		
		for (int i = 1; i < 6; i++) {
			System.out.println("Run: " + i);
			
			// Generator for priority queues
			System.out.println("Generating Queue");
			for (int k = 0; k < 50; k++){
				ProcessSimulator p = new ProcessSimulator(Integer.toString(k), randBetween(0,MAX_QUANTA), randBetween(0.1,10), rand.nextInt(MAX_PRIORITY) + 1, processCompleted);
				processQueue.add(p);
			}
			
			System.out.println("*****************************************First Come First Serve*****************************************");
			FirstComeFirstServed test = new FirstComeFirstServed(processQueue);
			test.runFCFS();
			for (int j = 0; j < test.getOutputListing().size(); j++) {
				System.out.println(test.getOutputListing().get(j));
			}
			
			processQueue.clear();
		}
	}
	
	/**
	 * Generates an array list with different processes inside.
	 * @return the list of different processes inside
	 */
	public static ArrayList<ProcessSimulator> processGenerator(){
		ArrayList<ProcessSimulator> processList = new ArrayList<ProcessSimulator>();
		Random rand = new Random();
		boolean processCompleted = false;
		for (int i = 0; i < 50; i++){
			ProcessSimulator p = new ProcessSimulator( Integer.toString(i), randBetween(0,MAX_QUANTA), randBetween(0.1,10), 
					rand.nextInt(MAX_PRIORITY) + 1, processCompleted);
			processList.add(p);
		}
		Collections.sort(processList, new ArrivalTimeComparator());
		return processList;
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
