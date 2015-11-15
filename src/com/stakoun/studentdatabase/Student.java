package com.stakoun.studentdatabase;

/**
 * The Student class represents a single student and contains all their information.
 * @author Peter Stakoun
 */
public class Student
{
	private int student_number;
	private String first_name;
	private String last_name;
	private String home_form;
	private int[] marks;
	
	/**
	 * The sole constructor for the Student class.
	 * @param marks
	 * @param home_form
	 * @param last_name
	 * @param first_name
	 * @param student_number
	 */
	public Student(int student_number, String first_name, String last_name, String home_form, int[] marks)
	{
		this.student_number = student_number;
		this.first_name = first_name;
		this.last_name = last_name;
		this.home_form = home_form;
		this.marks = marks;
	}
	
	public int getAverage()
	{
		if (marks.length == 0)
			return 0;
		
		int sum = 0;
		for (int m : marks)
			sum += m;
		
		return sum/marks.length;
	}

	public String toString()
	{
		String strMarks = "[";
		for (int i = 0; i < marks.length; i++) {
			if (i > 0)
				strMarks += ", ";
			strMarks += marks[i];
		}
		strMarks += "]";
		return "Student Number: " + student_number + " | Name: " + last_name + ", " + first_name + " | Home Form: " + home_form + " | Marks: " + strMarks + " | Average: " + getAverage();
	}

	public String toCSV()
	{
		String sep = DatabaseWriter.sep;
		String LF = DatabaseWriter.LF;
		String csv = student_number + sep + first_name + sep + last_name + sep + home_form;
		for (int i = 0; i < 8; i++)
			if (i < marks.length)
				csv += sep + marks[i];
			else
				csv += sep;
		csv += LF;
		return csv;
	}
	
	public static Student fromCSV(String csv) throws IllegalArgumentException
	{
		String args[] = csv.split(DatabaseWriter.sep);
		
		if (args.length < 4)
			throw new IllegalArgumentException();
		
		int student_number;
		
		try {
			student_number = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
		
		String first_name = args[1];
		String last_name = args[2];
		String home_form = args[3];
		
		int numMarks = args.length - 4;
		int[] marks = new int[numMarks];
		
		try {
			for (int i = 0; i < numMarks; i++)
				marks[i] = Integer.parseInt(args[i+4]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
		
		Student student = new Student(student_number, first_name, last_name, home_form, marks);
		
		return student;
	}

}
