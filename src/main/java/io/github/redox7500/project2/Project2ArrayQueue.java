package io.github.redox7500.project2;

import java.lang.StringBuilder;

/** Array-based queue implementation */
public class Project2ArrayQueue<E> extends Project2AbstractQueue<E>
{
    /** The default maximum size of this queue implementation */
    private static final int DEFAULT_SIZE = 10;

    /** Size of the queue (which will not always be as long as the entire array) */
    private int size;

    /** Index of the front element */
    private int front;

    /** Array holding this queue's elements */
    private E[] array;

    @SuppressWarnings("unchecked") // For generic array
    Project2ArrayQueue(int size)
    {
        this.size = 0;
        this.front = 1;
        this.array = (E[])new Object[size];
    }

    public Project2ArrayQueue() {this(Project2ArrayQueue.DEFAULT_SIZE);}
    
    @Override
    public void clear() {this.front = 0; this.size = 0;}
    
    @Override
    public void enqueue(E value)
    {
        assert this.size != this.array.length : "Queue is full";

        int index = this.front + this.size++;
        if (index >= this.array.length) index -= this.array.length;

        this.array[index] = value;
    }

    @Override
    public E dequeue()
    {
        assert this.size != 0 : "Queue is empty";

        E it = this.array[this.front++];
        
        if (this.front == this.array.length) this.front = 0;

        this.size--;

        return it;
    }
    
    @Override
    public E frontValue() {assert this.size != 0 : "Queue is empty"; return this.array[this.front];}

    @Override
    public int length() {return this.size;}
    
    @Override
    public String toString()
    {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append('[');
        if (this.size > 0)
        {
            toReturn.append(this.array[this.front]);
            for (int i = this.front + 1; i < this.front + this.size; i++)
            {
                toReturn.append(", ");
                toReturn.append(this.array[(i >= this.array.length)? i - this.array.length : i]);
            }
        }
        toReturn.append(']');

        return toReturn.toString();
    }

    @Override
    public Project2ArrayQueue<E> copy()
    {
        Project2ArrayQueue<E> toReturn = new Project2ArrayQueue<>(this.array.length);

        toReturn.array = this.array.clone();
        toReturn.size = this.size;
        toReturn.front = this.front;

        return toReturn;
    }
}
