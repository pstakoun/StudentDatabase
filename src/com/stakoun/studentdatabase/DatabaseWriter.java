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
	private File file;
	private FileWriter writer;
	
	/**
	 * The sole constructor for the DatabaseWriter class.
	 * @param file 
	 * @throws IOException 
	 */
	public DatabaseWriter(File file) throws IOException
	{
		this.file = file;
	}
	
	public void addStudent(Student student) throws IOException
	{
		open();
		writer.append(student.toCSV());
		close();
	}
	
	private void open() throws IOException
	{
		writer = new FileWriter(file, true);
	}
	
	private void close() throws IOException
	{
		writer.flush();
		writer.close();
	}

	public static void initFile(File tempFile) throws IOException
	{
		FileWriter tempWriter = new FileWriter(tempFile);
		tempWriter.append("student_number" + sep + "first_name" + sep + "last_name" + sep + "home_form" + sep +
				"course1" + sep + "mark1" + sep + "course2" + sep + "mark2" + sep + "course3" + sep + "mark3" + sep + "course4" + sep + "mark4" + sep +
				"course5" + sep + "mark5" + sep + "course6" + sep + "mark6" + sep + "course7" + sep + "mark7" + sep + "course8" + sep + "mark8" + LF);
		tempWriter.flush();
		tempWriter.close();
	}

}
