package io.github.redox7500.project2;

import java.util.HashMap;
import java.util.Map;

import io.github.redox7500.testutil.TestHarness;
import io.github.redox7500.testutil.TestEqualityResult;

/** This is what my dictionaries will extend, which implements {@link io.github.redox7500.project2.Project2Dictionary Project2Dictionary}
 * <br>
 * Right now it only forces toString() implementations.
*/
public abstract class Project2AbstractDictionary<K extends Comparable<? super K>, V> implements Project2Dictionary<K, V>
{
    public abstract String toString();

    @Override
    public void printRelevantInfo(String name)
    {
        System.out.print(name + ": ");
        try {System.out.println(this);}
        catch (Throwable error) {System.out.println("error: " + error);}

        System.out.print(name + " size: ");
        int size = 0;
        try {size = this.size(); System.out.println(size);}
        catch (Throwable error) {System.out.println("error: " + error);}
    }

    @Override
    public TestEqualityResult testEquality(Project2Dictionary<K, V> other)
    {
        int thisSize;
        try {thisSize = this.size();}
        catch (Throwable _) {return TestEqualityResult.ERROR_THIS;}

        int otherSize;
        try {otherSize = other.size();}
        catch (Throwable _) {return TestEqualityResult.ERROR_OTHER;}

        if (thisSize != otherSize) return TestEqualityResult.UNEQUAL;
        if (thisSize == 0) return TestEqualityResult.EQUAL; // this one is debatable but i'm feeling lazy right now.
        
        // try {this.moveCurrentIndexToStart();}
        // catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}
        
        // try {other.moveCurrentIndexToStart();}
        // catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        // for (int i = 0; ; i++)
        // {
        //     E thisCurrentValue;
        //     try {thisCurrentValue = this.getCurrentValue();}
        //     catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        //     E otherCurrentValue;
        //     try {otherCurrentValue = other.getCurrentValue();}
        //     catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        //     if (!thisCurrentValue.equals(otherCurrentValue)) return Project1TestHarness.Testable.EqualityTestResult.UNEQUAL;

        //     if (i == thisSize - 1) break;

        //     try {this.moveCurrentIndexToNext();}
        //     catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        //     try {other.moveCurrentIndexToNext();}
        //     catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}
        // }

        // try {this.moveCurrentIndexTo(thisCurrentIndex);}
        // catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        // try {other.moveCurrentIndexTo(otherCurrentIndex);} // could also be phrased as thisCurrentIndex
        // catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        // Project2Dictionary<K, V> thisCopy;
        // try {thisCopy = this.copy();}
        // catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        // Project2Dictionary<K, V> otherCopy;
        // try {otherCopy = other.copy();}
        // catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        K[] thisKeys;
        try {thisKeys = this.getKeys();}
        catch (Throwable _) {return TestEqualityResult.ERROR_THIS;}

        K[] otherKeys;
        try {otherKeys = other.getKeys();}
        catch (Throwable _) {return TestEqualityResult.ERROR_OTHER;}

        // use weak hash map?
        Map<K, Integer> keyCounts = new HashMap<>();
        for (K key : thisKeys) keyCounts.put(key, keyCounts.getOrDefault(key, 0) + 1);
        // System.out.println(keyCounts);
        for (K key : otherKeys)
        {
            Integer keyCount = keyCounts.get(key);
            switch (keyCount)
            {
                case null: return TestEqualityResult.UNEQUAL;
                case 1: keyCounts.remove(key); break;
                default: keyCounts.put(key, keyCount - 1);
            }
        }
        // System.out.println(keyCounts);
        if (!keyCounts.isEmpty()) return TestEqualityResult.UNEQUAL;

        for (K key : thisKeys) // could be otherKeys too i suppose
        {
            V thisValue;
            try {thisValue = this.find(key);}
            catch (Throwable _) {return TestEqualityResult.ERROR_THIS;}

            V otherValue;
            try {otherValue = other.find(key);}
            catch (Throwable _) {return TestEqualityResult.ERROR_OTHER;}

            if (thisValue != otherValue) return TestEqualityResult.UNEQUAL;
        }

        // if (this.toString() != other.toString()) return Project1TestHarness.Testable.EqualityTestResult.UNEQUAL;
        
        return TestEqualityResult.EQUAL;
    }
}
