import java.util.*;

/**
 * Models a ShortestJobFirst class to serve processes in shortest run time first.
 * @author sandeepsamra
 * @author Pulkit Agrawal
 * @author yen_my_huynh
 */
public class ShortestJobFirst
{
	private ArrayList<ProcessSimulator> processQueue;
	private ArrayList<ProcessSimulator> processQueueTrack; 
	public ArrayList<String> outputListing;
	protected ArrayList<String> TimeTrack;
	
	private int quanta; 
	
	/**
	 * Constructs objects for ShortestJobFirst class.
	 * @param processQueues the priority queue list of processes (size: 100)
	 */
	public ShortestJobFirst(ArrayList<ProcessSimulator> processQueue)
	{
		this.processQueue = processQueue;
		this.processQueueTrack = new ArrayList<ProcessSimulator>();
		this.outputListing = new ArrayList<String>();
		this.TimeTrack = new ArrayList<>();
		this.quanta = 0;
	}
	
	/**
	 * Runs SJF schedule algorithm.
	 */
	public void runSJF() {	
		boolean processFinished = false;
		boolean firstProcess =  true;
		ProcessSimulator currentProcess;
		
		// same as first come first served, except sort by the ExpectedRunTimeComparator.
		Comparator<ProcessSimulator> comp = new ExpectedRunTimeComparator();
		Collections.sort(processQueue, comp);
		
		int count = 0;
		// runs when all processes are not all finished and is less than 100 quantum.
		while (!processFinished && quanta < 100){
			currentProcess = processQueue.get(count);
			
			if (!currentProcess.isVisited()) {
				currentProcess.setFirstQuanta(quanta);
				currentProcess.setVisited(true);
			}
			
			// runs and waits until the process arrives and increases quanta during the wait.
			while (currentProcess.getArrivalTime() > quanta){
				quanta++;
				TimeTrack.add("Idle");
			}
			
			// Runs the current process until the end of expected run time.
			// if 5s is given, then runs until reaches 5s.
			int runTime = (int) currentProcess.getExpectedRunTime();
			int i = 0;
			while (i != runTime){
				i++;
				TimeTrack.add(currentProcess.getId());
				if (i == runTime){
					// when a process is completed, adds it to the ArrayList
					currentProcess.setProcessCompleted(true);
					processQueueTrack.add(currentProcess);
				}
			}
			// resets the time for the next process.
			runTime = 0;
			
			// if it's the first process, then run. After that, sets it to false, so other processes 
			// after it can run.
			if (firstProcess == true){ 
				currentProcess.setFinishedTime(currentProcess.getArrivalTime(), currentProcess.getExpectedRunTime());
				currentProcess.setTurnAroundTime(currentProcess.getFinishedTime(), currentProcess.getArrivalTime());
				currentProcess.setWaitingTime(currentProcess.getTurnAroundTime(), currentProcess.getExpectedRunTime());
				currentProcess.setResponseTime(currentProcess.getFirstQuanta());
				firstProcess = false;
			}	
			// processes after the first one
			else {
				currentProcess.setFinishedTime(quanta, currentProcess.getExpectedRunTime());
				currentProcess.setTurnAroundTime(currentProcess.getFinishedTime(), currentProcess.getArrivalTime());
				currentProcess.setWaitingTime(currentProcess.getTurnAroundTime(), currentProcess.getExpectedRunTime());
				currentProcess.setResponseTime(currentProcess.getFirstQuanta());
			}
			
			// sets quanta as the new arrival time for the next process.
			quanta = (int) currentProcess.getFinishedTime();
			
			// loops through the PriorityQueue list to check if they're done or not.
			// Not all processes will run since the simulator only has 100 quantum
			for (ProcessSimulator p : processQueue) {
				processFinished = p.isProcessCompleted();
			}
			count++;
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

		for (ProcessSimulator p : processQueueTrack){
			turnAroundTimeTotal += p.getTurnAroundTime();
			waitingTimeTotal += p.getWaitingTime();
			responseTimeTotal += p.getResponseTime();
			
			outputListing.add(p.toString());
		}
		// gathers up all the statistics
		float averageTurnAroundTime = turnAroundTimeTotal/ processQueueTrack.size();
		float averageWaitingTime = waitingTimeTotal/ processQueueTrack.size();
		float averageResponseTime = responseTimeTotal/ processQueueTrack.size();
		// casts throughtput to avoid truncating
		float throughput = (float) processQueueTrack.size()/ 100;

		String track = new String();
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
