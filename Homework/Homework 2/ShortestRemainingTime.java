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
	private ArrayList<String> outputListing;
	private int quanta;
        
        public ShortestRemainingTime(PriorityQueue<ProcessSimulator> processQueue){
		this.processQueue = processQueue;
		this.processQueueTrack = new ArrayList<>();
		this.outputListing = new ArrayList<>();
		this.quanta = 0;
	}
        public void runSRT(){
		
		ProcessSimulator currentProcess;
                
		// runs when all processes are not all finished and is less than 100 quantum.
		while (quanta < 100){
                        if (runtimeQueue.peek()==null){
                            
                            while(quanta <= processQueue.peek().getArrivalTime()){
                                quanta++;
                            }
                        }
                        try {
                            while (processQueue.peek().getArrivalTime() <= quanta && processQueue.peek() != null){
                                //System.out.println("Arrival time "+processQueue.peek().getArrivalTime());
                                runtimeQueue.add(processQueue.poll());
                        }
                        } catch (NullPointerException e) {
                            //System.out.println("NullPointer Exceptiono Caught!");
                        }
                            
                        currentProcess = runtimeQueue.poll();
                        
                        currentProcess.setRemainingRunTime(currentProcess.getRemainingRunTime()-1);
                        if(currentProcess.getRemainingRunTime()>0){
                            runtimeQueue.add(currentProcess);
                            processQueueTrack.add(currentProcess);
                        }
                        else{
                            currentProcess.setProcessCompleted(true);
                            processQueueTrack.add(currentProcess);
				currentProcess.setFinishedTime(0, quanta+1);
                                //System.out.println("PID "+currentProcess.getId()+" finished time is "+currentProcess.getFinishedTime());
				currentProcess.setTurnAroundTime(currentProcess.getFinishedTime(), currentProcess.getArrivalTime());
				currentProcess.setWaitingTime(currentProcess.getTurnAroundTime(), currentProcess.getExpectedRunTime());
				currentProcess.setResponseTime(currentProcess.getWaitingTime());
                        }
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
			count++;
			
			if (count == 10){
				timeChart += "\n";
				count = 0;
			}
		
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
