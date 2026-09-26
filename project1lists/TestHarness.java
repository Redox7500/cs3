package project1lists;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.Comparator;
import java.util.function.Predicate;

// change leaves thing to be recursion to save memory?
/** Test harness to test CustomList implementations (specifically CustomList<Integer, ?> implementations) */
public class TestHarness
{
    /** A list of all of the CustomListMethods to be tested */
    private static final CustomListMethod<?>[] customListMethods = new CustomListMethod[]{
        new TestHarness.CustomListMethod.CustomListIndexSupplier  ("size",                    CustomList::size),
        new TestHarness.CustomListMethod.CustomListIndexSupplier  ("getCurrentIndex",         CustomList::getCurrentIndex),
        new TestHarness.CustomListMethod.CustomListIndexConsumer  ("moveCurrentIndexTo",      CustomList::moveCurrentIndexTo),
        new TestHarness.CustomListMethod.CustomListRunnable       ("moveCurrentIndexToStart", CustomList::moveCurrentIndexToStart),
        new TestHarness.CustomListMethod.CustomListRunnable       ("moveCurrentIndexToEnd",   CustomList::moveCurrentIndexToEnd),
        new TestHarness.CustomListMethod.CustomListRunnable       ("moveCurrentIndexLeft",    CustomList::moveCurrentIndexLeft),
        new TestHarness.CustomListMethod.CustomListRunnable       ("moveCurrentIndexRight",   CustomList::moveCurrentIndexRight),
        new TestHarness.CustomListMethod.CustomListValueSupplier<>("getCurrentValue",         CustomList::getCurrentValue),
        new TestHarness.CustomListMethod.CustomListValueConsumer<>("setCurrentValue",         CustomList::setCurrentValue),
        new TestHarness.CustomListMethod.CustomListRunnable       ("clear",                   CustomList::clear),
        new TestHarness.CustomListMethod.CustomListValueConsumer<>("insert",                  CustomList::insert),
        new TestHarness.CustomListMethod.CustomListValueConsumer<>("append",                  CustomList::append),
        new TestHarness.CustomListMethod.CustomListValueSupplier<>("remove",                  CustomList::remove)
    };

    /** Test the given CustomList(s) and print the outputs nicely
     * @param constructors The constructors of the CustomList(s) you want to test
     * @param names The names of the CustomList(s) you want to test (in the same order as the constructors)
     */
    @SuppressWarnings("rawtypes")
    public static void testCustomLists(Supplier[] constructors, String[] names)
    {
        System.out.print("\033[?25l");

        int customListCount = constructors.length;
        if (customListCount != names.length) return;

        int maxNameLength = 0;
        for (String name : names) maxNameLength = Math.max(maxNameLength, name.length());
        for (String name : names) System.out.println(String.format("%-" + (maxNameLength + 2) + "s", name + ": ") + "-");

        System.out.print("\033[" + customListCount + "A");
        AsyncSpinner.start();

        for (int i = 0; i < customListCount; i++)
        {
            System.out.print("\033[25G\033[25G");
            AsyncSpinner.start();

            long startTime = System.nanoTime();
            @SuppressWarnings("unchecked")
            boolean result = TestHarness.testCustomList(constructors[i], 7);
            String elapsedTime = String.format("%.5f", (System.nanoTime() - startTime) / (float)1_000_000_000);

            AsyncSpinner.stop();
            if (result)
            {
                System.out.println("Success (" + elapsedTime + " seconds elapsed)");
            }
            else
            {
                System.out.println("\033[" + i + "A (" + elapsedTime + " seconds elapsed)");
                break;
            }
        }

        AsyncSpinner.kill();
        System.out.print("\033[99999;99999H\033[1A\033[?25h");
    }

