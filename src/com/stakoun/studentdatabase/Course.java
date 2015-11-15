package com.stakoun.studentdatabase;

/**
 * The Course class represents a school course.
 * @author Peter Stakoun
 */
public class Course
{
	private String code;
	private int mark;
	
	/**
	 * The sole constructor for the Course class.
	 */
	public Course(String code, int mark)
	{
		this.code = code;
		this.mark = mark;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public int getMark()
	{
		return mark;
	}
	
	public String toString()
	{
		return "(" + code + ", " + mark + ")";
	}

}
