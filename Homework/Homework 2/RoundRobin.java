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
	private ArrayList<String> outputListing;
	private LinkedList<ProcessSimulator> waiting;
	private int quanta;
	
	
	public RoundRobin(PriorityQueue<ProcessSimulator> processQueue) {
		this.processQueue = processQueue;
		this.processQueueTrack = new ArrayList<>();
		this.outputListing = new ArrayList<>();
		this.quanta = 0;
	}
	
	/**
	 * Runs RR scheduling algorithm.
	 */
	public void runRR(){
		ProcessSimulator currentProcess, incomingProcess;
		waiting = new LinkedList<ProcessSimulator>();
		int cp = 0;
		
		// runs when all processes are not all finished and is less than 100 quantum.
		while (quanta < 100){ 
			
			if (processQueue.size() > 0 && processQueue.peek().getArrivalTime() == quanta ) {
				while(processQueue.size() > 0 && processQueue.peek().getArrivalTime() == quanta) {
					
					incomingProcess = processQueue.poll();					//incoming process = new guy
					float t = incomingProcess.getRemainingRunTime();
					incomingProcess.setRemainingRunTime(t-1);
					
					
					quanta++;
					
					
					if (incomingProcess.getRemainingRunTime() > 0) {
						waiting.add(incomingProcess);
					}
					else {
						incomingProcess.setFinishedTime(incomingProcess.getArrivalTime(), quanta);
						incomingProcess.setTurnAroundTime(incomingProcess.getFinishedTime(), incomingProcess.getArrivalTime());
						incomingProcess.setWaitingTime(incomingProcess.getTurnAroundTime(), incomingProcess.getExpectedRunTime());
						incomingProcess.setResponseTime(incomingProcess.getWaitingTime());
						
						processQueueTrack.add(incomingProcess);
					}
					
				}
				
			}
			
			else if (waiting.isEmpty()) {
				quanta++;
			}
			
			else {
				currentProcess = waiting.pollFirst();
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
		String timeChart = "";
		
		for (ProcessSimulator p : processQueueTrack){
			turnAroundTimeTotal += p.getTurnAroundTime();
			waitingTimeTotal += p.getWaitingTime();
			responseTimeTotal += p.getResponseTime();
			outputListing.add(p.toString());
		}
		// gathers up all the statistics
		float averageTurnAroundTime = turnAroundTimeTotal/ processQueue.size();
		float averageWaitingTime = waitingTimeTotal/ processQueue.size();
		float averageResponseTime = responseTimeTotal/ processQueue.size();
		// casts throughtput to avoid truncating
		float throughput = (float) processQueueTrack.size()/ 100;
		String timeChartDisplay = "\n" + "Time Chart:" + timeChart;
		outputListing.add(timeChartDisplay);
		timeChartDisplay = "Average Turnaround Time: " + averageTurnAroundTime + "\tAverage Waiting Time: "
				+ averageWaitingTime + "\tAverage Response Time: " + averageResponseTime + "\tThroughput: "
				+ throughput + "\n";
		outputListing.add(timeChartDisplay);
	}
	
	/**
	 * Gets the output listing.
	 * @return the output listing
	 */
	public ArrayList<String> getOutputListing(){
		return outputListing;
	}
	
	
}
