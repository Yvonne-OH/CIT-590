package hw7;

import java.util.Scanner; // Import the Scanner class

public class HelloWorld {

	public static void main(String[] args) {

		System.out.println("Hello,World!");
		
		/*
		 Q1 Say Hello
			a. Ask the user to enter their full name.
			b. The user should type in their first name and last name, separated by a space.
				i. Print “Hello, <full name>!” where <full name> gets replaced by the full name of the user.
		 */

		Scanner scanner = new Scanner(System.in); 		    // Create a Scanner object to read input
		System.out.println("Please enter your full name:"); // Prompt the user to enter their full name
		String fullName = scanner.nextLine(); 				// Read the full name entered by the user
		System.out.println("Hello, " + fullName + "!");     // Print Hello with their full name
		//scanner.close(); 									// Close the scanner object 
		
		
		/*
		 Q2 Add Five Numbers
			a. Ask the user to enter a total of 5 numbers (ints or doubles), and hit enter after each. Assume each number is an int or a double.
			b. Print the sum (as a double) of all the numbers each time.
		 */
		
        double sum = 0; 							   // Initialize the sum to 0
        // Ask the user to enter 5 numbers
        for(int i = 1; i <= 5; i++) {
            System.out.println("Enter number " + i + ":"); // Prompt for each number
            double number = scanner.nextDouble();     // Read the number as a double 
            sum += number;             					 // Add the number to the sum
            System.out.println("Sum: " +  String.format("%.2f", sum));   // Print the current sum
        }
        
		/*
		 Q3 Even or Odd
			a. Ask the user to enter an integer.
			b. Check if the number is even or odd. Assume this will be a positive integer.
				i. If it is even, print “<number> is even”, where <number> gets replaced by the number.
				ii. If it is odd, print “<number> is odd”, where <number> gets replaced by the number
		 */
        System.out.println("Please enter an integer :");
        while (!scanner.hasNextInt()) { // Clear the invalid input
            System.out.println("That's not an integer. Please enter an integer:");
            scanner.next(); 
        }
        
        int checkNumber = scanner.nextInt(); //read an integer input from the use
        
        if (checkNumber % 2 == 0) {
            System.out.println(checkNumber + " is even");
        } else {
            System.out.println(checkNumber + " is odd");
        }
        
		/*
		 Q4 Prime or Composite
			a. Ask the user to enter a positive integer. Assume this will be a positive integer.
			b. Check if the number is prime or composite.
				i. If it is prime, print “<number> is prime”, where <number> gets replaced by the number.
				ii. If it is composite, print “<number> is composite”, where <number> gets replaced by the number.
				iii. If the number is 1, print 1.
		 */
        
        System.out.println("Please enter a positive integer:");
        int num = scanner.nextInt();

        if (num == 1) { // Handling the special case of 1
            System.out.println("1 is neither prime nor composite.");
        } else {
            // Check if num is prime or composite
            boolean isPrime = true; // Flag
            for (int i = 2; i <= Math.sqrt(num); i++) {
                if (num % i == 0) {
                    isPrime = false;
                    break; // num is not prime
                }
            }
            
            if (isPrime) {
                System.out.println(num + " is prime");
            } else {
                System.out.println(num + " is composite");
            }
        }
        
		/*
		 Q5 Convert Seconds to Time
			a. Ask the user to enter some number of seconds (as an int) and convert it to hours:minutes:seconds.
				i. For example, if input seconds is 1432, print output in the format: 0:23:52
				ii. If input seconds is 0, print output in the format: 0:0:0
				iii. If input seconds is negative, print output in the format: -1:-1:-1
		 */

        System.out.println("Please enter the seconds: ");   // Prompt the user to enter the number of seconds
        int inputSeconds = scanner.nextInt();
        
        if (inputSeconds < 0) {         					// Prompt the user to enter the number of seconds
            System.out.println("-1:-1:-1");
        } else {
            int hours = inputSeconds / 3600; 				// Calculate hours, minutes, and seconds
            int minutes = (inputSeconds % 3600) / 60;
            int seconds = inputSeconds % 60;

            System.out.println(hours + ":" + minutes + ":" + seconds);   // Print the result 
        }
        
		/*
		 Q6. Say Goodbye
			a. Print “Goodbye, <full name>!” where <full name> gets replaced by the full name of the user.
		 */
        
        System.out.println("Goodbye, " + fullName + "!");
        

       
        scanner.close();         // Close the scanner 

	}

}
