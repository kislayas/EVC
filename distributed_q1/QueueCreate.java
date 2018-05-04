package distributed_q1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

public class QueueCreate {
	private final Map<Integer, BlockingQueue<BigInteger>> processToQueueMap;
	private final Map<Integer, Integer> sendSimulation;
	private final Map<Integer, Integer> recSimulation;
	private final Map<Integer, Integer> internalSimulation;
	private static PrintWriter out = null;
	//public WriteFile wf;
	private int eventCount = 0;
	private int count = 0;
	
	public synchronized void graphData(int evcBits) {
		//System.out.println("$$$$$$$$$$$$$$$");
		eventCount++;
		if(count==100){
			
			String content = eventCount + "\t" + evcBits;
			//wf = writeFile();
			System.out.println(content);
			//wf.bw.write("";)
			count=0;
		}
		count++;
	}
	
	public Map<Integer, BlockingQueue<BigInteger>> getProcessToQueueMap() {
		return processToQueueMap;
	}
	public Map<Integer, Integer> getProcessToSendCountMap() {
		return sendSimulation;
	}
	public Map<Integer, Integer> getProcessToRecCountMap() {
		return 	recSimulation;
	}
	public Map<Integer, Integer> getProcessToInternalCountMap() {
		return internalSimulation;
	}

	public QueueCreate(List<Integer> primes){		
		processToQueueMap=new HashMap<>();
		sendSimulation = new HashMap<Integer,Integer>();
		recSimulation = new HashMap<Integer,Integer>();
		internalSimulation = new HashMap<Integer,Integer>();
		primes.forEach(prime->{
			processToQueueMap.put(prime, new ArrayBlockingQueue<BigInteger>(10));
			sendSimulation.put(prime, 0);
			recSimulation.put(prime, 0);
			internalSimulation.put(prime, 0);
		});
	}

	public synchronized void reset(List<Integer> primes) {
		
		primes.forEach(prime->{
			processToQueueMap.put(prime, new ArrayBlockingQueue<BigInteger>(10));
			sendSimulation.put(prime, 0);
			recSimulation.put(prime, 0);
			internalSimulation.put(prime, 0);
		});
	}
}