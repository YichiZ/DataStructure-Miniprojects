package source;

import java.util.ArrayList;
import java.util.List;

public class RadixTree {

	public static void main(String[] args) {
		/*
		 * Add the keys in the RadixTree
		 */
		ArrayList keys = new ArrayList();
		int[] key = {0};
		keys.add(key);
		int[] key2 = {0,1,1};
		keys.add(key2);
		int[] key3 = {1,0};
		keys.add(key3);
		int[] key4 = {1,0,0};
		keys.add(key4);
		int[] key5 = {1,0,1,1};
		keys.add(key5);
		
		/*
		 * Add the node in the RadixTree
		 */
		RadixTree radixTree = new RadixTree();
		Node<int[]> root = new Node<>();
		root.isRoot = true;
		radixTree.constructTree(keys, root);
		radixTree.preOrder(root);
	}
	
	/*
	 * Preorder Tree Traversal Search: 1. Visit node 2. preorder(leftnode) 3. preorder(rightnode)
	 */
	public static void preOrder(Node<int[]> currentNode) {
		if(currentNode == null)
		{
			return;
		}
		
		/*
		 * Output a key, then set the node to visited
		 */
		if (currentNode.isValidKey)
		{
			currentNode.print();
			currentNode.isValidKey = false;
		}
		
		preOrder(currentNode.getChild(currentNode, false));
		preOrder(currentNode.getChild(currentNode, true));

	}
	
	/*
	 * Construct the tree
	 */
	public void constructTree(ArrayList keys, Node<int[]> root)
	{
		RadixTree radixTree = new RadixTree();
		for(int i = 0; i < keys.size(); i ++)
		{
			int[] key = (int[]) keys.get(i);
			radixTree.addChildNode(key, root, key);
		}
	}
	
	/*
	 * remove the first element of and array
	 */
	public int[] removeFirstElement(int[] oldArray)
	{
		int[] newArray = new int[oldArray.length - 1];
		
		for (int i = 0; i < newArray.length; i ++)
		{
			newArray[i] = oldArray[i+1];
		}
		return newArray;
	}
	
	public void addChildNode(int[] key, Node<int[]> currentNode, int[] fullKey)
	{
		if(key.length == 0)
		{
			currentNode.data = fullKey;
			currentNode.isValidKey = true;
		}
		if(key.length != 0)
		{
			if(key[0] == 0)
			{
				Node<int[]> childNode;
				
				if(currentNode.hasLeftChild == true)
				{
					Node<int[]> child = currentNode.getChild(currentNode, false);
					key = this.removeFirstElement(key);
					this.addChildNode(key, child, fullKey);
					
					
				}
				
				if(currentNode.hasLeftChild == false)
				{
					childNode = new Node<>(false);
					currentNode.hasLeftChild = true;
					currentNode.addChild(childNode);
					key = this.removeFirstElement(key);
					this.addChildNode(key, childNode, fullKey);
				}
				
			}
			
			else if(key[0] == 1)
			{
				Node<int[]> childNode;
				
				if(currentNode.hasRightChild == true)
				{
						Node<int[]> child = currentNode.getChild(currentNode, true);
						key = this.removeFirstElement(key);
						this.addChildNode(key, child, fullKey);
				}
				
				if(currentNode.hasRightChild == false)
				{
					childNode = new Node<>(true);
					currentNode.hasRightChild = true;
					currentNode.addChild(childNode);
					key = this.removeFirstElement(key);
					this.addChildNode(key, childNode, fullKey);
				}
			}
		}
	}
	
	
}

class Node<T> {

	public T data;
	public List<Node<T>> children;
	public boolean isRoot = false;
	public boolean isRightChild = false;
	public boolean isValidKey = false;
	public boolean hasRightChild = false;
	public boolean hasLeftChild = false;
	
	public Node(boolean isRightChild)
	{
		if (isRightChild)
		{
			this.isRightChild = true;
		}

	}
	public Node() {
		super();
	}

	/**
	 * Convenience ctor to create a Node<T> with an instance of T.
	 * 
	 * @param data
	 *            an instance of T.
	 */
	public Node(T data) {
		this();
		setData(data);
	}
	
	public Node<int[]> getChild (Node<int[]> currentNode, boolean isRightChild)
	{
		for (int i = 0; i < currentNode.getNumberOfChildren(); i ++)
		{
			Node<int[]> child = currentNode.children.get(i);
			if(child.isRightChild == isRightChild)
			{
				return child;
			}
		}
		
		return null;
	}

