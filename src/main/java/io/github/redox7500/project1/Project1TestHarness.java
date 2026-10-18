package io.github.redox7500.project1;

import java.util.ArrayList;
import java.util.function.Supplier;

/** Test harness to test CustomList implementations (specifically CustomList<Integer, ?> implementations) */
public class Project1TestHarness
{
    public static <E, T extends Project1List<E>> boolean test(Supplier<T> supplier1, int depth)
    {
        long startTime = System.nanoTime();

        boolean toReturn = new ThisState<E, T>(supplier1).branch(depth);
        System.out.print((toReturn)? "Success!" : "Failed!");
        System.out.printf(" (%.5f seconds elapsed)\n", (System.nanoTime() - startTime) / (float)1_000_000__000);

        return toReturn;
    }

    /** Expresses a state that a CustomList and its corresponding ArrayList can be in, along with arrays to keep track of the function call and argument histories */
    private static abstract class State<S extends Testable<S>, T extends S, U extends S>
    {
        abstract NamedTestFunction<S>[] getTestFunctions();
        // static TestFunction<S>[] testFunctions;
        abstract Object[][] generateArguments(int function);

        /** The instance of the CustomList being tested that resulted after the functionHistory/argumentHistory */
        S toTest1;

        /** An instance of ArrayList that resulted after the functionHistory/argumentHistory */
        S toTest2;

        /** An array containing all of the function calls leading up to this state */
        int[] functionHistory;

        /** An array containing all of the arguments used in the function calls leading up to this state */
        Object[][] argumentHistory; // maybe combine functionHistory and argumentHistory into an array of records?

        State()
        {
            this.functionHistory = new int[0];
            this.argumentHistory = new Object[0][];
        }

        /** Log all of the information of this State (with formatting to work with {@link #project1lists.TestHarness.testCustomLists testCustomLists}) */
        void printInfo()
        {
            toTest1.printRelevantInfo("First object");
            System.out.println();
            toTest2.printRelevantInfo("Second object");
            System.out.println();
            System.out.println("Function history (first at the top):");
            NamedTestFunction<S>[] functions = this.getTestFunctions();
            for (int i = 0; i < this.functionHistory.length; i++)
            {
                System.out.print('\t' + functions[this.functionHistory[i]].name() + '(');
                int argumentLength = this.argumentHistory[i].length;
                if (argumentLength > 0)
                {
                    System.out.print(this.argumentHistory[i][0]);
                    for (int j = 1; j < this.argumentHistory[i].length; j++) System.out.print(this.argumentHistory[i][j]);
                }
                System.out.println(')');
            }
            System.out.println();
        }

        abstract State<S, T, U> createInstance();

        /** Branch out from this state and add the new leaf states to leaves
         * @param leaves The deque to add the new leaf states to
         * @return A boolean representing whether any of the leaves had CustomLists that misbehaved or not
         */
        // final boolean branch(TestFunction<S>[] testFunctions, BiFunction<State<S, T, U>, Integer, Object[][]> argumentGenerator, int depth)
        final boolean branch(int depth)
        {
            if (depth == 0) return true;

            Project1TestHarness.NamedTestFunction<S>[] functions = this.getTestFunctions();
            for (int function = 0; function < functions.length; function++)
            {
                Object[][] arguments = this.generateArguments(function);
                for (Object[] argument : arguments)
                {
                    State<S, T, U> newState = this.createInstance();

                    newState.toTest1 = this.toTest1.copy();
                    newState.toTest2 = this.toTest2.copy();

                    int historyLength = this.functionHistory.length;

                    newState.functionHistory = new int[historyLength + 1];
                    System.arraycopy(this.functionHistory, 0, newState.functionHistory, 0, historyLength);
                    newState.functionHistory[historyLength] = function;

                    newState.argumentHistory = new Object[historyLength + 1][];
                    System.arraycopy(this.argumentHistory, 0, newState.argumentHistory, 0, historyLength);
                    newState.argumentHistory[historyLength] = argument;

                    Object result1 = null;
                    Object result2 = null;
                    boolean error1 = false;
                    boolean error2 = false;

                    try {result1 = functions[function].function().apply(newState.toTest1, argument);}
                    catch (Throwable _) {error1 = true;}
                    try {result2 = functions[function].function().apply(newState.toTest2, argument);}
                    catch (Throwable _) {error2 = true;}

                    if (error1 && error2) continue;
                    if (error1 && !error2)
                    {
                        System.out.println("Custom list had an error while ArrayListWrapper did not at state below");// \0337");
                        newState.printInfo();

                        return false;
                    }
                    if (!error1 && error2)
                    {
                        System.out.println("ArrayListWrapper had an error while custom list did not at state below");// \0337");
                        newState.printInfo();

                        return false;
                    }

                    if ((result1 == null)? result2 != null : !result1.equals(result2))
                    {
                        System.out.println("Results were not equal at state below");// \0337");
                        newState.printInfo();

                        return false;
                    }

                    switch (newState.toTest1.equalityTest(newState.toTest2))
                    {
                        case ERROR_THIS:
                            System.out.println("Custom list had an error when being compared to ArrayListWrapper");
                            newState.printInfo();

                            return false;
                        case ERROR_OTHER:
                            System.out.println("Custom list had an error when being compared to ArrayListWrapper");
                            newState.printInfo();

                            return false;
                        case UNEQUAL:
                            System.out.println("Lists were not equal at state below");
                            newState.printInfo();

                            return false;
                        case EQUAL:
                            if (!newState.branch(depth - 1)) return false;
                    }
                }
            }
            // make a copy with extra arg/function state thing?

            return true;
        }
    }

    private static class ThisState<E, T extends Project1List<E>> extends Project1TestHarness.State<Project1List<E>, T, ArrayListWrapper<E>>
    {
        ThisState() {}
        
        ThisState(Supplier<T> supplier)
        {
            this.toTest1 = supplier.get();
            this.toTest2 = new ArrayListWrapper<E>();
        }

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

    public interface Testable<T extends Testable<T>> extends Project1Copyable<T>
    {
        EqualityTestResult equalityTest(T other);
        void printRelevantInfo(String name);
        
        enum EqualityTestResult
        {
            ERROR_THIS,
            ERROR_OTHER,
            UNEQUAL,
            EQUAL
        }
    }

    @FunctionalInterface
    private interface TestFunction<T extends Testable<T>>
    {
        OptionalValue<?> apply(T toTest, Object[] args);
    }

    record NamedTestFunction<T extends Testable<T>>(String name, TestFunction<T> function) {}

    private record OptionalValue<T>(boolean empty, T value)
    {
        OptionalValue() {this(true, null);}

        OptionalValue(T value) {this(false, value);}
    }
}
