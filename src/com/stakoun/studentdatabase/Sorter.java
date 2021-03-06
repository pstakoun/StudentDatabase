package com.stakoun.studentdatabase;

/**
 * The Sorter class is used to sort arrays of Students in different ways.
 * @author Peter Stakoun
 */
public class Sorter
{
	public Comparable[] sort(Comparable[] original)
	{
		int len = original.length;
		
		if (len < 2)
			return original;

		Comparable[] left = new Comparable[len/2];
		
		Comparable[] right;
		if (len % 2 == 0)
			right = new Comparable[len/2];
		else
			right = new Comparable[len/2+1];
		
		for (int i = 0; i < original.length/2; i++) {
			left[i] = original[i];
		}
		
		for (int i = original.length/2; i < original.length; i++) {
			right[i-original.length/2] = original[i];
		}
		
		return merge(sort(left), sort(right));
	}
	
	private Comparable[] merge(Comparable[] left, Comparable[] right)
	{
		Comparable[] merged = new Comparable[left.length+right.length];
		int leftI = 0;
		int rightI = 0;
		int i = 0;
		while (i < merged.length) {
			if (rightI == right.length && leftI != left.length)
				merged[i] = left[leftI++];
			else if (leftI == left.length && rightI != right.length)
				merged[i] = right[rightI++];
			else if (left[leftI].compareTo(right[rightI]) <= 0)
				merged[i] = left[leftI++];
			else
				merged[i] = right[rightI++];
			i++;
		}
		return merged;
	}
	
}
