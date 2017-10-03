

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class HighestPriorityFirstNP {

	// the four priority queues
	private PriorityQueue<ProcessSimulator> pQ1;
	private PriorityQueue<ProcessSimulator> pQ2;
	private PriorityQueue<ProcessSimulator> pQ3;
	private PriorityQueue<ProcessSimulator> pQ4;
	private ArrayDeque<ProcessSimulator> processQueue;
	private ArrayList<ProcessSimulator> processQueueTrack;
	private ArrayList<PriorityQueue<ProcessSimulator>> processList;
	public ArrayList<String> outputListing;
	private int quanta;

	private float waitingTime;
	private float turnAroundTime;
	private float responseTime;

	// save the processes created in the tester
	

	/**
	 * Constructor for HPF-NPE, with a ArrayDeque of processes to be scheduled
	 * passed in
	 * 
	 * @param processQueue the ArrayDeque of different processes inside
	 */
	public HighestPriorityFirstNP(ArrayDeque<ProcessSimulator> processQueue) {
		// create the priority queues
		pQ1 = new PriorityQueue<ProcessSimulator>(new ArrivalTimeComparator());
		pQ2 = new PriorityQueue<ProcessSimulator>(new ArrivalTimeComparator());
		pQ3 = new PriorityQueue<ProcessSimulator>(new ArrivalTimeComparator());
		pQ4 = new PriorityQueue<ProcessSimulator>(new ArrivalTimeComparator());
		this.processQueue = processQueue;
		processQueueTrack = new ArrayList<ProcessSimulator>();
		this.processList = new ArrayList();
		this.processList.add(pQ1);
		this.processList.add(pQ2);
		this.processList.add(pQ3);
		this.processList.add(pQ4);
		outputListing = new ArrayList<String>();

		quanta = 0;
	}

	/**
	 * Runs HPFNP schedule algorithm.
	 */
	public void runHPFNP() {
		while (!processQueue.isEmpty()) {
			// separates processes and adds them to different queues depend on their priority.
			// 1 = first, 2 = second ..., 4 = fourth.
			if (processQueue.peek().getPriority() == 1) {
				pQ1.add(processQueue.pop());
			} else if (processQueue.peek().getPriority() == 2) {
				pQ2.add(processQueue.pop());
			} else if (processQueue.peek().getPriority() == 3) {
				pQ3.add(processQueue.pop());
			} else
			{
				pQ4.add(processQueue.pop());
			}
		}
		increaseWaitedQuantum();
		
		while (quanta < 100) {
			increasePriority();
			// runs as long as quanta is less than 100, 
			// runs processes by priority. If the first priority queues has no more processes 
			// to run, move onto the next priority queues.
			if (!pQ1.isEmpty()) {
				runProcess(pQ1.poll());
			} else if (!pQ2.isEmpty()) {
				runProcess(pQ2.poll());
			} else if (!pQ3.isEmpty()) {
				runProcess(pQ3.poll());
			} else if (!pQ4.isEmpty()) {
				runProcess(pQ4.poll());
			}
		} 
		printStatistics(processQueueTrack);
	}

	/**
	 * Runs first priority queue, and so on if time allows.
	 * @param currentProcess the current process
	 */
	private void runProcess(ProcessSimulator currentProcess) {
		waitingTime = currentProcess.getWaitingTime();
		turnAroundTime = currentProcess.getTurnAroundTime();
		responseTime = currentProcess.getResponseTime();
		// every time a service is performing, increases quanta and puts a completed process
		// inside the completed queue track.
		if (currentProcess.getExpectedRunTime() > 0) {
			currentProcess.setResponseTime(quanta - currentProcess.getArrivalTime());
			quanta += currentProcess.getExpectedRunTime();
			processQueueTrack.add(currentProcess);
			currentProcess.setFinishedTime(quanta, currentProcess.getExpectedRunTime());
			currentProcess.setTurnAroundTime(currentProcess.getFinishedTime(), currentProcess.getArrivalTime());
			currentProcess.setWaitingTime(currentProcess.getTurnAroundTime(), currentProcess.getExpectedRunTime());

		}
		// add times after a process is finished and added into the process queue track.
		if (currentProcess.getExpectedRunTime() <= 1) {
			responseTime += currentProcess.getExpectedRunTime();
		}
		else {
			responseTime += 1;
		}
		waitingTime += currentProcess.getArrivalTime() - currentProcess.getExpectedRunTime();
		turnAroundTime += (quanta - currentProcess.getArrivalTime());
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
		float averageTurnAroundTime = turnAroundTimeTotal/ processQueueTrack.size();
		float averageWaitingTime = waitingTimeTotal/ processQueueTrack.size();
		float averageResponseTime = responseTimeTotal/ processQueueTrack.size();
		// casts throughtput to avoid truncating
		float throughput = (float) processQueueTrack.size()/ 99;
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
