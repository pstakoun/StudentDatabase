package com.stakoun.studentdatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The DatabaseReader class ... TODO
 * @author Peter Stakoun
 */
public class DatabaseReader
{
	private static final String sep = ",";
	private File file;
	private BufferedReader reader;
	private Student[] students;
	
	/**
	 * The sole constructor for the DatabaseReader class.
	 * @param file 
	 * @throws IOException 
	 */
	public DatabaseReader(File file) throws IOException
	{
		this.file = file;
		students = new Student[getLineCount()];
		update();
	}
	
	public Student[] getStudents()
	{
		return students;
	}
	
	public Student[] getStudents(int limit)
	{
		Student[] subarray = new Student[limit];
		for (int i = 0; i < limit; i++)
			subarray[i] = students[i];
		return subarray;
	}
	
	private int getLineCount() throws IOException
	{
		open();
		int count = 0;
		while (reader.readLine() != null) count++;
		close();
		return count;
	}
	
	private void open() throws FileNotFoundException
	{
		reader = new BufferedReader(new FileReader(file));
	}
	
	private void update() throws IOException
	{
		int numStudents = getLineCount()-1;
		if (numStudents < 0)
			numStudents = 0;
		open();
		students = new Student[numStudents];
		reader.readLine();
		String line;
		int i = 0;
		while ((line = reader.readLine()) != null)
			students[i++] = Student.fromCSV(line);
		close();
	}
	
	public void close() throws IOException
	{
		reader.close();
	}

}
