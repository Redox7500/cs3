package io.github.redox7500.project2;

import io.github.redox7500.project1.Project1TestHarness;

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
    public EqualityTestResult equalityTest(Project2Dictionary<K, V> other)
    {
        int thisSize;
        try {thisSize = this.size();}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        int otherSize;
        try {otherSize = other.size();}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        if (thisSize != otherSize) return Project1TestHarness.Testable.EqualityTestResult.UNEQUAL;
        if (thisSize == 0) return Project1TestHarness.Testable.EqualityTestResult.EQUAL; // this one is debatable but i'm feeling lazy right now.
        
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

        if (this.toString() != other.toString()) return Project1TestHarness.Testable.EqualityTestResult.UNEQUAL;
        
        return Project1TestHarness.Testable.EqualityTestResult.EQUAL;
    }
}
