package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q1_KWaysMergeSort;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Shuffle;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort.Timer;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.MergeSortVariations;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Node;

/**
 * Class to implements Merge sort by splitting the array A 
 * into k fragments, sort them recursively, and merge them
 * using a priority queue
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class KWayMergeSort {

	
	/**
	 * This method merges all the sorted sub arrays using a priority queue
	 * 
	 * @param subA : int[][]: input sorted sub arrays 
	 * @param k :int : number of sub arrays
	 * @param sortedArray :int: output sorted Array
	 */
	public static void mergeKSortedArrays(int[][] subA, int k, int sortedArray[]){
		
		PriorityQueue<Node> queue = new PriorityQueue<>(k);
		int l = 0;
		int i = 0;
		for (int[] arr : subA) {
			Node n = new Node(arr[0], 1, i, arr.length);
			queue.add(n);
			i++;
		}
		
		Node tmp = queue.poll();
		while(tmp != null){
			
			sortedArray[l] = tmp.getElement();
			l++;
			if(tmp.hasNext()){
				tmp.setElement(subA);
				tmp.setNextIndex(tmp.getNextIndex()+1);
				queue.add(tmp);
			}
				tmp = queue.poll();
		
		}
		
	}
	
	/**
	 * This method partitions the array into k subarrays.
	 * 
	 * @param A : int[]: input array
	 * @param sizeOfSubarray : int: max size of sub array.
	 * @return : int[][]: sub arrays as 2D array.
	 */
	
	public static int[][] fragmentArray(int A[],int sizeOfSubarray){
		
		int k = (int)Math.ceil((double)A.length/sizeOfSubarray);
		
        int[][] subA = new int[k][];

        for(int i = 0; i < k; ++i) {
            int start = i * sizeOfSubarray;
            int length = Math.min(A.length - start, sizeOfSubarray);

            int[] temp = new int[length];
            System.arraycopy(A, start, temp, 0, length);
            subA[i] = temp;
        }
        
		return subA;
	}
	
	/**
	 * This method fragments the array and sorts them and then merges 
	 * all the sub arrays together
	 * 
	 * @param A
	 * @param k
	 * @return
	 */
	public static int[] kWaysMergeSort(int A[], int k){
		int subA[][] = fragmentArray(A, k);
		for (int[] array : subA) {
			MergeSortVariations.mergeSortVersion4(array);
		}
		int sortedArray[] = new int[A.length]; 
		mergeKSortedArrays(subA, subA.length, sortedArray);
		return sortedArray;
	}
	
	public static void generateArray(int arr[], int size) {
		int j = size;
		for (int i = 0; i < size ; i++) {
			arr[i] = j;
			j--;
		}
	}
	
	// Driver Code
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		int k = in.nextInt();
		int[] arr = new int[size];
		
		generateArray(arr, size);
		Shuffle.shuffle(arr);
		System.out.println(Arrays.toString(arr));
		System.out.println("\nMerge Sort K ways");
		
		Timer t = new Timer();
		int[] a = kWaysMergeSort(arr, k);
		t.end();
		System.out.println(Arrays.toString(a));
		System.out.println(t);
		in.close();

	}

}