    /** Test every method of the customListClass
     * Note: This function also only works with an element type of Integer, see {@link project1lists.TestHarness.testErrors(CustomList, List, int, int, int) testErrors} for why
     * @param customListClass The class to test the behavior of
     * @param depth How many layers deep every function is applied (making a new layer) and the behavior tested
     * @return A boolean representing whether or not the customListClass behaves as expected (compared to the builtin {@link java.util.ArrayList ArrayList}) <!-- this link doesn't work for some reason -->
     */
    private static <E, T extends CustomList<E, T>> boolean testCustomList(Supplier<T> constructor, int depth)
    {
        ArrayDeque<State<E, T>> leaves = new ArrayDeque<>();
        leaves.add(new State<>(constructor));

        for (int i = 0; i < depth; i++)
        {
            int leafCount = leaves.size();
            // System.out.println(leafCount);
            for (int j = 0; j < leafCount; j++) if (!leaves.poll().addLeaves(leaves)) return false;
        }
        return true;
    }

    /** Expresses a state that a CustomList and its corresponding ArrayList can be in, along with arrays to keep track of the function call and argument histories */
    private static class State<E, T extends CustomList<E, T>>
    {
        /** The instance of the CustomList being tested that resulted after the functionHistory/argumentHistory */
        T customList;

        /** An instance of ArrayList that resulted after the functionHistory/argumentHistory */
        ArrayListWrapper<E> arrayListWrapper;

        /** An array containing all of the function calls leading up to this state */
        int[] functionHistory;

        /** An array containing all of the arguments used in the function calls leading up to this state */
        int[] argumentHistory;

        State(Supplier<T> constructor)
        {
            this.customList = constructor.get();
            this.arrayListWrapper = new ArrayListWrapper<>();
            this.functionHistory = new int[0];
            this.argumentHistory = new int[0];
        }

        State(State<E, T> previousState, int function, int argument)
        {
            this.customList = previousState.customList.copy();
            this.arrayListWrapper = previousState.arrayListWrapper.copy();

            int depth = previousState.functionHistory.length + 1;

            this.functionHistory = new int[depth];
            System.arraycopy(previousState.functionHistory, 0, this.functionHistory, 0, depth - 1);
            this.functionHistory[depth - 1] = function;

            this.argumentHistory = new int[depth];
            System.arraycopy(previousState.argumentHistory, 0, this.argumentHistory, 0, depth - 1);
            this.argumentHistory[depth - 1] = argument;
        }

        /** Log all of the information of this State (with formatting to work with {@link #project1lists.TestHarness.testCustomLists testCustomLists}) */
        void printInfo()
        {
            System.out.print("\033[2KCustom list: ");
            try {System.out.println(this.customList);}
            catch (Throwable error) {System.out.println("Error: " + error.getMessage());}
            System.out.println("\033[2KArrayList:   " + this.arrayListWrapper);
            System.out.print("\033[2KCustom list current index: ");
            try {System.out.println(this.customList.getCurrentIndex());}
            catch (Throwable error) {System.out.println("Error: " + error.getMessage());}
            System.out.println("\033[2KArrayList current index:   " + this.arrayListWrapper.getCurrentIndex());
            System.out.print("\033[2KCustom list current value: ");
            if (this.customList.size() > 0)
            {
                try {System.out.println(this.customList.getCurrentValue());}
                catch (Throwable error) {System.out.println("Error: " + error.getMessage());}
            }
            else
            {
                System.out.println("N/A (empty list)");
            }
            System.out.println("\033[2KArrayList current value:   " + ((this.arrayListWrapper.size() > 0)? this.arrayListWrapper.getCurrentValue() : "N/A (empty list)"));
            System.out.println("\033[2KFunction history (most recent at the bottom): ");
            for (int i = 0; i < this.functionHistory.length; i++)
            {
                int function = this.functionHistory[i];
                System.out.print("\033[2K\t" + TestHarness.customListMethods[function].name() + "(");
                if (TestHarness.customListMethods[function].function() instanceof BiConsumer) System.out.print(this.argumentHistory[i]);
                System.out.println(")");
            }
            System.out.print("\0338\033[" + (this.functionHistory.length + 5) + "A");
        }

