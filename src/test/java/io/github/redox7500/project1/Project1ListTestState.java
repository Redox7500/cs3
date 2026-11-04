package io.github.redox7500.project1;

import java.util.ArrayList;
import java.util.function.Supplier;

import io.github.redox7500.testutil.OptionalValue;
import io.github.redox7500.testutil.TestHarness;
import io.github.redox7500.testutil.TestState;
import io.github.redox7500.testutil.TestFunction;
import io.github.redox7500.testutil.TestFunctionNamed;

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
}
