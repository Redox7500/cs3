package io.github.redox7500.project2;

// Array-based queue implementation
class MyArrayQueue<E> implements MyQueue<E>
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
    public void clear() {this.front = 0; this.size = 0;}

    // Put "it" in queue
    public void enqueue(E it)
    {
        assert this.size != this.array.length - 1 : "Queue is full";

        int index = this.front + this.size++;
        if (index >= this.array.length) index -= this.array.length;

        this.array[index] = it; // could make modulo a piecewise thing (more than or less than this.array.length) for performance theoretically -edit i did that
    }

    // Take element out of queue
    public E dequeue()
    {
        assert this.size != 0 : "Queue is empty";

        E it = this.array[this.front++];
        
        if (this.front == this.array.length) this.front = 0;

        this.size--;

        return it;
    }

    // Get front value
    public E frontValue() {assert this.size != 0 : "Queue is empty"; return this.array[this.front];}

    // Return length
    public int length() {return this.size;}
}