        boolean evaluate()
        {
            TestHarness.CustomListMethod<?> function = TestHarness.customListMethods[this.functionHistory[this.functionHistory.length - 1]];
            int argument = this.argumentHistory[this.argumentHistory.length - 1];

            Object customListResult = null;
            Object arrayListWrapperResult = null;
            boolean customListError = false;
            boolean arrayListWrapperError = false;

            try {customListResult = function.call(this.customList, argument);}
            catch (Throwable _) {customListError = true;}
            try {arrayListWrapperResult = function.call(this.arrayListWrapper, argument);}
            catch (Throwable _) {arrayListWrapperError = true;}

            boolean customListUncheckable = false;
            boolean unequal = (customListResult == null)? arrayListWrapperResult != null : !customListResult.equals(arrayListWrapperResult);

            int customListSize;
            int customListCurrentIndex;
            E customListCurrentValue;
            try
            {
                customListSize = customList.size();
                customListCurrentIndex = customList.getCurrentIndex();
                customListCurrentValue = customList.getCurrentValue();
            }
            catch (Throwable _)
            {
                customListUncheckable = true;
            }
            int arrayListWrapperSize = arrayListWrapper.size();
            if (
                customListSize != arrayListWrapperSize ||
                customListCurrentIndex != this.arrayListWrapper.getCurrentIndex() ||
                (customListSize > 0 && arrayListWrapperSize > 0 && customListCurrentValue != this.arrayListWrapper.getCurrentValue())
            )
            {
                unequal = true;
                arrayListWrapperSize = 0; // only thing i could think of
            }
            else
            {
                int customListIndex = customList.getCurrentIndex();
                
                try {customList.moveCurrentIndexToStart();}
                catch (Throwable _) {customListUncheckable = true; arrayListWrapperSize = 0;}
                for (int i = 0; i < arrayListWrapperSize; i++)
                {
                    try {customListCurrentValue = customList.getCurrentValue();}
                    catch (Throwable _) {customListUncheckable = true; break;}
                    if (customListCurrentValue != arrayListWrapper.arrayList.get(i)) {unequal = true; break;}
                    if (i < arrayListWrapperSize - 1)
                    {
                        try {customList.moveCurrentIndexRight();}
                        catch (Throwable _) {customListUncheckable = true; break;}
                    }
                }
                try {customList.moveCurrentIndexTo(customListIndex);} // if this errors i don't even know dude
                catch (Throwable _) {customListUncheckable = true;}
            }
            
            if (customListError && arrayListWrapperError) continue;
            if (customListError && !arrayListWrapperError) 
            {
                System.out.println("Custom list had an error while ArrayList did not at state below\0337");
                newState.printInfo();

                return false;
            }
            if (arrayListWrapperError && !customListError)
            {
                System.out.println("ArrayList had an error while custom list did not at state below\0337");
                newState.printInfo();

                return false;
            }
            if ((customListResult == null)? arrayListWrapperResult != null : !customListResult.equals(arrayListWrapperResult))
            {
                System.out.println("Lists were not equal at state below\0337");
                newState.printInfo();

                return false;
            }
        }

