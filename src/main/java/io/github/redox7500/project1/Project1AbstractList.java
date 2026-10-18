package io.github.redox7500.project1;

/** All of the default implementations of the CustomList required methods except for Shaffer's aliases */
public abstract class Project1AbstractList<E> implements Project1List<E>
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
    public Project1List<E> copy()
    {
        Project1List<E> toReturn;

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
    public Project1TestHarness.Testable.EqualityTestResult equalityTest(Project1List<E> other) // only supports comparisons with other CustomLists
    {
        int thisSize;
        try {thisSize = this.size();}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        int otherSize;
        try {otherSize = other.size();}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        if (thisSize != otherSize) return Project1TestHarness.Testable.EqualityTestResult.UNEQUAL;
        if (thisSize == 0) return Project1TestHarness.Testable.EqualityTestResult.EQUAL; // this one is debatable but i'm feeling lazy right now.

        int thisCurrentIndex;
        try {thisCurrentIndex = this.getCurrentIndex();}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        int otherCurrentIndex;
        try {otherCurrentIndex = other.getCurrentIndex();}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        if (thisCurrentIndex != otherCurrentIndex) return Project1TestHarness.Testable.EqualityTestResult.UNEQUAL;

        try {this.moveCurrentIndexToStart();}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}
        
        try {other.moveCurrentIndexToStart();}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        for (int i = 0; ; i++)
        {
            E thisCurrentValue;
            try {thisCurrentValue = this.getCurrentValue();}
            catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

            E otherCurrentValue;
            try {otherCurrentValue = other.getCurrentValue();}
            catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

            if (!thisCurrentValue.equals(otherCurrentValue)) return Project1TestHarness.Testable.EqualityTestResult.UNEQUAL;

            if (i == thisSize - 1) break;

            try {this.moveCurrentIndexToNext();}
            catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

            try {other.moveCurrentIndexToNext();}
            catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}
        }

        try {this.moveCurrentIndexTo(thisCurrentIndex);}
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_THIS;}

        try {other.moveCurrentIndexTo(otherCurrentIndex);} // could also be phrased as thisCurrentIndex
        catch (Throwable _) {return Project1TestHarness.Testable.EqualityTestResult.ERROR_OTHER;}

        return Project1TestHarness.Testable.EqualityTestResult.EQUAL;
    }
    
    @Override
    public String toString()
    {
        int currentIndex = this.getCurrentIndex();
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
        System.out.print(name + ": ");
        try {System.out.println(this);}
        catch (Throwable error) {System.out.println("error: " + error);}

        System.out.print(name + " size: ");
        int size = 0;
        try {size = this.size(); System.out.println(size);}
        catch (Throwable error) {System.out.println("error: " + error);}

        System.out.print(name + " current value: ");
        if (size > 0) // kinda scuffed using this possibly unitialized value of 0 thing but idk i'll think about that one for later
        // also i could definitely make this a ternary in the line below, where this.getCurrentValue() is it would branch i think
        {
            try {System.out.println(this.getCurrentValue());}
            catch (Throwable error) {System.out.println("error: " + error);}
        }
        else
        {
            System.out.println("N/A (empty list)");
        }

        System.out.print(name + " current index: ");
        try {System.out.println(this.getCurrentIndex());}
        catch (Throwable error) {System.out.println("error: " + error);}
    }
}
