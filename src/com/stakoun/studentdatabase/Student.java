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
	private Course[] courses;
	
	/**
	 * The sole constructor for the Student class.
	 * @param marks
	 * @param home_form
	 * @param last_name
	 * @param first_name
	 * @param student_number
	 */
	public Student(int student_number, String first_name, String last_name, String home_form, Course[] courses)
	{
		this.student_number = student_number;
		this.first_name = first_name;
		this.last_name = last_name;
		this.home_form = home_form;
		this.courses = courses;
	}
	
	public int getAverage()
	{
		if (courses.length == 0)
			return 0;
		
		int sum = 0;
		for (Course c : courses)
			sum += c.getMark();
		
		return sum/courses.length;
	}

	public String toString()
	{
		String strCourses = "[";
		for (int i = 0; i < courses.length; i++) {
			if (i > 0)
				strCourses += ", ";
			strCourses += courses[i].toString();
		}
		strCourses += "]";
		return "Student Number: " + student_number + " | Name: " + last_name + ", " + first_name + " | Home Form: " + home_form + " | Courses: " + strCourses + " | Average: " + getAverage();
	}

	public String toCSV()
	{
		String sep = DatabaseWriter.sep;
		String LF = DatabaseWriter.LF;
		String csv = student_number + sep + first_name + sep + last_name + sep + home_form;
		for (int i = 0; i < courses.length; i++) {
			csv += sep + courses[i].getCode();
			csv += sep + courses[i].getMark();
		}
		csv += LF;
		return csv;
	}
	
	public static Student fromCSV(String csv) throws IllegalArgumentException
	{
		String args[] = csv.split(DatabaseWriter.sep);
		
		if (args.length < 4)
			throw new IllegalArgumentException();
		
		if (args.length % 2 != 0)
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
		
		int numCourses = (args.length - 4)/2;
		Course[] courses = new Course[numCourses];
		
		try {
			for (int i = 0; i < numCourses; i++)
				courses[i] = new Course(args[i*2+4], Integer.parseInt(args[i*2+5]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
		
		Student student = new Student(student_number, first_name, last_name, home_form, courses);
		
		return student;
	}

}
