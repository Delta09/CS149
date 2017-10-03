import java.util.Comparator;

	/**
	 * Models an ArrivalTimeComparator class to checks arrival time between 2 processes
	 * in ascending order.
	 * @author daany
	 *
	 */
	public class IdComparator implements Comparator<ProcessSimulator>{
		/**
		 * Overrides the compare method to order the arrival time between 2 processes.
		 * @param p1 the first process
		 * @param p2 the second process
		 */
		public int compare(ProcessSimulator p1, ProcessSimulator p2){
			return  p1.getId().compareTo(p2.getId());
		}
	}
