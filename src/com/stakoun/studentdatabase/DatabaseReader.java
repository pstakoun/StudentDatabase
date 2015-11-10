package com.stakoun.studentdatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The DatabaseReader class ... TODO
 * @author Peter Stakoun
 */
public class DatabaseReader
{
	private static final String sep = ",";
	private BufferedReader reader;
	
	/**
	 * The sole constructor for the DatabaseReader class.
	 * @param file 
	 * @throws IOException 
	 */
	public DatabaseReader(File file) throws IOException
	{
		reader = new BufferedReader(new FileReader(file));
	}
	
	public Student[] getStudents()
	{
		return null; // TODO
	}
	
	public Student[] getStudents(int limit)
	{
		return null; // TODO
	}
	
	public void close()
	{
		try {
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private int getLineCount()
	{
		return -1; // TODO
	}

}
