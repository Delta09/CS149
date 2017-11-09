import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author 
 * 
 * A ProcessFactory that generates processes such that there are one or more processes
 * that are unable to finish in SIM_TIME_MAX
 * 
 * Processes last int[1,5] seconds
 * Processes use [4, 8, 16, 32] MB of memory and are EVENLY and RANDOMLY distributed
 */
public class ProcessFactory 
{    
	public static final int PROCESS_TIME_MAX = 5;
	public static final int[] PROCESS_SIZES_MB = {5, 11, 17, 31};
	public static final String NAMES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" + //62
			"ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏ00D0ÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö"+ //58
			"ĀāĂăĄąĆćĈĉĊċČčĎďĐđĒēĔĕĖėĘęĚěĜĝĞğĠġĢģĤĥĦħ";//40 = 160 char/unique processes


	/** Generate processes randomly until the total task duration is greater
	 * than SIM_TIME_MAX by a bit so that there is at least one process that 
	 * doesn't finish executing
	 * @param n int : maximum number of processes to generate
	 */
	public static LinkedList<Process> generateProcesses(int n)
	{
		LinkedList<Process> processQueue = new LinkedList<>();

		// used for evenly distributing memory sizes
		Random r = new Random();
		int index = 0;
		int arrival=0;
		int duration = 0;
		int size = 0;
		List<Integer> d = new ArrayList<>();
		List<Integer> s = new ArrayList<>();

		for (int i = 0; i < 32; i++) {
			for (int j = 1; j <= PROCESS_TIME_MAX ; j++) {
				d.add(j);
			}
		}
		Collections.shuffle(d, new Random(10));
		
		
		for (int i = 0; i < 40; i++) {
			for (int j = 0; j < PROCESS_SIZES_MB.length ; j++) {
				s.add(PROCESS_SIZES_MB[j]);
			}
		}
		Collections.shuffle(s, new Random(10));
		
		
		while (processQueue.size() < NAMES.length())
		{
			size = s.get(index);
			duration = d.get(index);
			
			processQueue.addLast(new Process(duration, size, 
					NAMES.charAt(index), arrival, arrival));
			arrival = arrival + r.nextInt(2);

			index ++;
		}

		return processQueue;
	}
}
