package com.stakoun.studentdatabase;

/**
 * The Student class represents a single student and contains all their information.
 * @author Peter Stakoun
 */
public class Student implements Comparable
{
	public static enum SortBy
	{
		STUDENT_NUMBER, NAME, AVERAGE, HOME_FORM;
	}
	
	private static SortBy sortBy;
	
	private int student_number;
	private String first_name;
	private String last_name;
	private String home_form;
	private Course[] courses;
	private int average;
	
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
	
	private void setAverage()
	{
		if (courses.length == 0)
			return;
		
		int sum = 0;
		for (Course c : courses)
			sum += c.getMark();
		
		average = sum/courses.length;
	}
	
	public int getStudentNumber()
	{
		return student_number;
	}
	
	public String getFirstName()
	{
		return first_name;
	}
	
	public String getLastName()
	{
		return last_name;
	}

	public String getHomeForm()
	{
		return home_form;
	}
	
	public int getAverage()
	{
		return average;
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

	public static void setSortBy(SortBy sb)
	{
		sortBy = sb;
	}
	
	public static SortBy getSortBy()
	{
		return sortBy;
	}
	
	public int compareTo(Object o)
	{
		Student that = (Student) o;
		switch (sortBy) {
		case STUDENT_NUMBER:
			return this.getStudentNumber() - that.getStudentNumber();
		case NAME:
			return (this.getLastName()+this.getFirstName()).compareTo(that.getLastName()+that.getFirstName());
		default:
			return 0;
		}
	}

}
