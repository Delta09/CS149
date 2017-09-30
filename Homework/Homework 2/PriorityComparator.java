import java.util.Comparator;

/**
 * Models a PriorityComparator class to checks the priority between 2 processes
 * in ascending order.
 * @author yen_my_huynh
 */
public class PriorityComparator implements Comparator<ProcessSimulator>{
	/**
	 * Overrides the compare method to order the priority between 2 processes.
	 * @param p1 the first process
	 * @param p2 the second process
	 */
	public int compare(ProcessSimulator p1, ProcessSimulator p2){
		return p1.getPriority() - p2.getPriority();
	}
}
