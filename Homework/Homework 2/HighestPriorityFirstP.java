import java.util.*;

/**
 * A class representing highest priority first algorithm that is preemptive
 * In this algorithm, a process with the highest priority is ran first but the scheduler
 * switches between different processes using a round robin approach
 * 
 * Preemptive -> Once a process is on the CPU, it does not relinquish the CPU until the current 
 * CPU burst has been completed
 */
public class HighestPriorityFirstP{

	//4 priority queues for different priorities
	private PriorityQueue<ProcessSimulator> pQ1;
	private PriorityQueue<ProcessSimulator> pQ2;
	private PriorityQueue<ProcessSimulator> pQ3;
	private PriorityQueue<ProcessSimulator> pQ4;
	private ArrayDeque<ProcessSimulator> processQueue; //an array with all the processes
	private ArrayList<PriorityQueue<ProcessSimulator>> processList;
	protected ArrayList<ProcessSimulator> TimeTrack;
	private LinkedList<ProcessSimulator> toAdd;
	private int quanta; //current quanta
	private float waitTime = 0; //wait time for a process
	private float turnAroundTime = 0; //turnAround time for a process
	private float responseTime = 0; //responseTime for a process

	//round robin instance variables
	private ArrayList<ProcessSimulator> processQueueTrack;
	private LinkedList<ProcessSimulator> waiting;
	public ArrayList<String> outputListing;

	public HighestPriorityFirstP(ArrayDeque<ProcessSimulator> tprocessQueue) {
		// create the priority queues
		pQ1 = new PriorityQueue<ProcessSimulator>(new ArrivalTimeComparator());
		pQ2 = new PriorityQueue<ProcessSimulator>(new ArrivalTimeComparator());
		pQ3 = new PriorityQueue<ProcessSimulator>(new ArrivalTimeComparator());
		pQ4 = new PriorityQueue<ProcessSimulator>(new ArrivalTimeComparator());
		outputListing = new ArrayList<String>();
		this.TimeTrack = new ArrayList<>();
		
		this.toAdd = new LinkedList<ProcessSimulator>();
		
		//initialize instance variables
		this.processQueue = tprocessQueue;
		this.processQueueTrack = new ArrayList<ProcessSimulator>();
		this.processList = new ArrayList();
		this.processList.add(pQ1);
		this.processList.add(pQ2);
		this.processList.add(pQ3);
		this.processList.add(pQ4);
		quanta = 0; 
		
	}

