package io.github.redox7500.project1;

import io.github.redox7500.testutil.TestHarness;

// make a static thing that takes in Project1List or smth and does copy or equalitytest with it statically wow so cool so no inheritance hopefully
public interface TestableProject1List<E> implements io.github.redox7500.project1.Project1List<E>, io.github.redox7500.testutil.TestHarness.Testable<TestableProject1List<E>>
{
    @SuppressWarnings("unchecked")
    @Override
    public default TestableProject1List<E> copy()
    {
        TestableProject1List<E> toReturn;

        try {toReturn = this.getClass().getDeclaredConstructor().newInstance();}
        catch (Throwable _) {System.err.println("Class to copy has no visible parameterless constructor"); return null;}

        int size = this.size();
        if (size == 0) return toReturn;

        int currentIndex = this.getCurrentIndex();

        try
        {
            this.moveCurrentIndexToStart();

            for (int i = 0; ; i++)
            {
                toReturn.append(this.getCurrentValue());

                if (i == size - 1) break;

                this.moveCurrentIndexToNext();
            }
            toReturn.append(this.getCurrentValue());

            toReturn.moveCurrentIndexTo(currentIndex);
        }
        finally
        {
            this.moveCurrentIndexTo(currentIndex);
        }

        return toReturn;
    }
    
    @Override
    public TestHarness.Testable.EqualityTestResult equalityTest(TestableProject1AbstractList<E> other) // only supports comparisons with other CustomLists
    {
        int thisSize;
        try {thisSize = this.size();}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        int otherSize;
        try {otherSize = other.size();}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        if (thisSize != otherSize) return TestHarness.Testable.EqualityTestResult.UNEQUAL;
        if (thisSize == 0) return TestHarness.Testable.EqualityTestResult.EQUAL; // this one is debatable but i'm feeling lazy right now.

        int thisCurrentIndex;
        try {thisCurrentIndex = this.getCurrentIndex();}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        int otherCurrentIndex;
        try {otherCurrentIndex = other.getCurrentIndex();}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        if (thisCurrentIndex != otherCurrentIndex) return TestHarness.Testable.EqualityTestResult.UNEQUAL;

        try {this.moveCurrentIndexToStart();}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_THIS;}
        
        try {other.moveCurrentIndexToStart();}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        for (int i = 0; ; i++)
        {
            E thisCurrentValue;
            try {thisCurrentValue = this.getCurrentValue();}
            catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

            E otherCurrentValue;
            try {otherCurrentValue = other.getCurrentValue();}
            catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

            if (!thisCurrentValue.equals(otherCurrentValue)) return TestHarness.Testable.EqualityTestResult.UNEQUAL;

            if (i == thisSize - 1) break;

            try {this.moveCurrentIndexToNext();}
            catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

            try {other.moveCurrentIndexToNext();}
            catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}
        }

        try {this.moveCurrentIndexTo(thisCurrentIndex);}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        try {other.moveCurrentIndexTo(otherCurrentIndex);} // could also be phrased as thisCurrentIndex
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        return TestHarness.Testable.EqualityTestResult.EQUAL;
    }
}
