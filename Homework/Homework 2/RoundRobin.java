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
	protected ArrayList<ProcessSimulator> TimeTrack;
	private ArrayList<String> outputListing;
	private LinkedList<ProcessSimulator> waiting;
	private LinkedList<ProcessSimulator> toAdd;
	private int quanta;
	private int quantumSlice;


	/**
	 * Constructor for the Round Robin scheduler
	 * @param processQueue queue of processeses to be execcuted
	 * @param quantumSlice the time slice you would  like to 
	 */
	public RoundRobin(PriorityQueue<ProcessSimulator> processQueue, int quantumSlice) {
		this.processQueue = processQueue;
		this.processQueueTrack = new ArrayList<>();
		this.TimeTrack = new ArrayList<>();
		this.outputListing = new ArrayList<>();
		this.waiting = new LinkedList<ProcessSimulator>();
		this.toAdd = new LinkedList<ProcessSimulator>();
		this.quanta = 0;
		this.quantumSlice = quantumSlice;
	}

	/**
	 * Runs RR scheduling algorithm.
	 * Assumes that process blocks the rest of the quantum slice once completed
	 */
	public void runRR(){
		ProcessSimulator currentProcess, incomingProcess;
		ProcessSimulator idleProcess = new ProcessSimulator("Idle", 0.0f, 0.0f, 0, false); 
		waiting = new LinkedList<ProcessSimulator>();
		
		// runs when all processes are not all finished and is less than 100 quantum.
		while (quanta < 100){
			if (processQueue.size() > 0 && processQueue.peek().getArrivalTime() <= quanta ) {
				
				while(processQueue.size() > 0 && processQueue.peek().getArrivalTime() <= quanta) {
					incomingProcess = processQueue.poll();					//incoming process = new guy

						toAdd.add(incomingProcess);
				}
				
				if (!toAdd.isEmpty()) {				
					waiting.addAll(0, toAdd); 	//add to the front of the waiting queue
					toAdd.clear();				// clear the LL so that new values can be added 
				}
			}

			// if the waiting is not empty, time to process everything in the queue
			if (!waiting.isEmpty()) {
				currentProcess = waiting.pollFirst();
				
				currentProcess.setVisitedQuanta(quanta);
				
				//check to see if the process has been visited before. This is to collect the response time statistic
				if (!currentProcess.isVisited()) {
					currentProcess.setFirstQuanta(quanta);
					currentProcess.setVisited(true);
				}
				
				TimeTrack.add(currentProcess);  //add to time track to print time chart

				currentProcess.setRemainingRunTime(currentProcess.getRemainingRunTime()-quantumSlice); // reduce the remaining time by 1
				quanta+= quantumSlice; 
				
				

				if (currentProcess.getRemainingRunTime() > 0) {
					waiting.add(currentProcess); //if incomplete then add back to waiting queu
				}

				else{
					//quanta += currentProcess.getRemainingRunTime();     if the process does not block till the end of the slice, readjust
					currentProcess.setFinishedTime(0, quanta);				
					currentProcess.setTurnAroundTime(currentProcess.getFinishedTime(), currentProcess.getArrivalTime());
					currentProcess.setWaitingTime(currentProcess.getTurnAroundTime(), currentProcess.getExpectedRunTime());
					currentProcess.setResponseTime( currentProcess.getFirstQuanta());
					processQueueTrack.add(currentProcess);				//add it to the completed process queue
					
				}
			}

			else {
				
				idleProcess.setVisitedQuanta(quanta);	//set this quanta to idle
				TimeTrack.add(idleProcess);					
				quanta+= 1;								
			}

		}


		printStatistics(processQueueTrack); //print out the statistics 
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
		float throughput = (float) processQueueTrack.size()/ 99;

		String track = new String();
		int count=0;
		for (ProcessSimulator p : TimeTrack){
			track += ("Q(" + p.getVisitedtQuanta() +")=" + p.getId() + ", ");
			count++;
			if (count%10 == 0) {
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
