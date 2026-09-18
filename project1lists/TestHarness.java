package project1lists;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.Arrays;

public class TestHarness
{
    /** Return type of testCustomListMethod */
    private static enum TestCustomListResult
    {
        /** Returned by testCustomListMethod when customList and list having the same behavior and neither produce errors */
        SUCCESS,

        /** Returned by testCustomListMethod when customList and list have different behavior */
        FAIL,

        /** Returned by testCustomListMethod when both customList and list produce an error (such as the current index being out of bounds) */
        ERROR
    }

    /** Tests if customList and list behave the same when the specified function is called
     * <p>
     * Note: This function only works with Integer lists because generating random numbers for them is easy and there's really no purpose to add other types; they should all work the same (also Integers make it so that functionInput works for both values and indices)
     * @param customList An instance of your custom list implementation to test
     * @param list An instance of reliable code such as ArrayList that has the same values/state as your customList
     * @param currentIndex A supplementary property of list that matches your customList's internal current index (as stated before, both instances should have the exact same values/state)
     * @param function The index of which function you would like to test. These indices are 0 based and go from the top to the bottom of the CustomList.java file.
     * @param functionInput (optional) The value to give to the function you chose, which will not be used if the function you chose has no inputs
     * @return A TestCustomListResult stating the behavior of the customList relative to the list
     */
    private static TestCustomListResult testCustomListMethod(State<?> state)
    {
        int function = state.functionHistory[state.functionHistory.length - 1];

        assert function >= 0 && function < 13 : "Invalid function index";

        int argument = state.argumentHistory[state.argumentHistory.length - 1];
        switch (function)
        {
            case 0:
            {
                int customListSize;
                try {customListSize = state.customList.size();}
                catch (Throwable _) {return TestCustomListResult.FAIL;}

                return (customListSize == state.arrayList.size())? TestCustomListResult.SUCCESS : TestCustomListResult.FAIL;
            }
            case 1:
            {
                int customListCurrentIndex;
                try {customListCurrentIndex = state.customList.getCurrentIndex();}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                
                return (customListCurrentIndex == state.arrayListCurrentIndex)? TestCustomListResult.SUCCESS : TestCustomListResult.FAIL;
            }
            case 2:
            {
                boolean listError = argument < 0 || ((state.arrayList.size() > 0)? argument >= state.arrayList.size() : argument != 0);

                try {state.customList.moveCurrentIndexTo(argument);}
                catch (Throwable _) {return (listError)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                if (listError) return TestCustomListResult.FAIL;
                state.arrayListCurrentIndex = argument;

                return TestCustomListResult.SUCCESS;
            }
            case 3:
            {
                try {state.customList.moveCurrentIndexToStart();}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                state.arrayListCurrentIndex = 0;

                return TestCustomListResult.SUCCESS;
            }
            case 4:
            {
                try {state.customList.moveCurrentIndexToEnd();}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                state.arrayListCurrentIndex = Math.max(state.arrayList.size() - 1, 0);

                return TestCustomListResult.SUCCESS;
            }
            case 5:
            {
                boolean listError = state.arrayListCurrentIndex <= 0;

                try {state.customList.moveCurrentIndexLeft();}
                catch (Throwable _) {return (listError)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                state.arrayListCurrentIndex--;

                return (listError)? TestCustomListResult.FAIL : TestCustomListResult.SUCCESS;
            }
            case 6:
            {
                boolean listError = (state.arrayList.size() > 0)? state.arrayListCurrentIndex >= state.arrayList.size() - 1 : true;

                try {state.customList.moveCurrentIndexRight();}
                catch (Throwable _) {return (listError)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                if (listError) return TestCustomListResult.FAIL;
                state.arrayListCurrentIndex++;

                return TestCustomListResult.SUCCESS;
            }
            case 7:
            {
                boolean listError = state.arrayList.size() == 0;

                Integer customListCurrentValue;
                try {customListCurrentValue = state.customList.getCurrentValue();}
                catch (Throwable _) {return (listError)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                if (listError) return TestCustomListResult.FAIL;
                
                return (customListCurrentValue == state.arrayList.get(state.arrayListCurrentIndex))? TestCustomListResult.SUCCESS : TestCustomListResult.FAIL;
            }
            case 8:
            {
                try {state.customList.setCurrentValue(argument);}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                if (state.arrayList.size() > 0) state.arrayList.set(state.arrayListCurrentIndex, argument);
                else state.arrayList.add(argument);

                return TestCustomListResult.SUCCESS;
            }
            case 9:
            {
                try {state.customList.clear();}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                state.arrayList.clear();

                return TestCustomListResult.SUCCESS;
            }
            case 10:
            {
                try {state.customList.insert(argument);}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                state.arrayList.add(state.arrayListCurrentIndex, argument);

                return TestCustomListResult.SUCCESS;
            }
            case 11:
            {
                try {state.customList.append(argument);}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                state.arrayList.add(argument);

                return TestCustomListResult.SUCCESS;
            }
            case 12:
            {
                boolean listError = state.arrayList.size() == 0;

                try {state.customList.remove();}
                catch (Throwable _) {return (listError)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                if (listError) return TestCustomListResult.FAIL;
                state.arrayList.remove(state.arrayListCurrentIndex);

                return TestCustomListResult.SUCCESS;
            }
            default:
            {
                return TestCustomListResult.FAIL; // This code should never be able to happen because of the assert above
            }
        }
    }

    private static class State<T extends CustomList<Integer>>
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

        @SuppressWarnings("unchecked")
        private State(State<T> previousState, int function, int argument)
        {
            this.customList = (T)previousState.customList.copy();
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

        private boolean isValid()
        {
            if (customList.size() != arrayList.size() || customList.getCurrentIndex() != this.arrayListCurrentIndex) return false;

            int customListIndex = customList.getCurrentIndex();
            
            customList.moveCurrentIndexToStart();
            boolean toReturn = true;
            for (int i = 0; i < arrayList.size() - 1; i++)
            {
                if (customList.getCurrentValue() != arrayList.get(i)) {toReturn = false; break;}
                customList.moveCurrentIndexRight();
            }
            if (customListIndex != 0) customList.moveCurrentIndexTo(customListIndex);

            return toReturn;
        }

        private void printInfo()
        {
            System.out.println("Custom list: " + this.customList);
            System.out.println("ArrayList: " + this.arrayList);
            System.out.println("Current index: " + this.arrayListCurrentIndex);
            System.out.println("Function history (first to most recent): " + Arrays.toString(this.functionHistory));
            System.out.println("Argument history (first to most recent): " + Arrays.toString(this.argumentHistory));
        }

        private boolean addLeaves(ArrayList<State<T>> leaves)
        {
            for (int currentFunction = 0; currentFunction < 13; currentFunction++)
            {
                for (int currentArgument = -1; currentArgument < this.arrayList.size() + 1; currentArgument++)
                {
                    State<T> newState = new State<>(this, currentFunction, currentArgument);
                    
                    switch (TestHarness.testCustomListMethod(newState))
                    {
                        case TestCustomListResult.ERROR:
                            continue;
                        case TestCustomListResult.FAIL:
                            System.out.println("Failed at state below");
                            newState.printInfo();

                            return false;
                        case TestCustomListResult.SUCCESS:
                            if (!newState.isValid())
                            {
                                System.out.println("Lists were not equal at state below");
                                newState.printInfo();

                                return false;
                            }
                            leaves.add(newState);
                    }
                }
            }

            return true;
        }
    }

    /** Test every method of the customListClass
     * Note: This function also only works with an element type of Integer, see {@link project1lists.TestHarness.testCustomListMethod(CustomList, List, int, int, int) testCustomListMethod} for why
     * @param customListClass The class to test the behavior of
     * @param depth How many layers deep every function is applied (making a new layer) and the behavior tested
     * @return A boolean representing whether or not the customListClass behaves as expected (compared to the builtin {@link java.util.ArrayList ArrayList}) <!-- this link doesn't work for some reason -->
     */
    public static <T extends CustomList<Integer>> boolean testCustomList(Supplier<T> constructor, int depth)
    {
        ArrayList<State<T>> leaves = new ArrayList<>();
        leaves.add(new State<>(constructor));

        for (int i = 0; i < depth; i++)
        {
            ArrayList<State<T>> newLeaves = new ArrayList<>();
            for (State<T> leaf : leaves) if (!leaf.addLeaves(newLeaves)) return false;
            leaves = newLeaves;
        }
        return true;
    }
}