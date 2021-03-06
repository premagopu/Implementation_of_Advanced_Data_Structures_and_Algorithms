package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q5_BinaryHeapSort;

import java.util.Comparator;

/**
 * This class implements the Binary Heap using an array.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 * @param <T>
 *
 */
public class BinaryHeap<T> {

	T array[];
	int heapSize = 0;
	static int ZERO_INDEX = 0;
	Comparator<T> comparator;

	/**
	 * Constructor to create a new binary heap with the given capacity and
	 * priority set based on the comparator.
	 *
	 * @param capacity
	 * @param comparator
	 */
	// TODO: Remove
	@SuppressWarnings("unchecked")
	public BinaryHeap(int capacity, Comparator<T> comparator) {
		array = (T[]) new Object[capacity];
		this.comparator = comparator;
	}

	/**
	 * Constructor to construct a binary heap within the given array priority
	 * set based on the comparator.
	 *
	 * @param A
	 * @param comparator
	 */
	public BinaryHeap(T a[], Comparator<T> comparator, int n) {
		array = a;
		heapSize = n;
		this.comparator = comparator;
		buildHeap();
	}

	private int parent(int i) {
		return (i - 1) >> 1;
	}

	int leftChild(int i) {
		return (i << 1) + 1;
	}

	int rightChild(int i) {
		return (i << 1) + 2;
	}

	/**
	 * Percolates down the hole. Takes O(lg n) time. When it is called,
	 * percolateDown assumes that the binary trees rooted at LEFT.i and RIGHT.i
	 * satisfy the order property, but heap order property may be violated at
	 * index i with its immediate children. PercolateDown lets the value at A[i]
	 * �float down� in the heap so that the subtree rooted at index i obeys the
	 * heap order property.
	 *
	 * @param i
	 */
	void percolateDown(int i) {
		T x = array[i];
		int smallest = leftChild(i);
		while (smallest < heapSize) {
			if (smallest < heapSize - 1 && comparator.compare(array[smallest], array[smallest + 1]) == 1)
				smallest++;
			if (comparator.compare(x, array[smallest]) < 1)
				break;
			array[i] = array[smallest];
			i = smallest;
			smallest = leftChild(i);
		}
		array[i] = x;
	}

	/**
	 * Heap order property may be violated at the index i with its parent. This
	 * method will be called during the addition of a new element into the heap.
	 *
	 * @param i
	 */
	void percolateUp(int i) {
		T x = array[i];
		while (i > 0 && comparator.compare(x, array[parent(i)]) < 0) {
			array[i] = array[parent(i)];
			i = parent(i);
		}
		array[i] = x;
	}

	public void insert(T x) {
		add(x);
	}

	public T deleteMin() {
		return remove();
	}

	public T min() {
		return peek();
	}

	/**
	 * Adds the given element into the binary heap and preserves and structure
	 * and heap order property.
	 *
	 * @param x
	 */
	public void add(T x) {
		if (heapSize == array.length)
			throw new IllegalStateException("Heap is full");
		array[heapSize] = x;
		percolateUp(heapSize);
		heapSize++;
	}

	/**
	 * Fact : for a binary heap, elements from A[floor(n/2)+1,...n] represent
	 * the leaf nodes. So before calling buildMinHeap each of those elements
	 * represent a 1-element minheap. As said in the comment of minheapify these
	 * elements already satisfy min-heap order property, but when we add add an
	 * element from A[n/2], heap order property will be violated and thus we
	 * call minheap(i); Similarly we continue for n/2-1,n/2-2,...1.
	 *
	 * @param A
	 */
	void buildHeap() {
		for (int i = heapSize / 2 - 1; i >= ZERO_INDEX; i--)
			percolateDown(i);
	}

	/**
	 * Removes the min element (i.e., maximum priority element) from the binary
	 * heap and preserves the structure and order property through
	 * percolateDown.
	 *
	 * @return
	 */
	public T remove() {
		if (heapSize == 0)
			throw new IllegalStateException("Heap is Empty");
		T root = array[ZERO_INDEX];
		array[ZERO_INDEX] = array[heapSize--];
		percolateDown(ZERO_INDEX);
		return root;
	}

	/**
	 * Returns the min element (i.e., maximum priority element) from the binary
	 * heap.
	 *
	 * @return
	 */
	public T peek() {
		if (heapSize == 0)
			throw new IllegalStateException("Heap is Empty");
		return array[ZERO_INDEX];
	}

	/**
	 * Replaces root of binary heap by x if x has higher priority (smaller) than
	 * root, and restore heap order. Otherwise do nothing. This operation is
	 * used in finding largest k elements in a stream.
	 *
	 * @param x
	 */
	public void replace(T x) {
		array[ZERO_INDEX] = x;
		percolateDown(ZERO_INDEX);
	}

	/**
	 * Returns the array representation of the binary heap.
	 *
	 * @return
	 */
	public T[] toArray() {
		return array;
	}

	/**
	 * Get the minimum element from the root node one by one and then minHeapify
	 * to preserve the heap order structure - O(n log n)
	 *
	 * Sorted order depends on comparator used to buid heap. min heap ==>
	 * descending order max heap ==> ascending order
	 */
	public static <T> void heapSort(T[] A, Comparator<T> comp) {
		BinaryHeap<T> heap = new BinaryHeap<>(A, comp, A.length);
		// buildHeap() method has already called in the constructor.
		T tmp = heap.peek();
		for (int i = 1; i < heap.array.length; i++) {
			heap.array[ZERO_INDEX] = heap.array[heap.heapSize - 1];
			heap.array[heap.heapSize - 1] = tmp;
			heap.heapSize--;
			heap.percolateDown(ZERO_INDEX);
			tmp = heap.array[ZERO_INDEX];
		}
	}
}
