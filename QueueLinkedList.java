// Code taken from: http://eddmann.com/posts/implementing-a-queue-in-java-using-arrays-and-linked-lists
import java.util.Queue;

public class QueueLinkedList<T> {

    private Out history;

    private int total;
    private Node first, last;

    private final int maxsize;

    private class Node {
        private T ele;
        private Node next;
    }

    public QueueLinkedList(int size) { 
        maxsize = size;

        history = new Out("HISTORY.txt");
    }

    public QueueLinkedList<T> enqueue(T ele) {
        Node current = last;
        last = new Node();
        last.ele = ele;

        if (total++ == 0) first = last;
        else current.next = last;

        while(total > maxsize) {
            this.dequeue();
        }

        return this;
    }

    private void dequeue() {
        if (total == 0) throw new java.util.NoSuchElementException();
        T ele = first.ele;
        first = first.next;
        if (--total == 0) last = null;
        
        history.println(ele);
    }

    public void close() {
        history.println(this.toString());
        history.close();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Node tmp = first;
        while (tmp != null) {
            sb.append(tmp.ele).append("\n"); // latest last
            //sb.insert(0, tmp.ele).insert(0, "\n"); // latest first
            tmp = tmp.next;
        }
        return sb.toString();
    }

}