package Quicksort;

import java.util.Random;

public class QuickSort 
{
	public static int[] sort(int[] array)  //overloaded method -> only called when only an array is passed in
	{
		quicksort(array, 0, array.length-1);
		return array;
	}
	
	private static void quicksort(int[]array, int lowIndex, int highIndex) 
	{
		
		if (lowIndex >= highIndex) 
		{return;}
		
		int pivotIndex = new Random().nextInt(highIndex - lowIndex) + lowIndex;
		int pivot = array[pivotIndex];
		swap(array, pivotIndex, highIndex);
		
		int leftPointer = partition(array, lowIndex, highIndex, pivot); //alt shift m to create a new method out of code
		
		quicksort(array, lowIndex, leftPointer -1); 		//left
		quicksort(array, leftPointer + 1, highIndex);		//right
	}

	private static int partition(int[] array, int lowIndex, int highIndex, int pivot) 
	{
		
		int leftPointer = lowIndex;
		int rightPointer = highIndex;
		
		while (leftPointer < rightPointer) {
			
			while (array[leftPointer] <= pivot && leftPointer < rightPointer) 
			{leftPointer++;}
			
			while (array[rightPointer] >= pivot && leftPointer < rightPointer)
			{rightPointer--;}
			
			swap(array, leftPointer, rightPointer);	
		}
		
		swap(array, leftPointer, highIndex);
		return leftPointer;
	}
	
	private static void swap(int[] array, int index1, int index2) 
	{
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
}
