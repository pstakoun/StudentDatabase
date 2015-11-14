package com.stakoun.studentdatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The DatabaseWriter class ... TODO
 * @author Peter Stakoun
 */
public class DatabaseWriter
{
	public static final String sep = ",";
	public static final String LF = "\n";
	private FileWriter writer;
	
	/**
	 * The sole constructor for the DatabaseWriter class.
	 * @param file 
	 * @throws IOException 
	 */
	public DatabaseWriter(File file) throws IOException
	{
		writer = new FileWriter(file, true);
	}
	
	public void addStudent(Student student) throws IOException
	{
		writer.append(student.toCSV());
		writer.flush();
	}
	
	public void close() throws IOException
	{
		writer.flush();
		writer.close();
	}

}
