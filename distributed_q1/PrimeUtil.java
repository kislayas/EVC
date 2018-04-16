/**
 * 
 */
package distributed_q1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kislaya
 *
 */
public class PrimeUtil {
	private List<Integer> primes;
	// private Random random;
	
	public List<Integer> getPrimes() {
		return primes;
	}

	public PrimeUtil(int number) {
		primes =new ArrayList<>();
		 int count = 0;
	        int num = 2;
	        
	        while(count != number) { // while count!= number of prime numbers entered keep searching..
	            boolean prime = true;// to determine whether the number is prime or not
	            for (int i = 2; i <= Math.sqrt(num); i++) { //efficiency matters
	                if (num % i == 0) {
	                    prime = false; // if number divides any other number its not a prime so set prime to false and break the loop.
	                    break;
	                }
	            }
	        if (prime) {
	            count++;
	            primes.add(num);
	            System.out.println(num);
	        }
	        num++;
	        }
	}
	
	
	
}
