package distributed_q1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.math.BigInteger;

public class QueueCreate {
	private final Map<Integer, BlockingQueue<BigInteger>> processToQueueMap;
	private final Map<Integer, Integer> sendSimulation;
	private final Map<Integer, Integer> recSimulation;
	private final Map<Integer, Integer> internalSimulation;

	
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
		sendSimulation = new HashMap<>();
		recSimulation = new HashMap<>();
		internalSimulation = new HashMap<>();
		primes.forEach(prime->{
			processToQueueMap.put(prime, new ArrayBlockingQueue<BigInteger>(10));
			sendSimulation.put(prime, 0);
			recSimulation.put(prime, 0);
			internalSimulation.put(prime, 0);

		});
	}
	
	
}