        /** Branch out from this state and add the new leaf states to leaves
         * @param leaves The deque to add the new leaf states to
         * @return A boolean representing whether any of the leaves had CustomLists that misbehaved or not
         */
        boolean addLeaves(ArrayDeque<State<E, T>> leaves)
        {
            for (int currentFunction = 0; currentFunction < 13; currentFunction++)
            {
                int firstArgument, lastArgument;
                int argumentStep = 1;
                switch (TestHarness.customListMethods[currentFunction])
                {
                    case TestHarness.CustomListMethod.CustomListIndexConsumer _ -> {firstArgument = -1; lastArgument = this.arrayListWrapper.size();}
                    case TestHarness.CustomListMethod.CustomListValueConsumer<?> _ -> firstArgument = lastArgument = this.functionHistory.length;
                    default -> firstArgument = lastArgument = 0;
                }
                for (int currentArgument = firstArgument; currentArgument < lastArgument + 1; currentArgument += argumentStep)
                {
                    State<E, T> newState = new State<>(this, currentFunction, currentArgument);
                    TestHarness.CustomListMethod<?> function = TestHarness.customListMethods[newState.functionHistory[newState.functionHistory.length - 1]];
                    int argument = newState.argumentHistory[newState.argumentHistory.length - 1];

                    Object customListResult = null;
                    Object arrayListWrapperResult = null;
                    boolean customListError = false;
                    boolean arrayListWrapperError = false;

                    try {customListResult = function.call(newState.customList, argument);}
                    catch (Throwable _) {customListError = true;}
                    try {arrayListWrapperResult = function.call(newState.arrayListWrapper, argument);}
                    catch (Throwable _) {arrayListWrapperError = true;}

                    boolean customListUncheckable = false;
                    boolean unequal = (customListResult == null)? arrayListWrapperResult != null : !customListResult.equals(arrayListWrapperResult);

                    int customListSize;
                    int customListCurrentIndex;
                    E customListCurrentValue;
                    try
                    {
                        customListSize = customList.size();
                        customListCurrentIndex = customList.getCurrentIndex();
                        customListCurrentValue = customList.getCurrentValue();
                    }
                    catch (Throwable _)
                    {
                        customListUncheckable = true;
                    }
                    int arrayListWrapperSize = arrayListWrapper.size();
                    if (
                        customListSize != arrayListWrapperSize ||
                        customListCurrentIndex != this.arrayListWrapper.getCurrentIndex() ||
                        (customListSize > 0 && arrayListWrapperSize > 0 && customListCurrentValue != this.arrayListWrapper.getCurrentValue())
                    )
                    {
                        unequal = true;
                        arrayListWrapperSize = 0; // only thing i could think of
                    }
                    else
                    {
                        int customListIndex = customList.getCurrentIndex();
                        
                        try {customList.moveCurrentIndexToStart();}
                        catch (Throwable _) {customListUncheckable = true; arrayListWrapperSize = 0;}
                        for (int i = 0; i < arrayListWrapperSize; i++)
                        {
                            try {customListCurrentValue = customList.getCurrentValue();}
                            catch (Throwable _) {customListUncheckable = true; break;}
                            if (customListCurrentValue != arrayListWrapper.arrayList.get(i)) {unequal = true; break;}
                            if (i < arrayListWrapperSize - 1)
                            {
                                try {customList.moveCurrentIndexRight();}
                                catch (Throwable _) {customListUncheckable = true; break;}
                            }
                        }
                        try {customList.moveCurrentIndexTo(customListIndex);} // if this errors i don't even know dude
                        catch (Throwable _) {customListUncheckable = true;}
                    }
                    
                    if (customListError && arrayListWrapperError) continue;
                    if (customListError && !arrayListWrapperError) 
                    {
                        System.out.println("Custom list had an error while ArrayList did not at state below\0337");
                        newState.printInfo();

                        return false;
                    }
                    if (arrayListWrapperError && !customListError)
                    {
                        System.out.println("ArrayList had an error while custom list did not at state below\0337");
                        newState.printInfo();

                        return false;
                    }
                    if ((customListResult == null)? arrayListWrapperResult != null : !customListResult.equals(arrayListWrapperResult))
                    {
                        System.out.println("Lists were not equal at state below\0337");
                        newState.printInfo();

                        return false;
                    }

                    leaves.addLast(newState);
                }
            }

            return true;
        }

        // /** Return type of {@link #project1lists.TestHarness.State.isValid isValid} */
        // enum Validity
        // {
        //     VALID,
        //     INVALID,
        //     ERROR
        // }

        enum Evaluate
        {
            SUCCESS,
            UNEQUAL,
            ERROR_BOTH,
            ERROR_CUSTOM_LIST,
            ERROR_ARRAY_LIST_WRAPPER
        }
    }

    private static class A<E> extends ArrayList<E> {}

    private static class ArrayListWrapper<E> extends DefaultCustomListImplementations<E, ArrayListWrapper<E>>
    {
        ArrayList<E> arrayList;
        int currentIndex = 0;

        ArrayListWrapper() {super();}

        @Override
        public void clear() {this.arrayList.clear(); this.currentIndex = 0;}

