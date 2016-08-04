// My study of Big O Notation implemented in Java based on - https://www.youtube.com/watch?v=V6mKVRU1evU

// Big O notation is a way to measure how well a
// computer algorithm scales as the amount of data
// involved increases. It is not always a measure
// of speed as you'll see below.

// This is a rough overview of Big O and doesn't 
// cover topics such as asymptotic analysis, which
// covers comparing algorithms as data approaches
// infinity

// What we are defining is the part of the algorithm 
// that has the greatest effect. For example
// 45n^3 + 20n^2 + 19 = 84 (n=1)
// 45n^3 + 20n^2 + 19 = 459 (n=2) Does 19 matter?
// 45n^3 + 20n^2 + 19 = 47019 (n=10) 
// Does the 20n^2 matter if 45n^3 = 45,000?
// Since n^3 is most important, the notation is O(n^3) also stated as an Order of n-cubed.
import java.util.Arrays;

public class BigONotation {

	private int[] theArray;

	private int arraySize;

	// set items in array to 0 at initialization 
	private int itemsInArray = 0;

	static long startTime;
	static long endTime;

	// constructor that initializs the array size
	BigONotation(int size) {

		arraySize = size;

		theArray = new int[size];
	}

	// generate a random number of elements for the array based on the provided arraySize
	// at initialization 
	public void generateRandomArray() {

		for (int i = 0; i < arraySize; i++) {

			theArray[i] = (int) (Math.random() * 1000) + 10;
		}

		itemsInArray = arraySize - 1;
	}

	// 
	public void swapValues(int indexOne, int indexTwo) {

		int temp = theArray[indexOne];
		theArray[indexOne] = theArray[indexTwo];
		theArray[indexTwo] = temp;
	}

	// O(1) An algorithm that executes in the same
	// time regardless of the amount of data.
	// This code executes in the same amount of
	// time no matter how big the array is
	public void addItemToArray(int newItem) {
		theArray[itemsInArray++] = newItem;
	}

	// 0(N) An algorithm thats time to complete will
	// grow in direct proportion to the amount of data
	// The linear search is an example of this

	// To find all values that match what we
	// are searching for we will have to look in
	// exactly each item in the array

	// If we just wanted to find one match the Big O
	// is the same because it describes the worst
	// case scenario in which the whole array must
	// be searched
	public void linearSearchForValue(int value) {

		// value to search for
		boolean valueInArray = false;

		String indexsWithValue = "";

		// system check to see how long it takes to execute 
		startTime = System.currentTimeMillis();

		// search for the value and change bool to true
		for (int i = 0; i < arraySize; i++) {

			if (theArray[i] == value) {
				valueInArray = true;
				indexsWithValue += i + " ";
			}
		}

		System.out.println("Value Found: " + valueInArray);

		endTime = System.currentTimeMillis();

		System.out.println("Linear Search Took " + (endTime - startTime));
	}

	// O(N^2) Time to complete will be proportional to
	// the square of the amount of data (Bubble Sort)
	// Algorithms with more nested iterations will result
	// in O(N^3), O(N^4), etc. performance

	// Each pass through the outer loop (0)N requires us
	// to go through the entire list again so N multiplies
	// against itself or it is squared
	public void bubbleSort() {

		startTime = System.currentTimeMillis();

		// for each item in the array 
		for (int i = arraySize - 1; i > 1; i--) {

			// swap values if the item is larger than the following item
			for (int j = 0; j < i; j++) {

				if (theArray[j] > theArray[j + 1]) {

					swapValues(j, j + 1);
				}
			}
		}

		endTime = System.currentTimeMillis();

		System.out.println("Bubble Sort Took " + (endTime - startTime));
	}

	// O (log N) Occurs when the data being used is decreased
	// by roughly 50% each time through the algorithm. The
	// Binary search is a perfect example of this.

	// Pretty fast because the log N increases at a dramatically
	// slower rate as N increases

