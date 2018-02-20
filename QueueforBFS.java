package structures;
import java.util.NoSuchElementException;

/**
 * A refined version of a queue that is best suited for BFS purposes.
 * 
 * -------------
 * 
 * Версия очереди, удобная для использования в поиске в ширину.
 */

public class QueueforBFS<T>
{
	Node<T> head;
	Node<T> rear;
	int size;
	public QueueforBFS() 
	{		
        head = null;
        rear = null;
        size = 0;
    }
	public boolean isEmpty() 
	{
         return (size == 0);
	}
	public int size() 
	{
         return size;
	}
	public void enqueue(T element) 
	{
		 Node<T> newNode = new Node<T>(element);
         if(head == null)
         {
        	 head = newNode;
         }
         else
         {
        	 rear.setNext(newNode);
         }
         rear = newNode;
         size++;
	}
	public T dequeue() throws NoSuchElementException 
	{
		if(!isEmpty())
		{
			T res = head.getData();
			head = head.getNext();
			size--;
			return res;
		}
		else
		{
			throw new NoSuchElementException("The Queue is empty");
		}
	}
	public T peek() throws NoSuchElementException 
	{
        if(!isEmpty())
        {
        	return head.getData();
        }
        else throw new NoSuchElementException("The Queue is empty");
	}
}
