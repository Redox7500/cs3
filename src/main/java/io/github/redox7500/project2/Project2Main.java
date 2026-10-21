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
        // Project2LinkedList<Integer> a = new Project2LinkedList<>();
        // a.append(1);
        // a.append(3);
        // a.append(5);
        // a.append(7);
        // a.append(null);
        // a.reverseOrder();
        // Project2LinkedList<Integer> b = new Project2LinkedList<>();
        // b.append(2);
        // b.append(3);
        // b.append(4);
        // b.append(5);
        // b.append(6);
        // System.out.println(Project2LinkedList.mergeLists(a, b));
        // Project2Dictionary<String, Integer> a = new Project2SortedArrayDictionary<>(100);
        // a.insert("a", 1);
        // a.removeAny();
        // a.insert("a", 1);
        // a.insert("b", 2);
        // a.insert("a", 5);
        // a.insert("d", 3);
        // a.insert("c", 3);
        // a.insert("a", 1);
        // a.insert("z", 2);
        // a.insert("x", 2);
        // a.insert("x", 2);
        // a.insert("x", 2);
        // a.insert("b", 3);
        // System.out.println(a.find("z"));
        // System.out.println(a.remove("a"));
        // System.out.println(a);

        Project2TowersOfHanoi a = new Project2TowersOfHanoi();
        a.solve(2);
        System.out.println(a);
        // a.moveTopDisk(0, 2);
        // a.moveTopDisk(0, 1);
        // // a.moveTopDisk(0, 2);
        // a.moveTopDisk(2, 1);
        // System.out.println(a);
    }
}
