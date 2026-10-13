package io.github.redox7500.project1;

public interface Testable<T extends Testable<T>> extends Copyable<T>
{
    static <T extends Testable<T>> FullTestResult fullTest(Testable<T> toTest1, Testable<T> toTest2, TestFunction<T>[] testFunctions, Object[] args)
    {
        for (TestFunction<T> testFunction : testFunctions)
        {
            Testable<T> toTest1Copy = toTest1.copy();
            Testable<T> toTest2Copy = toTest2.copy();

            Object result1 = null;
            Object result2 = null;
            boolean error1 = false;
            boolean error2 = false;

            try {result1 = testFunction.apply(toTest1Copy, args);}
            catch (Throwable _) {error1 = true;}
            try {result2 = testFunction.apply(toTest2Copy, args);}
            catch (Throwable _) {error2 = true;}

            if (error1 && error2) continue;
            if (error1 && !error2) return FullTestResult.ERROR_1;
            if (!error1 && error2) return FullTestResult.ERROR_2;
            switch (toTest1Copy.equalityTest(toTest2Copy))
            {
                case ERROR_1: return FullTestResult.INEVALUABLE_1;
                case ERROR_2: return FullTestResult.INEVALUABLE_2;
                case UNEQUAL: return FullTestResult.UNEQUAL;
                case EQUAL:
                    Testable.fullTest(toTest1Copy, toTest2Copy, testFunctions, )
            }
        }
    }

    EqualityTestResult equalityTest(Testable<T> toTest);
    void printRelevantInfo(String name);
    
    enum EqualityTestResult
    {
        ERROR_1,
        ERROR_2,
        UNEQUAL,
        EQUAL
    }

    enum FullTestResult
    {
        ERROR_BOTH,
        ERROR_1,
        ERROR_2,
        INEVALUABLE_1,
        INEVALUABLE_2,
        UNEQUAL,
        SUCCESS
    }

    @FunctionalInterface
    interface TestFunction<T extends Testable<T>>
    {
        Object apply(Testable<T> toTest, Object[] args);
    }
}
