/**
 * 
 * @author Phoenix Boisnier
 * 
 * Change-log:
 * 	11/25/2017
 * 	-finished kicking BST's remove method's teeth in
 * 	
 */
import java.util.ArrayList;
import java.util.Iterator;

public class BST<T extends Comparable<? super T>> implements Iterable<T> {
	
	private ArrayList<T> BSTList = new ArrayList<>();
	private BSTNode<T> root;
	private int labelCount;
	private int size;
	
	public class BSTIterator<V extends Comparable<? super V>> implements Iterator<V>{
		BST<V>.BSTNode<V> current;
        BST<V> bst;       
 
        public BSTIterator(BST<V> bst) {
            current = null;
            this.bst = bst;
            bst.labelAll();
        }
 
        public boolean hasNext(){
            if (current == null) return !bst.isEmpty();
            return current.getNext(bst) != null;
        }
 
        public V next(){
            if (current == null) current = bst.searchByLabel(bst.root, 0);
            else current = current.getNext(bst);
            return current.value;
        }
	}
	
	public Iterator<T> iterator() {
		return new BSTIterator<>(this);
	}
	
	 private class BSTNode<U extends Comparable<? super U>> {
		 	U value;
		 	int label;
	        BSTNode<U> left;
	        BSTNode<U> right;
	 
	        public BSTNode(U input){
	            this.value = (U) input;
	        }
	        
	        public BSTNode<T> getNext(BST<T> tree){
	        	return tree.searchByLabel(root, this.labelNext());
	        }
	        
	        private int labelNext() {
	        	int plus = label+1;
	        	return plus;
	        }
	        
	 
	        // Adapted from Todd Davies answer to printing a BST on Stack Overflow.
	        // https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
	        //	-modified characters of tree diagram
	        private StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb) {
	            if(right!=null) {
	                right.toString(new StringBuilder().append(prefix).append(isTail ? "|   " : "    "), false, sb);
	            }
	            sb.append(prefix).append(isTail ? "+-- " : "+-- ").append(value).append("\n");
	            if(left!=null) {
	                left.toString(new StringBuilder().append(prefix).append(isTail ? "    " : "|   "), true, sb);
	            }
	            return sb;
	        }

