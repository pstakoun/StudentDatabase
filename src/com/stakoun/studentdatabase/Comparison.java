package com.stakoun.studentdatabase;

/**
 * The Comparison class compares 2 values with its assigned comparison.
 * @author Peter Stakoun
 */
public class Comparison
{
	private static enum Type {
		EQUAL, GREATER, LESS, GREATER_OR_EQUAL, LESS_OR_EQUAL
	}
	
	private Type type;
	
	/**
	 * The sole constructor for the Comparison class.
	 */
	public Comparison(String s)
	{
		type = getType(s);
	}
	
	private Type getType(String s)
	{
		if (s.equals("="))
			return Type.EQUAL;
		if (s.equals(">"))
			return Type.GREATER;
		if (s.equals("<"))
			return Type.LESS;
		if (s.equals(">="))
			return Type.GREATER_OR_EQUAL;
		if (s.equals("<="))
			return Type.LESS_OR_EQUAL;
		return null;
	}
	
	public boolean compare(Comparable a, Comparable b)
	{
		int n = a.compareTo(b);
		if (type == Type.EQUAL)
			return n == 0;
		if (type == Type.GREATER)
			return n > 0;
		if (type == Type.LESS)
			return n < 0;
		if (type == Type.GREATER_OR_EQUAL)
			return n >= 0;
		if (type == Type.LESS_OR_EQUAL)
			return n <= 0;
		return false;
	}

}
