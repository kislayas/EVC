/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed_q1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author kislaya
 */
public class Distributed_Q1 {
	
    //generate n prime numbers
   
    public static void simulation(List<Integer> primes){
    	QueueCreate queueCreate= new QueueCreate(primes);
        primes.forEach(prime->{
        	List<Integer> tempPrimes=new ArrayList<>();
        	tempPrimes.addAll(primes);
        	tempPrimes.remove(prime);
            new Thread(new MyRunnable(prime,tempPrimes,queueCreate)).start();
        });
    }
}
