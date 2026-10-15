package io.github.redox7500.project2;

public class Project2Main
{
    public static void main(String[] args)
    {
        Project2Queue<Integer> queue = new Project2LinkedQueue<Integer>();

        queue.enqueue(1);
        System.out.println(queue);
        System.out.println(queue.dequeue());
        System.out.println(queue);
        queue.dequeue();
    }
}
