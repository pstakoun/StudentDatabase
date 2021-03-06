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
	public static enum Field
	{
		STUDENT_NUMBER, FIRST_NAME, LAST_NAME, NAME, AVERAGE, HOME_FORM
	}
	
	private Scanner inputScanner;
	private String[] commandHelp;
	private Sorter sorter;
	private String input;
	private DatabaseWriter writer;
	private DatabaseReader reader;
	private Student[] students;
	
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
		sorter = new Sorter();
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
		} while (true);
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
		} else if (args[0].equalsIgnoreCase("SORT")) {
			try {
				sort(args);
			} catch (IllegalArgumentException e) {
				System.err.println("Usage: " + commandHelp[5]);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} else if (args[0].equalsIgnoreCase("FIND")) {
			try {
				find(args);
			} catch (IllegalArgumentException e) {
				System.err.println("Usage: " + commandHelp[6]);
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
		
		students = reader.readStudents();
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
		if (args.length < 4)
			throw new IllegalArgumentException();
		String[] query = new String[] {args[1], args[2], args[3]};
		deleteWhere(query);
	}
	
	private void show(String[] args) throws IllegalArgumentException, IOException
	{
		if (args.length > 2)
			throw new IllegalArgumentException();
		
		if (reader == null)
			throw new IOException("no active table found");

		students = reader.readStudents();
		
		if (args.length != 1) {
			int limit;
			try {
				limit = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}
			showStudents(limit);
		} else {
			showStudents();
		}
	}
	
	private void sort(String[] args) throws IllegalArgumentException, IOException
	{
		if (args.length > 2)
			throw new IllegalArgumentException();
		
		if (writer == null)
			throw new IOException("no active table found");
		
		if (args.length > 1)
			Student.setSortBy(getFieldFromString(args[1]));
		else
			Student.setSortBy(Field.STUDENT_NUMBER);
		
		Comparable[] sorted = sorter.sort(students);
		for (int i = 0; i < students.length; i++)
		{
			students[i] = (Student) sorted[i];
		}
		
		writer.overwrite(students);
	}

	private void find(String[] args) throws IllegalArgumentException, IOException
	{
		if (args.length < 4)
			throw new IllegalArgumentException();
		String[] query = new String[] {args[1], args[2], args[3]};
		showStudents(getWhere(query));
	}
	
	private void deleteWhere(String[] args) throws IllegalArgumentException, IOException
	{
		if (args.length != 3)
			throw new IllegalArgumentException();
		
		if (writer == null)
			throw new IOException("no active table found");
		
		Field field;
		Comparison comp;
		String value;
		
		if ((field = getFieldFromString(args[0])) == null)
			return;
		if ((comp = getComparisonFromString(args[1])) == null)
			return;
		value = args[2];
		
		int count = 0;
		for (int i = 0; i < students.length; i++) {
			String v;
			if ((v = students[i].getValueOfField(field)) == null) break;
			if (comp.compare(v, value)) {
				students[i] = null;
				count++;
			}
		}
		
		Student[] subarray = new Student[students.length-count];
		int i = 0;
		for (Student s : students)
			if (s != null)
				subarray[i++] = s;
		
		students = subarray;
		
		writer.overwrite(students);
	}
	
	private Student[] getWhere(String[] args) throws IllegalArgumentException
	{
		if (args.length != 3)
			throw new IllegalArgumentException();
		
		Field field;
		Comparison comp;
		String value;
		
		if ((field = getFieldFromString(args[0])) == null)
			return null;
		if ((comp = getComparisonFromString(args[1])) == null)
			return null;
		value = args[2];
		
		int count = 0;
		for (Student s : students) {
			String v;
			if ((v = s.getValueOfField(field)) == null) break;
			if (comp.compare(v, value)) {
				count++;
			}
		}
		
		Student[] subarray = new Student[count];
		int i = 0;
		for (Student s : students) {
			String v;
			if ((v = s.getValueOfField(field)) == null) break;
			if (comp.compare(v, value)) {
				subarray[i++] = s;
			}
		}
		
		return subarray;
	}
	
	private void showStudents()
	{
		for (Student s : students)
			System.out.println(s.toString());
	}
	
	private void showStudents(int limit)
	{
		Student[] arr = getStudentsWithLimit(limit);
		for (Student s : arr)
			System.out.println(s.toString());
	}
	
	private void showStudents(Student[] stdnts)
	{
		for (Student s : stdnts)
			System.out.println(s.toString());
	}
	
	private Student[] getStudentsWithLimit(int limit)
	{
		if (limit > students.length)
			return students;
		
		Student[] subarray = new Student[limit];
		for (int i = 0; i < limit; i++)
			subarray[i] = students[i];
		return subarray;
	}
	
	private Field getFieldFromString(String s)
	{
		if (s.equalsIgnoreCase("student_number"))
			return Field.STUDENT_NUMBER;
		else if (s.equalsIgnoreCase("first_name"))
			return Field.FIRST_NAME;
		else if (s.equalsIgnoreCase("last_name"))
			return Field.LAST_NAME;
		else if (s.equalsIgnoreCase("name"))
			return Field.NAME;
		else if (s.equalsIgnoreCase("home_form"))
			return Field.HOME_FORM;
		else if (s.equalsIgnoreCase("average"))
			return Field.AVERAGE;
		return null;
	}
	
	private Comparison getComparisonFromString(String s)
	{
		if (s.equals("=") || s.equals(">") || s.equals("<") || s.equals(">=") || s.equals("<="))
			return new Comparison(s);
		return null;
	}
	
	private void displayCommandHelp()
	{
		for (String s : commandHelp)
			System.out.println(s);
	}
	
}
