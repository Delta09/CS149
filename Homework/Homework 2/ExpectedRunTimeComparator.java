import java.util.Comparator;

/**
 * Models an ExpectedRunTime class to check the expected run time between 2 processes
 * in ascending order.
 * @author yen_my_huynh
 *
 */
public class ExpectedRunTimeComparator implements Comparator<ProcessSimulator> {
	/**
	 * Overrides the compare method to order the expected run time between 2 processes.
	 * @param p1 the first process
	 * @param p2 the second process
	 */
	public int compare(ProcessSimulator p1, ProcessSimulator p2){
		float expectedRunTimeDifference = p1.getExpectedRunTime() - p2.getExpectedRunTime();
		int idOrder = p1.getId().compareTo(p2.getId());
		if (expectedRunTimeDifference > 0){
			return 1;
		}
		else if (expectedRunTimeDifference < 0){
			return -1;
		}
		else{
			return idOrder;
		}
	}
}