	        public String toString() {
	            return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
	        }
	    }
	 
	    private BSTNode<T> insert(BSTNode<T> curr, T value){
	        if (curr == null)
	            return new BSTNode<>(value);
	        if (value.compareTo(curr.value) > 0)
	            curr.right = insert(curr.right, value);
	        else if (value.compareTo(curr.value) < 0)
	            curr.left = insert(curr.left, value); 
	        return curr;
	    }
	 
	    public void insert(T value){
	        root = insert(root, value);
	        size++;
	    }
	    
	    /**
	     * Removes the specified node from the tree.
	     * @param value Value to be removed.
	     */
	    public void remove(T value) {
	    	//as long as the tree exists...
	    	if(root != null) {
	    		if(value.compareTo(root.value)!=0) {
	    			if(value.compareTo(root.value)<0) {
	    				this.removeRec(value, root, root.left);
	    			}
	    			else this.removeRec(value, root, root.right);
	    		}
	    		else {
	    			if(root.left==null) {
	    				root = root.right;
	    			}
	    			else {
	    				//we find the greatest lesser and save its data
		    			BSTNode<T> hold = this.findLesserGreatest(root.left);
		    			T valHold = hold.value;
		    			//because if it has children
		    			if(hold.left != null) {
		    				//we know it won't have a greater child, so it is now its lesser child
		    				hold = hold.left;
		    			}
		    			//root's children must be maintained, so root's value changes
		    			root.value = valHold;
		    			//then we kill the old version
		    			this.removeRec(valHold, root, root.left);
	    			}
	    		}
	    	}
	    	size--;
	    }
	    
	    /**
	     * Removes the root.
	     */
	    public void uproot() {
	    	if(root!=null) {
		    	this.remove(root.value);
	    	}
	    }
	    
	    /**
	     * A private method used to search for and destroy BSTNode with given value.
	     * @param value
	     * @param node
	     */
	    private void removeRec(T value, BSTNode<T> prev, BSTNode<T> node) {
	    	//as long as our current node exists
	    	if(node!=null) {
	    		//if the node is the node we are searching for
	    		if(node.value.compareTo(value)==0) {
	    			//if the node has both children
	    			if(node.left!=null && node.right!=null) {
	    				//we find the greatest lesser and save its data
		    			BSTNode<T> hold = this.findLesserGreatest(node.left);
		    			T valHold = hold.value;
		    			//because if it has children
		    			if(hold.left != null) {
		    				//we know it won't have a greater child, so it is now its lesser child
		    				hold = hold.left;
		    			}
		    			//root's children must be maintained, so root's value changes
		    			node.value = valHold;
		    			//then we kill the old version
		    			this.removeRec(valHold, node, node.left);
	    			}
	    			//if the node has only left child
	    			else if (node.right==null && node.left!=null) {
	    				if(this.isLeftChild(prev, node)) {
	    					prev.left = node.left;
	    				}
	    				if(this.isRightChild(prev, node)) {
	    					prev.right = node.left;
	    				}
	    			}
	    			//if the node only has right child
	    			else if (node.left==null && node.right!=null) {
	    				if(this.isLeftChild(prev, node)) {
	    					prev.left = node.right;
	    				}
	    				if(this.isRightChild(prev, node)) {
	    					prev.right = node.right;
	    				}
	    			}
	    			//otherwise the node has no children
	    			else {
	    				this.killChild(prev, node);
	    			}
	    		}
	    		//otherwise we keep looking
	    		else {
	    			//if the value is less than our current value, we go left
	    			if(value.compareTo(node.value)<0) {
	    				this.removeRec(value, node, node.left);
	    			}
	    			//otherwise it is less than 
	    			else {
	    				this.removeRec(value, node, node.right);
	    			}
	    		}
	    	}
	    }
	    
	    /**
	     * A private method that determines if the a node is a lesser child of another node.
	     * @param parent Another node.
	     * @param child A node.
	     * @return
	     */
	    private boolean isLeftChild(BSTNode<T> parent, BSTNode<T> child) {
	    	if(parent.left==null) {
	    		return false;
	    	}
	    	else if(child.value.compareTo(parent.left.value)==0) {
	    		return true;
	    	}
	    	else return false;
	    }
	    
	    /**
	     * A private method that determines if the a node is a greater child of another node.
	     * @param parent Another node.
	     * @param child A node.
	     * @return
	     */
	    private boolean isRightChild(BSTNode<T> parent, BSTNode<T> child) {
	    	if(parent.right==null) {
	    		return false;
	    	}
	    	else if(child.value.compareTo(parent.right.value)==0) {
	    		return true;
	    	}
	    	else return false;
	    }
	        
	    /**
	     * A private method that removes a child leaf node from a parent node.
	     * @param parent A node that has a child, child.
	     * @param child A node that is a child of a node, parent.
	     */
	    private void killChild(BSTNode<T> parent, BSTNode<T> child) {
	    	if(parent.left!=null) {
	    		if(parent.left.value.compareTo(child.value)==0) {
	    			parent.left = null;
	    		}
	    	}
	    	if(parent.right!=null) {
	    		if(parent.right.value.compareTo(child.value)==0) {
	    			parent.right = null;
	    		}
	    	}
	    }
	    
	    /**
	     * Finds the greatest value of the node given.
	     * @param node The node being searched. Left node.
	     * @return The greatest node of the least nodes.
	     */
	    private BSTNode<T> findLesserGreatest(BSTNode<T> node){
	    	if(node.right == null) {
	    		return node;
	    	}
	    	else return this.findLesserGreatest(node.right);
	    }
	 
	    private boolean search(BSTNode<T> curr, T value){
	        System.out.println("Visiting: " + (curr == null ? "null :(" : curr.value));
	        if (curr == null) return false;
	        if (curr.value.compareTo(value) == 0) return true;
	        if (value.compareTo(curr.value) > 0)
	            return search(curr.right, value);
	        return search(curr.left, value);
	    }
	 
	    public boolean search(T value){
	        return search(root, value);
	    }
	 
	    private void printInOrder(BSTNode<T> curr){
	        if (curr != null) {
	            // Print everything in left subtree
	            printInOrder(curr.left);
	            // Print curr.value
	            System.out.print(curr.value + " ");
	            // Print everything in right subtree
	            printInOrder(curr.right);
	        }
	    }
	    
	    private void labelAll() {
	    	labelCount = 0;
	    	this.labelInOrder(root);
	    }
	    
	    private void labelInOrder(BSTNode<T> node) {
	    	if(node!=null) {
	    		labelInOrder(node.left);
	    		node.label = labelCount;
	    		labelCount++;
	    		labelInOrder(node.right);
	    	}
	    }
	    
	    private BSTNode<T> searchByLabel(BSTNode<T> node, int label){
	    	if(node==null) {
	    		return null;
	    	}
	    	if(node.label==label) {
	    		return node;
	    	}
	    	if(node.label<label) {
	    		return this.searchByLabel(node.right, label);
	    	}
	    	if(node.label>label) {
	    		return this.searchByLabel(node.left, label);
	    	}
	    	return null;
	    }
	 
	    public void printInOrder(){
	        printInOrder(root);
	        System.out.println();
	    }
	 
	    public void printTree(){
	    	if(root!=null) {
	    		System.out.println(root.toString());
	    	}   
	    }
	   
	    private void listBuilder(BSTNode<T> node) {
	    	 if (node != null) {    
	    		 listBuilder(node.left);
	    		 BSTList.add(node.value);
	    		 listBuilder(node.right);
	    	 }
	    }
	    
	    /**
	     * Don't make me explain this.
	     * @return
	     */
	    public boolean isEmpty() {
	    	if(root==null) {
	    		return true;
	    	}
	    	else return false;
	    }
	    
	    /**
	     * Returns true if greater than or equal to 10.
	     * @return
	     */
	    public boolean maxSize() {
	    	if(size>=10) {
	    		return true;
	    	}
	    	else return false;
	    }
	    
	    /**
	     * Don't make me explain this.
	     * @return
	     */
	    public int getSize() {
	    	return size;
	    }
	    
	    /**
	     * Gives the tree in ArrayList form.
	     * @return
	     */
	    public ArrayList<T> getList(){
	    	BSTList = new ArrayList<>();
	    	this.listBuilder(root);
	    	return BSTList;
	    }
}
