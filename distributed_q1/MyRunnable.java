
package distributed_q1;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

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
	private WriteFile writeFile1;
	private WriteFile writeFile2;

	
	public MyRunnable(int id, List<Integer> tempPrimes, QueueCreate queueCreate, WriteFile writeFile1, WriteFile writeFile2) {
		this.threadId = id;
		this.queueCreate = queueCreate;
		this.primes = tempPrimes;
		this.random = new Random();
		this.writeFile1 = writeFile1;
		this.writeFile2 = writeFile2;
	}

	private int getRandomPrime() {
		return primes.get(random.nextInt(primes.size()));
	}

	@Override
	public void run() {
		System.out.println("Current Thread id is : " + this.threadId);
		eventProcess();
		// some code to run in parallel
		// this could also be another class that implements Runnable
	}

	private static BigInteger gcd(BigInteger a, BigInteger b) {
	    //while (b > 0)
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
		BlockingQueue<BigInteger>myQueue=queueCreate.getProcessToQueueMap().get(threadId);
		int sendCount = queueCreate.getProcessToSendCountMap().get(threadId);
		int recCount = queueCreate.getProcessToRecCountMap().get(threadId);
		// int internalCount = queueCreate.getProcessToInternalCountMap().get(threadId);
		BigInteger evc = new BigInteger("1");

		try {
			while (true) {
				int randomPrime = getRandomPrime();
				/*if(queueCreate.eventCount==primes.size()/5 || queueCreate.eventCount==0) {
					
				}*/
				try {
					String content = queueCreate.eventCount + " " + evc.bitLength() + "\n";
					writeFile1.bw.write(content);
				} catch (IOException e) {

					e.printStackTrace();

				} 
				evc = evc.multiply(BigInteger.valueOf(threadId));
				//send event
				if (queueCreate.getProcessToQueueMap().get(randomPrime).offer(evc, 4L, TimeUnit.SECONDS)) {
					queueCreate.eventCount++;
					sendCount++;
					queueCreate.getProcessToSendCountMap().put(threadId,sendCount);
					System.out.println("message sent successfully from : " + threadId + "--"+ evc + ", to : " + randomPrime);				
				}
				
				//rec event
				BigInteger myMess = myQueue.poll();
				System.out.println(myMess);
				// Long myMess=myQueue.poll();
				if(myMess!=null) {
					// BigInteger temp = BigInteger.valueOf(threadId).multiply(evc);
					evc = lcm(myMess, evc);	
					System.out.println("Message received : " + threadId + " : " + evc);
					recCount++;
					queueCreate.eventCount++;
					queueCreate.getProcessToRecCountMap().put(threadId,recCount);
				}
				//total event of each process
				int events = queueCreate.getProcessToRecCountMap().get(threadId) + queueCreate.getProcessToSendCountMap().get(threadId);
				try {
					String content2 = queueCreate.eventCount + " " + events + "\n";
					writeFile2.bw.write(content2);
				} catch (IOException e) {

					e.printStackTrace();

				} 
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}