package project1lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestHarness
{
    private static final Random RNG = new Random();

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

    /** Tests if customList and list have the same size and the same elements in the same order
     * <p>
     * Note: This function will not reset the current index of customList
     * @param customList An instance of an implementation of CustomList to compare
     * @param list An instance of an implementation of List to compare
     * @return A boolean representing whether or not customList and list have the same size and the same elements in the same order
     */
    private static <T> boolean equals(CustomList<T> customList, List<T> list)
    {
        if (customList.size() != list.size()) return false;

        int customListIndex = customList.getCurrentIndex();
        
        customList.moveCurrentIndexToStart();
        boolean toReturn = true;
        for (int i = 0; i < list.size() - 1; i++)
        {
            if (customList.getCurrentValue() != list.get(i)) {toReturn = false; break;}
            customList.moveCurrentIndexRight();
        }
        if (customListIndex != 0) customList.moveCurrentIndexTo(customListIndex);

        return toReturn;
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
    private static TestCustomListResult testCustomListMethod(CustomList<Integer> customList, List<Integer> list, int[] currentIndex, int function, int functionInput)
    {
        assert function >= 0 && function < 13 : "Invalid function index";

        switch (function)
        {
            case 0:
            {
                int customListSize;
                try {customListSize = customList.size();}
                catch (Throwable _) {return TestCustomListResult.FAIL;}

                return (customListSize == list.size())? TestCustomListResult.SUCCESS : TestCustomListResult.FAIL;
            }
            case 1:
            {
                // boolean listError = currentIndex[0] < 0 || ((list.size() > 0)? currentIndex[0] >= list.size() : currentIndex[0] != 0);
                
                int customListCurrentIndex;
                try {customListCurrentIndex = customList.getCurrentIndex();}
                // catch (Throwable _) {return (listError)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                
                // return (!listError && customListCurrentIndex == currentIndex[0])? TestCustomListResult.SUCCESS : TestCustomListResult.FAIL;
                return (customListCurrentIndex == currentIndex[0])? TestCustomListResult.SUCCESS : TestCustomListResult.FAIL;
            }
            case 2:
            {
                boolean listError = functionInput < 0 || ((list.size() > 0)? functionInput >= list.size() : functionInput != 0);

                try {customList.moveCurrentIndexTo(functionInput);} // this is kinda scuffed, not all indices will be checked with this implementation, fix maybe by looping through all valid?
                catch (Throwable _) {return (listError)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                if (listError) return TestCustomListResult.FAIL;
                currentIndex[0] = functionInput;
                // System.out.println(list.size());
                // System.out.println(functionInput);
                // System.out.println(customList.getCurrentIndex());

                return TestCustomListResult.SUCCESS;
            }
            case 3:
            {
                try {customList.moveCurrentIndexToStart();}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                currentIndex[0] = 0;

                return TestCustomListResult.SUCCESS;
            }
            case 4:
            {
                try {customList.moveCurrentIndexToEnd();}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                currentIndex[0] = Math.max(list.size() - 1, 0);

                return TestCustomListResult.SUCCESS;
            }
            case 5:
            {
                boolean listError = currentIndex[0] <= 0;

                try {customList.moveCurrentIndexLeft();}
                catch (Throwable _) {return (listError)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                currentIndex[0]--;

                return (listError)? TestCustomListResult.FAIL : TestCustomListResult.SUCCESS;
            }
            case 6:
            {
                boolean listError = (list.size() > 0)? currentIndex[0] >= list.size() - 1 : true;

                try {customList.moveCurrentIndexRight();}
                catch (Throwable _) {return (listError)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                if (listError) return TestCustomListResult.FAIL;
                currentIndex[0]++;

                return TestCustomListResult.SUCCESS;
            }
            case 7:
            {
                boolean listError = list.size() == 0;

                Integer customListCurrentValue;
                try {customListCurrentValue = customList.getCurrentValue();}
                catch (Throwable _) {return (listError)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                if (listError) return TestCustomListResult.FAIL;

                // note that this should never have an error because a branch will stop if it encounters an invalid index/any kind of error at all/any kind of discrepancy between customList and list
                return (customListCurrentValue == list.get(currentIndex[0]))? TestCustomListResult.SUCCESS : TestCustomListResult.FAIL;
            }
            case 8:
            {
                try {customList.setCurrentValue(functionInput);}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                if (list.size() > 0) list.set(currentIndex[0], functionInput);
                else list.add(functionInput);

                return TestCustomListResult.SUCCESS;
            }
            case 9:
            {
                try {customList.clear();}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                list.clear();

                return TestCustomListResult.SUCCESS;
            }
            case 10:
            {
                try {customList.insert(functionInput);}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                list.add(currentIndex[0], functionInput);

                return TestCustomListResult.SUCCESS;
            }
            case 11:
            {
                try {customList.append(functionInput);}
                catch (Throwable _) {return TestCustomListResult.FAIL;}
                list.add(functionInput);

                return TestCustomListResult.SUCCESS;
            }
            case 12:
            {
                boolean listError = list.size() == 0;

                try {customList.remove();}
                catch (Throwable _) {return (listError)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                if (listError) return TestCustomListResult.FAIL;
                list.remove(currentIndex[0]);

                return TestCustomListResult.SUCCESS;
            }
            default:
            {
                return TestCustomListResult.FAIL; // This code should never be able to happen because of the assert above
            }
        }
    }

    /** This is an overload of {@link #project1lists.Main.testCustomListMethod(CustomList, List, int, int, Byte) testCustomListMethod} without the last Byte argument (functionInput). This is replaced with a random number.
     * <p>
     * Note: This function only works with Byte lists because generating random numbers for them is easy and there's really no purpose to add other types; they should all work the same (also Bytes are the smallest)
     * @param customList An instance of your custom list implementation to test
     * @param list An instance of reliable code such as ArrayList that has the same values/state as your customList
     * @param currentIndex A supplementary property of list that matches your customList's internal current index (as stated before, both instances should have the exact same values/state)
     * @param function The index of which function you would like to test. These indices are 0 based and go from the top to the bottom of the CustomList.java file.
     * @param functionInput (optional) The value to give to the function at the index you chose, which will not be used if the function you chose has no inputs
     * @return A TestCustomListResult stating the behavior of the customList relative to the list
     */
    private static TestCustomListResult testCustomListMethod(CustomList<Integer> customList, List<Integer> list, int[] currentIndex, int function)
    {
        return TestHarness.testCustomListMethod(customList, list, currentIndex, function, RNG.nextInt());
    }

    /** Test every function of the customListClass
     * <p>
     * Note: This function also only uses Integer elements, see {@link #project1lists.TestHarness.testFunction(CustomList, List, int, int, int) testFunction} for why
     * @param customListClass The CustomList implementation to test
     * @return Whether or not the class's behavior is as expected (compared to {@link #java.util.ArrayList ArrayList}) <!-- this link doesn't work for some weird reason -->
    */
    /** Test every method of the customListClass
     * Note: This function also only works with an element type of Integer, see {@link project1lists.TestHarness.testCustomListMethod(CustomList, List, int, int, int) testCustomListMethod} for why
     * @param customListClass The class to test the behavior of
     * @param depth How many layers deep every function is applied (making a new layer) and the behavior tested
     * @return A boolean representing whether or not the customListClass behaves as expected (compared to the builtin {@link java.util.ArrayList ArrayList}) <!-- this link doesn't work for some reason -->
     */
    public static <T extends CustomList<Integer>> boolean testCustomList(Class<T> customListClass, int depth)
    {
        ArrayList<CustomList<Integer>> customListLeaves = new ArrayList<>();
        ArrayList<ArrayList<Integer>> arrayListLeaves = new ArrayList<>();
        ArrayList<int[]> arrayListLeafCurrentIndices = new ArrayList<>();
        ArrayList<int[]> functionHistories = new ArrayList<>();
        ArrayList<int[]> argumentHistories = new ArrayList<>();
        try {customListLeaves.add(customListClass.getDeclaredConstructor().newInstance());}
        catch (Throwable _) {System.out.println("customListClass has no accessible parameterless constructor"); return false;}
        arrayListLeaves.add(new ArrayList<>());
        arrayListLeafCurrentIndices.add(new int[]{0});
        functionHistories.add(new int[]{});
        argumentHistories.add(new int[]{});
        for (int currentDepth = 0; currentDepth < depth; currentDepth++)
        {
            // lowk make this a node class or something?
            ArrayList<CustomList<Integer>> newCustomListLeaves = new ArrayList<>();
            ArrayList<ArrayList<Integer>> newArrayListLeaves = new ArrayList<>();
            ArrayList<int[]> newArrayListLeafCurrentIndices = new ArrayList<>();
            ArrayList<int[]> newFunctionHistories = new ArrayList<>();
            ArrayList<int[]> newArgumentHistories = new ArrayList<>();
            for (int currentLeafIndex = 0; currentLeafIndex < arrayListLeaves.size(); currentLeafIndex++)
            {
                for (int currentFunction = 0; currentFunction < 13; currentFunction++)
                {
                    for (int currentArgument = -1; currentArgument < arrayListLeaves.get(currentLeafIndex).size() + 1; currentArgument++)
                    {
                        CustomList<Integer> newCustomListLeaf = customListLeaves.get(currentLeafIndex).copy();
                        ArrayList<Integer> newArrayListLeaf = new ArrayList<>(arrayListLeaves.get(currentLeafIndex));
                        int[] newArrayListLeafCurrentIndex = new int[]{arrayListLeafCurrentIndices.get(currentLeafIndex)[0]};

                        int[] newFunctionHistory = new int[currentDepth + 1];
                        System.arraycopy(functionHistories.get(currentLeafIndex), 0, newFunctionHistory, 0, currentDepth);
                        newFunctionHistory[currentDepth] = currentFunction;
                        
                        int[] newArgumentHistory = new int[currentDepth + 1];
                        System.arraycopy(argumentHistories.get(currentLeafIndex), 0, newArgumentHistory, 0, currentDepth);
                        newArgumentHistory[currentDepth] = currentArgument;
                        
                        switch (TestHarness.testCustomListMethod(newCustomListLeaf, newArrayListLeaf, newArrayListLeafCurrentIndex, currentFunction, currentArgument))
                        {
                            case TestCustomListResult.ERROR:
                                continue;
                            case TestCustomListResult.FAIL:
                                System.out.println("Failed at state below");
                                System.out.println("Custom list: " + newCustomListLeaf);
                                System.out.println("ArrayList: " + newArrayListLeaf);
                                System.out.println("Current index: " + newArrayListLeafCurrentIndex[0]);
                                System.out.println("Current depth (starting at 0): " + currentDepth);
                                System.out.println("Function history (first to last): " + newFunctionHistory);
                                System.out.println("Argument history (first to last): " + newArgumentHistory);
                                return false;
                            case TestCustomListResult.SUCCESS:
                                if (!TestHarness.equals(newCustomListLeaf, newArrayListLeaf))
                                {
                                    System.out.println("Lists were not equal at state below");
                                    System.out.println("Custom list: " + newCustomListLeaf);
                                    System.out.println("ArrayList: " + newArrayListLeaf);
                                    System.out.println("Current index: " + newArrayListLeafCurrentIndex[0]);
                                    System.out.println("Current depth (starting at 0): " + currentDepth);
                                    System.out.println("Function history (first to last): " + newFunctionHistory);
                                    System.out.println("Argument history (first to last): " + newArgumentHistory);
                                    return false;
                                }
                        
                                newCustomListLeaves.add(newCustomListLeaf);
                                newArrayListLeaves.add(newArrayListLeaf);
                                newArrayListLeafCurrentIndices.add(newArrayListLeafCurrentIndex);
                                newFunctionHistories.add(newFunctionHistory);
                                newArgumentHistories.add(newArgumentHistory);
                        }
                    }
                }
            }
            customListLeaves = newCustomListLeaves;
            arrayListLeaves = newArrayListLeaves;
            arrayListLeafCurrentIndices = newArrayListLeafCurrentIndices;
            functionHistories = newFunctionHistories;
            argumentHistories = newArgumentHistories;
        }
        return true;
    }
}