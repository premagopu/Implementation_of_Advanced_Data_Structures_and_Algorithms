package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q1_MergeSort;

/**
 * Class MergeSort
 * 
 * <P> This class performs merge sort on a generic array 
 * of size ranging from 1M - 16M
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class MergeSortGeneric<T> {
	
	public static<T extends Comparable<? super T>> void mergeSort(T[] arr,T[] tmp) {
		divide(arr,tmp,0,arr.length-1);
	}
	
	private static<T extends Comparable<? super T>> void divide(T[] arr,T[] tmp, int l,int r){ //Divide method to carry out the divide phase of divide and conquer strategy
		if(l<r) {
			int mid = (l+r)/2;
			
			divide(arr,tmp,l,mid);
			divide(arr,tmp,mid+1,r);

			merge(arr,tmp,l,mid,r);
		}	
	}
	
	private static<T extends Comparable<? super T>> void merge(T[] arr,T[] tmp, int l,int m,int r) { //Merge method to carry out the conquer phase of divide and conquer strategy
		for(int i=0;i<=r;i++)
			tmp[i]=arr[i];
		
		int i=l,j=m+1,k=l;
		
		while(i<=m && j<=r) {
			if (tmp[i].compareTo(tmp[j])<=0) {
				arr[k]=tmp[i];
				i++;
			}
			else {
				arr[k]=tmp[j];
				j++;
			}
			k++;
		}
		while(i<=m) {
			arr[k]=tmp[i];
			k++;
			i++;
		}
		
	}
	
	public static void main(String args[]) {
		
		int n = 100000;
	    Integer[] integerArr = new Integer[n];
	    for(int i=0; i<n; i++) {
	    		integerArr[i] = new Integer(i+1);
		}
	    System.out.println("Array Creation Completed");
	    Shuffle.shuffle(integerArr);
//	    System.out.println("Printing Shuffled Array");
//	    for(Integer i:arr)
//			System.out.print(i+"  ");
	    
		Timer t = new Timer();
		mergeSort(integerArr,new Integer[integerArr.length]);
		System.out.println(t.end());
		//for(Integer i:arr)
		//	System.out.print(i+"  ");
		
//		t.start();
//		mergeSort(doubleArr,new Double[doubleArr.length]);
//		System.out.println(t.end());
//		for(Double i:doubleArr)
//			System.out.print(i+"  ");
		
	}

}
