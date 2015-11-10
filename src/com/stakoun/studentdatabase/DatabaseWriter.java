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
	private static final String sep = ",";
	private static final String LF = "\n";
	private FileWriter writer;
	
	/**
	 * The sole constructor for the DatabaseWriter class.
	 * @param file 
	 * @throws IOException 
	 */
	public DatabaseWriter(File file) throws IOException
	{
		writer = new FileWriter(file);
	}
	
	public void addStudent(Student student)
	{
		try {
			writer.append(student.toCSV());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void close()
	{
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
