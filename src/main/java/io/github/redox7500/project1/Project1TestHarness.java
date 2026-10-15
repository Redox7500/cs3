package io.github.redox7500.project1;

import java.util.ArrayList;
import java.util.function.Supplier;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Future;
// import java.util.concurrent.Executors;

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

    // /** Test the given CustomList(s) and print the outputs nicely
    //  * @param constructors The constructors of the CustomList(s) you want to test
    //  * @param names The names of the CustomList(s) you want to test (in the same order as the constructors)
    //  */
    // @SuppressWarnings("rawtypes")
    // public static <E, S extends Testable<S>, T extends S, U extends S> void testCustomList(Supplier<T> testConstructor, Supplier<U> verifiedConstructor, Supplier<E> valueGenerator, String name)
    // {
    //     System.out.print("\033[?25l");

    //     System.out.print(name);
    //     AsyncSpinner.start();
        
    //     System.out.print("\033[25G\033[25G");
    //     AsyncSpinner.start();

    //     long startTime = System.nanoTime();
    //     @SuppressWarnings("unchecked")
    //     boolean result = new State<S, T, U>(testConstructor, verifiedConstructor).branch(7);
    //     String elapsedTime = String.format("%.5f", (System.nanoTime() - startTime) / (float)1_000_000_000);

    //     AsyncSpinner.stop();
    //     if (result)
    //     {
    //         System.out.println("Success (" + elapsedTime + " seconds elapsed)");
    //     }
    //     else
    //     {
    //         System.out.println("(" + elapsedTime + " seconds elapsed)");
    //     }

    //     AsyncSpinner.kill();
    //     System.out.print("\033[?25h");
    // }

    /** Expresses a state that a CustomList and its corresponding ArrayList can be in, along with arrays to keep track of the function call and argument histories */
    private static abstract class State<S extends Testable<S>, T extends S, U extends S>
    {
        abstract TestFunction<S>[] getTestFunctions();
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
            toTest2.printRelevantInfo("Second object");
            System.out.println("Function history (first at the top):");
            for (int i = 0; i < this.functionHistory.length; i++)
            {
                System.out.println("\t" + )
            }
        }

        // State.Evaluation evaluate()
        // {
        //     // TestHarness.CustomListMethod<?> function = TestHarness.customListMethods[this.functionHistory[this.functionHistory.length - 1]];
        //     TestHarness.TestFunction<S> function = this.getTestFunctions()[this.functionHistory.length - 1]; // could make the index a variable for both here, it's also known as depth i guess
        //     Object[] argument = this.argumentHistory[this.argumentHistory.length - 1];

        //     Object result1 = null;
        //     Object result2 = null;
        //     boolean error1 = false;
        //     boolean error2 = false;

        //     try {result1 = function.apply(this.toTest1, argument);}
        //     catch (Throwable _) {error1 = true;}
        //     try {result2 = function.apply(this.toTest2, argument);}
        //     catch (Throwable _) {error2 = true;}

        //     if (error1 && error2) return State.Evaluation.ERROR_BOTH;
        //     if (error1 && !error2) return State.Evaluation.ERROR_CUSTOM_LIST;
        //     if (arrayListWrapperError && !customListError) return State.Evaluation.ERROR_ARRAY_LIST_WRAPPER;
        //     if ((customListResult == null)? arrayListWrapperResult != null : !customListResult.equals(arrayListWrapperResult)) return State.Evaluation.UNEQUAL;

        //     int customListSize;
        //     int customListCurrentIndex;
        //     Optional<E> customListCurrentValue;
        //     try
        //     {
        //         customListSize = customList.size();
        //         customListCurrentIndex = customList.getCurrentIndex();
        //         customListCurrentValue = (customListSize == 0)? Optional.empty() : Optional.of(customList.getCurrentValue());
        //     }
        //     catch (Throwable _)
        //     {
        //         return State.Evaluation.INEVALUABLE_CUSTOM_LIST;
        //     }
        //     int arrayListWrapperSize = arrayListWrapper.size();
        //     Optional<E> arrayListWrapperCurrentValue = (arrayListWrapperSize == 0)? Optional.empty() : Optional.of(arrayListWrapper.getCurrentValue());
        //     if (
        //         customListSize != arrayListWrapperSize ||
        //         customListCurrentIndex != this.arrayListWrapper.getCurrentIndex() ||
        //         !customListCurrentValue.equals(arrayListWrapperCurrentValue)
        //     ) return State.Evaluation.UNEQUAL;

        //     int customListIndex;
        //     try {customListIndex = customList.getCurrentIndex();}
        //     catch (Throwable _) {return State.Evaluation.INEVALUABLE_CUSTOM_LIST;}
            
        //     try {customList.moveCurrentIndexToStart();}
        //     catch (Throwable _) {return State.Evaluation.INEVALUABLE_CUSTOM_LIST;}
        //     for (int i = 0; i < arrayListWrapperSize; i++)
        //     {
        //         E c;
        //         try {c = customList.getCurrentValue();}
        //         catch (Throwable _) {return State.Evaluation.INEVALUABLE_CUSTOM_LIST;}
        //         if (c != arrayListWrapper.arrayList.get(i)) {return State.Evaluation.UNEQUAL;}
        //         if (i < arrayListWrapperSize - 1)
        //         {
        //             try {customList.moveCurrentIndexToNext();}
        //             catch (Throwable _) {return State.Evaluation.INEVALUABLE_CUSTOM_LIST;}
        //         }
        //     }
        //     try {customList.moveCurrentIndexTo(customListIndex);} // if this errors i don't even know dude
        //     catch (Throwable _) {return State.Evaluation.INEVALUABLE_CUSTOM_LIST;}

        //     return State.Evaluation.SUCCESS;
        // }

        // State.Evaluation extend()
        // {

        // }

        abstract State<S, T, U> createInstance();

        /** Branch out from this state and add the new leaf states to leaves
         * @param leaves The deque to add the new leaf states to
         * @return A boolean representing whether any of the leaves had CustomLists that misbehaved or not
         */
        // final boolean branch(TestFunction<S>[] testFunctions, BiFunction<State<S, T, U>, Integer, Object[][]> argumentGenerator, int depth)
        final boolean branch(int depth)
        {
            // System.out.println(depth);
            if (depth == 0) return true;

            // for (int currentFunction = 0; currentFunction < testFunctions.length; currentFunction++)
            // {
            //     // int firstArgument, lastArgument;
            //     // int argumentStep = 1;
            //     // switch (TestHarness.customListMethods[currentFunction])
            //     // {
            //     //     case TestHarness.CustomListMethod.CustomListIndexConsumer _ -> {firstArgument = -1; lastArgument = this.verifiedList.size();} // change args to be based on inputs to testharness method?
            //     //     case TestHarness.CustomListMethod.CustomListValueConsumer<?> _ -> firstArgument = lastArgument = this.functionHistory.length;
            //     //     default -> firstArgument = lastArgument = 0;
            //     // }
            //     Object[][] arguments = argumentGenerator.apply(this, currentFunction);
            //     for (int currentArgument = 0; currentArgument < arguments.length; currentArgument++)
            //     {
            //         State<S, T, U> newState; //= new State<S, T, U>(this, currentFunction, arguments[currentArgument]);
            //         try {newState = this.getClass().getDeclaredConstructor().newInstance();}
            //         catch (Throwable _) {System.err.println("TestHarness.State subclass has no visible parameterless constructor");}
            //         switch (newState.evaluate())
            //         {
            //             case State.Evaluation.ERROR_BOTH:
            //                 continue;
            //             case State.Evaluation.ERROR_CUSTOM_LIST:
            //                 System.out.println("Custom list had an error while ArrayListWrapper did not at state below\0337");
            //                 newState.printInfo();

            //                 return false;
            //             case State.Evaluation.ERROR_ARRAY_LIST_WRAPPER:
            //                 System.out.println("ArrayListWrapper had an error while custom list did not at state below\0337");
            //                 newState.printInfo();

            //                 return false;
            //             case State.Evaluation.INEVALUABLE_CUSTOM_LIST:
            //                 System.out.println("Custom list had an error when being compared to ArrayListWrapper\0337");
            //                 newState.printInfo();

            //                 return false;
            //             case State.Evaluation.UNEQUAL:
            //                 System.out.println("Lists were not equal at state below\0337");
            //                 newState.printInfo();

            //                 return false;
            //             case State.Evaluation.SUCCESS:
            //                 if (depth > 1 && !newState.branch(testFunctions, argumentGenerator, depth - 1)) return false;
            //         }
            //     }
            // }

            // return true;
            Project1TestHarness.TestFunction<S>[] functions = this.getTestFunctions();
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
                    // if (this.functionHistory.length == 2) System.out.println(this.functionHistory[1]);
                    System.arraycopy(this.functionHistory, 0, newState.functionHistory, 0, historyLength);
                    newState.functionHistory[historyLength] = function;

                    newState.argumentHistory = new Object[historyLength + 1][];
                    System.arraycopy(this.argumentHistory, 0, newState.argumentHistory, 0, historyLength);
                    newState.argumentHistory[historyLength] = argument;

                    Object result1 = null;
                    Object result2 = null;
                    boolean error1 = false;
                    boolean error2 = false;

                    try {result1 = functions[function].apply(newState.toTest1, argument);}
                    catch (Throwable _) {error1 = true;}
                    try {result2 = functions[function].apply(newState.toTest2, argument);}
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
                            System.out.println("Custom list had an error when being compared to ArrayListWrapper");// \0337");
                            newState.printInfo();

                            return false;
                        case ERROR_OTHER:
                            System.out.println("Custom list had an error when being compared to ArrayListWrapper");// \0337");
                            newState.printInfo();

                            return false;
                        case UNEQUAL:
                            System.out.println("Lists were not equal at state below");// \0337");
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
        // static TestHarness.TestFunction<CustomList<E>>[] testFunctions;
        // ThisState(State<CustomList<E>, T, ArrayListWrapper<E>> state, int function, Object[] argument)
        // {
        //     super(state, function, argument);
        // }

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
//             "size",                    
// "getCurrentIndex",         
// "moveCurrentIndexTo",      
// "moveCurrentIndexToStart", 
// "moveCurrentIndexToEnd",   
// "moveCurrentIndexLeft",    
// "moveCurrentIndexRight",   
// "getCurrentValue",         
// "setCurrentValue",         
// "clear",                   
// "insert",                  
// "append",                  
// "remove",                  
            // return (TestFunction<CustomList<E>>[])new TestFunction<?>[]
            // {

            // };
        }

        @Override
        Object[][] generateArguments(int function)
        {
            // // probably a switch here
            return switch (function)
            {
                case 2, 8 -> 
                {
                    int size = this.toTest2.size();
                    Object[][] toReturn = new Object[size + 2][1];
                    for (int i = 0; i < size + 2; i++) toReturn[i] = new Object[]{i - 1};

                    yield toReturn;
                }
                case 10, 11 -> new Object[][]{{this.functionHistory.length}};
                default -> new Object[][]{{null}};
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

    // /** Fancy spinning line character */
    // private static class AsyncSpinner
    // {
    //     /** Character frames to play for animating the spinner */
    //     static final char[] frames = {'-', '\\', '|', '/'};

    //     /** Thread to animate the spinner on (so that other operations can be run while the spinner is animating) */
    //     static ExecutorService executor;

    //     /** Current task that animates the spinner (if null there is no spinner) */
    //     static Future<?> task;

    //     /** Play AsyncSpinner.frames (should not be used outside of AsyncSpinner) */
    //     static void animate()
    //     {
    //         int frame = 0;
    //         while (!Thread.currentThread().isInterrupted())
    //         {
    //             System.out.print(frames[frame] + "\033[1D");
    //             frame = (frame + 1) % frames.length;
    //             try {Thread.sleep(100);}
    //             catch (InterruptedException _) {Thread.currentThread().interrupt();}
    //         }
    //     }

    //     /** Start the spinner's animation (to be used outside) */
    //     static void start()
    //     {
    //         if (AsyncSpinner.executor == null) AsyncSpinner.executor = Executors.newSingleThreadExecutor();
    //         AsyncSpinner.task = executor.submit(AsyncSpinner::animate);
    //     }

    //     /** End the spinner's animation */
    //     static void stop() {if (AsyncSpinner.task != null) AsyncSpinner.task.cancel(true);}

    //     /** Completely kill the spinner's thread */
    //     static void kill() {if (AsyncSpinner.executor != null) AsyncSpinner.executor.shutdownNow();}
    // }

    public interface Testable<T extends Testable<T>> extends Project1Copyable<T>
    {
        // static <T extends Testable<T>> FullTestResult fullTest(Testable<T> toTest1, Testable<T> toTest2, TestFunction<T>[] testFunctions, Object[] args)
        // {
        //     for (TestFunction<T> testFunction : testFunctions)
        //     {
        //         Testable<T> toTest1Copy = toTest1.copy();
        //         Testable<T> toTest2Copy = toTest2.copy();

        //         Object result1 = null;
        //         Object result2 = null;
        //         boolean error1 = false;
        //         boolean error2 = false;

        //         try {result1 = testFunction.apply(toTest1Copy, args);}
        //         catch (Throwable _) {error1 = true;}
        //         try {result2 = testFunction.apply(toTest2Copy, args);}
        //         catch (Throwable _) {error2 = true;}

        //         if (error1 && error2) continue;
        //         if (error1 && !error2) return FullTestResult.ERROR_1;
        //         if (!error1 && error2) return FullTestResult.ERROR_2;
        //         switch (toTest1Copy.equalityTest(toTest2Copy))
        //         {
        //             case ERROR_1: return FullTestResult.INEVALUABLE_1;
        //             case ERROR_2: return FullTestResult.INEVALUABLE_2;
        //             case UNEQUAL: return FullTestResult.UNEQUAL;
        //             case EQUAL:
        //                 Testable.fullTest(toTest1Copy, toTest2Copy, testFunctions, )
        //         }
        //     }
        // }

        EqualityTestResult equalityTest(T other);
        void printRelevantInfo(String name);
        
        enum EqualityTestResult
        {
            ERROR_THIS,
            ERROR_OTHER,
            UNEQUAL,
            EQUAL
        }

        // enum FullTestResult
        // {
        //     ERROR_BOTH,
        //     ERROR_1,
        //     ERROR_2,
        //     INEVALUABLE_1,
        //     INEVALUABLE_2,
        //     UNEQUAL,
        //     SUCCESS
        // }
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
