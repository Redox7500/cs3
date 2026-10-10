package io.github.redox7500.project1lists;

public interface Testable<T> extends Copyable<T>
{
    static <T> Result fullTest(Testable<T> toTest1, Testable<T> toTest2, TestFunction<T> testFunction, Object[] args)
    {
        Object result1 = null;
        Object result2 = null;
        boolean error1 = false;
        boolean error2 = false;

        try {result1 = testFunction.apply(toTest1, args);}
        catch (Throwable _) {error1 = true;}
        try {result2 = testFunction.apply(toTest2, args);}
        catch (Throwable _) {error2 = true;}

        if (error1 && error2) return Result.ERROR_BOTH;
        if (error1 && !error2) return Result.ERROR_1;
        if (!error1 && error2) return Result.ERROR_2;
        return (((result1 == null)? result2 == null : result1.equals(result2)) && toTest1.equals(toTest2))? Result.SUCCESS : Result.UNEQUAL;
        // probably change equals to something like equalityCheck and make it output a Result thing, then no more hashCode too
        // return ((result1 == null)? result2 == null : result1.equals(result2))? Result.SUCCESS : Result.UNEQUAL;
    }

    void testEquality(Testable<T> toTest);
    void printRelevantInfo(String name);
    
    enum Equality
    {
        
    }

    enum Result
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
    interface TestFunction<T>
    {
        Object apply(Testable<T> toTest, Object[] args);
    }
}