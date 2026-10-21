package io.github.redox7500.project1;

import java.util.ArrayList;
import java.util.function.Supplier;

/** Test harness to test CustomList implementations (specifically CustomList<Integer, ?> implementations) */
public class Project1TestHarness
{
    /** Expresses a state that a CustomList and its corresponding ArrayList can be in, along with arrays to keep track of the function call and argument histories */
    public static abstract class State<S extends Testable<S>, T extends S, U extends S>
    {
        abstract NamedTestFunction<S>[] getTestFunctions();
        // static TestFunction<S>[] testFunctions;
        abstract Object[][] generateArguments(int function);

        /** The instance of the CustomList being tested that resulted after the functionHistory/argumentHistory */
        S toTest1;

        /** An instance of ArrayList that resulted after the functionHistory/argumentHistory */
        S toTest2;

        /** An array containing all of the function calls leading up to this state */
        int[] functionHistory;

        /** An array containing all of the arguments used in the function calls leading up to this state */
        Object[][] argumentHistory; // maybe combine functionHistory and argumentHistory into an array of records?

        State()
        {
            this.functionHistory = new int[0];
            this.argumentHistory = new Object[0][];
        }

        /** Log all of the information of this State (with formatting to work with {@link #project1lists.TestHarness.testCustomLists testCustomLists}) */
        void printInfo()
        {
            toTest1.printRelevantInfo("First object");
            System.out.println();
            toTest2.printRelevantInfo("Second object");
            System.out.println();
            System.out.println("Function history (first at the top):");
            NamedTestFunction<S>[] functions = this.getTestFunctions();
            for (int i = 0; i < this.functionHistory.length; i++)
            {
                System.out.print('\t' + functions[this.functionHistory[i]].name() + '(');
                int argumentLength = this.argumentHistory[i].length;
                if (argumentLength > 0)
                {
                    System.out.print(this.argumentHistory[i][0]);
                    for (int j = 1; j < this.argumentHistory[i].length; j++) System.out.print(this.argumentHistory[i][j]);
                }
                System.out.println(')');
            }
            System.out.println();
        }

        abstract State<S, T, U> createInstance();

        /** Branch out from this state and add the new leaf states to leaves
         * @param leaves The deque to add the new leaf states to
         * @return A boolean representing whether any of the leaves had CustomLists that misbehaved or not
         */
        // final boolean branch(TestFunction<S>[] testFunctions, BiFunction<State<S, T, U>, Integer, Object[][]> argumentGenerator, int depth)
        final boolean branch(int depth)
        {
            if (depth == 0) return true;

            Project1TestHarness.NamedTestFunction<S>[] functions = this.getTestFunctions();
            for (int function = 0; function < functions.length; function++)
            {
                Object[][] arguments = this.generateArguments(function);
                for (Object[] argument : arguments)
                {
                    State<S, T, U> newState = this.createInstance();

                    newState.toTest1 = this.toTest1.copy();
                    newState.toTest2 = this.toTest2.copy();

                    int historyLength = this.functionHistory.length;

                    newState.functionHistory = new int[historyLength + 1];
                    System.arraycopy(this.functionHistory, 0, newState.functionHistory, 0, historyLength);
                    newState.functionHistory[historyLength] = function;

                    newState.argumentHistory = new Object[historyLength + 1][];
                    System.arraycopy(this.argumentHistory, 0, newState.argumentHistory, 0, historyLength);
                    newState.argumentHistory[historyLength] = argument;

                    Object result1 = null;
                    Object result2 = null;
                    boolean error1 = false;
                    boolean error2 = false;

                    try {result1 = functions[function].function().apply(newState.toTest1, argument);}
                    catch (Throwable _) {error1 = true;}
                    try {result2 = functions[function].function().apply(newState.toTest2, argument);}
                    catch (Throwable _) {error2 = true;}

                    if (error1 && error2) continue;
                    if (error1 && !error2)
                    {
                        System.out.println("Custom list had an error while ArrayListWrapper did not at state below");// \0337");
                        newState.printInfo();

                        return false;
                    }
                    if (!error1 && error2)
                    {
                        System.out.println("ArrayListWrapper had an error while custom list did not at state below");// \0337");
                        newState.printInfo();

                        return false;
                    }

                    if ((result1 == null)? result2 != null : !result1.equals(result2))
                    {
                        System.out.println("Results were not equal at state below");// \0337");
                        newState.printInfo();

                        return false;
                    }

                    switch (newState.toTest1.equalityTest(newState.toTest2))
                    {
                        case ERROR_THIS:
                            System.out.println("Custom list had an error when being compared to ArrayListWrapper");
                            newState.printInfo();

                            return false;
                        case ERROR_OTHER:
                            System.out.println("Custom list had an error when being compared to ArrayListWrapper");
                            newState.printInfo();

                            return false;
                        case UNEQUAL:
                            System.out.println("Lists were not equal at state below");
                            newState.printInfo();

                            return false;
                        case EQUAL:
                            if (!newState.branch(depth - 1)) return false;
                    }
                }
            }
            // make a copy with extra arg/function state thing?

            return true;
        }
    }

    public static <S extends Testable<S>, T extends S, U extends S> boolean test(Supplier<Project1TestHarness.State<S, T, U>> stateSupplier, int depth)
    {
        long startTime = System.nanoTime();

        // boolean toReturn = new ThisState<E, T>(supplier1).branch(depth);
        boolean toReturn = stateSupplier.get().branch(depth);
        System.out.print((toReturn)? "Success!" : "Failed!");
        System.out.printf(" (%.5f seconds elapsed)\n", (System.nanoTime() - startTime) / (float)1_000_000__000);

        return toReturn;
    }

    public interface Testable<T extends Testable<T>> extends Project1Copyable<T>
    {
        EqualityTestResult equalityTest(T other);
        void printRelevantInfo(String name);
        
        enum EqualityTestResult
        {
            ERROR_THIS,
            ERROR_OTHER,
            UNEQUAL,
            EQUAL
        }
    }

    @FunctionalInterface
    public interface TestFunction<T extends Testable<T>>
    {
        OptionalValue<?> apply(T toTest, Object[] args);
    }

    public record NamedTestFunction<T extends Testable<T>>(String name, TestFunction<T> function) {}

    public record OptionalValue<T>(boolean empty, T value)
    {
        OptionalValue() {this(true, null);}

        OptionalValue(T value) {this(false, value);}
    }
}
