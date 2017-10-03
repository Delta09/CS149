import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Models a FirstComeFirstServed Scheduler
 * @author daany
 *
 */
public class RoundRobin {
	protected PriorityQueue<ProcessSimulator> processQueue;
	protected ArrayList<ProcessSimulator> processQueueTrack;
	protected ArrayList<String> TimeTrack;
	private ArrayList<String> outputListing;
	private LinkedList<ProcessSimulator> waiting;
	private int quanta;
	
	
	public RoundRobin(PriorityQueue<ProcessSimulator> processQueue) {
		this.processQueue = processQueue;
		this.processQueueTrack = new ArrayList<>();
		this.TimeTrack = new ArrayList<>();
		this.outputListing = new ArrayList<>();
		this.quanta = 0;
	}
	
	/**
	 * Runs RR scheduling algorithm.
	 */
	public void runRR(){
		ProcessSimulator currentProcess, incomingProcess;
		waiting = new LinkedList<ProcessSimulator>();
		
		// runs when all processes are not all finished and is less than 100 quantum.
		while (quanta < 100){
			
			if (processQueue.size() > 0 && processQueue.peek().getArrivalTime() == quanta ) {
				
				while(processQueue.size() > 0 && processQueue.peek().getArrivalTime() == quanta) {
					
					incomingProcess = processQueue.poll();					//incoming process = new guy
					
					waiting.add(incomingProcess);
					
					}
				
				
		
			}
			
			
			if (!waiting.isEmpty()) {
				currentProcess = waiting.pollFirst();
				
				
				
				TimeTrack.add(currentProcess.getId());
				
				currentProcess.setRemainingRunTime(currentProcess.getRemainingRunTime()-1);
				quanta++;
				
				
				
				if (currentProcess.getRemainingRunTime() > 0) {
					waiting.add(currentProcess);
				}
				
				else {
					currentProcess.setFinishedTime(currentProcess.getArrivalTime(), quanta);
					currentProcess.setTurnAroundTime(currentProcess.getFinishedTime(), currentProcess.getArrivalTime());
					currentProcess.setWaitingTime(currentProcess.getTurnAroundTime(), currentProcess.getExpectedRunTime());
					currentProcess.setResponseTime(currentProcess.getWaitingTime());
					
					processQueueTrack.add(currentProcess);
				}
				
				
		
			}
			
			else {
				quanta++;
				TimeTrack.add("Idle");
				
			}
			
			}
			
			
		

		printStatistics(processQueueTrack);
	}
	
	/**
	 * Prints out the FCFS scheduling algorithm statistics: average turn around time,
	 * waiting time, and response time.
	 * @param processQueueTrack the track of all processes that ran successfully
	 */
	public void printStatistics(ArrayList<ProcessSimulator> processQueueTrack){
		float turnAroundTimeTotal = 0;
		float waitingTimeTotal = 0;
		float responseTimeTotal = 0;
		String stats = "";
		int count = 0;
		
		for (ProcessSimulator p : processQueueTrack){
			turnAroundTimeTotal += p.getTurnAroundTime();
			waitingTimeTotal += p.getWaitingTime();
			responseTimeTotal += p.getResponseTime();
			count++;
			
			if (count == 10){
				stats += "\n";
				count = 0;
			}
		
			outputListing.add(p.toString());
		}
		// gathers up all the statistics
		float averageTurnAroundTime = turnAroundTimeTotal/ processQueueTrack.size();
		float averageWaitingTime = waitingTimeTotal/ processQueueTrack.size();
		float averageResponseTime = responseTimeTotal/ processQueueTrack.size();
		
		// casts throughtput to avoid truncating
		float throughput = (float) processQueueTrack.size()/ 50;
		
		String track = new String();
		System.out.println("Time Chart:" );
		int quanta=0;
		for (String p : TimeTrack){
			track += ("Q(" + quanta +")="+p + ", ");
			quanta++;
			if (quanta%10 == 0) {
				track += "\n";
			}
			
			}
		String timeChartDisplay = "\nTime Chart per quantum (Q): \n \n"+track+"\n";
		outputListing.add(timeChartDisplay);
		
		stats = "Average Turnaround Time: " + averageTurnAroundTime + "\tAverage Waiting Time: "
				+ averageWaitingTime + "\tAverage Response Time: " + averageResponseTime + "\tThroughput: "
				+ throughput + "\n";
		outputListing.add(stats);
	}

	
	/**
	 * Gets the output listing.
	 * @return the output listing
	 */
	public ArrayList<String> getOutputListing(){
		return outputListing;
	}
	
	
}