	/**
	 * Return the children of Node<T>. The Tree<T> is represented by a
	 * single root Node<T> whose children are represented by a List<Node
	 * <T>>. Each of these Node<T> elements in the List can have children.
	 * The getChildren() method will return the children of a Node<T>.
	 * 
	 * @return the children of Node<T>
	 */
	public List<Node<T>> getChildren() {
		if (this.children == null) {
			return new ArrayList<Node<T>>();
		}
		return this.children;
	}

	/**
	 * Sets the children of a Node<T> object. See docs for getChildren() for
	 * more information.
	 * 
	 * @param children
	 *            the List<Node<T>> to set.
	 */
	public void setChildren(List<Node<T>> children) {
		this.children = children;
	}

	/**
	 * Returns the number of immediate children of this Node<T>.
	 * 
	 * @return the number of immediate children.
	 */
	public int getNumberOfChildren() {
		if (children == null) {
			return 0;
		}
		return children.size();
	}

	/**
	 * Adds a child to the list of children for this Node<T>. The addition
	 * of the first child will create a new List<Node<T>>.
	 * 
	 * @param child
	 *            a Node<T> object to set.
	 */
	public void addChild(Node<T> child) {
		if (children == null) {
			children = new ArrayList<Node<T>>();
		}
		children.add(child);
	}

	/**
	 * Inserts a Node<T> at the specified position in the child list. Will *
	 * throw an ArrayIndexOutOfBoundsException if the index does not exist.
	 * 
	 * @param index
	 *            the position to insert at.
	 * @param child
	 *            the Node<T> object to insert.
	 * @throws IndexOutOfBoundsException
	 *             if thrown.
	 */
	
	public void insertChildAt(int index, Node<T> child) throws IndexOutOfBoundsException {
		if (index == getNumberOfChildren()) {
			// this is really an append
			addChild(child);
			return;
		} else {
			children.get(index); // just to throw the exception, and stop
									// here
			children.add(index, child);
		}
	}

	/**
	 * Remove the Node<T> element at index index of the List<Node<T>>.
	 * 
	 * @param index
	 *            the index of the element to delete.
	 * @throws IndexOutOfBoundsException
	 *             if thrown.
	 */
	public void removeChildAt(int index) throws IndexOutOfBoundsException {
		children.remove(index);
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void print()
	{
		int[] data = (int[]) this.data;
		
		for (int i = 0; i < data.length; i ++)
		{
			System.out.print(data[i]);
		}
		
		System.out.print(" ");
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{").append(getData().toString()).append(",[");
		int i = 0;
		for (Node<T> e : getChildren()) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(e.getData().toString());
			i++;
		}
		sb.append("]").append("}");
		return sb.toString();
	}
}

class Tree<T> {

	private Node<T> rootElement;

	/**
	 * Default creator.
	 */
	public Tree() {
		super();
	}

	/**
	 * Return the root Node of the tree.
	 * 
	 * return the root element.
	 */
	public Node<T> getRootElement() {
		return this.rootElement;
	}

	/**
	 * Set the root Element for the tree.
	 * 
	 * param rootElement the root element to set.
	 */
	public void setRootElement(Node<T> rootElement) {
		this.rootElement = rootElement;
	}

	/**
	 * Returns the Tree<T> as a List of Node<T> objects. The elements of the
	 * List are generated from a pre-order traversal of the tree.
	 * 
	 * return a List<Node<T>>.
	 */
	public List<Node<T>> toList() {
		List<Node<T>> list = new ArrayList<Node<T>>();
		walk(rootElement, list);
		return list;
	}

	/**
	 * Returns a String representation of the Tree. The elements are
	 * generated from a pre-order traversal of the Tree.
	 * 
	 * @return the String representation of the Tree.
	 */
	public String toString() {
		return toList().toString();
	}

	/**
	 * Walks the Tree in pre-order style. This is a recursive method, and is
	 * called from the toList() method with the root element as the first
	 * argument. It appends to the second argument, which is passed by
	 * reference * as it recurses down the tree.
	 * 
	 * @param element
	 *            the starting element.
	 * @param list
	 *            the output of the walk.
	 */
	private void walk(Node<T> element, List<Node<T>> list) {
		list.add(element);
		for (Node<T> data : element.getChildren()) {
			walk(data, list);
		}
	}
}
