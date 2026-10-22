package io.github.redox7500.project2;

import java.util.function.Supplier;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;

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

        Project1TestHarness.test(() -> new ThisState<Integer>(() -> new Project2LinkedQueue<Integer>()), 12);

        // i should override static things or something like that (set static properties?) instead of making an extension of State i think maybe
        // Project1TestHarness.test(() -> new ThisState<String, Integer>(() -> new Project2SortedArrayDictionary<String, Integer>(12)), 12);
    }

    // this one is for the queues, just swap out this implementation for the one below if you want to test the queues instead of the dictionaries or vice versa
    private static class ThisState<E> extends Project1TestHarness.State<Project2Queue<E>, Project2Queue<E>, ArrayDequeWrapper<E>>
    {
        ThisState() {}
        
        ThisState(Supplier<Project2Queue<E>> supplier)
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
        protected ThisState<E> createInstance() {return new ThisState<E>();}
    }

    // private static class ThisState<K extends Comparable<? super K>, V> extends Project1TestHarness.State<Project2Dictionary<K, V>, Project2Dictionary<K, V>, HashMapWrapper<K, V>>
    // {
    //     ThisState() {}
        
    //     ThisState(Supplier<Project2Dictionary<K, V>> supplier)
    //     {
    //         this.toTest1 = supplier.get();
    //         this.toTest2 = new HashMapWrapper<K, V>();
    //     }

    //     @SuppressWarnings("unchecked")
    //     @Override
    //     protected NamedTestFunction<Project2Dictionary<K, V>>[] getTestFunctions()
    //     {
    //         // do something to make this not make a new array every single time maybe?
    //         // clear, insert, remove, removeAny, find, size
    //         return (NamedTestFunction<Project2Dictionary<K, V>>[])new NamedTestFunction<?>[]
    //         {
    //             new NamedTestFunction<Project2Dictionary<K, V>>("clear",     (l, a) -> {l.clear();                  return new OptionalValue<Object>();}),
    //             new NamedTestFunction<Project2Dictionary<K, V>>("insert",    (l, a) -> {l.insert((K)a[0], (V)a[1]); return new OptionalValue<Object>();}),
    //             new NamedTestFunction<Project2Dictionary<K, V>>("remove",    (l, a) -> new OptionalValue<Object>(l.remove((K)a[0]))), // could be OptionalValue<V> for specificity now that i think about it
    //             // new NamedTestFunction<Project2Dictionary<K, V>>("removeAny", (l, a) -> new OptionalValue<Object>(l.removeAny())), // removeAny can't really be tested i think, especially not here!
    //             new NamedTestFunction<Project2Dictionary<K, V>>("find",      (l, a) -> new OptionalValue<Object>(l.find((K)a[0]))),
    //             new NamedTestFunction<Project2Dictionary<K, V>>("size",      (l, a) -> new OptionalValue<Object>(l.size())),
    //         };
    //     }

    //     @Override
    //     protected Object[][] generateArguments(int function)
    //     {
    //         return switch (function)
    //         {
    //             case 1 -> new Object[][]{{("key" + (Integer)this.functionHistory.length).toString(), this.functionHistory.length}};
    //             case 2, 4 -> new Object[][]{{(K)((Integer)this.functionHistory.length).toString()}}; // probably want to change this but oh well
    //             default -> new Object[][]{{}};
    //         };
    //     }

    //     @Override
    //     protected ThisState<K, V> createInstance() {return new ThisState<K, V>();}
    // }

    private static class ArrayDequeWrapper<E> extends Project2AbstractQueue<E>
    {
        Queue<E> arrayDeque = new ArrayDeque<>();

        ArrayDequeWrapper() {super();}
        
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
        public ArrayDequeWrapper<E> copy()
        {
            ArrayDequeWrapper<E> toReturn = new ArrayDequeWrapper<>();

            toReturn.arrayDeque = new ArrayDeque<>(this.arrayDeque);

            return toReturn;
        }
    }

    private static class HashMapWrapper<K extends Comparable<? super K>, V> extends Project2AbstractDictionary<K, V>
    {
        Map<K, V> hashMap = new HashMap<>();

        // clear, insert, remove, removeAny, find, size, copy

        @Override
        public void clear() {this.hashMap.clear();}

        @Override
        public void insert(K key, V value) {this.hashMap.put(key, value);}

        @Override // i kinda wanna change these to not return null but error, that would allow for nulls and make people aware that it errored i think
        public V remove(K key) {return this.hashMap.remove(key);}

        @Override
        public V removeAny() {return (this.hashMap.size() > 0)? this.hashMap.remove(this.hashMap.keySet().iterator().next()) : null;}

        @Override
        public V find(K key) {return this.hashMap.get(key);}

        @Override
        public int size() {return this.hashMap.size();}

        @Override
        public HashMapWrapper<K, V> copy()
        {
            HashMapWrapper<K, V> toReturn = new HashMapWrapper<>();

            toReturn.hashMap = new HashMap<>(this.hashMap); // fix later, this is shallow i think but it should be at least 2 layers deep, maybe infinitely deep if i feel extra cool

            return toReturn;
        }

        @Override
        public String toString() {return this.hashMap.toString();} // this does a slightly different formatting thing than what i do but it works i guess

        @Override
        @SuppressWarnings("unchecked") // idk if this is the right ordering of these two annotations
        public K[] getKeys() {return (K[])this.hashMap.keySet().toArray(new Comparable[0]);} // people say to not use this overload of toArray but i think it's fine specifically for here
    }
}
