package project1lists;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.BiConsumer;

public class TestHarness
{
    private static final CustomListMethod[] customListMethods = new CustomListMethod[]{
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

    /** Tests if customList and list behave the same when the specified function is called
     * <p>
     * Note: This function only works with Integer lists because generating random numbers for them is easy and there's really no purpose to add other types; they should all work the same (also Integers make it so that functionInput works for both values and indices)
     * @param customList An instance of your custom list implementation to test
     * @param list An instance of reliable code such as ArrayList that has the same values/state as your customList
     * @param currentIndex A supplementary property of list that matches your customList's internal current index (as stated before, both instances should have the exact same values/state)
     * @param function The index of which function you would like to test. These indices are 0 based and go from the top to the bottom of the CustomList.java file.
     * @param functionInput The value to give to the function you chose, which will not be used if the function you chose has no inputs
     * @return A TestHarness.ErrorTestResult stating the behavior of the customList relative to the list
     */
    private static TestHarness.ErrorTestResult testErrors(State<?> state)
    {
        int function = state.functionHistory[state.functionHistory.length - 1];

        assert function >= 0 && function < 13 : "Invalid function index";

        int argument = state.argumentHistory[state.argumentHistory.length - 1];
        switch (function)
        {
            case 0:
            {
                try {state.customList.size();}
                catch (Throwable _) {return TestHarness.ErrorTestResult.CUSTOM_LIST;}

                return TestHarness.ErrorTestResult.NONE;
            }
            case 1:
            {
                try {state.customList.getCurrentIndex();}
                catch (Throwable _) {return TestHarness.ErrorTestResult.CUSTOM_LIST;}
                
                return TestHarness.ErrorTestResult.NONE;
            }
            case 2:
            {
                boolean arrayListError = argument < 0 || ((state.arrayList.size() > 0)? argument >= state.arrayList.size() : argument != 0);

                try {state.customList.moveCurrentIndexTo(argument);}
                catch (Throwable _) {return (arrayListError)? TestHarness.ErrorTestResult.BOTH : TestHarness.ErrorTestResult.CUSTOM_LIST;}
                if (arrayListError) return TestHarness.ErrorTestResult.ARRAY_LIST;
                state.arrayListCurrentIndex = argument;

                return TestHarness.ErrorTestResult.NONE;
            }
            case 3:
            {
                try {state.customList.moveCurrentIndexToStart();}
                catch (Throwable _) {return TestHarness.ErrorTestResult.CUSTOM_LIST;}
                state.arrayListCurrentIndex = 0;

                return TestHarness.ErrorTestResult.NONE;
            }
            case 4:
            {
                try {state.customList.moveCurrentIndexToEnd();}
                catch (Throwable _) {return TestHarness.ErrorTestResult.CUSTOM_LIST;}
                state.arrayListCurrentIndex = Math.max(state.arrayList.size() - 1, 0);

                return TestHarness.ErrorTestResult.NONE;
            }
            case 5:
            {
                boolean arrayListError = state.arrayListCurrentIndex <= 0;

                try {state.customList.moveCurrentIndexLeft();}
                catch (Throwable _) {return (arrayListError)? TestHarness.ErrorTestResult.BOTH : TestHarness.ErrorTestResult.CUSTOM_LIST;}
                if (arrayListError) return TestHarness.ErrorTestResult.ARRAY_LIST;
                state.arrayListCurrentIndex--;

                return TestHarness.ErrorTestResult.NONE;
            }
            case 6:
            {
                boolean arrayListError = (state.arrayList.size() > 0)? state.arrayListCurrentIndex >= state.arrayList.size() - 1 : true;

                try {state.customList.moveCurrentIndexRight();}
                catch (Throwable _) {return (arrayListError)? TestHarness.ErrorTestResult.BOTH : TestHarness.ErrorTestResult.CUSTOM_LIST;}
                if (arrayListError) return TestHarness.ErrorTestResult.ARRAY_LIST;
                state.arrayListCurrentIndex++;

                return TestHarness.ErrorTestResult.NONE;
            }
            case 7:
            {
                boolean arrayListError = state.arrayList.size() == 0;

                try {state.customList.getCurrentValue();}
                catch (Throwable _) {return (arrayListError)? TestHarness.ErrorTestResult.BOTH : TestHarness.ErrorTestResult.CUSTOM_LIST;}
                if (arrayListError) return TestHarness.ErrorTestResult.ARRAY_LIST;

                return TestHarness.ErrorTestResult.NONE;
            }
            case 8:
            {
                try {state.customList.setCurrentValue(argument);}
                catch (Throwable _) {return TestHarness.ErrorTestResult.CUSTOM_LIST;}
                if (state.arrayList.size() > 0) state.arrayList.set(state.arrayListCurrentIndex, argument);
                else state.arrayList.add(argument);

                return TestHarness.ErrorTestResult.NONE;
            }
            case 9:
            {
                try {state.customList.clear();}
                catch (Throwable _) {return TestHarness.ErrorTestResult.CUSTOM_LIST;}
                state.arrayList.clear();
                state.arrayListCurrentIndex = 0;

                return TestHarness.ErrorTestResult.NONE;
            }
            case 10:
            {
                try {state.customList.insert(argument);}
                catch (Throwable _) {return TestHarness.ErrorTestResult.CUSTOM_LIST;}
                state.arrayList.add(state.arrayListCurrentIndex, argument);

                return TestHarness.ErrorTestResult.NONE;
            }
            case 11:
            {
                try {state.customList.append(argument);}
                catch (Throwable _) {return TestHarness.ErrorTestResult.CUSTOM_LIST;}
                state.arrayList.add(argument);

                return TestHarness.ErrorTestResult.NONE;
            }
            case 12:
            {
                boolean arrayListError = state.arrayList.size() == 0;

                try {state.customList.remove();}
                catch (Throwable _) {return (arrayListError)? TestHarness.ErrorTestResult.BOTH : TestHarness.ErrorTestResult.CUSTOM_LIST;}
                if (arrayListError) return TestHarness.ErrorTestResult.ARRAY_LIST;
                state.arrayList.remove(state.arrayListCurrentIndex);
                if (state.arrayListCurrentIndex == state.arrayList.size() && state.arrayList.size() > 0) state.arrayListCurrentIndex--;

                return TestHarness.ErrorTestResult.NONE;
            }
            default:
            {
                return TestHarness.ErrorTestResult.NONE; // This code should never be able to happen because of the assert above
            }
        }
    }

    /** Test every method of the customListClass
     * Note: This function also only works with an element type of Integer, see {@link project1lists.TestHarness.testErrors(CustomList, List, int, int, int) testErrors} for why
     * @param customListClass The class to test the behavior of
     * @param depth How many layers deep every function is applied (making a new layer) and the behavior tested
     * @return A boolean representing whether or not the customListClass behaves as expected (compared to the builtin {@link java.util.ArrayList ArrayList}) <!-- this link doesn't work for some reason -->
     */
    public static <T extends CustomList<Integer, T>> boolean testCustomList(Supplier<T> constructor, int depth)
    {
        ArrayDeque<State<T>> leaves = new ArrayDeque<>();
        leaves.add(new State<>(constructor));

        for (int i = 0; i < depth; i++)
        {
            int leafCount = leaves.size();
            System.out.println(leafCount);
            for (int j = 0; j < leafCount; j++) if (!leaves.poll().addLeaves(leaves)) return false;
        }
        return true;
    }

    private static class State<T extends CustomList<Integer, T>>
    {
        private T customList;
        private ArrayList<Integer> arrayList;
        private int arrayListCurrentIndex;
        private int[] functionHistory;
        private int[] argumentHistory;

        private State(Supplier<T> constructor)
        {
            this.customList = constructor.get();
            this.arrayList = new ArrayList<>();
            this.arrayListCurrentIndex = 0;
            this.functionHistory = new int[0];
            this.argumentHistory = new int[0];
        }

        private State(State<T> previousState, int function, int argument)
        {
            this.customList = previousState.customList.copy();
            this.arrayList = new ArrayList<>(previousState.arrayList);
            this.arrayListCurrentIndex = previousState.arrayListCurrentIndex;

            int depth = previousState.functionHistory.length + 1;

            this.functionHistory = new int[depth];
            System.arraycopy(previousState.functionHistory, 0, this.functionHistory, 0, depth - 1);
            this.functionHistory[depth - 1] = function;

            this.argumentHistory = new int[depth];
            System.arraycopy(previousState.argumentHistory, 0, this.argumentHistory, 0, depth - 1);
            this.argumentHistory[depth - 1] = argument;
        }

        private State.Validity isValid()
        {
            try
            {
                if (
                    customList.size() != arrayList.size() ||
                    customList.getCurrentIndex() != this.arrayListCurrentIndex ||
                    (customList.size() > 0 && arrayList.size() > 0 && customList.getCurrentValue() != this.arrayList.get(this.arrayListCurrentIndex))
                ) return State.Validity.INVALID;

                int customListIndex = customList.getCurrentIndex();
                
                customList.moveCurrentIndexToStart();
                State.Validity toReturn = State.Validity.VALID;
                for (int i = 0; i < arrayList.size(); i++)
                {
                    if (customList.getCurrentValue() != arrayList.get(i)) {toReturn = State.Validity.INVALID; break;}
                    if (i < arrayList.size() - 1) customList.moveCurrentIndexRight();
                }
                customList.moveCurrentIndexTo(customListIndex); // if this errors i don't even know dude

                return toReturn;
            }
            catch (Throwable error)
            {
                error.printStackTrace();
                return State.Validity.ERROR;
            }
        }

        private void printInfo()
        {
            System.out.println("Custom list: " + this.customList);
            System.out.println("ArrayList:   " + this.arrayList);
            System.out.print("Custom list current index: ");
            try {System.out.println(this.customList.getCurrentIndex());}
            catch (Throwable error) {error.printStackTrace();}
            System.out.println("ArrayList current index:   " + this.arrayListCurrentIndex);
            System.out.print("Custom list current value: ");
            try
            {
                if (this.customList.size() > 0) System.out.println(this.customList.getCurrentValue());
                else System.out.println("N/A (empty list)");
            }
            catch (Throwable error)
            {
                error.printStackTrace();
            }
            System.out.println("ArrayList current value:   " + ((this.arrayList.size() > 0)? this.arrayList.get(this.arrayListCurrentIndex) : "N/A (empty list)"));
            System.out.println("Function history (most recent at the bottom): ");
            for (int i = 0; i < this.functionHistory.length; i++)
            {
                int function = this.functionHistory[i];
                System.out.print("\t" + TestHarness.customListMethods[function].name() + "(");
                if (TestHarness.customListMethods[function].function() instanceof BiConsumer) System.out.print(this.argumentHistory[i]);
                System.out.println(")");
            }
        }

        private boolean addLeaves(ArrayDeque<State<T>> leaves)
        {
            for (int currentFunction = 0; currentFunction < 13; currentFunction++)
            {
                int firstArgument, lastArgument;
                int argumentStep = 1;
                switch (TestHarness.customListMethods[currentFunction])
                {
                    case TestHarness.CustomListMethod.CustomListIndexConsumer _ -> {firstArgument = -1; lastArgument = this.arrayList.size();}
                    case TestHarness.CustomListMethod.CustomListValueConsumer<?> _ -> firstArgument = lastArgument = this.functionHistory.length;
                    default -> firstArgument = lastArgument = 0;
                }
                for (int currentArgument = firstArgument; currentArgument < lastArgument + 1; currentArgument += argumentStep)
                {
                    State<T> newState = new State<>(this, currentFunction, currentArgument);
                    switch (TestHarness.testErrors(newState))
                    {
                        case TestHarness.ErrorTestResult.BOTH:
                            continue;
                        case TestHarness.ErrorTestResult.CUSTOM_LIST:
                            System.out.println("Custom list had an error while ArrayList did not at state below");
                            newState.printInfo();

                            return false;
                        case TestHarness.ErrorTestResult.ARRAY_LIST:
                            System.out.println("ArrayList had an error while custom list did not at state below");
                        case TestHarness.ErrorTestResult.NONE:
                            switch (newState.isValid())
                            {
                                case State.Validity.INVALID:
                                    System.out.println("Lists were not equal at state below");
                                    newState.printInfo();

                                    return false;
                                case State.Validity.ERROR:
                                    System.out.println("Error checking custom list's validity at state below");
                                    newState.printInfo();

                                    return false;
                                case State.Validity.VALID:
                                    leaves.addLast(newState);
                            }
                    }
                }
            }

            return true;
        }

        private enum Validity
        {
            VALID,
            INVALID,
            ERROR
        }
    }

    private sealed interface CustomListMethod
    {
        record CustomListRunnable        (String name, Consumer  <CustomList<?, ?>>          function) implements CustomListMethod {}
        record CustomListValueConsumer<E>(String name, BiConsumer<CustomList<E, ?>, E>       function) implements CustomListMethod {}
        record CustomListIndexConsumer   (String name, BiConsumer<CustomList<?, ?>, Integer> function) implements CustomListMethod {}
        record CustomListValueSupplier<E>(String name, Function  <CustomList<E, ?>, E>       function) implements CustomListMethod {}
        record CustomListIndexSupplier   (String name, Function  <CustomList<?, ?>, Integer> function) implements CustomListMethod {}

        public String name();
        public Object function();
    }

    /** Return type of testErrors */
    private static enum ErrorTestResult
    {
        /** Returned by testErrors when neither state.customList nor state.arrayList produce errors */
        NONE,

        /** Returned by testErrors when both state.customList and state.arrayList produce an error (such as the current index being out of bounds) */
        BOTH,

        /** Returned by testErrors when state.customList produces an error but state.arrayList does not */
        CUSTOM_LIST,

        /** Returned by testErrors when state.arrayList produces an error but state.customList does not */
        ARRAY_LIST,
    }
}