	// O (log N) algorithms are very efficient because increasing
	// the amount of data has little effect at some point early
	// on because the amount of data is halved on each run through
	public void binarySearchForValue(int value) {

		startTime = System.currentTimeMillis();

		int lowIndex = 0;
		int highIndex = arraySize - 1;

		// how many times do we go through the algorithm?
		int timesThrough = 0;

		while (lowIndex <= highIndex) {

			// constantly cutting the amount of data in half as we go through it is 
			// why this algorithm is so efficient 
			int middleIndex = (highIndex + lowIndex) / 2;

			if (theArray[middleIndex] < value)
				lowIndex = middleIndex + 1;

			else if (theArray[middleIndex] > value)
				highIndex = middleIndex - 1;

			else {

				System.out.println("\nFound a Match for " + value
						+ " at Index " + middleIndex);

				lowIndex = highIndex + 1;
			}

			// calculate the number of times we went through the entire process to show
			// the efficiency of this algorithm 
			timesThrough++;
		}

		// This doesn't really show anything because
		// the algorithm is so fast

		endTime = System.currentTimeMillis();

		System.out.println("Binary Search Took " + (endTime - startTime));

		System.out.println("Times Through: " + timesThrough);
	}

	// O (n log n) Most sorts are at least O(N) because
	// every element must be looked at, at least once.
	// The bubble sort is O(N^2)
	// To figure out the number of comparisons we need
	// to make with the Quick Sort we first know that
	// it is comparing and moving values very
	// efficiently without shifting. That means values
	// are compared only once. So each comparison will
	// reduce the possible final sorted lists in half.
	// Comparisons = log n! (Factorial of n)
	// Comparisons = log n + log(n-1) + .... + log(1)
	// This evaluates to n log n
	public void quickSort(int left, int right) {

		// done working with the array, it is all sorted
		if (right - left <= 0)
			return;
		// it isn't done being sorted
		else {

			int pivot = theArray[right];

			int pivotLocation = partitionArray(left, right, pivot);

			quickSort(left, pivotLocation - 1);
			quickSort(pivotLocation + 1, right);
		}
	}
	
	// 
	public int partitionArray(int left, int right, int pivot) {

		int leftPointer = left - 1;
		int rightPointer = right;

		while (true) {

			while (theArray[++leftPointer] < pivot)
				;

			while (rightPointer > 0 && theArray[--rightPointer] > pivot)
				;

			if (leftPointer >= rightPointer) {

				break;

			} else {

				swapValues(leftPointer, rightPointer);
			}
		}

		swapValues(leftPointer, right);

		return leftPointer;
	}

	// test the Big O Notation implementation
	public static void main(String[] args) {

		// create several different arrays of various sizes in order to test
		BigONotation testAlgo = new BigONotation(10);

		testAlgo.addItemToArray(10);

		System.out.println(Arrays.toString(testAlgo.theArray));

		BigONotation testAlgo2 = new BigONotation(100000);
		testAlgo2.generateRandomArray();

		BigONotation testAlgo3 = new BigONotation(200000);
		testAlgo3.generateRandomArray();

		BigONotation testAlgo4 = new BigONotation(30000);
		testAlgo4.generateRandomArray();

		BigONotation testAlgo5 = new BigONotation(400000);
		testAlgo5.generateRandomArray();

		//O(N) Test
		testAlgo2.linearSearchForValue(20);
		testAlgo3.linearSearchForValue(20);
		testAlgo4.linearSearchForValue(20);
		testAlgo5.linearSearchForValue(20);

		// O(N^2) Test
		// This is much slower than O(N) due to the inner loop involved 
		// with each iteration of the items in the array.
		// It takes a lot longer for the N^2 bubble sort with each larger 
		// data set size increase.
		testAlgo2.bubbleSort();
		testAlgo3.bubbleSort();
		testAlgo4.bubbleSort();

		// O(log N) Test
		// This is a dramatic imporovement over O(N)
		// Measuring the time doesn't matter a lot with efficient algorithms since the 
		// time change is not very different. 
		// It only goes through the search process 9-10 times on average for 10k items
		// to find the desired value. 
		testAlgo2.binarySearchForValue(20);
		testAlgo3.binarySearchForValue(20);
		 
		// O(n log n) Test
		// It takes about 44 milleconds to find a value in an array of 300k elements.
		startTime = System.currentTimeMillis();
		testAlgo2.quickSort(0, testAlgo2.itemsInArray);
		endTime = System.currentTimeMillis();
		System.out.println("Quick Sort Took " + (endTime - startTime));
	}
}