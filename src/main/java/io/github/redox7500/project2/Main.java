package io.github.redox7500.project2;

public class Main
{
    public static void main(String[] args)
    {
        MyQueue<Integer> queue = new MyLinkedQueue<Integer>();

        queue.enqueue(0);
        System.out.println(queue.dequeue());
        queue.dequeue();
    }
}
