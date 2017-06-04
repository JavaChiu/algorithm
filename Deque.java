/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:  java Deque 
 *  Dependencies: StdIn.java StdOut.java
 *
 ******************************************************************************/

/*
 * Your deque implementation must support each deque operation in constant worst-case time. 
 * A deque containing n items must use at most 48n + 192 bytes of memory.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;

    private class Node {
        private Item value;
        private Node next;
        private Node previous;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null)
                throw new NoSuchElementException("the deque is empty");

            Item item = current.value;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("we don't support this method");
        }

    }

    public Deque() { // construct an empty deque

    }

    public boolean isEmpty() { // is the deque empty?
        return size == 0;
    }

    public int size() { // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) { // add the item to the front
        if (item == null)
            throw new NullPointerException("the item you add is null");

        Node newFirst = new Node();
        newFirst.value = item;
        newFirst.next = first;
        if (first != null)
            first.previous = newFirst;
        first = newFirst;
        first.previous = null;
        size++;
        if (size == 1)
            last = first;
    }

    public void addLast(Item item) { // add the item to the end
        if (item == null)
            throw new NullPointerException("the item you add is null");

        Node newLast = new Node();
        newLast.value = item;
        newLast.previous = last;
        if (last != null)
            last.next = newLast;
        last = newLast;
        last.next = null;
        size++;
        if (size == 1)
            first = last;
    }

    public Item removeFirst() { // remove and return the item from the front
        if (size == 0)
            throw new NoSuchElementException("the deque is empty");

        Node oldFirst = first;
        if (size > 1)
            first = oldFirst.next;
        if (size > 0)
            size--;
        if (size == 1)
            last = first;
        if (size == 0)
            last = null;
        return oldFirst.value;
    }

    public Item removeLast() { // remove and return the item from the end
        if (size == 0)
            throw new NoSuchElementException("the deque is empty");

        Node oldLast = last;
        if (size > 1)
            last = oldLast.previous;
        if (size > 0)
            size--;
        if (size == 1)
            first = last;
        if (size == 0)
            first = null;
        return oldLast.value;
    }

    public Iterator<Item> iterator() { // return an iterator over items in order
                                       // from front to end
        return new DequeIterator();
    }

    public static void main(String[] args) { // unit testing (optional)
        Deque<String> deque = new Deque<String>();
        while (!StdIn.isEmpty()) {
            deque.addFirst(StdIn.readLine());
            deque.addFirst(StdIn.readLine());
            deque.addLast(StdIn.readLine());
            deque.addLast(StdIn.readLine());

            Iterator<String> iterator = deque.iterator();
            while (iterator.hasNext()) {
                StdOut.print(iterator.next());
            }
        }
    }
}