        @Override
        public void insert(E value) {if (this.arrayList.size() != 0) this.arrayList.add(this.currentIndex, value); else this.append(value);}

        @Override
        public void append(E value) {this.arrayList.add(value);}

        @Override
        public E remove() {return this.arrayList.remove(this.currentIndex);}

        @Override
        public E getCurrentValue() {return this.arrayList.get(this.currentIndex);}

        @Override
        public void setCurrentValue(E value) {if (this.arrayList.size() != 0) this.arrayList.set(this.currentIndex, value); else this.append(value);}

        @Override
        public int getCurrentIndex() {return this.currentIndex;}

        @Override
        public void moveCurrentIndexTo(int index) {this.currentIndex = index;}

        @Override
        public int size() {return this.arrayList.size();}

        @Override
        public ArrayListWrapper<E> copy()
        {
            ArrayListWrapper<E> toReturn = new ArrayListWrapper<>();
            toReturn.arrayList = new ArrayList<>(this.arrayList); // i know this makes an extra array list shut up i'm lazy
            toReturn.currentIndex = this.currentIndex;

            return toReturn;
        }

        // boolean add(E value) {return this.arrayList.add(value);}

        // void add(int index, E value) {this.arrayList.add(index, value);}

        // boolean addAll(Collection<? extends E> collection) {return this.arrayList.addAll(collection);}

        // boolean addAll(int index, Collection<? extends E> collection) {return this.arrayList.addAll(index, collection);}

        // boolean contains(Object value) {return this.arrayList.contains(value);}

        // void ensureCapacity(int minCapacity) {this.arrayList.ensureCapacity(minCapacity);}

        // void forEach(Consumer<? super E> action) {this.arrayList.forEach(action);}

        // E get(int index) {return this.arrayList.get(index);}

        // int indexOf(Object value) {return this.arrayList.indexOf(value);}

        // boolean isEmpty() {return this.arrayList.isEmpty();}

        // Iterator<E> iterator() {return this.arrayList.iterator();}

        // int lastIndexOf(Object value) {return this.arrayList.lastIndexOf(value);}

        // ListIterator<E> listIterator() {return this.arrayList.listIterator();}

        // ListIterator<E> listIterator(int index) {return this.arrayList.listIterator(index);}

        // E remove(int index) {E toReturn = this.arrayList.remove(index); this.currentIndex = Math.min(this.currentIndex, this.arrayList.size() - 2); return toReturn;}

        // boolean remove(Object value) {boolean toReturn = this.arrayList.remove(value); this.currentIndex = Math.min(this.currentIndex, this.arrayList.size() - 1); return toReturn;}

        // boolean removeIf(Predicate<? super E> filter) {boolean toReturn = this.arrayList.removeIf(filter); this.currentIndex = Math.min(this.currentIndex, this.arrayList.size() - 1); return toReturn;}

        // void replaceAll(UnaryOperator<E> operator) {this.arrayList.replaceAll(operator);}

        // boolean retainAll(Collection<?> collection) {boolean toReturn = this.arrayList.retainAll(collection); this.currentIndex = Math.min(this.currentIndex, this.arrayList.size() - 2); return toReturn;}

        // E set(int index, E value) {return this.arrayList.set(index, value);}

        // void sort(Comparator<? super E> comparator) {this.arrayList.sort(comparator);}

        // Spliterator<E> spliterator() {return this.arrayList.spliterator();}

        // List<E> subList(int fromIndex, int toIndex) {return this.arrayList.subList(fromIndex, toIndex);}

        // Object[] toArray() {return this.arrayList.toArray();}

        // <T> T[] toArray(T[] array) {return this.arrayList.toArray(array);}

        // void trimToSize() {this.arrayList.trimToSize();}
    }

    /** Fancy spinning line character */
    private static class AsyncSpinner
    {
        /** Character frames to play for animating the spinner */
        static final char[] frames = {'-', '\\', '|', '/'};

        /** Thread to animate the spinner on (so that other operations can be run while the spinner is animating) */
        static ExecutorService executor;

