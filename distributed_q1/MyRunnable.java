/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed_q1;

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
	
	public MyRunnable(int id, List<Integer> tempPrimes, QueueCreate queueCreate) {
		this.threadId = id;
		this.queueCreate = queueCreate;
		this.primes = tempPrimes;
		this.random = new Random();
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
	    while (b.compareTo(BigInteger.valueOf(0))==1)
	    {
	        //long temp = b;
	        //b = a % b; // % is remainder
	        //a = temp;
	        BigInteger temp = b;
	        a = a.mod(b);
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
		int internalCount = queueCreate.getProcessToInternalCountMap().get(threadId);

		int count = 0;
		BigInteger evc = new BigInteger("1");
		try {
			while (true) {
				int randomPrime = getRandomPrime();
				System.out.println("-----------" + threadId + "%%%" + evc);
				evc = evc.multiply(BigInteger.valueOf(threadId));
				//send event
				if (queueCreate.getProcessToQueueMap().get(randomPrime).offer(evc, 2L, TimeUnit.SECONDS)) {
					sendCount++;					
					System.out.println("message sent successfully from : " + threadId + "--"+ evc + ", to : " + randomPrime);
					
				}
				//rec event
				BigInteger myMess = myQueue.poll();
				// Long myMess=myQueue.poll();
				if(myMess!=null) {
					// BigInteger temp = BigInteger.valueOf(threadId).multiply(evc);
					evc = lcm(myMess, evc);
					System.out.println("Message received : " + threadId + " : " + evc);
					recCount++;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
