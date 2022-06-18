package Quicksort;

import java.util.Random;

public class QuickSort 
{
	public static int[] sortInt(int[] array)  //overloaded method -> only called when only an array is passed in
	{
		quicksortInt(array, 0, array.length-1);
		return array;
	}
	
	private static void quicksortInt(int[]array, int lowIndex, int highIndex) 
	{
		
		if (lowIndex >= highIndex) 
		{return;}
		
		int pivotIndex = new Random().nextInt(highIndex - lowIndex) + lowIndex;
		int pivot = array[pivotIndex];
		swapInt(array, pivotIndex, highIndex);
		
		int leftPointer = partitionInt(array, lowIndex, highIndex, pivot); //alt shift m to create a new method out of code
		
		quicksortInt(array, lowIndex, leftPointer -1); 		//left
		quicksortInt(array, leftPointer + 1, highIndex);		//right
	}
	

	private static int partitionInt(int[] array, int lowIndex, int highIndex, int pivot) 
	{
		
		int leftPointer = lowIndex;
		int rightPointer = highIndex;
		
		while (leftPointer < rightPointer) {
			
			while (array[leftPointer] <= pivot && leftPointer < rightPointer) 
			{leftPointer++;}
			
			while (array[rightPointer] >= pivot && leftPointer < rightPointer)
			{rightPointer--;}
			
			swapInt(array, leftPointer, rightPointer);	
		}
		
		swapInt(array, leftPointer, highIndex);
		return leftPointer;
	}
	
	private static void swapInt(int[] array, int index1, int index2) 
	{
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
	
//----------------------------------------------------------------------------------------------------	
	
	//DOUBLE
	public static double[] sortDouble(double[] array)  //overloaded method -> only called when only an array is passed in
	{
		quicksortDouble(array, 0, array.length-1);
		return array;
	}
	
	private static void quicksortDouble(double[]array, int lowIndex, int highIndex) 
	{
		
		if (lowIndex >= highIndex) 
		{return;}
		
		int pivotIndex = new Random().nextInt(highIndex - lowIndex) + lowIndex;
		double pivot = array[pivotIndex];
		swapDouble(array, pivotIndex, highIndex);
		
		int leftPointer = partitionDouble(array, lowIndex, highIndex, pivot); //alt shift m to create a new method out of code
		
		quicksortDouble(array, lowIndex, leftPointer -1); 		//left
		quicksortDouble(array, leftPointer + 1, highIndex);		//right
	}
	
	private static int partitionDouble(double[] array, int lowIndex, int highIndex, double pivot) 
	{
		
		int leftPointer = lowIndex;
		int rightPointer = highIndex;
		
		while (leftPointer < rightPointer) {
			
			while (array[leftPointer] <= pivot && leftPointer < rightPointer) 
			{leftPointer++;}
			
			while (array[rightPointer] >= pivot && leftPointer < rightPointer)
			{rightPointer--;}
			
			swapDouble(array, leftPointer, rightPointer);	
		}
		
		swapDouble(array, leftPointer, highIndex);
		return leftPointer;
	}
	
	private static void swapDouble(double[] array, int index1, int index2) 
	{
		double temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
}
