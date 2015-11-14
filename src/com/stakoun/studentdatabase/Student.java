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

	public String toString()
	{
		return "Student " + student_number + "";
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
	
	public static Student fromCSV(String csv)
	{
		return null;
	}

}
