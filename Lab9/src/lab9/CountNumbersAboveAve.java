	/**
 * 
 */
package lab9;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * @author jadlu
 *
 */
public class CountNumbersAboveAve {

	public static void main(String[] args) {
		
		PrintWriter writer = null;
		Random r = new Random();
		double average = 0;
		
		
		try {
			writer = new PrintWriter("tenNumbers.txt");
			
			
			for(int i = 0; i <10; i++) {
				
				double mark = r.nextInt(10000)/100.0;
				
				average += mark;
				
				writer.write(mark + "\n");
			}
			
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			writer.close();
		}
		
		average /= 10;
		average = Math.round((int)(average*100))/100.0;
		
		File inputFile = new File("tenNumbers.txt");
		
		Scanner fileScanner = null;
		int count = 0;
		
		try {
			fileScanner = new Scanner(inputFile);
			
			while(fileScanner.hasNextDouble()) {
				double value = fileScanner.nextDouble();
				System.out.println(value);
				
				if(value>average) {
					count++;
				}
			}
			
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			fileScanner.close();
		}
		
		System.out.println("The average is " + average + " and there are " + count + " numbers above the average");

	}

}
