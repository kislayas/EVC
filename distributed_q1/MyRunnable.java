
package distributed_q1;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import com.google.common.math.BigIntegerMath;

/**
 *
 * @author kislaya
 */
public class MyRunnable implements Runnable

{
	private int threadId;
	private QueueCreate queueCreate;
	private List<Integer> primes;
	private Random random;
	//private PrintWriter out;
	//private WriteFile writeFile2;
	private List<Integer> allPrimes;


	
	public MyRunnable(List<Integer> allPrimes, int id, List<Integer> tempPrimes, QueueCreate queueCreate) {
		this.allPrimes = allPrimes;
		this.threadId = id;
		this.queueCreate = queueCreate;
		this.primes = tempPrimes;
		this.random = new Random();
		//this.out = out;
		//this.writeFile2 = writeFile2;
	}

	private int getRandomPrime() {
		return primes.get(random.nextInt(primes.size()));
	}

	@Override
	public void run() {
		//System.out.println("Current Thread id is : " + this.threadId);
		eventProcess();
		// some code to run in parallel
		// this could also be another class that implements Runnable
	}

	private static BigInteger gcd(BigInteger a, BigInteger b) {
	    while (b.compareTo(BigInteger.valueOf(0)) == 1) {
	        BigInteger temp = b;        
	        b = a.mod(b);
	        a = temp;
	    }
	    return a;
	}
	private static BigInteger lcm(BigInteger a, BigInteger b) {
	    return a.multiply((b.divide(gcd(a, b))));
	}
			
	private void eventProcess() {
		// BlockingQueue<BigInteger>myQueue=queueCreate.getProcessToQueueMap().get(threadId);
		//int sendCount = queueCreate.getProcessToSendCountMap().get(threadId);
		//int recCount = queueCreate.getProcessToRecCountMap().get(threadId);
		//int internalCount = queueCreate.getProcessToInternalCountMap().get(threadId);
		BigInteger evc = new BigInteger("1");

		try {
			while (true) {
				int s = 32*allPrimes.size();
				// System.out.println("Evc is :"+evc);
				// System.out.println("its log is :" + BigIntegerMath.log2(evc, RoundingMode.DOWN));

				int randomPrime = getRandomPrime();
				/*if(queueCreate.eventCount==primes.size()/5 || queueCreate.eventCount==0) {
					
				}
				if(BigIntegerMath.log2(evc, RoundingMode.DOWN) % 5 == 0 || queueCreate.getEventCount() % 5==0) {
					try {
						System.out.println("((((((((((((");
						System.out.println(BigIntegerMath.log2(evc, RoundingMode.DOWN));
						String content = queueCreate.getEventCount() + " " + BigIntegerMath.log2(evc, RoundingMode.DOWN) + "\n";
						System.out.println(content);
						out.println(content);
					} catch (Exception e) {
	
						e.printStackTrace();
	
					} 
				}*/
				evc = evc.multiply(BigInteger.valueOf(threadId));
				//internalCount++;
				queueCreate.getProcessToSendCountMap().put(threadId,queueCreate.getProcessToInternalCountMap().get(threadId));
				//send event
				if (queueCreate.getProcessToQueueMap().get(randomPrime).offer(evc, 4L, TimeUnit.SECONDS)) {
					//System.out.println(queueCreate.getProcessToQueueMap().get(randomPrime).size());
					int temp = queueCreate.getProcessToSendCountMap().get(threadId);
					queueCreate.getProcessToSendCountMap().put(threadId, temp+1);
					//System.out.println("message sent successfully from : " + threadId + "--"+ evc + ", to : " + randomPrime);				
				}
				
				//rec event
				BigInteger myMess = queueCreate.getProcessToQueueMap().get(threadId).poll();
				if(myMess!=null) {
					evc = lcm(myMess, evc);	
					//System.out.println("Message received : " + threadId + " : " + evc);
					queueCreate.getProcessToRecCountMap().put(threadId, queueCreate.getProcessToRecCountMap().get(threadId));
					queueCreate.graphData(BigIntegerMath.log2(evc, RoundingMode.DOWN));
				}
				//total event of each process
				//int events = queueCreate.getProcessToRecCountMap().get(threadId) + queueCreate.getProcessToSendCountMap().get(threadId);
				if(BigIntegerMath.log2(evc, RoundingMode.DOWN) >= s) {
					System.out.println(BigIntegerMath.log2(evc, RoundingMode.DOWN));
					System.out.println("##################################################");
					queueCreate.reset(allPrimes);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}