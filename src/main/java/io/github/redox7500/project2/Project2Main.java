package io.github.redox7500.project2;

public class Project2Main
{
    public static void main(String[] args)
    {
        // Project2Queue<Integer> queue = new Project2LinkedQueue<Integer>();

        // queue.enqueue(1);
        // System.out.println(queue);
        // System.out.println(queue.dequeue());
        // System.out.println(queue);
        // queue.dequeue();
        Project2LinkedList<Integer> a = new Project2LinkedList<>();
        a.append(1);
        a.append(3);
        a.append(5);
        a.append(7);
        a.append(null);
        // a.reverseOrder();
        Project2LinkedList<Integer> b = new Project2LinkedList<>();
        b.append(2);
        b.append(3);
        b.append(4);
        b.append(5);
        b.append(6);
        System.out.println(Project2LinkedList.mergeLists(a, b));
    }
}
