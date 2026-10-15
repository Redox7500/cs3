package io.github.redox7500.project2;

import io.github.redox7500.project1.Project1Link;

// Linked queue implementation
class Project2LinkedQueue<E> extends Project2AbstractQueue<E>
{
    private Project1Link<E> front; // Pointer to front queue node
    private Project1Link<E> rear; // Pointer to rear queuenode
    private int size; // Number of elements in queue
    
    public Project2LinkedQueue() {this.front = this.rear = null; this.size = 0;}

    // Reinitialize queue
    @Override
    public void clear() {this.front = this.rear = null; this.size = 0;}

    // Put element on rear
    @Override
    public void enqueue(E it)
    {
        Project1Link<E> newLink = Project1Link.acquire(it);
        if (this.size == 0) this.front = this.rear = newLink;
        else this.rear.nextLink = newLink; this.rear = this.rear.nextLink;

        this.size++;
    }

    // remove element from front
    @Override
    public E dequeue()
    {
        assert this.size != 0 : "Queue is empty";

        E it = this.front.value; // Store dequeued value

        Project1Link<E> newFrontLink = this.front.nextLink;
        this.front.release();
        this.front = newFrontLink;
        if (this.front == null) this.rear = null;

        this.size--;

        return it; // Return Object
    }

    // Get front element
    @Override
    public E frontValue() {assert size != 0 : "Queue is empty"; return this.front.value;}

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
            toReturn.append(this.front.value);
            Project1Link<E> currentLink = this.front;
            for (int i = 1; i < this.size; i++)
            {
                currentLink = currentLink.nextLink;
                toReturn.append(", ");
                toReturn.append(currentLink.value);
            }
        }
        toReturn.append(']');

        return toReturn.toString();
    }
}