        /** Current task that animates the spinner (if null there is no spinner) */
        static Future<?> task;

        /** Play AsyncSpinner.frames (should not be used outside of AsyncSpinner) */
        static void animate()
        {
            int frame = 0;
            while (!Thread.currentThread().isInterrupted())
            {
                System.out.print(frames[frame] + "\033[1D");
                frame = (frame + 1) % frames.length;
                try {Thread.sleep(100);}
                catch (InterruptedException _) {Thread.currentThread().interrupt();}
            }
        }

        /** Start the spinner's animation (to be used outside) */
        static void start()
        {
            if (AsyncSpinner.executor == null) AsyncSpinner.executor = Executors.newSingleThreadExecutor();
            AsyncSpinner.task = executor.submit(AsyncSpinner::animate);
        }

        /** End the spinner's animation */
        static void stop() {if (AsyncSpinner.task != null) AsyncSpinner.task.cancel(true);}

        /** Completely kill the spinner's thread */
        static void kill() {if (AsyncSpinner.executor != null) AsyncSpinner.executor.shutdownNow();}
    }

    /** An interface to combine all of the types of methods CustomLists have into a uniform function type (with names)
     * <p>
     * The CustomList is passed into the CustomListMethod.function() as the first argument.
    */
    private sealed interface CustomListMethod<T>
    {
        /** Get the name of this CustomListMethod */
        public String name();

        // change to be something that calls the function according to what type this CustomListMethod is so that i don't have to do isinstanceof checks for accept/apply calls?
        /** Get the function of this CustomListMethod */
        public T function();

        public Object call(CustomList<?, ?> customList, Object argument);

        /** A method of CustomList that has no inputs or outputs */
        record CustomListRunnable(String name, Consumer<CustomList<?, ?>> function) implements CustomListMethod<Consumer<CustomList<?, ?>>>
        {public Object call(CustomList<?, ?> customList, Object argument) {this.function.accept(customList); return null;}}
        
        /** A method of CustomList that has a value as an input and no outputs */
        record CustomListValueConsumer<E>(String name, BiConsumer<CustomList<E, ?>, E> function) implements CustomListMethod<BiConsumer<CustomList<E, ?>, E>>
        {public Object call(CustomList<?, ?> customList, Object argument) {this.function.accept((CustomList<E, ?>)customList, (E)argument); return null;}}
        
        /** A method of CustomList that has an index as an input and no outputs */
        record CustomListIndexConsumer(String name, BiConsumer<CustomList<?, ?>, Integer> function) implements CustomListMethod<BiConsumer<CustomList<?, ?>, Integer>>
        {public Object call(CustomList<?, ?> customList, Object argument) {this.function.accept(customList, (int)argument); return null;}}
        
        /** A method of CustomList that has no inputs and a value as an output */
        record CustomListValueSupplier<E>(String name, Function<CustomList<E, ?>, E> function) implements CustomListMethod<Function<CustomList<E, ?>, E>>
        {public Object call(CustomList<?, ?> customList, Object argument) {return this.function.apply((CustomList<E, ?>)customList);}}
        
        /** A method of CustomList that has no inputs and an index as an output */
        record CustomListIndexSupplier(String name, Function<CustomList<?, ?>, Integer> function) implements CustomListMethod<Function<CustomList<?, ?>, Integer>>
        {public Integer call(CustomList<?, ?> customList, Object argument) {return this.function.apply(customList);}}
    }

    // /** Return type of testErrors */
    // private enum TestResult
    // {
    //     // /** Returned by testErrors when neither state.customList nor state.arrayList produce errors */
    //     // NONE,

    //     /** Returned by testErrors when both state.customList and state.arrayList produce an error (such as the current index being out of bounds) */
    //     ERROR_BOTH,

    //     /** Returned by testErrors when state.customList produces an error but state.arrayList does not */
    //     ERROR_CUSTOM_LIST,

    //     /** Returned by testErrors when state.arrayList produces an error but state.customList does not */
    //     ERROR_ARRAY_LIST_WRAPPER,

    //     UNEQUAL,

    //     SUCCESS
    // }
}