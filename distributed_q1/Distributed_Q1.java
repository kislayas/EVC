/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed_q1;


import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author kislaya
 */
public class Distributed_Q1 {
	
    //generate n prime numbers
	//static WriteFile writeFile1 = null;
	//static WriteFile writeFile2 = null;
	//static PrintWriter out = null;
    public static void simulation(List<Integer> primes, int number){
    	QueueCreate queueCreate= new QueueCreate(primes);
		/*try {
			//out = new PrintWriter("EventsVsEventSize1.dat");
			//writeFile1 = new WriteFile("EventsVsEventSize1.dat");
			//writeFile2 = new WriteFile("TEventsVsPEventSize1.dat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        primes.forEach(prime->{
        	List<Integer> tempPrimes=new ArrayList<>();
        	tempPrimes.addAll(primes);
        	tempPrimes.remove(prime);
            new Thread(new MyRunnable(primes, prime,tempPrimes,queueCreate)).start();
        });
    }
}
