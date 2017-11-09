import java.util.LinkedList;
import java.util.Random;

public class RP extends Pager
{
	public RP(LinkedList<memoryPage> memory, LinkedList<Process> process) {
		super(memory, process);
	}

	@Override
	public int run(){
            Random rand = new Random();
            int remove = rand.nextInt(99);
            return remove;
	}

	@Override
	public String getName() {
		return "Random Pick";
	};
}
