/******************************************************************************
 *  Compilation: javac RandomizedQueue.java 
 *  Execution: java RandomizedQueue
 *  Dependencies: StdIn.java StdOut.java StdRandom.java
 *
 ******************************************************************************/

/*
 * Your randomized queue implementation must support each randomized queue
 * operation (besides creating an iterator) in constant amortized time. That is,
 * any sequence of m randomized queue operations (starting from an empty queue)
 * should take at most cm steps in the worst case, for some constant c. A
 * randomized queue containing n items must use at most 48n + 192 bytes of
 * memory.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int numberOfItem;

    public RandomizedQueue() { // construct an empty randomized queue
        array = (Item[]) new Object[2];
    }

    public boolean isEmpty() { // is the queue empty?
        return numberOfItem == 0;
    }

    public int size() { // return the number of items on the queue
        return numberOfItem;
    }

    public void enqueue(Item item) { // add the item
        if (item == null)
            throw new NullPointerException("Emm...could you not add an empty item?");

        if (numberOfItem == array.length) {
            Item[] temp = (Item[]) new Object[numberOfItem * 2];
            for (int i = 0; i < array.length; i++)
                temp[i] = array[i];
            array = temp;
        }
        array[numberOfItem++] = item;
    }

    public Item dequeue() { // remove and return a random item
        if (numberOfItem == 0)
            throw new NoSuchElementException("Oops! seems like the queue is empty...");

        int randomNum = StdRandom.uniform(numberOfItem);
        Item randomItem = array[randomNum];
        for (int i = randomNum; i < array.length - 1; i++)
            array[i] = array[i + 1];
        array[numberOfItem-1] = null;

        if (--numberOfItem <= array.length / 4) {
            Item[] temp = (Item[]) new Object[array.length / 2];
            for (int i = 0; i < numberOfItem; i++)
                temp[i] = array[i];
            array = temp;
        }

        return randomItem;
    }

    public Item sample() { // return (but do not remove) a random item
        if (numberOfItem == 0)
            throw new NoSuchElementException("Oops! seems like the queue is empty...");

        int randomNum = StdRandom.uniform(numberOfItem);
        return array[randomNum];
    }

    public Iterator<Item> iterator() { // return an independent iterator over
                                       // items in random order
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) { // unit testing (optional)
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while (true) {
            StdOut.println("What would you like to do now? Add/Remove/Show One/Show All/Exit");
            String cmd = StdIn.readLine();
            if (cmd.equals("Add")) {
                StdOut.println("Ok then, please enter a string.");
                randomizedQueue.enqueue(StdIn.readLine());
                continue;
            } 
            else if (cmd.equals("Remove")) {
                StdOut.println(randomizedQueue.dequeue());
                continue;
            } 
            else if (cmd.equals("Show One")) {
                StdOut.println(randomizedQueue.sample());
                continue;
            } 
            else if (cmd.equals("Show All")) {
                Iterator<String> iterator = randomizedQueue.iterator();
                while (iterator.hasNext()) {
                    StdOut.println(iterator.next());
                }
                continue;
            } 
            else if (cmd.equals("Exit")) {
                break;
            }
        }
        StdOut.println("Thank you bye!");
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int n;

        @Override
        public boolean hasNext() {
            return n < numberOfItem;
        }

        @Override
        public Item next() {
            if (n >= numberOfItem)
                throw new NoSuchElementException("How many do you want?");

            return array[n++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Uh-uh...we don't use this method anymore, neither should you ;)");
        }

    }
}