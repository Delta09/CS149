///**
// * Models a ProcessSimulator class to give the current process attributes.
// * @author yen_my_huynh
// *
// */
public class ProcessSimulator {
	private float arrivalTime;
	private float expectedRunTime;
	private int priority;
	private String id;

	private float remainingRunTime;
	private boolean processCompleted;
	private float turnAroundTime;
	private float waitingTime;
	private float responseTime;
	private float finishedTime;
	private int firstQuanta;
	private boolean visited;
	private int visitedQuanta;	 
	private int waitedQuantum;



	/**
	 * Constructs a ProcessSimulator with id, an arrival time, expected run time, priority
	 * and status.
	 * @param id the id
	 * @param arrivalTime the arrival time
	 * @param expectedRunTime the expected run time
	 * @param priority the priority
	 * @param processCompleted marks status 
	 */
	public ProcessSimulator(String id, float arrivalTime, float expectedRunTime, int priority, boolean processCompleted) {
		this.arrivalTime = arrivalTime;
		this.expectedRunTime = expectedRunTime;
		this.priority = priority;
		this.id = id;
		this.processCompleted = processCompleted;

		remainingRunTime = expectedRunTime;
		turnAroundTime = 0;
		waitingTime = 0;
		responseTime = 0;
		finishedTime = 0;
		waitedQuantum = 0;
		visited = false;
	}

	/**
	 * Gets the arrival time of the current process.
	 * @return the arrival time
	 */
	public float getArrivalTime(){
		return arrivalTime;
	}

	/**
	 * sets the current process's id.
	 * @return the process's id
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * Gets the current process's expected run time.
	 * @return the expected run time
	 */
	public float getExpectedRunTime(){
		return expectedRunTime;
	}

	/**
	 * Gets the current process's priority.
	 * @return the priority
	 */
	public int getPriority(){
		return priority;
	}

	/**
	 * Gets the current process's remaining run time.
	 * @return the remaining run time
	 */
	public float getRemainingRunTime(){
		return remainingRunTime;
	}

	/**
	 * Sets remaining run time of the current process.
	 * @param remainingRunTime the remaining run time
	 */
	public void setRemainingRunTime(float remainingRunTime){
		this.remainingRunTime = remainingRunTime;
	}

	/**
	 * Gets the id of the current process.
	 * @return the id
	 */
	public String getId(){
		return id;
	}
	/**
	 * Gets the status of the current process.
	 * @return true if ready to run, if not, false
	 */
	public boolean isProcessCompleted(){
		return processCompleted;
	}

	/**
	 * Sets the status of the current process.
	 * @param processReady true if ready, if not, false
	 */
	public void setProcessCompleted(boolean processCompleted){
		this.processCompleted = processCompleted;
	}

	/**
	 * Sets turn around time as the difference between finished time and the
	 * arrival of the current process.
	 * @param finishedTime the finished time
	 * @param arrivalTime the arrival time
	 */
	public void setTurnAroundTime(float finishedTime, float arrivalTime){
		turnAroundTime = finishedTime - arrivalTime;
	}

	/**
	 * Gets the turn around time of the current process.
	 * @return the turn around time
	 */
	public float getTurnAroundTime(){
		return turnAroundTime;
	}
	/**
	 * Sets wait time as the difference between the turn around time and run
	 time.
	 * @param turnAroundTime turn around time of the current process
	 * @param runTime the run time of the current process
	 */
	public void setWaitingTime(float turnAroundTime, float runTime){
		waitingTime = turnAroundTime - runTime;
	}
	/**
	 * Gets the wait time of the current process.
	 * @return the wait time
	 */
	public float getWaitingTime(){
		return waitingTime;
	}

	/**
	 * Sets a new response time for the current process.
	 * @param responseTime the response time
	 */
	public void setResponseTime(float responseTime){
		this.responseTime = responseTime;
	}

	/**
	 * Gets the response time of the current process.
	 * @return the response time
	 */
	public float getResponseTime(){
		return responseTime;
	}

	/**
	 * Sets finished time as the sum of the arrival time and run time of the
	 current process.
	 * @param arrivalTime the arrival time
	 * @param runTime the run time
	 */
	public void setFinishedTime(float arrivalTime, float runTime){
		finishedTime = arrivalTime + runTime;
	}

	/**
	 * Gets the finished time of the current process.
	 * @return the finished time
	 */
	public float getFinishedTime(){
		return finishedTime;
	}

	/**
	 * Returns the first quanta that this process occurs at
	 * @return the quanta time
	 */
	public int getFirstQuanta() {
		return firstQuanta;
	}

	/**
	 * sets the first quanta that this process occurs at
	 * @param quanta visited at
	 */
	public void setFirstQuanta(int quanta) {
		this.firstQuanta = quanta;
	}

	/**
	 * Returns the quanta that this process visited at
	 * @return the quanta time
	 */
	public int getVisitedtQuanta() {
		return visitedQuanta;
	}

	/**
	 * sets the quanta that this process is visited at
	 * @param quanta time
	 */
	public void setVisitedQuanta(int quanta) {
		this.visitedQuanta = quanta;
	}

	/**
	 * Overrides string representation into certain format.
	 * @return the new representation
	 */
	public String toString(){
		return "Process id: " + id + "\t\tArrival Time: " + arrivalTime +
				"\tExpected Run Time: " + expectedRunTime + "\t\tPriority: " + priority + "\n";
	}

	/**
	 * Boolean value to see if the process has been visited before
	 * @return the new representation
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * sets the value to visited as true or false
	 * @param b is the boolean value
	 */
	public void setVisited(boolean b) {
		this.visited = b;
	}	

 /**
	  * Gets the waited quantum.
	  * @return the waited quantum
	  */
	  public float getWaitedQuantum() {
          return waitedQuantum;
	  }

	  /**
	   * Increases the waited quantum.
	   */
	  public void increaseWaitedQuantum(){
		  waitedQuantum++; 
	  }
   
	  /**
	   * Increases the priority of a process if waited for more than 5 quantum.
	   */
	  public void increasePriority(){
		  if(getPriority() > 1){
		   priority--;
		  }
		  waitedQuantum = 0;
	   }
}
