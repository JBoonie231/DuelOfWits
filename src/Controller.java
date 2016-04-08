/**
 * Controller handles user input and relays that information to it's instance in main.
 */

import java.util.Scanner;
/**
 * @author Joshua Boone, Chase Perdue
 *
 */
public class Controller 
{
	Scanner scanner =  new Scanner(System.in);
	public Controller() 
	{
		
	}
	
	public String getInput()
	{
		String userInput = scanner.nextLine();
		userInput = userInput.toUpperCase();
		return userInput;
	}
}
