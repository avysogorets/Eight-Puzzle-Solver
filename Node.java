

/**
 * The implementation of Nodes, which is later used to realize a queue.
 * 
 * @author artemvysogorets
 *
 */

public class Node<T> {

	private T data;

	private Node<T> next;

	public Node(T data) {
		this.data = data;
	}

	public Node(T data, Node<T> next) {
		this.data = data;
		this.next = next;
	}

	public T getData() {
		return data;
	}

	public Node<T> getNext() {
		return next;
	}


	public void setData(T data) {
		this.data = data;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}
}
