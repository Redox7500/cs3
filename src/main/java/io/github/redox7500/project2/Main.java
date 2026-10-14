package io.github.redox7500.project2;

public class Main
{
    public static void main(String[] args)
    {
        MyQueue<Integer> queue = new MyLinkedQueue<Integer>();

        queue.enqueue(1);
        System.out.println(queue);
        System.out.println(queue.dequeue());
        System.out.println(queue);
        queue.dequeue();
    }
}
