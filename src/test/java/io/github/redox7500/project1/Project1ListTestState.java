package io.github.redox7500.project1;

import java.util.function.Supplier;

import io.github.redox7500.testutil.OptionalValue;
import io.github.redox7500.testutil.TestState;
import io.github.redox7500.testutil.TestFunction;
import io.github.redox7500.testutil.TestFunctionNamed;
import io.github.redox7500.testutil.TestEqualityResult;

public class Project1ListTestState<E, T extends Project1List<E>> extends TestState<Project1List<E>, T, Project1ArrayListWrapper<E>>
{
    Project1ListTestState() {}
        
    Project1ListTestState(Supplier<T> supplier)
    {
        this.toTest1 = supplier.get();
        this.toTest2 = new Project1ArrayListWrapper<E>();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TestFunctionNamed<Project1List<E>>[] getTestFunctions()
    {
        // do something to make this not make a new array every single time maybe?
        return (TestFunctionNamed<Project1List<E>>[])new TestFunctionNamed<?>[]
        {
            new TestFunctionNamed<Project1List<E>>("size", (l, a) -> new OptionalValue<Integer>(l.size())),
            new TestFunctionNamed<Project1List<E>>("getCurrentIndex", (l, a) -> new OptionalValue<Integer>(l.getCurrentIndex())),
            new TestFunctionNamed<Project1List<E>>("moveCurrentIndexTo", (l, a) -> {l.moveCurrentIndexTo((int)a[0]); return new OptionalValue<Object>();}),
            new TestFunctionNamed<Project1List<E>>("moveCurrentIndexToStart", (TestFunction<Project1List<E>>)(l, a) -> {l.moveCurrentIndexToStart(); return new OptionalValue<Object>();}),
            new TestFunctionNamed<Project1List<E>>("moveCurrentIndexToEnd", (l, a) -> {l.moveCurrentIndexToEnd(); return new OptionalValue<Object>();}),
            new TestFunctionNamed<Project1List<E>>("moveCurrentIndexToPrevious", (l, a) -> {l.moveCurrentIndexToPrevious(); return new OptionalValue<Object>();}),
            new TestFunctionNamed<Project1List<E>>("moveCurrentIndexToNext", (TestFunction<Project1List<E>>)(l, a) -> {l.moveCurrentIndexToNext(); return new OptionalValue<Object>();}),
            new TestFunctionNamed<Project1List<E>>("getCurrentValue", (TestFunction<Project1List<E>>)(l, a) -> new OptionalValue<E>(l.getCurrentValue())),
            new TestFunctionNamed<Project1List<E>>("setCurrentValue", (l, a) -> {l.setCurrentValue((E)a[0]); return new OptionalValue<Object>();}),
            new TestFunctionNamed<Project1List<E>>("clear", (l, a) -> {l.clear(); return new OptionalValue<Object>();}),
            new TestFunctionNamed<Project1List<E>>("insert", (l, a) -> {l.insert((E)a[0]); return new OptionalValue<Object>();}),
            new TestFunctionNamed<Project1List<E>>("append", (l, a) -> {l.append((E)a[0]); return new OptionalValue<Object>();}),
            new TestFunctionNamed<Project1List<E>>("remove", (l, a) -> new OptionalValue<Object>(l.remove()))
        };
    }

    @Override
    protected Object[][] generateArguments(int function)
    {
        return switch (function)
        {
            case 2 -> 
            {
                int size = this.toTest2.size();
                Object[][] toReturn = new Object[size + 2][1];
                for (int i = 0; i < size + 2; i++) toReturn[i] = new Object[]{i - 1};

                yield toReturn;
            }
            case 8, 10, 11 -> new Object[][]{{this.functionHistory.length}};
            default -> new Object[][]{{}};
        };
    }

    @Override
    protected Project1ListTestState<E, T> createInstance() {return new Project1ListTestState<E, T>();}

    // @SuppressWarnings("unchecked")
    @Override
    protected Project1List<E> copyToTest(Project1List<E> toCopy)
    {
        return toCopy.copy();
        // Project1List<E> toReturn;

        // try {toReturn = toCopy.getClass().getDeclaredConstructor().newInstance();}
        // catch (Throwable _) {System.err.println("Class to copy has no visible parameterless constructor"); return null;}

        // int size = toCopy.size();
        // if (size == 0) return toReturn;

        // int currentIndex = toCopy.getCurrentIndex();

        // try
        // {
        //     toCopy.moveCurrentIndexToStart();

        //     for (int i = 0; ; i++)
        //     {
        //         toReturn.append(toCopy.getCurrentValue());

        //         if (i == size - 1) break;

        //         toCopy.moveCurrentIndexToNext();
        //     }
        //     toReturn.append(toCopy.getCurrentValue());

        //     toReturn.moveCurrentIndexTo(currentIndex);
        // }
        // finally
        // {
        //     toCopy.moveCurrentIndexTo(currentIndex);
        // }

        // return toReturn;
    }

    @Override
    protected TestEqualityResult testEquality()
    {
        int toTest1Size;
        try {toTest1Size = this.toTest1.size();}
        catch (Throwable _) {return TestEqualityResult.ERROR_THIS;}

        int toTest2Size;
        try {toTest2Size = this.toTest2.size();}
        catch (Throwable _) {return TestEqualityResult.ERROR_OTHER;}

        if (toTest1Size != toTest2Size) return TestEqualityResult.UNEQUAL;
        if (toTest1Size == 0) return TestEqualityResult.EQUAL; // this one is debatable but i'm feeling lazy right now.

        int toTest1CurrentIndex;
        try {toTest1CurrentIndex = this.toTest1.getCurrentIndex();}
        catch (Throwable _) {return TestEqualityResult.ERROR_THIS;}

        int toTest2CurrentIndex;
        try {toTest2CurrentIndex = this.toTest2.getCurrentIndex();}
        catch (Throwable _) {return TestEqualityResult.ERROR_OTHER;}

        if (toTest1CurrentIndex != toTest2CurrentIndex) return TestEqualityResult.UNEQUAL;

        try {this.toTest1.moveCurrentIndexToStart();}
        catch (Throwable _) {return TestEqualityResult.ERROR_THIS;}
        
        try {this.toTest2.moveCurrentIndexToStart();}
        catch (Throwable _) {return TestEqualityResult.ERROR_OTHER;}

        for (int i = 0; ; i++)
        {
            E toTest1CurrentValue;
            try {toTest1CurrentValue = this.toTest1.getCurrentValue();}
            catch (Throwable _) {return TestEqualityResult.ERROR_THIS;}

            E toTest2CurrentValue;
            try {toTest2CurrentValue = this.toTest2.getCurrentValue();}
            catch (Throwable _) {return TestEqualityResult.ERROR_OTHER;}

            if (!toTest1CurrentValue.equals(toTest2CurrentValue)) return TestEqualityResult.UNEQUAL;

            if (i == toTest1Size - 1) break;

            try {this.toTest1.moveCurrentIndexToNext();}
            catch (Throwable _) {return TestEqualityResult.ERROR_THIS;}

            try {this.toTest2.moveCurrentIndexToNext();}
            catch (Throwable _) {return TestEqualityResult.ERROR_OTHER;}
        }

        try {this.toTest1.moveCurrentIndexTo(toTest1CurrentIndex);}
        catch (Throwable _) {return TestEqualityResult.ERROR_THIS;}

        try {this.toTest2.moveCurrentIndexTo(toTest2CurrentIndex);} // could also be phrased as thisCurrentIndex
        catch (Throwable _) {return TestEqualityResult.ERROR_OTHER;}

        return TestEqualityResult.EQUAL;
    }

    protected void printRelevantInfo(String name)
    {
        System.out.print(name + ": ");
        try {System.out.println(this.toTest1);}
        catch (Throwable error) {System.out.println("error: " + error);}

        System.out.print(name + " size: ");
        int size = 0;
        try {size = this.toTest1.size(); System.out.println(size);}
        catch (Throwable error) {System.out.println("error: " + error);}

        System.out.print(name + " current value: ");
        if (size > 0) // kinda scuffed using this possibly unitialized value of 0 thing but idk i'll think about that one for later
        // also i could definitely make this a ternary in the line below, where this.getCurrentValue() is it would branch i think
        {
            try {System.out.println(this.toTest1.getCurrentValue());}
            catch (Throwable error) {System.out.println("error: " + error);}
        }
        else
        {
            System.out.println("N/A (empty list)");
        }

        System.out.print(name + " current index: ");
        try {System.out.println(this.toTest1.getCurrentIndex());}
        catch (Throwable error) {System.out.println("error: " + error);}
    }
}
