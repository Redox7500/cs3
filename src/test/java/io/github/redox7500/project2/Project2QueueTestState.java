package io.github.redox7500.project2;

import io.github.redox7500.testutil.TestState;
import io.github.redox7500.testutil.TestFunctionNamed;
import io.github.redox7500.testutil.TestHarness;
import io.github.redox7500.testutil.TestFunction;

import java.util.function.Supplier;

import org.junit.platform.commons.annotation.Testable;

import io.github.redox7500.testutil.OptionalValue;
import io.github.redox7500.testutil.TestEqualityResult;

public class Project2QueueTestState<E> extends TestState<Project2Queue<E>, Project2Queue<E>, Project2ArrayDequeWrapper<E>>
{
    Project2QueueTestState() {}
    
    Project2QueueTestState(Supplier<Project2Queue<E>> supplier)
    {
        this.toTest1 = supplier.get();
        this.toTest2 = new Project2ArrayDequeWrapper<E>();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TestFunctionNamed<Project2Queue<E>>[] getTestFunctions()
    {
        // do something to make this not make a new array every single time maybe?
        return (TestFunctionNamed<Project2Queue<E>>[])new TestFunctionNamed<?>[]
        {
            new TestFunctionNamed<Project2Queue<E>>("clear",      (l, a) -> {l.clear();          return new OptionalValue<Object>();}),
            new TestFunctionNamed<Project2Queue<E>>("enqueue",    (l, a) -> {l.enqueue((E)a[0]); return new OptionalValue<Object>();}),
            new TestFunctionNamed<Project2Queue<E>>("dequeue",    (l, a) -> new OptionalValue<Object>(l.dequeue())),
            new TestFunctionNamed<Project2Queue<E>>("frontValue", (l, a) -> new OptionalValue<Object>(l.frontValue())),
            new TestFunctionNamed<Project2Queue<E>>("length",     (l, a) -> new OptionalValue<Object>(l.length())),
        };
    }

    @Override
    protected Object[][] generateArguments(int function)
    {
        return switch (function)
        {
            case 1 -> new Object[][]{{this.functionHistory.length}};
            default -> new Object[][]{{}};
        };
    }

    @Override
    protected Project2QueueTestState<E> createInstance() {return new Project2QueueTestState<E>();}

    @Override
    protected Project2Queue<E> copyToTest(Project2Queue<E> toCopy) {return toCopy.copy();}

    @Override
    public void printRelevantInfo(String name)
    {
        System.out.print(name + ": ");
        try {System.out.println(this);}
        catch (Throwable error) {System.out.println("error: " + error);}

        System.out.print(name + " size: ");
        int size = 0;
        try {size = this.toTest1.length(); System.out.println(size);}
        catch (Throwable error) {System.out.println("error: " + error);}

        System.out.print(name + " front value: ");
        if (size > 0) // kinda scuffed using this possibly unitialized value of 0 thing but idk i'll think about that one for later
        // also i could definitely make this a ternary in the line below, where this.getCurrentValue() is it would branch i think
        {
            try {System.out.println(this.toTest1.frontValue());}
            catch (Throwable error) {System.out.println("error: " + error);}
        }
        else
        {
            System.out.println("N/A (empty queue)");
        }
    }

    @Override
    public TestEqualityResult testEquality()
    {
        int toTest1Size;
        try {toTest1Size = this.toTest1.length();}
        catch (Throwable _) {return TestEqualityResult.ERROR_THIS;}

        int toTest2Size;
        try {toTest2Size = this.toTest2.length();}
        catch (Throwable _) {return TestEqualityResult.ERROR_OTHER;}

        // frontValue, enqueue
        if (toTest1Size != toTest2Size) return TestEqualityResult.UNEQUAL;
        if (toTest1Size == 0) return TestEqualityResult.EQUAL; // this one is debatable but i'm feeling lazy right now.

        E toTest1FrontValue;
        try {toTest1FrontValue = this.toTest1.frontValue();}
        catch (Throwable _) {return TestEqualityResult.ERROR_THIS;}

        E toTest2FrontValue;
        try {toTest2FrontValue = this.toTest2.frontValue();}
        catch (Throwable _) {return TestEqualityResult.ERROR_OTHER;}

        if (toTest1FrontValue != toTest2FrontValue) return TestEqualityResult.UNEQUAL;

        // try {this.moveCurrentIndexToStart();}
        // catch (Throwable _) {return Project1TestEqualityResult.ERROR_THIS;}
        
        // try {other.moveCurrentIndexToStart();}
        // catch (Throwable _) {return Project1TestEqualityResult.ERROR_OTHER;}

        // for (int i = 0; ; i++)
        // {
        //     E thisCurrentValue;
        //     try {thisCurrentValue = this.getCurrentValue();}
        //     catch (Throwable _) {return Project1TestEqualityResult.ERROR_THIS;}

        //     E otherCurrentValue;
        //     try {otherCurrentValue = other.getCurrentValue();}
        //     catch (Throwable _) {return Project1TestEqualityResult.ERROR_OTHER;}

        //     if (!thisCurrentValue.equals(otherCurrentValue)) return Project1TestEqualityResult.UNEQUAL;

        //     if (i == thisSize - 1) break;

        //     try {this.moveCurrentIndexToNext();}
        //     catch (Throwable _) {return Project1TestEqualityResult.ERROR_THIS;}

        //     try {other.moveCurrentIndexToNext();}
        //     catch (Throwable _) {return Project1TestEqualityResult.ERROR_OTHER;}
        // }

        // try {this.moveCurrentIndexTo(thisCurrentIndex);}
        // catch (Throwable _) {return Project1TestEqualityResult.ERROR_THIS;}

        // try {other.moveCurrentIndexTo(otherCurrentIndex);} // could also be phrased as thisCurrentIndex
        // catch (Throwable _) {return Project1TestEqualityResult.ERROR_OTHER;}

        if (!this.toTest1.toString().equals(this.toTest2.toString())) return TestEqualityResult.UNEQUAL; // just realized that this won't work for, say, a string like "0, 1" because that would most definitely mess it up, but right now i'm only using integers so it should be fine for the moment/time being
        
        return TestEqualityResult.EQUAL;
        // // i'll fix this
        // return EqualityTestResult.EQUAL;
    }
}