	/**
	 * A method to put the processes in the right queue
	 * and run them
	 */
	public void runHPFP() {
		while (quanta < 100) {
			while (!processQueue.isEmpty()) {
				// separates processes and adds them to different queues depend
				// on
				// their priority.
				// 1 = first, 2 = second ..., 4 = fourth.
				if (processQueue.peek().getPriority() == 1) {
					pQ1.add(processQueue.pop());
				} else if (processQueue.peek().getPriority() == 2) {
					pQ2.add(processQueue.pop());
				} else if (processQueue.peek().getPriority() == 3) {
					pQ3.add(processQueue.pop());
				} else {
					pQ4.add(processQueue.pop());
				}
			}
			increaseWaitedQuantum();
			// if the list is all separated in specific priority order, then
			// allows to retrieves processes from first priority, second... fourth.
			// prevent going over quanta by increment it.
			if (pQ1.isEmpty() && pQ2.isEmpty() && pQ3.isEmpty() && pQ4.isEmpty()) {
				quanta += 1;
			}
			
			else { // else the queue's are ready to run using RR
				increasePriority();
				if (!pQ1.isEmpty()) {
					runProcess(pQ1.poll(), pQ1);
				} else if (!pQ2.isEmpty()) {
					runProcess(pQ2.poll(), pQ2);
				} else if (!pQ3.isEmpty()) {
					runProcess(pQ3.poll(), pQ3);
				} else if (!pQ4.isEmpty()) {
					runProcess(pQ4.poll(), pQ4);
				}
			}
		}
		quanta++;
		printStatistics(processQueueTrack);
	}

/**
 * This method runs a highest priority first PE using Round Robin
 * Loops over a given priority queue and gives each process a time based on the RR algorithm
 * @param currentProcess - the process we're running
 * @param currentQueue - the given queue in which the current process is
 */
private void runProcess(ProcessSimulator currentProcess, PriorityQueue<ProcessSimulator> currentQueue) {
		ProcessSimulator incomingProcess;// = currentQueue.poll();
		waiting = new LinkedList<ProcessSimulator>();
		ProcessSimulator idleProcess = new ProcessSimulator("Idle", 0.0f, 0.0f, 0, false); 

		// runs when all processes are not all finished and is less than 100
		// quantum.
		while (quanta < 100){ 
		if (currentQueue.size() > 0 && currentQueue.peek().getArrivalTime() <= quanta) {
			while(currentQueue.size() > 0 && currentQueue.peek().getArrivalTime() <= quanta) {
				incomingProcess = currentQueue.poll();					//incoming process = new guy

					toAdd.add(incomingProcess);
			}
			
			if (!toAdd.isEmpty()) {				
				waiting.addAll(0, toAdd);
				toAdd.clear();
			}

		}

		else if (waiting.isEmpty()) {
			quanta++;
		}
		
		if (!waiting.isEmpty()) {
			currentProcess = waiting.pollFirst();
			
			currentProcess.setVisitedQuanta(quanta);
			
			if (!currentProcess.isVisited()) {
				currentProcess.setFirstQuanta(quanta);
				currentProcess.setVisited(true);
			}
			
			TimeTrack.add(currentProcess);

			currentProcess.setRemainingRunTime(currentProcess.getRemainingRunTime()-1);
			quanta+= 1;
			
			

			if (currentProcess.getRemainingRunTime() > 0) {
				waiting.add(currentProcess);
			}

			else{
				//quanta += currentProcess.getRemainingRunTime();
				currentProcess.setFinishedTime(0, quanta);
				currentProcess.setTurnAroundTime(currentProcess.getFinishedTime(), currentProcess.getArrivalTime());
				currentProcess.setWaitingTime(currentProcess.getTurnAroundTime(), currentProcess.getExpectedRunTime());
				currentProcess.setResponseTime( currentProcess.getFirstQuanta());
				processQueueTrack.add(currentProcess);
				
			}




		}

		else {
			
			idleProcess.setVisitedQuanta(quanta);
			TimeTrack.add(idleProcess);
			quanta+= 1;
		}


		}
}

/**
 * If a process waits for more than 5 quantum, then move it out of current queue
 * and bumps it up to a higher priority queue.
 */
private void increasePriority() {
	// Increase priority if a process waited for 5 quantum.
	for (int i = 0; i < 4; i++) {
		ArrayList<ProcessSimulator> temp = new ArrayList<>();
		for (ProcessSimulator p : processList.get(i))
			temp.add(p);
		for (ProcessSimulator p : temp) {
			if (p.getWaitedQuantum() >= 5) {
				p.increasePriority();
				processList.get(i).add(p);
				processList.get(i).remove(p);
			}
		}
	}
}

/**
 * For every quanta a process waits, increments it.
 */
private void increaseWaitedQuantum() {
	for (int i = 0; i < 4; i++) {
		PriorityQueue<ProcessSimulator> temp2 = processList.get(i);
		for (ProcessSimulator p : temp2) {
			p.increaseWaitedQuantum();
		}
	}
}
/**
 * Prints out the HFFP scheduling algorithm statistics: average turn around time,
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
	float averageTurnAroundTime = turnAroundTimeTotal/ processQueueTrack.size();
	float averageWaitingTime = waitingTimeTotal/ processQueueTrack.size();
	float averageResponseTime = responseTimeTotal/ processQueueTrack.size();
	// casts throughtput to avoid truncating
	float throughput = (float) processQueueTrack.size()/ 99;

	String track = new String();
	int quanta=0;
	for (ProcessSimulator p : TimeTrack){
		track += ("Q(" + p.getVisitedtQuanta() +")="+p.getId() + ", ");
		quanta++;
		if (quanta%10 == 0) {
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
	public ArrayList<String> getOutputListing() {
		return outputListing;
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
