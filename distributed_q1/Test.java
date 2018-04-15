package distributed_q1;

import java.util.List;
import java.util.Scanner;

public class Test {

	 private static Scanner scan;
		/**
	     * @param args the command line arguments
	     */ 
	    public static void main(String[] args) {
	        // TODO code application logic here
	        //primes = gen_prime(n)
	    	System.out.println("Enter number of process: ");
	    	 scan = new Scanner(System.in);
	    	 int number = Integer.parseInt( scan.nextLine());
	    	 
	    	 PrimeUtil primeUtil=new PrimeUtil(number);
	       // List<Integer> primes = Distributed_Q1.getFirstNPrimes();
	        Distributed_Q1.simulation(primeUtil.getPrimes());
	    }

}
