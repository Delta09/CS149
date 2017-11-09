import java.util.Random;
import java.util.HashSet;

/**
 * @author 
 * 
 * An object representing a Process which has a runtime, memory size,
 * name, and arrival time
 */
public class Process implements Cloneable
{
    public int duration;
    private int current;
    public int size;
    public double start;
    public int arrival;
    public char name;
    public HashSet<Integer> pagesinMemory;
    
    public Process(int duration, int size, char name, int arrival, double start)
    {
        this.duration = duration;
        this.size = size;
        this.name = name;
        this.arrival = arrival;
        this.start = start;
        this.current = 0;
        pagesinMemory = new HashSet<Integer>();
    }
        
    @Override 
    public Object clone() throws CloneNotSupportedException 
    {
        return new Process(this.duration, this.size, this.name, this.arrival, this.start);
    }
    
    public String toString() {
    	return ("Arrival : " + arrival + "\tName: " + name +  "\tSize: " + size + "\tDuration: " + duration );
    }
    
    public int NextPage(){
		Random rand = new Random();
		int j;
		// Generates a random r between 0 and process size (inclusive)
		int r = rand.nextInt(size);
		// Takes 70% of process size
		double r70percent = size*0.7;
		if (0 <= r && r < r70percent){
			// Generates a random Î”i to be -1, 0 or 1
			int Δi = rand.nextInt(3)-1;
			if (Δi<0 && current==0){
				j = size-1;
			}
			else {
				j = current+ Δi;
			}
			return j;
		}
		else{
			int Δi = rand.nextInt(size-2)+2;
			j = Δi + current;
			if (j > size-1){
				j = j-size;
			}
			return j;
		}
	}
    
    public boolean addPage(int page) {
    	return (pagesinMemory.add(page));
    }
    
    public boolean equals(Object other) {
    	if(this.name == ((Process) other).name)
    		return true;
    	else
    		return false;
    	}
    
}
