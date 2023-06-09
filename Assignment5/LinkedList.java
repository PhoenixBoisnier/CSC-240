import java.util.Iterator;
 /**
  * 
  * @author Phoenix Boisnier
  * 
  * Change Log:
  *  11/5/17
  *  -made methods recursive
  *
  * @param <T>
  */
public class LinkedList<T> implements Iterable<T> {
 
    public class LLIterator<U> implements Iterator<U>{
        LinkedList<U>.Node<U> current;
        LinkedList<U> ll;
 
        public LLIterator(LinkedList<U> ll){
            current = null;
            this.ll = ll;
        }
 
        public boolean hasNext(){
            if (current == null) return !ll.isEmpty();
            return current.next != null;
        }
 
        public U next(){
            if (current == null) current = ll.head;
            else current = current.next;
            return current.data;
        }
    }
    
    private class Node<V> {
        V data;
        Node<V> next;
 
        public Node(V data){
            this.data = data;
        }
 
        public Node(V data, Node<V> next){
            this(data);
            this.next = next;
        }
 
        public String toString(){
            return "" + data;
        }
    }
 
    private Node<T> head, tail;
    private int count;
 
    public LinkedList(){
        head = tail = null;
        count = 0;
    }
 
    public LinkedList(T d){
        head = tail = new Node<T>(d);
        count = 1;
    }
 
    public int length() {
        return count;
    }
 
    public boolean isEmpty(){
        return head == null;
    }
 
    public void append(T d){
        if (isEmpty()) 
            head = tail = new Node<T>(d);
        else 
            tail = tail.next = new Node<T>(d);
        count++;
    }
 
    public void prepend(T d){
        head = new Node<T>(d, head);
        count++;
    }
 
    public T getFirst() {
        return head.data;
    }
 
    public Iterator<T> iterator(){
        return new LLIterator<T>(this);
    }
 
    public boolean maxSize() {
    	if(count>=10) {
    		return true;
    	}
    	else return false;
    }
    
    public String toString(){
        String retVal = "";
 
        for (Node<T> temp = head ; temp != null ; temp = temp.next){
            retVal += temp.toString() + "\n";
        }
 
        return retVal;
    }
    
    /**
     * Inserts new data at index. Inserts data to head if list is empty.
     * @param data data to be inserted.
     * @param index the index it gets inserted to.
     */
    public void insert(T data, int index) {
    	if(index == 0) {
    		head = new Node<>(data, head);
    		count++;
    	}
    	else this.insertRec(data, index-1, head);
    /*	Node<T> temp = null;
    	Node<T> tempNext = null;
    	if(index>=count) {
    		System.out.println("Index out of bounds.");
    	}
    	else {
    		temp = this.head;
    		tempNext = temp.next;
    		if(index == 0) {
    			this.head = new Node<T>(data, this.head);
    			count++;
    		}
    		else {
    			for(int i = 0; i<count; i++) {
    				if(i==index-1) {
        				break;
        			}
        			temp = tempNext;
            		tempNext = tempNext.next;
        		}
        		System.out.println("Temp data: "+temp.data);
        		temp.next = new Node<T>(data, tempNext);
        		count++;
        	}
    	}*/
    }		
    
    /**
     * Recursively inserts data.
     * @param data data being inserted.
     * @param index the index being inserted at.
     * @param node the head.
     * @return
     */
    private boolean insertRec(T data, int index, Node<T> node) {
    	if(head == null) {
    		head = new Node<>(data);
    		return true;
    	}
    	else if(node == null || index < 0) {
    		return false;
    	}
    	else if(index == 0) {
    		Node<T> hold = node.next;
    		node.next = new Node<>(data, hold);
    		count++;
    		return true;
    	}
    	else return this.insertRec(data, index-1, node.next);
    }
    
    /**
     * Checks to see if the item in question exists in the list.
     * @param data the item being checked for.
     * @return true if it is in the list
     */
    public boolean exists(T data) {
    	return this.existsRec(data, head); 	
    /*	boolean val = false;
    	Node<T> temp = this.head;
    	while(temp!=null){
    		if(temp.data==data) {
    			val = true;
    		}
    		temp = temp.next;
    	}
    	return val; */
    }
    
    /**
     * Recursively checks for data.
     * @param data data being checked.
     * @param node begins at the head.
     * @return true if it is in the list.
     */
    private boolean existsRec(T data, Node<T> node) {
    	if(node==null) {
    		return false;
    	}
    	else if(node.data==data) {
    		return true;
    	}
    	else return this.existsRec(data, node.next);
    }
    
    /**
     * Removes the item from the list.
     * @param data the item to be removed.
     * @return true if the item was removed.
     */
    public boolean remove(T data) {
    	if(head != null) {
	    	if(head!=null && head.data==data) {
	    		head = head.next;
	    		count--;
	    		return true;
	    	}
	    	return this.removeRec(data, head, head.next);
    	}
    	else return false;
    /*	boolean val = this.exists(data);
    	if(this.length()==0) {
    		return false;
    	}
    	if(head.data==data) {
    		head = head.next;
    		val = true;
    		count--;
    	}
    	else {
        	Node<T> tempPrev = this.head;
        	Node<T> temp = tempPrev.next;
    		while(temp!=null) {
    			if(temp.data==data) {
    				tempPrev.next = temp.next;
    				val = true;
    				count--;
    				break;
    			}
    			tempPrev = tempPrev.next;
    			temp = tempPrev.next;
    		}
    	}
    	return val;		*/
    }
    
    /**
     * Recursively removes data.
     * @param data being removed.
     * @param prev the head
     * @param node
     * @return
     */
    private boolean removeRec(T data, Node<T> prev, Node<T> node) {
    	if(node == null) {
    		return false;
    	}
    	else if(node.data==data) {
    		prev.next = node.next;
    		count--;
    		return true;
    	}
    	else return this.removeRec(data, prev.next, node.next);
    }
    
    /**
     * Finds the item at index and returns it.
     * @param index the location in the linked list.
     * @return data at that location.
     */
    public T get(int index) {
    	return this.getRec(index, head);
    /*	Node<T> returnData = null;
    	if(index>=count) {
    		return null;
    	}
    	else {
    		returnData = this.head;
    		for(int i = 0; i<count; i++) {
    			if(i==index) {
    				break;
    			}
    			returnData = returnData.next;
    		}
    	}
    	return returnData.data; */
    }
    
    /**
     * Recursively finds data at index.
     * @param index the index.
     * @param node the head
     * @return null if index out of bounds, otherwise the data at index.
     */
    private T getRec(int index, Node<T> node) {
    	if(node == null || index < 0) {
    		return null;
    	}
    	else if(index == 0) {
    		return node.data;
    	}
    	else return this.getRec(index-1, node.next);
    }
}