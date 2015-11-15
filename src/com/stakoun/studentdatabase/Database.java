package com.stakoun.studentdatabase;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The Main class initializes the database configuration and controls program flow.
 * @author Peter Stakoun
 */
public class Database
{
	private Scanner inputScanner;
	private String[] commandHelp;
	private String input;
	private DatabaseWriter writer;
	private DatabaseReader reader;
	
	/**
	 * The sole constructor for the Main class.
	 */
	public Database()
	{
		inputScanner = new Scanner(System.in);
		commandHelp = new String[] {
				"CREATE table_name",
				"FOCUS table_name",
				"INSERT student_number first_name last_name home_form [course1 mark1 ... course8 mark8]",
				"DELETE condition",
				"SHOW [max_entries]",
				"SORT [student_number | name | home_form | average]",
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
		new Database();
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
				System.err.println("Usage: " + commandHelp[0]);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} else if (args[0].equalsIgnoreCase("FOCUS")) {
			try {
				focus(args);
			} catch (IllegalArgumentException e) {
				System.err.println("Usage: " + commandHelp[1]);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} else if (args[0].equalsIgnoreCase("INSERT")) {
			try {
				insert(args);
			} catch (IllegalArgumentException e) {
				System.err.println("Usage: " + commandHelp[2]);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} else if (args[0].equalsIgnoreCase("DELETE")) {
			try {
				delete(args);
			} catch (IllegalArgumentException e) {
				System.err.println("Usage: " + commandHelp[3]);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} else if (args[0].equalsIgnoreCase("SHOW")) {
			try {
				show(args);
			} catch (IllegalArgumentException e) {
				System.err.println("Usage: " + commandHelp[4]);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} else if (args[0].equalsIgnoreCase("FIND")) {
			try {
				find(args);
			} catch (IllegalArgumentException e) {
				System.err.println("Usage: " + commandHelp[5]);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} else {
			System.out.println("Type help or '?' for a list of available commands");
		}
	}
	
	private void create(String[] args) throws IllegalArgumentException, IOException
	{
		if (args.length != 2)
			throw new IllegalArgumentException();
		
		if (args[1].contains("/") || args[1].contains("\\"))
			throw new IOException("table_name is invalid");
		
		String fileName = "tables" + File.separator + args[1] + ".csv";
		File file = new File(fileName);
		
		if (file.exists())
			throw new IOException("table_name already exists");
		
		file.getParentFile().mkdirs();
		file.createNewFile();
		
		DatabaseWriter.initFile(file);
	}
	
	private void focus(String[] args) throws IllegalArgumentException, IOException
	{
		if (args.length != 2)
			throw new IllegalArgumentException();
		
		String fileName = "tables" + File.separator + args[1] + ".csv";
		File file = new File(fileName);
		
		if (!file.exists())
			throw new IOException("table_name does not exist");
		
		writer = new DatabaseWriter(file);
		reader = new DatabaseReader(file);
	}
	
	private void insert(String[] args) throws IllegalArgumentException, IOException
	{
		if (args.length < 5 || args.length > 21)
			throw new IllegalArgumentException();
		
		if (args.length % 2 != 1)
			throw new IllegalArgumentException();
		
		if (writer == null)
			throw new IOException("no active table found");
		
		int student_number;
		
		try {
			student_number = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
		
		String first_name = args[2];
		String last_name = args[3];
		String home_form = args[4];
		
		int numCourses = (args.length - 5)/2;
		Course[] courses = new Course[numCourses];
		
		try {
			for (int i = 0; i < numCourses; i++)
				courses[i] = new Course(args[i*2+5], Integer.parseInt(args[i*2+6]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
		
		Student student = new Student(student_number, first_name, last_name, home_form, courses);
	
		writer.addStudent(student);
	}
	
	private void delete(String[] args) throws IllegalArgumentException, IOException
	{
		// TODO
	}
	
	private void show(String[] args) throws IllegalArgumentException, IOException
	{
		if (args.length > 3)
			throw new IllegalArgumentException();
		
		if (reader == null)
			throw new IOException("no active table found");
		
		reader.update();
		
		Student[] students;
		if (args.length == 1) {
			students = reader.getStudents();
		} else {
			int limit;
			try {
				limit = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}
			students = reader.getStudents(limit);
		}
		
		for (Student s : students)
			System.out.println(s.toString());
	}
	
	private void find(String[] args) throws IllegalArgumentException, IOException
	{
		// TODO
	}
	
	private void displayCommandHelp()
	{
		for (String s : commandHelp)
			System.out.println(s);
	}
	
}
