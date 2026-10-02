package io.github.redox7500.project1lists;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.Optional;

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

        State.Evaluation evaluate()
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

            if (customListError && arrayListWrapperError) return State.Evaluation.ERROR_BOTH;
            if (customListError && !arrayListWrapperError) return State.Evaluation.ERROR_CUSTOM_LIST;
            if (arrayListWrapperError && !customListError) return State.Evaluation.ERROR_ARRAY_LIST_WRAPPER;
            if ((customListResult == null)? arrayListWrapperResult != null : !customListResult.equals(arrayListWrapperResult)) return State.Evaluation.UNEQUAL;

            int customListSize;
            int customListCurrentIndex;
            Optional<E> customListCurrentValue;
            try
            {
                customListSize = customList.size();
                customListCurrentIndex = customList.getCurrentIndex();
                customListCurrentValue = (customListSize == 0)? Optional.empty() : Optional.of(customList.getCurrentValue());
            }
            catch (Throwable _)
            {
                return State.Evaluation.INEVALUABLE_CUSTOM_LIST;
            }
            int arrayListWrapperSize = arrayListWrapper.size();
            Optional<E> arrayListWrapperCurrentValue = (arrayListWrapperSize == 0)? Optional.empty() : Optional.of(arrayListWrapper.getCurrentValue());
            if (
                customListSize != arrayListWrapperSize ||
                customListCurrentIndex != this.arrayListWrapper.getCurrentIndex() ||
                !customListCurrentValue.equals(arrayListWrapperCurrentValue)
            ) return State.Evaluation.UNEQUAL;

            int customListIndex;
            try {customListIndex = customList.getCurrentIndex();}
            catch (Throwable _) {return State.Evaluation.INEVALUABLE_CUSTOM_LIST;}
            
            try {customList.moveCurrentIndexToStart();}
            catch (Throwable _) {return State.Evaluation.INEVALUABLE_CUSTOM_LIST;}
            for (int i = 0; i < arrayListWrapperSize; i++)
            {
                E c;
                try {c = customList.getCurrentValue();}
                catch (Throwable _) {return State.Evaluation.INEVALUABLE_CUSTOM_LIST;}
                if (c != arrayListWrapper.arrayList.get(i)) {return State.Evaluation.UNEQUAL;}
                if (i < arrayListWrapperSize - 1)
                {
                    try {customList.moveCurrentIndexRight();}
                    catch (Throwable _) {return State.Evaluation.INEVALUABLE_CUSTOM_LIST;}
                }
            }
            try {customList.moveCurrentIndexTo(customListIndex);} // if this errors i don't even know dude
            catch (Throwable _) {return State.Evaluation.INEVALUABLE_CUSTOM_LIST;}

            return State.Evaluation.SUCCESS;
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
                    switch (newState.evaluate())
                    {
                        case State.Evaluation.ERROR_BOTH:
                            continue;
                        case State.Evaluation.ERROR_CUSTOM_LIST:
                            System.out.println("Custom list had an error while ArrayListWrapper did not at state below\0337");
                            newState.printInfo();

                            return false;
                        case State.Evaluation.ERROR_ARRAY_LIST_WRAPPER:
                            System.out.println("ArrayListWrapper had an error while custom list did not at state below\0337");
                            newState.printInfo();

                            return false;
                        case State.Evaluation.INEVALUABLE_CUSTOM_LIST:
                            System.out.println("Custom list had an error when being compared to ArrayListWrapper\0337");
                            newState.printInfo();

                            return false;
                        case State.Evaluation.UNEQUAL:
                            System.out.println("Lists were not equal at state below\0337");
                            newState.printInfo();

                            return false;
                        case State.Evaluation.SUCCESS:
                            leaves.addLast(newState);
                    }
                }
            }

            return true;
        }

        enum Evaluation
        {
            ERROR_BOTH,
            ERROR_CUSTOM_LIST,
            ERROR_ARRAY_LIST_WRAPPER,
            INEVALUABLE_CUSTOM_LIST,
            UNEQUAL,
            SUCCESS
        }
    }

    private static class ArrayListWrapper<E> extends DefaultCustomListImplementations<E, ArrayListWrapper<E>>
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
        {@SuppressWarnings("unchecked") public Object call(CustomList<?, ?> customList, Object argument) {this.function.accept((CustomList<E, ?>)customList, (E)argument); return null;}}
        
        /** A method of CustomList that has an index as an input and no outputs */
        record CustomListIndexConsumer(String name, BiConsumer<CustomList<?, ?>, Integer> function) implements CustomListMethod<BiConsumer<CustomList<?, ?>, Integer>>
        {public Object call(CustomList<?, ?> customList, Object argument) {this.function.accept(customList, (int)argument); return null;}}
        
        /** A method of CustomList that has no inputs and a value as an output */
        record CustomListValueSupplier<E>(String name, Function<CustomList<E, ?>, E> function) implements CustomListMethod<Function<CustomList<E, ?>, E>>
        {@SuppressWarnings("unchecked") public Object call(CustomList<?, ?> customList, Object argument) {return this.function.apply((CustomList<E, ?>)customList);}}
        
        /** A method of CustomList that has no inputs and an index as an output */
        record CustomListIndexSupplier(String name, Function<CustomList<?, ?>, Integer> function) implements CustomListMethod<Function<CustomList<?, ?>, Integer>>
        {public Integer call(CustomList<?, ?> customList, Object argument) {return this.function.apply(customList);}}
    }
}