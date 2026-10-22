package io.github.redox7500.project2;

import io.github.redox7500.project1.Project1TestHarness;
import io.github.redox7500.project1.Project1TestHarness.Testable;

/** This is what my dictionaries will extend, which implements {@link io.github.redox7500.project2.Project2Queue Project2Queue}
 * <br>
 * Right now it only forces toString() implementations.
*/
public abstract class Project2AbstractQueue<E> implements Project2Queue<E>
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
        try {size = this.length(); System.out.println(size);}
        catch (Throwable error) {System.out.println("error: " + error);}

        System.out.print(name + " front value: ");
        if (size > 0) // kinda scuffed using this possibly unitialized value of 0 thing but idk i'll think about that one for later
        // also i could definitely make this a ternary in the line below, where this.getCurrentValue() is it would branch i think
        {
            try {System.out.println(this.frontValue());}
            catch (Throwable error) {System.out.println("error: " + error);}
        }
        else
        {
            System.out.println("N/A (empty queue)");
        }
    }

    @Override
    public Testable.EqualityTestResult equalityTest(Project2Queue<E> other)
    {
        int thisSize;
        try {thisSize = this.length();}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        int otherSize;
        try {otherSize = other.length();}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        // frontValue, enqueue
        if (thisSize != otherSize) return Project1TestHarness.Testable.EqualityTestResult.UNEQUAL;
        if (thisSize == 0) return Project1TestHarness.Testable.EqualityTestResult.EQUAL; // this one is debatable but i'm feeling lazy right now.

        E thisFrontValue;
        try {thisFrontValue = this.frontValue();}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        E otherFrontValue;
        try {otherFrontValue = other.frontValue();}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        if (thisFrontValue != otherFrontValue) return Project1TestHarness.Testable.EqualityTestResult.UNEQUAL;

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

        if (!this.toString().equals(other.toString())) return Project1TestHarness.Testable.EqualityTestResult.UNEQUAL;
        
        return Project1TestHarness.Testable.EqualityTestResult.EQUAL;
        // // i'll fix this
        // return EqualityTestResult.EQUAL;
    }
}
