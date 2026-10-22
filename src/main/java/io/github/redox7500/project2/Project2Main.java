package io.github.redox7500.project2;

import java.util.function.Supplier;
import java.util.ArrayDeque;

import io.github.redox7500.project1.Project1ArrayList;
import io.github.redox7500.project1.Project1TestHarness;
import io.github.redox7500.project1.Project1TestHarness.NamedTestFunction;
import io.github.redox7500.project1.Project1TestHarness.OptionalValue;

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

        // Project2TowersOfHanoi a = new Project2TowersOfHanoi();
        // a.solve(2);
        // System.out.println(a);
        // a.moveTopDisk(0, 2);
        // a.moveTopDisk(0, 1);
        // // a.moveTopDisk(0, 2);
        // a.moveTopDisk(2, 1);
        // System.out.println(a);

        Project1TestHarness.test(() -> new ThisState<Integer, Project2LinkedQueue<Integer>>(() -> new Project2LinkedQueue<Integer>()), 12);
    }

    private static class ThisState<E, T extends Project2Queue<E>> extends Project1TestHarness.State<Project2Queue<E>, T, ArrayDequeWrapper<E>>
    {
        ThisState() {}
        
        ThisState(Supplier<T> supplier)
        {
            this.toTest1 = supplier.get();
            this.toTest2 = new ArrayDequeWrapper<E>();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected NamedTestFunction<Project2Queue<E>>[] getTestFunctions()
        {
            // do something to make this not make a new array every single time maybe?
            return (NamedTestFunction<Project2Queue<E>>[])new NamedTestFunction<?>[]
            {
                new NamedTestFunction<Project2Queue<E>>("clear",      (l, a) -> {l.clear();          return new OptionalValue<Object>();}),
                new NamedTestFunction<Project2Queue<E>>("enqueue",    (l, a) -> {l.enqueue((E)a[0]); return new OptionalValue<Object>();}),
                new NamedTestFunction<Project2Queue<E>>("dequeue",    (l, a) -> new OptionalValue<Object>(l.dequeue())),
                new NamedTestFunction<Project2Queue<E>>("frontValue", (l, a) -> new OptionalValue<Object>(l.frontValue())),
                new NamedTestFunction<Project2Queue<E>>("length",     (l, a) -> new OptionalValue<Object>(l.length())),
            };
        }

        @Override
        protected Object[][] generateArguments(int function)
        {
            return switch (function)
            {
                case 1 -> new Object[][]{{this.functionHistory.length}};
                default -> new Object[][]{{}};
            };
        }

        @Override
        protected ThisState<E, T> createInstance() {return new ThisState<E, T>();}
    }

    private static class ArrayDequeWrapper<E> extends Project2AbstractQueue<E>
    {
        ArrayDeque<E> arrayDeque = new ArrayDeque<>();

        ArrayDequeWrapper() {super();}
        
        @Override
        public void clear() {this.arrayDeque.clear();}
        
        @Override
        public void enqueue(E value) {this.arrayDeque.offer(value);}

        @Override
        public E dequeue() {assert this.arrayDeque.size() != 0 : "Queue is empty"; return this.arrayDeque.poll();}
        
        @Override
        public E frontValue() {return this.arrayDeque.getFirst();}

        @Override
        public int length() {return this.arrayDeque.size();}
        
        @Override
        public String toString() {return this.arrayDeque.toString();}

        @Override
        public ArrayDequeWrapper<E> copy()
        {
            ArrayDequeWrapper<E> toReturn = new ArrayDequeWrapper<>();

            toReturn.arrayDeque = new ArrayDeque<>(this.arrayDeque);

            return toReturn;
        }
    }
}
