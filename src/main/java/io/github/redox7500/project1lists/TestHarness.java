package io.github.redox7500.project1lists;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.Optional;
import java.lang.Cloneable;
import java.lang.classfile.Interfaces;

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
        new TestHarness.CustomListMethod.CustomListRunnable       ("moveCurrentIndexToPrevious",    CustomList::moveCurrentIndexToPrevious),
        new TestHarness.CustomListMethod.CustomListRunnable       ("moveCurrentIndexToNext",   CustomList::moveCurrentIndexToNext),
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
    public static <E, S extends Testable<S>, T extends S, U extends S> void testCustomList(Supplier<T> testConstructor, Supplier<U> verifiedConstructor, Supplier<E> valueGenerator, String name)
    {
        System.out.print("\033[?25l");

        System.out.print(name);
        AsyncSpinner.start();
        
        System.out.print("\033[25G\033[25G");
        AsyncSpinner.start();

        long startTime = System.nanoTime();
        @SuppressWarnings("unchecked")
        boolean result = new State<S, T, U>(testConstructor, verifiedConstructor).branch(7);
        String elapsedTime = String.format("%.5f", (System.nanoTime() - startTime) / (float)1_000_000_000);

        AsyncSpinner.stop();
        if (result)
        {
            System.out.println("Success (" + elapsedTime + " seconds elapsed)");
        }
        else
        {
            System.out.println("(" + elapsedTime + " seconds elapsed)");
        }

        AsyncSpinner.kill();
        System.out.print("\033[?25h");
    }

    /** Expresses a state that a CustomList and its corresponding ArrayList can be in, along with arrays to keep track of the function call and argument histories */
    private static class State<S extends Testable<S>, T extends S, U extends S>
    {
        /** The instance of the CustomList being tested that resulted after the functionHistory/argumentHistory */
        S toTest1;

        /** An instance of ArrayList that resulted after the functionHistory/argumentHistory */
        S toTest2;

        /** An array containing all of the function calls leading up to this state */
        int[] functionHistory;

        /** An array containing all of the arguments used in the function calls leading up to this state */
        int[] argumentHistory;

        State(Supplier<T> testConstructor, Supplier<U> verifiedConstructor)
        {
            this.toTest1 = testConstructor.get();
            this.toTest2 = verifiedConstructor.get();
            this.functionHistory = new int[0];
            this.argumentHistory = new int[0];
        }

        @SuppressWarnings("unchecked")
        State (State<S, T, U> previousState, int function, int argument)
        {
            this.toTest1 = previousState.toTest1.copy();
            this.toTest2 = previousState.toTest2.copy();

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
            toTest1.printRelevantInfo("First object");
            toTest2.printRelevantInfo("Second object");
        }

        State.Evaluation evaluate()
        {
            TestHarness.CustomListMethod<?> function = TestHarness.customListMethods[this.functionHistory[this.functionHistory.length - 1]];
            int argument = this.argumentHistory[this.argumentHistory.length - 1];

            Object customListResult = null;
            Object arrayListWrapperResult = null;
            boolean customListError = false;
            boolean arrayListWrapperError = false;

            try {customListResult = function.call(this.toTest1, argument);}
            catch (Throwable _) {customListError = true;}
            try {arrayListWrapperResult = function.call(this.toTest2, argument);}
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
                    try {customList.moveCurrentIndexToNext();}
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
        boolean branch(int depth)
        {
            if (depth == 0) return true;

            for (int currentFunction = 0; currentFunction < 13; currentFunction++)
            {
                int firstArgument, lastArgument;
                int argumentStep = 1;
                switch (TestHarness.customListMethods[currentFunction])
                {
                    case TestHarness.CustomListMethod.CustomListIndexConsumer _ -> {firstArgument = -1; lastArgument = this.verifiedList.size();} // change args to be based on inputs to testharness method?
                    case TestHarness.CustomListMethod.CustomListValueConsumer<?> _ -> firstArgument = lastArgument = this.functionHistory.length;
                    default -> firstArgument = lastArgument = 0;
                }
                for (int currentArgument = firstArgument; currentArgument < lastArgument + 1; currentArgument += argumentStep)
                {
                    State<S, T, U> newState = new State<>(this, currentFunction, currentArgument);
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
                            if (!newState.branch(depth - 1)) return false;
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

    private static class ArrayListWrapper<E> extends DefaultCustomListImplementations<E>
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

        public Object call(CustomList<?> customList, Object argument);

        /** A method of CustomList that has no inputs or outputs */
        record CustomListRunnable(String name, Consumer<CustomList<?>> function) implements CustomListMethod<Consumer<CustomList<?>>>
        {public Object call(CustomList<?> customList, Object argument) {this.function.accept(customList); return null;}}
        
        /** A method of CustomList that has a value as an input and no outputs */
        record CustomListValueConsumer<E>(String name, BiConsumer<CustomList<E>, E> function) implements CustomListMethod<BiConsumer<CustomList<E>, E>>
        {@SuppressWarnings("unchecked") public Object call(CustomList<?> customList, Object argument) {this.function.accept((CustomList<E>)customList, (E)argument); return null;}}
        
        /** A method of CustomList that has an index as an input and no outputs */
        record CustomListIndexConsumer(String name, BiConsumer<CustomList<?>, Integer> function) implements CustomListMethod<BiConsumer<CustomList<?>, Integer>>
        {public Object call(CustomList<?> customList, Object argument) {this.function.accept(customList, (int)argument); return null;}}
        
        /** A method of CustomList that has no inputs and a value as an output */
        record CustomListValueSupplier<E>(String name, Function<CustomList<E>, E> function) implements CustomListMethod<Function<CustomList<E>, E>>
        {@SuppressWarnings("unchecked") public Object call(CustomList<?> customList, Object argument) {return this.function.apply((CustomList<E>)customList);}}
        
        /** A method of CustomList that has no inputs and an index as an output */
        record CustomListIndexSupplier(String name, Function<CustomList<?>, Integer> function) implements CustomListMethod<Function<CustomList<?>, Integer>>
        {public Integer call(CustomList<?> customList, Object argument) {return this.function.apply(customList);}}
    }

    public interface Testable<T> extends Copyable<T>
    {
        static <T> Result fullTest(Testable<T> toTest1, Testable<T> toTest2, TestFunction<T> testFunction, Object[] args)
        {
            Object result1 = null;
            Object result2 = null;
            boolean error1 = false;
            boolean error2 = false;

            try {result1 = testFunction.apply(toTest1, args);}
            catch (Throwable _) {error1 = true;}
            try {result2 = testFunction.apply(toTest2, args);}
            catch (Throwable _) {error2 = true;}

            if (error1 && error2) return Result.ERROR_BOTH;
            if (error1 && !error2) return Result.ERROR_1;
            if (!error1 && error2) return Result.ERROR_2;
            return (((result1 == null)? result2 == null : result1.equals(result2)) && toTest1.equals(toTest2))? Result.SUCCESS : Result.UNEQUAL;
            // probably change equals to something like equalityCheck and make it output a Result thing, then no more hashCode too
            // return ((result1 == null)? result2 == null : result1.equals(result2))? Result.SUCCESS : Result.UNEQUAL;
        }

        void printRelevantInfo(String name);
        
        enum Result
        {
            ERROR_BOTH,
            ERROR_1,
            ERROR_2,
            INEVALUABLE_1,
            INEVALUABLE_2,
            UNEQUAL,
            SUCCESS
        }

        @FunctionalInterface
        interface TestFunction<T>
        {
            Object apply(Testable<T> toTest, Object[] args);
        }
    }
}