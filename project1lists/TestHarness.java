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
        for (int i = 0; i < list.size(); i++)
        {
            if (customList.getCurrentValue() != list.get(i)) {toReturn = false; break;}
        }
        customList.moveCurrentIndexTo(customListIndex);
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
                catch (Exception _) {return TestCustomListResult.SUCCESS;}

                return (customListSize == list.size())? TestCustomListResult.SUCCESS : TestCustomListResult.FAIL;
            }
            case 1:
            {
                int customListCurrentIndex; // This value should never be used

                boolean listError = currentIndex[0] < 0 || currentIndex[0] >= list.size();

                try {customListCurrentIndex = customList.getCurrentIndex();}
                catch (Exception _) {return (listError)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}

                return (!listError && customListCurrentIndex == currentIndex[0])? TestCustomListResult.SUCCESS : TestCustomListResult.FAIL;
            }
            case 2:
            {
                try {customList.moveCurrentIndexTo(0);}
                catch (Exception _) {return TestCustomListResult.FAIL;}
                currentIndex[0] = 0;

                return TestCustomListResult.SUCCESS;
            }
            case 3:
            {
                try {customList.moveCurrentIndexToStart();}
                catch (Exception _) {return TestCustomListResult.FAIL;}
                currentIndex[0] = 0;

                return TestCustomListResult.SUCCESS;
            }
            case 4:
            {
                try {customList.moveCurrentIndexToEnd();}
                catch (Exception _) {return TestCustomListResult.FAIL;}
                currentIndex[0] = list.size() - 1;

                return TestCustomListResult.SUCCESS;
            }
            case 5:
            {
                try {customList.moveCurrentIndexLeft();}
                catch (Exception _) {return (currentIndex[0] == 0)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                currentIndex[0]--;

                return TestCustomListResult.SUCCESS;
            }
            case 6:
            {
                try {customList.moveCurrentIndexRight();}
                catch (Exception _) {return (currentIndex[0] == list.size() - 1)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                currentIndex[0]++;

                return TestCustomListResult.SUCCESS;
            }
            case 7:
            {
                // note that this should never have an error because a branch will stop if it encounters an invalid index/any kind of error at all/any kind of discrepancy between customList and list
                return (customList.getCurrentValue() == list.get(currentIndex[0]))? TestCustomListResult.SUCCESS : TestCustomListResult.FAIL;
            }
            case 8:
            {
                try {customList.setCurrentValue(functionInput);}
                catch (Exception _) {return (list.size() == 0)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                list.set(currentIndex[0], functionInput);

                return TestCustomListResult.SUCCESS;
            }
            case 9:
            {
                try {customList.clear();}
                catch (Exception _) {return TestCustomListResult.FAIL;}
                list.clear();

                return TestCustomListResult.SUCCESS;
            }
            case 10:
            {
                try {customList.insert(functionInput);}
                catch (Exception _) {return TestCustomListResult.FAIL;}
                list.add(currentIndex[0], functionInput);

                return TestCustomListResult.SUCCESS;
            }
            case 11:
            {
                try {customList.append(functionInput);}
                catch (Exception _) {return TestCustomListResult.FAIL;}
                list.add(functionInput);

                return TestCustomListResult.SUCCESS;
            }
            case 12:
            {
                try {customList.remove();}
                catch (Exception _) {return (list.size() == 0)? TestCustomListResult.ERROR : TestCustomListResult.FAIL;}
                list.remove(currentIndex);

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

    /** Tells you whether or not your customListClass behaves as expected (compared to the builtin {@link java.util.ArrayList ArrayList}) <!-- this link doesn't work for some reason -->
     * Note: This function also only works with an element type of Integer, see {@link project1lists.TestHarness.testCustomListMethod(CustomList, List, int, int, int)} for why
     * @param customListClass The class to test the behavior of
     * @param depth How many layers deep every function is applied (making a new layer) and the behavior tested
     * @return A boolean representing whether or not the customListClass behaves as expected (compared to the builtin {@link java.util.ArrayList ArrayList}) <!-- this link doesn't work for some reason -->
     */
    private static <T extends CustomList<Integer>> boolean testCustomList(Class<T> customListClass, int depth)
    {
        ArrayList<CustomList<Integer>> customListLeaves = new ArrayList<>();
        ArrayList<ArrayList<Integer>> arrayListLeaves = new ArrayList<>();
        ArrayList<int[]> arrayListLeafCurrentIndices = new ArrayList<>();
        try {customListLeaves.add(customListClass.getDeclaredConstructor().newInstance());}
        catch (Exception _) {return false;}
        arrayListLeaves.add(new ArrayList<>());
        arrayListLeafCurrentIndices.add(new int[]{0});
        for (int i = 0; i < depth; i++)
        {
            ArrayList<CustomList<Integer>> newCustomListLeaves = new ArrayList<>();
            ArrayList<ArrayList<Integer>> newArrayListLeaves = new ArrayList<>();
            ArrayList<int[]> newArrayListLeafCurrentIndices = new ArrayList<>();
            for (int j = 0; j < arrayListLeaves.size(); j++)
            {
                for (int k = 0; k < 13; k++)
                {
                    CustomList<Integer> newCustomListLeaf = customListLeaves.get(j).copy();
                    ArrayList<Integer> newArrayListLeaf = new ArrayList<>(arrayListLeaves.get(j));
                    int[] newArrayListLeafCurrentIndex = new int[1];
                    System.arraycopy(arrayListLeafCurrentIndices.get(j), 0, newArrayListLeafCurrentIndex, 0, 1);
                    
                    switch (TestHarness.testCustomListMethod(newCustomListLeaf, newArrayListLeaf, newArrayListLeafCurrentIndex, k, i))
                    {
                        case TestCustomListResult.ERROR:
                            continue;
                        case TestCustomListResult.FAIL:
                            return false;
                        default:
                    }
                    if (!TestHarness.equals(newCustomListLeaf, newArrayListLeaf)) return false;
                    
                    newCustomListLeaves.add(newCustomListLeaf);
                    newArrayListLeaves.add(newArrayListLeaf);
                    newArrayListLeafCurrentIndices.add(newArrayListLeafCurrentIndex);
                }
            }
            customListLeaves = newCustomListLeaves;
            arrayListLeaves = newArrayListLeaves;
            arrayListLeafCurrentIndices = newArrayListLeafCurrentIndices;
        }
        return true;
    }
}