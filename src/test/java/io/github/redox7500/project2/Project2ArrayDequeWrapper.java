package io.github.redox7500.project2;

import java.util.ArrayDeque;
import java.util.Queue;

public class Project2ArrayDequeWrapper<E> extends Project2AbstractQueue<E>
{
    Queue<E> arrayDeque = new ArrayDeque<>();

    Project2ArrayDequeWrapper() {super();}
    
    @Override
    public void clear() {this.arrayDeque.clear();}
    
    @Override
    public void enqueue(E value) {this.arrayDeque.offer(value);}

    @Override
    public E dequeue() {assert this.arrayDeque.size() != 0 : "Queue is empty"; return this.arrayDeque.poll();}
    
    @Override
    public E frontValue() {return this.arrayDeque.element();}

    @Override
    public int length() {return this.arrayDeque.size();}
    
    @Override
    public String toString() {return this.arrayDeque.toString();}

    @Override
    public Project2ArrayDequeWrapper<E> copy()
    {
        Project2ArrayDequeWrapper<E> toReturn = new Project2ArrayDequeWrapper<>();

        toReturn.arrayDeque = new ArrayDeque<>(this.arrayDeque);

        return toReturn;
    }
}
