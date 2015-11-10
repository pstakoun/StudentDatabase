package com.stakoun.studentdatabase;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The Main class initializes the database configuration and controls program flow.
 * @author Peter Stakoun
 */
public class Main
{
	private Scanner inputScanner;
	private String[] commandHelp;
	private String input;
	
	/**
	 * The sole constructor for the Main class.
	 */
	public Main()
	{
		inputScanner = new Scanner(System.in);
		commandHelp = new String[] {
				"CREATE table_name",
				"FOCUS table_name",
				"INSERT student_number first_name last_name home_form [mark1 ... mark8]",
				"DELETE condition",
				"FIND condition"
		};
		getUserInput();
	}

	/**
	 * The main method creates a new instance of Main.
	 * @param args
	 */
	public static void main(String[] args)
	{
		new Main();
	}
	
	private void getUserInput()
	{
		System.out.println("Type help or '?' for a list of available commands");
		do {
			input = inputScanner.nextLine();
			handleInput();
		} while (!input.equalsIgnoreCase("exit"));
	}

	private void handleInput()
	{
		String[] args = input.split("\\s+");
		if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
			displayCommandHelp();
		} else if (args[0].equalsIgnoreCase("CREATE")) {
			try {
				create(args);
			} catch (IllegalArgumentException e) {
				System.err.println("table_name must not be empty");
			} catch (IOException e) {
				System.err.println("table_name is invalid");
			}
		} else {
			System.out.println("Type help or '?' for a list of available commands");
		}
	}
	
	private void create(String[] args) throws IllegalArgumentException, IOException
	{
		if (args.length < 2)
			throw new IllegalArgumentException();
		if (args[1].matches("[/\\\\]+"))
			throw new IOException();
		
		String fileName = "tables" + File.separator + args[1] + ".csv";
		File file = new File(fileName);
		file.getParentFile().mkdirs();
		file.createNewFile();
		System.out.println(file.getAbsolutePath());
	}
	
	private void displayCommandHelp()
	{
		for (String s : commandHelp)
			System.out.println(s);
	}
	
}
