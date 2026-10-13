package io.github.redox7500.project2;

// Linked queue implementation
class MyLinkedQueue<E> implements MyQueue<E> {
    private Link<E> front; // Pointer to front queue node
    private Link<E> rear; // Pointer to rear queuenode
    int size; // Number of elements in queue
    
    public MyLinkedQueue() {this.front = this.rear = null; this.size = 0;}

    // Reinitialize queue
    public void clear() {this.front = this.rear = null; this.size = 0;}

    // Put element on rear
    public void enqueue(E it)
    {
        Link<E> newLink = Link.acquire(it);
        if (this.size == 0) this.front = this.rear = newLink;
        else this.rear.nextLink = newLink; this.rear = this.rear.nextLink;

        this.size++;
    }

    // remove element from front
    public E dequeue()
    {
        assert this.size != 0 : "Queue is empty";

        E it = this.front.value; // Store dequeued value

        Link<E> newFrontLink = this.front.nextLink;
        this.front.release();
        this.front = newFrontLink;
        if (this.front == null) this.rear = null;

        this.size--;

        return it; // Return Object
    }

    // Get front element
    public E frontValue() {assert size != 0 : "Queue is empty"; return this.front.value;}

    // Return length
    public int length() {return this.size;}

    private static class Link<E>
    {
        /** Linked list of nodes that are not currently being used and can be reused */
        private static Link<?> freeList;

        /** Value for this node */
        private E value = null;

        /** Pointer to next node in list */
        private Link<E> nextLink = null;

        Link() {}

        private static <E> Link<E> acquire()
        {
            if (freeList == null) return new Link<>();
            
            @SuppressWarnings("unchecked")
            Link<E> toReturn = (Link<E>)freeList;
            freeList = freeList.nextLink;

            return toReturn;
        }

        private static <E> Link<E> acquire(E value)
        {
            Link<E> toReturn = Link.acquire();
            toReturn.value = value;
            toReturn.nextLink = null;

            return toReturn;
        }

        @SuppressWarnings("unchecked")
        private void release()
        {
            this.value = null;
            this.nextLink = (Link<E>)Link.freeList;
            Link.freeList = this;
        }
    }
}
