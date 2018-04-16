/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed_q1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author kislaya
 */
public class Distributed_Q1 {
	
    //generate n prime numbers
	static WriteFile writeFile1 = null;
	static WriteFile writeFile2 = null;
    public static void simulation(List<Integer> primes){
    	QueueCreate queueCreate= new QueueCreate(primes);
		try {
			writeFile1 = new WriteFile("EventsVsEventSize.dat");
			writeFile2 = new WriteFile("TEventsVsPEventSize.dat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        primes.forEach(prime->{
        	List<Integer> tempPrimes=new ArrayList<>();
        	tempPrimes.addAll(primes);
        	tempPrimes.remove(prime);
            new Thread(new MyRunnable(prime,tempPrimes,queueCreate, writeFile1, writeFile2)).start();
        });
    }
}
