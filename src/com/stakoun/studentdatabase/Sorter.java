package com.stakoun.studentdatabase;

/**
 * The Sorter class is used to sort arrays of Students in different ways.
 * @author Peter Stakoun
 */
public class Sorter
{
	public Student[] sort(Student[] original)
	{
		int len = original.length;

		Student[] left = new Student[len/2];
		
		Student[] right;
		if (len % 2 == 0)
			right = new Student[len/2];
		else
			right = new Student[len/2+1];
		
		for (int i = 0; i < original.length/2; i++) {
			left[i] = original[i];
		}
		
		for (int i = original.length/2; i < original.length; i++) {
			right[i-original.length/2] = original[i];
		}
		
		return merge(sort(left), sort(right));
	}
	
	private Student[] merge(Student[] left, Student[] right)
	{
		Student[] merged = new Student[left.length+right.length];
		int leftI = 0;
		int rightI = 0;
		int i = 0;
		while (i < merged.length) {
			if (left[leftI].getAverage() <= right[rightI].getAverage() || rightI == right.length) { // TODO GENERALIZE
				merged[i] = left[leftI];
				leftI++;
			} else {
				merged[i] = right[rightI];
				rightI++;
			}
			i++;
		}
		return merged;
	}
	
}
