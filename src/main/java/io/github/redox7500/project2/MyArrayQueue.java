package io.github.redox7500.project2;

import java.lang.StringBuilder;

// Array-based queue implementation
class MyArrayQueue<E> extends MyAbstractQueue<E>
{
    private static final int defaultSize = 10;
    private int size;
    private int front; // Index of front element
    private E[] array; // Array holding queue elements
    
    MyArrayQueue() {this(MyArrayQueue.defaultSize);}

    @SuppressWarnings("unchecked") // For generic array
    MyArrayQueue(int size)
    {
        this.size = 0;
        this.front = 1;
        this.array = (E[])new Object[size]; // Create listArray
    }

    // Reinitialize
    @Override
    public void clear() {this.front = 0; this.size = 0;}

    // Put "it" in queue
    @Override
    public void enqueue(E it)
    {
        assert this.size != this.array.length - 1 : "Queue is full";

        int index = this.front + this.size++;
        if (index >= this.array.length) index -= this.array.length;

        this.array[index] = it; // could make modulo a piecewise thing (more than or less than this.array.length) for performance theoretically -edit i did that
    }

    // Take element out of queue
    @Override
    public E dequeue()
    {
        assert this.size != 0 : "Queue is empty";

        E it = this.array[this.front++];
        
        if (this.front == this.array.length) this.front = 0;

        this.size--;

        return it;
    }

    // Get front value
    @Override
    public E frontValue() {assert this.size != 0 : "Queue is empty"; return this.array[this.front];}

    // Return length
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
}
