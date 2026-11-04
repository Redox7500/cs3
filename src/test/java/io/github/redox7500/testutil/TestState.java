package io.github.redox7500.testutil;

/** Expresses a state that a CustomList and its corresponding ArrayList can be in, along with arrays to keep track of the function call and argument histories */
public abstract class TestState<S, T extends S, U extends S>
{
    protected abstract TestFunctionNamed<S>[] getTestFunctions();
    // static TestFunction<S>[] testFunctions;
    protected abstract Object[][] generateArguments(int function);

    protected abstract TestState<S, T, U> copy(int function, Object[] arg);

    /** The instance of the CustomList being tested that resulted after the functionHistory/argumentHistory */
    protected S toTest1;

    /** An instance of ArrayList that resulted after the functionHistory/argumentHistory */
    protected S toTest2;

    /** An array containing all of the function calls leading up to this state */
    protected int[] functionHistory;

    /** An array containing all of the arguments used in the function calls leading up to this state */
    protected Object[][] argumentHistory; // maybe combine functionHistory and argumentHistory into an array of records?

    protected TestState()
    {
        this.functionHistory = new int[0];
        this.argumentHistory = new Object[0][];
    }

    // /** Log all of the information of this State (with formatting to work with {@link #project1lists.TestHarness.testCustomLists testCustomLists}) */
    // protected void printInfo()
    // {
    //     toTest1.printRelevantInfo("First object");
    //     System.out.println();
    //     toTest2.printRelevantInfo("Second object");
    //     System.out.println();
    //     System.out.println("Function history (first at the top):");
    //     NamedTestFunction<S>[] functions = this.getTestFunctions();
    //     for (int i = 0; i < this.functionHistory.length; i++)
    //     {
    //         System.out.print('\t' + functions[this.functionHistory[i]].name() + '(');
    //         int argumentLength = this.argumentHistory[i].length;
    //         if (argumentLength > 0)
    //         {
    //             System.out.print(this.argumentHistory[i][0]);
    //             for (int j = 1; j < this.argumentHistory[i].length; j++) System.out.print(", " + this.argumentHistory[i][j]);
    //         }
    //         System.out.println(')');
    //     }
    //     System.out.println();
    // }

    protected abstract TestState<S, T, U> createInstance();

    /** Branch out from this state and add the new leaf states to leaves
     * @param leaves The deque to add the new leaf states to
     * @return A boolean representing whether any of the leaves had CustomLists that misbehaved or not
     */
    // final boolean branch(TestFunction<S>[] testFunctions, BiFunction<State<S, T, U>, Integer, Object[][]> argumentGenerator, int depth)
    protected final boolean branch(int depth)
    {
        if (depth == 0) return true;

        TestFunctionNamed<S>[] functions = this.getTestFunctions();
        for (int function = 0; function < functions.length; function++)
        {
            Object[][] arguments = this.generateArguments(function);
            for (Object[] argument : arguments)
            {
                TestState<S, T, U> newState = this.createInstance();

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
                    System.out.println("First object had an error while second object did not at state below");
                    // newState.printInfo();

                    return false;
                }
                if (!error1 && error2)
                {
                    System.out.println("Second object had an error while first object did not at state below");
                    // newState.printInfo();

                    return false;
                }

                if ((result1 == null)? result2 != null : !result1.equals(result2))
                {
                    System.out.println("Results were not equal at state below");
                    // newState.printInfo();

                    return false;
                }

                switch (newState.equalityTest())
                {
                    case ERROR_THIS:
                        System.out.println("First object had an error when being compared to second object");
                        // newState.printInfo();

                        return false;
                    case ERROR_OTHER:
                        System.out.println("Second object had an error when being compared to first object");
                        // newState.printInfo();

                        return false;
                    case UNEQUAL:
                        System.out.println("First and second objects were not equal at state below");
                        // newState.printInfo();

                        return false;
                    case EQUAL:
                        if (!newState.branch(depth - 1)) return false;
                }
            }
        }
        // make a copy with extra arg/function state thing?

        return true;
    }

    protected abstract EqualityTestResult equalityTest();
    protected abstract void printRelevantInfo(String name);
}
