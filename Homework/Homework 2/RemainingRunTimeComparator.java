import java.util.Comparator;

/**
 * Models a RemainingRunTime class to check the remaining run time between 2
 * processes in ascending order.
 * @author yen_my_huynh
 *
 */
public class RemainingRunTimeComparator implements Comparator<ProcessSimulator> {
	/**
	 * Overrides the compare method to order the remaining run time between 2
	 * processes.
	 * @param p1 the first process
	 * @param p2 the second process
	 */
	public int compare(ProcessSimulator p1, ProcessSimulator p2) {
		float remainingRunTimeDifference = p1.getRemainingRunTime() - p2.getRemainingRunTime();
		int idOrder = p1.getId().compareTo(p2.getId());
		if (remainingRunTimeDifference > 0) {
			return 1;
		} else if (remainingRunTimeDifference < 0) {
			return -1;
		} else {
			return idOrder;
		}
	}

}
