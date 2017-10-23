package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.BinaryTree;

/**
 * Class implements Binary Search Tree
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class BinarySearchTree<T extends Comparable<? super T>> extends BinaryTree<T> {

	/**
	 * This class implements the Entry of Binary Search Tree
	 * 
	 * @param <T>
	 */
	public static class Entry<T> extends BinaryTree.Entry<T> {

		/**
		 * Constructor to initialize each entry of BST
		 * 
		 * @param x
		 * @param left
		 * @param right
		 */
		public Entry(T x, Entry<T> left, Entry<T> right) {
			super(x, left, right);
		}
	}

	protected Stack<Entry<T>> stack;

	/**
	 * Iterates over all the nodes of Binary Search Tree
	 * 
	 * @return
	 */
	public Iterator<T> iterator() {
		return new BSTIterator<>((Entry<T>) root);
	}

	/**
	 * This class implements the iterator of BST
	 * 
	 * @param <E>
	 */
	private class BSTIterator<E> implements Iterator<E> {
		Stack<Entry<E>> stack;
		Entry<E> entry;

		/**
		 * Constructor to initialize the iterator of BST
		 * 
		 * @param root
		 */
		BSTIterator(Entry<E> root) {
			stack = new Stack<Entry<E>>();
			entry = root;
			while (entry != null) {
				stack.push(entry);
				entry = (Entry<E>) entry.left;
			}
		}

		public boolean hasNext() {
			return !stack.isEmpty();
		}

		public E next() {
			Entry<E> entry = stack.pop();
			E value = entry.element;
			if (entry.right != null) {
				entry = (Entry<E>) entry.right;
				while (entry != null) {
					stack.push(entry);
					entry = (Entry<E>) entry.left;
				}
			}
			return value;
		}
	}

	/**
	 * Adds new entry to the Binary Search Tree given an element
	 * 
	 * @param x
	 * @return
	 */
	public boolean add(T x) {
		return add(new Entry<T>(x, null, null));
	}

	/**
	 * Adds new entry to the Binary Search Tree given an entry
	 * 
	 * @param newEntry
	 * @return
	 */
	public boolean add(Entry<T> newEntry) {
		if (root == null) {
			root = newEntry;
			size = 1;
			return true;
		}

		Entry<T> entry = find(newEntry.element);
		if (newEntry.element.compareTo(entry.element) == 0) {
			entry.element = newEntry.element;
			return false;
		} else if (newEntry.element.compareTo(entry.element) < 0) {
			entry.left = newEntry;
			stack.push(entry);
		} else {
			entry.right = newEntry;
			stack.push(entry);
		}
		size++;
		return true;
	}

	/**
	 * Removes an entry from Binary Search Tree
	 * 
	 * @param x
	 * @return
	 */
	public T remove(T x) {
		if (root == null) {
			return null;
		}
		Entry<T> entry = find(x);
		if (entry.element.compareTo(x) != 0) {
			return null;
		}
		T result = entry.element;
		if (entry.left == null || entry.right == null) {
			byPass(entry);
		} else {
			stack.push(entry);
			Entry<T> minRight = find((Entry<T>) entry.right, entry.element);
			entry.element = minRight.element;
			byPass(minRight);
		}
		size--;
		return result;
	}

	/**
	 * Finds whether given element is present in the BST
	 * 
	 * @param x
	 * @return
	 */
	public Entry<T> find(T x) {
		stack = new Stack<Entry<T>>();
		stack.push(null);
		return find((Entry<T>) root, x);
	}

	/**
	 * Helper function for find method
	 * 
	 * @param entry
	 * @param x
	 * @return
	 */
	public Entry<T> find(Entry<T> entry, T x) {
		if (entry == null || entry.element == x) {
			return entry;
		}
		while (true) {
			if (x.compareTo(entry.element) < 0) {
				if (entry.left == null) {
					break;
				} else {
					stack.push(entry);
					entry = (Entry<T>) entry.left;
				}
			} else if (x.compareTo(entry.element) == 0) {
				break;
			} else {
				if (entry.right == null) {
					break;
				} else {
					stack.push(entry);
					entry = (Entry<T>) entry.right;
				}
			}

		}
		return entry;
	}

	/**
	 * Gets an element that is equal to x in the tree. Element in tree that is equal
	 * to x is returned, null otherwise.
	 */
	public T get(T x) {
		Entry<T> entry = find(x);
		return (entry != null && entry.element.compareTo(x) == 0) ? entry.element : null;
	}

	/**
	 * Check whether the given element is present in BST
	 * 
	 * @param x
	 * @return
	 */
	public boolean contains(T x) {
		Entry<T> entry = find(x);
		return entry != null && entry.element.compareTo(x) == 0;
	}

	/**
	 * Gets the minimum element of BST
	 * 
	 * @return
	 */
	public T min() {
		if (root == null)
			return null;
		Entry<T> entry = (Entry<T>) root;
		while (entry.left != null)
			entry = (Entry<T>) entry.left;
		return entry.element;
	}

	/**
	 * Gets the maximum element of BST
	 * 
	 * @return
	 */
	public T max() {
		if (root == null)
			return null;
		Entry<T> entry = (Entry<T>) root;
		while (entry.right != null)
			entry = (Entry<T>) entry.right;
		return entry.element;
	}

	/**
	 * Gets the height of the given entry in the tree
	 * 
	 * @param entry
	 * @return
	 */
	public int height(Entry<T> entry) {
		if (entry == null)
			return -1;
		int lh = height((Entry<T>) entry.left);
		int rh = height((Entry<T>) entry.right);
		return 1 + Math.max(lh, rh);
	}

	/**
	 * This method is a helper function for deletion operation for filling up the
	 * holes
	 * 
	 * @param entry
	 */
	public void byPass(Entry<T> entry) {

		Entry<T> parent = stack.peek();
		Entry<T> child = (Entry<T>) (entry.left == null ? entry.right : entry.left);
		if (parent == null) {// t is root
			root = child;
		} else if (parent.left == entry) {
			parent.left = child;

		} else {
			parent.right = child;
		}
	}

	public Comparable[] toArray() {
		Comparable[] arr = new Comparable[size];
		toArray((Entry<T>) root, arr, 0);
		return arr;
	}

	// Inorder traversal of tree
	int toArray(Entry<T> node, Comparable[] arr, int i) {
		if (node != null) {
			i = toArray((Entry<T>) node.left, arr, i);
			arr[i++] = node.element;
			i = toArray((Entry<T>) node.right, arr, i);
		}
		return i;
	}

	public void printTree() {
		System.out.print("[" + size + "]");
		inOrder(root);
		System.out.println();
	}

	/**
	 * Main method for testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BinarySearchTree<Integer> t = new BinarySearchTree<>();
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int x = in.nextInt();
			if (x > 0) {
				System.out.print("Add " + x + " : ");
				t.add(x);
				t.printTree();
			} else if (x < 0) {
				System.out.print("Remove " + x + " : ");
				t.remove(-x);
				t.printTree();
			} else {
				Comparable[] arr = t.toArray();
				System.out.print("Final: ");
				for (int i = 0; i < t.size; i++) {
					System.out.print(arr[i] + " ");
				}
				System.out.println();
				in.close();
				return;
			}
		}
		in.close();
	}

}
