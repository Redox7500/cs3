package io.github.redox7500.project2;

/** Queue ADT */
public interface Project2Queue<E>
{
    /** Reinitialize this queue. The user is responsible for reclaiming the storage used by the queue elements. */
    void clear();

    /**
     * Put a value in this queue
     * @param value The value to put in this queue
    */
    void enqueue(E value);

    /**
     * Take the front element out of this queue and return it
     * @return The front element of this queue
    */
    E dequeue();

    /**
     * Get the front element of this queue
     * @return The front element of this queue
     */
    E frontValue();
    
    /**
     * Get the length of this queue
     * @return The length of this queue
     */
    int length();
}
