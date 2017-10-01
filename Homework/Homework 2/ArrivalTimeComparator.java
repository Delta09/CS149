import java.util.Comparator;

/**
 * Models an ArrivalTimeComparator class to checks arrival time between 2 processes
 * in ascending order.
 * @author yen_my_huynh
 *
 */
public class ArrivalTimeComparator implements Comparator<ProcessSimulator>{
	/**
	 * Overrides the compare method to order the arrival time between 2 processes.
	 * @param p1 the first process
	 * @param p2 the second process
	 */
	public int compare(ProcessSimulator p1, ProcessSimulator p2){
		
		float arrivalTimeDifference = p1.getArrivalTime() - p2.getArrivalTime();
		int idOrder = p1.getId().compareTo(p2.getId());
		if (arrivalTimeDifference > 0 ){
			return 1;
		}
		else if (arrivalTimeDifference < 0){
			return -1;
		}
		else{
			return idOrder;
		}
	}
}
