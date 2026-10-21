package io.github.redox7500.project1;

import java.util.ArrayList;

import io.github.redox7500.project1.Project1TestHarness.*;

public class Project1Main
{
    public static void main(String[] args)
    {
        Project1TestHarness.test(() -> new ThisState<Integer, Project1ArrayList<Integer>>(), 7);
    }

    private static class ThisState<E, T extends Project1List<E>> extends Project1TestHarness.State<Project1List<E>, T, ArrayListWrapper<E>>
    {
        ThisState()
        {
            this.toTest1 = new Project1ArrayList<E>();
            this.toTest2 = new ArrayListWrapper<E>();
        }

        // ThisState() {}
        
        // ThisState(Supplier<T> supplier)
        // {
        //     this.toTest1 = supplier.get();
        //     this.toTest2 = new ArrayListWrapper<E>();
        // }

        @SuppressWarnings("unchecked")
        @Override
        Project1TestHarness.NamedTestFunction<Project1List<E>>[] getTestFunctions()
        {
            // do something to make this not make a new array every single time maybe?
            return (Project1TestHarness.NamedTestFunction<Project1List<E>>[])new Project1TestHarness.NamedTestFunction<?>[]
            {
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("size", (l, a) -> new OptionalValue<Integer>(l.size())),
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("getCurrentIndex", (l, a) -> new OptionalValue<Integer>(l.getCurrentIndex())),
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("moveCurrentIndexTo", (l, a) -> {l.moveCurrentIndexTo((int)a[0]); return new OptionalValue<Object>();}),
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("moveCurrentIndexToStart", (Project1TestHarness.TestFunction<Project1List<E>>)(l, a) -> {l.moveCurrentIndexToStart(); return new OptionalValue<Object>();}),
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("moveCurrentIndexToEnd", (l, a) -> {l.moveCurrentIndexToEnd(); return new OptionalValue<Object>();}),
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("moveCurrentIndexToPrevious", (l, a) -> {l.moveCurrentIndexToPrevious(); return new OptionalValue<Object>();}),
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("moveCurrentIndexToNext", (Project1TestHarness.TestFunction<Project1List<E>>)(l, a) -> {l.moveCurrentIndexToNext(); return new OptionalValue<Object>();}),
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("getCurrentValue", (Project1TestHarness.TestFunction<Project1List<E>>)(l, a) -> new OptionalValue<E>(l.getCurrentValue())),
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("setCurrentValue", (l, a) -> {l.setCurrentValue((E)a[0]); return new OptionalValue<Object>();}),
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("clear", (l, a) -> {l.clear(); return new OptionalValue<Object>();}),
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("insert", (l, a) -> {l.insert((E)a[0]); return new OptionalValue<Object>();}),
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("append", (l, a) -> {l.append((E)a[0]); return new OptionalValue<Object>();}),
                new Project1TestHarness.NamedTestFunction<Project1List<E>>("remove", (l, a) -> new OptionalValue<Object>(l.remove()))
            };
        }

        @Override
        Object[][] generateArguments(int function)
        {
            return switch (function)
            {
                case 2 -> 
                {
                    int size = this.toTest2.size();
                    Object[][] toReturn = new Object[size + 2][1];
                    for (int i = 0; i < size + 2; i++) toReturn[i] = new Object[]{i - 1};

                    yield toReturn;
                }
                case 8, 10, 11 -> new Object[][]{{this.functionHistory.length}};
                default -> new Object[][]{{}};
            };
        }

        @Override
        ThisState<E, T> createInstance() {return new ThisState<E, T>();}
    }

    private static class ArrayListWrapper<E> extends Project1AbstractList<E>
    {
        ArrayList<E> arrayList = new ArrayList<>();
        int currentIndex = 0;

        ArrayListWrapper() {super();}

        @Override
        public void clear() {this.arrayList.clear(); this.currentIndex = 0;}

        @Override
        public void insert(E value) {if (this.size() != 0) this.arrayList.add(this.currentIndex, value); else this.append(value);}

        @Override
        public void append(E value) {this.arrayList.add(value);}

        @Override
        public E remove() {E toReturn = this.arrayList.remove(this.currentIndex); if (this.currentIndex == this.size() && this.currentIndex != 0) this.currentIndex--; return toReturn;}

        @Override
        public E getCurrentValue() {return this.arrayList.get(this.currentIndex);}

        @Override
        public void setCurrentValue(E value) {if (this.size() != 0) this.arrayList.set(this.currentIndex, value); else this.append(value);}

        @Override
        public int getCurrentIndex() {return this.currentIndex;}

        @Override
        public void moveCurrentIndexTo(int index) {assert (this.size() > 0)? index >= 0 && index < this.size() : index == 0 : "Index out of range"; this.currentIndex = index;}

        @Override
        public int size() {return this.arrayList.size();}

        @Override
        public ArrayListWrapper<E> copy()
        {
            ArrayListWrapper<E> toReturn = new ArrayListWrapper<>();
            if (this.size() != 0) toReturn.arrayList = new ArrayList<>(this.arrayList); // i know this makes an extra array list shut up i'm lazy
            toReturn.currentIndex = this.currentIndex;

            return toReturn;
        }
    }
}
