package io.github.redox7500.project1;

/** All of the default implementations of the CustomList required methods except for Shaffer's aliases */
public abstract class DefaultCustomListImplementations<E> implements CustomList<E>
{
    @Override
    public void clear()
    {
        this.moveCurrentIndexToStart();
        for (int i = 0; i < this.size(); i++) this.remove();
    }
    
    @Override
    public void moveCurrentIndexToStart() {this.moveCurrentIndexTo(0);}
    
    @Override
    public void moveCurrentIndexToEnd() {this.moveCurrentIndexTo(Math.max(this.size() - 1, 0));}
    
    @Override
    public void moveCurrentIndexToPrevious() {this.moveCurrentIndexTo(this.getCurrentIndex() - 1);}
    
    @Override
    public void moveCurrentIndexToNext() {this.moveCurrentIndexTo(this.getCurrentIndex() + 1);}

    @SuppressWarnings("unchecked")
    @Override
    public CustomList<E> copy()
    {
        CustomList<E> toReturn;

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
    public TestHarness.Testable.EqualityTestResult equalityTest(CustomList<E> other) // only supports comparisons with other CustomLists
    {
        // boolean error1 = false;
        // boolean error2 = false;

        // int size1;
        // try {size1 = this.size();}
        // catch (Throwable _) {error1 = true;}
        // int size2;
        // try {size2 = this.size();}
        // catch (Throwable _) {error2 = true;}
        // if (error1 && error2) return TestHarness.Testable.EqualityTestResult.ERROR_BOTH;
        // if (error1 && !error2) return TestHarness.Testable.EqualityTestResult.ERROR_1;
        // if (!error1 && error2) return TestHarness.Testable.EqualityTestResult.ERROR_2;
        // if (size1 != size2) return TestHarness.Testable.EqualityTestResult.UNEQUAL;
        // if (size1 == 0) return TestHarness.Testable.EqualityTestResult.EQUAL;
        // could do what i did above and make it do the both thing but that's for later right now

        // i just thought of a thing
        // what if i make a record of size, nvm actually
        int thisSize;
        try {thisSize = this.size();}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        int otherSize;
        try {otherSize = other.size();}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        if (thisSize != otherSize) return TestHarness.Testable.EqualityTestResult.UNEQUAL;

        int thisCurrentIndex;
        try {thisCurrentIndex = this.getCurrentIndex();}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        int otherCurrentIndex;
        try {otherCurrentIndex = other.getCurrentIndex();}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        if (thisCurrentIndex != otherCurrentIndex) return TestHarness.Testable.EqualityTestResult.UNEQUAL;
        // int currentIndex = this.getCurrentIndex();
        // if (currentIndex != otherList.getCurrentIndex()) return false;

        try {this.moveCurrentIndexToStart();}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_THIS;}
        
        try {other.moveCurrentIndexToStart();}
        catch (Throwable _) {return TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}
        // otherList.moveCurrentIndexToStart();

        // for (int i = 0; ; i++)
        // {
        //     if (!(this.getCurrentValue().equals(otherList.getCurrentValue()))) return false;

        //     if (i == size - 1) break;

        //     this.moveCurrentIndexToNext();
        //     otherList.moveCurrentIndexToNext();
        // }

        // this.moveCurrentIndexTo(currentIndex);
        // otherList.moveCurrentIndexTo(currentIndex);

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

    // @Override
    // public int hashCode() // apparently i need to do this if i want to override equals(Object)
    // {
    //     int toReturn = 1;

    //     int size = this.size();
    //     int currentIndex = this.getCurrentIndex();

    //     if (this.size() > 0)
    //     {
    //         try
    //         {
    //             this.moveCurrentIndexToStart();

    //             for (int i = 0; ; i++)
    //             {
    //                 E currentValue = this.getCurrentValue();
    //                 toReturn = 31 * toReturn + ((currentValue == null)? 0 : currentValue.hashCode());

    //                 if (i == size - 1) break;

    //                 this.moveCurrentIndexToNext();
    //             }
    //         }
    //         finally
    //         {
    //             this.moveCurrentIndexTo(currentIndex);
    //         }
    //     }

    //     toReturn = 31 * toReturn + Integer.hashCode(size);
    //     toReturn = 31 * toReturn + Integer.hashCode(currentIndex);

    //     E currentValue = this.getCurrentValue();
    //     toReturn = 31 * toReturn + ((currentValue == null)? 0 : currentValue.hashCode());

    //     return toReturn;
    // }
    
    @Override
    public String toString()
    {
        int currentIndex = 0;
        int size = this.size();

        StringBuilder toReturn = new StringBuilder();
        toReturn.append('[');
        if (size != 0)
        {
            this.moveCurrentIndexToStart();

            toReturn.append(this.getCurrentValue());
            for (int i = 0; i < size - 1; i++)
            {
                toReturn.append(", ");
                toReturn.append(this.getCurrentValue());

                this.moveCurrentIndexToNext();
            }
        }
        toReturn.append(']');

        this.moveCurrentIndexTo(currentIndex);

        return toReturn.toString();
    }

    @Override
    public void printRelevantInfo(String name)
    {
        System.out.println(name + ": " + this);
        System.out.println(name + " size: " + this.size());
        System.out.println(name + " current value: " + this.getCurrentValue());
        System.out.println(name + " current index: " + this.getCurrentIndex());
    }
}
