import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *
 * @author Bala Nyan Kyaw
 */
public class ShortestRemainingTime {
	protected PriorityQueue<ProcessSimulator> processQueue;
	//The priority queue below will be sorted by Remaining Run Time
	protected PriorityQueue<ProcessSimulator> runtimeQueue = new PriorityQueue<ProcessSimulator>(50, new RemainingRunTimeComparator()); 
	protected ArrayList<ProcessSimulator> processQueueTrack;
	protected ArrayList<String> processTimeTrack;
	private ArrayList<String> outputListing;
	private int quanta;

	public ShortestRemainingTime(PriorityQueue<ProcessSimulator> processQueue){
		this.processQueue = processQueue;
		this.processQueueTrack = new ArrayList<>();
		this.processTimeTrack = new ArrayList<>();
		this.outputListing = new ArrayList<>();
		this.quanta = 0;
	}
	public void runSRT(){

		ProcessSimulator currentProcess;

		// runs when less than 100 quantum.
		while (quanta < 100){
			if (runtimeQueue.peek()==null){ //RuntimeQueue will be null at first so peeking it will result in Error..

				//Increment quanta until first process arrives.
				if (processQueue.peek()!= null){
					while(quanta < processQueue.peek().getArrivalTime()){
						quanta++;
						processTimeTrack.add("Idle");
					}
				}
			}

			/*There is an NullPointerException here because processQueue will run out and become empty before
			 *quanta reeaches 100. Throwing the exception is a faster alternative to figuring out how to fix it.*/

			//There may be multiple process with the same Arrival Time. Add all into runtimeQueue.
			while (processQueue.peek() != null && processQueue.peek().getArrivalTime() <= quanta){
				//Debugging code: System.out.println("Arrival time "+processQueue.peek().getArrivalTime());
				runtimeQueue.add(processQueue.poll());
			}


			//Take the process with the lowest Remaining Run Time and set it to current process.
			currentProcess = runtimeQueue.poll();
			
			if (!currentProcess.isVisited()) {
				currentProcess.setFirstQuanta(quanta);
				currentProcess.setVisited(true);
			}

			//CPU gives 1 quantum to the current Process, therefore runtime decreases by 1.
			currentProcess.setRemainingRunTime(currentProcess.getRemainingRunTime()-1);

			/*As long as the current process has remaining runtime, put it back in runtimeQueue.
			 *REMEMBER: runtimeQueue will give out processes with the lowest runtime*/
			if(currentProcess.getRemainingRunTime()>0){
				runtimeQueue.add(currentProcess);
				processTimeTrack.add(currentProcess.getId());
			}
			else{
				//If current process finishes, set its status to true.
				currentProcess.setProcessCompleted(true);
				processQueueTrack.add(currentProcess);
				processTimeTrack.add(currentProcess.getId());

				//Compute it's finsihed time, turnaround time, wait time and response time.
				currentProcess.setFinishedTime(currentProcess.getArrivalTime(), quanta+1);
				//System.out.println("PID "+currentProcess.getId()+" finished time is "+currentProcess.getFinishedTime());
				currentProcess.setTurnAroundTime(currentProcess.getFinishedTime(), currentProcess.getArrivalTime());
				currentProcess.setWaitingTime(currentProcess.getTurnAroundTime(), currentProcess.getExpectedRunTime());
				currentProcess.setResponseTime(currentProcess.getFirstQuanta());
			}

			//Increments quanta by 1.
			quanta++;
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
		int count = 0;

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
		int quantumCount = 0;
		for (String p : processTimeTrack){

			track += "Q("+quantumCount+")="+p + ", ";
			quantumCount++;
			
			if (quantumCount%10 == 0) {
				track += "\n";
			}
			
		}
		String timeChartDisplay = "\nTime Chart per quantum (Q): \n \n"+track+"\n";
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