package project1lists;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Main
{
    private static final Random RNG = new Random();

    /** Return type of testFunction */
    private static enum FunctionTestResult
    {
        /** Returned by testFunction when customList and list having the same behavior and neither produce errors */
        SUCCESS,

        /** Returned by testFunction when customList and list have different behavior */
        FAIL,

        /** Returned by testFunction when both customList and list produce an error (such as the current index being out of bounds) */
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
     * Note: This function only works with Byte lists because generating random numbers for them is easy and there's really no purpose to add other types; they should all work the same (also Bytes are the smallest)
     * @param customList An instance of your custom list implementation to test
     * @param list An instance of reliable code such as ArrayList that has the same values/state as your customList
     * @param currentIndex A supplementary property of list that matches your customList's internal current index (as stated before, both instances should have the exact same values/state)
     * @param function The index of which function you would like to test. These indices are 0 based and go from the top to the bottom of the CustomList.java file.
     * @param functionInput (optional) The value to give to the function at the index you chose, which will not be used if the function you chose has no inputs
     * @return A FunctionTestResult stating the behavior of the customList relative to the list
     */
    private static FunctionTestResult testFunction(CustomList<Byte> customList, List<Byte> list, int currentIndex, int function, int functionInput)
    {
        assert function >= 0 && function < 13 : "Invalid function index";

        switch (function)
        {
            case 0:
            {
                int customListSize;
                try {customListSize = customList.size();}
                catch (Exception _) {return FunctionTestResult.SUCCESS;}

                return (customListSize == list.size())? FunctionTestResult.SUCCESS : FunctionTestResult.FAIL;
            }
            case 1:
            {
                int customListCurrentIndex; // This value should never be used

                boolean listError = currentIndex < 0 || currentIndex >= list.size();

                try {customListCurrentIndex = customList.getCurrentIndex();}
                catch (Exception _) {return (listError)? FunctionTestResult.ERROR : FunctionTestResult.FAIL;}

                return (!listError && customListCurrentIndex == currentIndex)? FunctionTestResult.SUCCESS : FunctionTestResult.FAIL;
            }
            case 2:
            {
                try {customList.moveCurrentIndexTo(0);}
                catch (Exception _) {return FunctionTestResult.FAIL;}
                currentIndex = 0;

                return FunctionTestResult.SUCCESS;
            }
            case 3:
            {
                try {customList.moveCurrentIndexToStart();}
                catch (Exception _) {return FunctionTestResult.FAIL;}
                currentIndex = 0;

                return FunctionTestResult.SUCCESS;
            }
            case 4:
                {
                try {customList.moveCurrentIndexToEnd();}
                catch (Exception _) {return false;}
                currentIndex = list.size() - 1;

                return true;
            }
            case 5:
            {
                boolean listError = currentIndex == 0;

                try {customList.moveCurrentIndexLeft();}
                catch (Exception _) {return listError;}
                currentIndex--;

                return true;
            }
            case 6:
            {
                boolean listError = currentIndex == list.size() - 1;

                try {customList.moveCurrentIndexRight();}
                catch (Exception _) {return listError;}
                currentIndex++;

                return true;
            }
            case 7:
            {
                // note that this should never have an error because a branch will stop if it encounters an invalid index/any kind of error at all/any kind of discrepancy between customList and list
                return customList.getCurrentValue() == list.get(currentIndex);
            }
            case 8:
            {
                byte[] valueArray = new byte[]{}; RNG.nextBytes(valueArray);
                try {customList.setCurrentValue(valueArray[0]);}
                catch (Exception _) {return false;}
                list.set(currentIndex, valueArray[0]);

                return true;
            }
            case 9:
            {
                try {customList.clear();}
                catch (Exception _) {return false;}
                list.clear();

                return true;
            }
            case 10:
            {
                try {customList.insert(functionInput);}
                catch (Exception _) {return false;}
                list.add(currentIndex, functionInput);

                return true;
            }
            case 11:
            {
                try {customList.append(functionInput);}
                catch (Exception _) {return false;}
                list.add(functionInput);

                return true;
            }
            case 12:
            {
                boolean listError = list.size() == 0;

                try {customList.remove();}
                catch (Exception _) {return listError;}
                list.remove(currentIndex);

                return true;
            }
            default:
            {
                return false; // This code should never be able to happen because of the assert above
            }
        }
    }

    /** This is an overload of {@link #project1lists.Main.testFunction(CustomList, List, int, int, Byte) testFunction} without the last Byte argument (functionInput). This is replaced with a random number.
     * <p>
     * Note: This function only works with Byte lists because generating random numbers for them is easy and there's really no purpose to add other types; they should all work the same (also Bytes are the smallest)
     * @param customList An instance of your custom list implementation to test
     * @param list An instance of reliable code such as ArrayList that has the same values/state as your customList
     * @param currentIndex A supplementary property of list that matches your customList's internal current index (as stated before, both instances should have the exact same values/state)
     * @param function The index of which function you would like to test. These indices are 0 based and go from the top to the bottom of the CustomList.java file.
     * @param functionInput (optional) The value to give to the function at the index you chose, which will not be used if the function you chose has no inputs
     * @return A FunctionTestResult stating the behavior of the customList relative to the list
     */
    private static boolean testFunction(CustomList<Byte> customList, List<Byte> list, int currentIndex, int function)
    {
        byte[] value = new byte[]{}; RNG.nextBytes(value);
        return Main.testFunction(customList, list, currentIndex, function, value[0]);
    }

    public static void main(String[] args)
    {
        
        CustomList<Integer> list = new CustomLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.moveCurrentIndexToEnd();
        System.out.println(list.getCurrentValue());
        list.println();
        list.moveCurrentIndexLeft();
        list.remove();
        list.println();
        list.insert(3);
        System.out.println(list.getCurrentValue());
        list.println();
        list.moveCurrentIndexRight();
        System.out.println(list.getCurrentValue());
        list.remove();
        list.println();
        list.remove();
        list.println();
        list.append(4);
        list.println();
        System.out.println(list.getCurrentValue());
    }
}