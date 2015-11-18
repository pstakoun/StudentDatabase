package com.stakoun.studentdatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The DatabaseReader class reads Student information from the Database.
 * @author Peter Stakoun
 */
public class DatabaseReader
{
	private static final String sep = ",";
	private File file;
	private BufferedReader reader;
	
	/**
	 * The sole constructor for the DatabaseReader class.
	 * @param file 
	 * @throws IOException 
	 */
	public DatabaseReader(File file) throws IOException
	{
		this.file = file;
	}
	
	public Student[] readStudents() throws IOException
	{
		int numStudents = getLineCount()-1;
		if (numStudents < 0)
			numStudents = 0;
		
		open();
		Student[] students = new Student[numStudents];
		reader.readLine();
		String line;
		int i = 0;
		while ((line = reader.readLine()) != null)
			students[i++] = Student.fromCSV(line);
		close();
		
		return students;
	}
	
	private int getLineCount() throws IOException
	{
		open();
		int count = 0;
		while (reader.readLine() != null) count++;
		close();
		return count;
	}
	
	private void open() throws IOException
	{
		reader = new BufferedReader(new FileReader(file));
	}
	
	private void close() throws IOException
	{
		reader.close();
	}

}
