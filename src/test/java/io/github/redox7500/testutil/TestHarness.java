package io.github.redox7500.testutil;
import java.util.function.Supplier;

import io.github.redox7500.project1.Project1Copyable;

/** Test harness to test CustomList implementations (specifically CustomList<Integer, ?> implementations) */
public class TestHarness
{
    public static <S extends Testable<S>, T extends S, U extends S> boolean test(Supplier<TestHarness.State<S, T, U>> stateSupplier, int depth)
    {
        long startTime = System.nanoTime();

        // boolean toReturn = new ThisState<E, T>(supplier1).branch(depth);
        boolean toReturn = stateSupplier.get().branch(depth);
        System.out.print((toReturn)? "Success!" : "Failed!");
        System.out.printf(" (%.5f seconds elapsed)\n", (System.nanoTime() - startTime) / (float)1_000_000__000);

        return toReturn;
    }

    // public interface Testable<T extends Testable<T>> extends Project1Copyable<T>
    // {
    //     // EqualityTestResult equalityTest(T other);
    //     // void printRelevantInfo(String name);
        
    //     enum EqualityTestResult
    //     {
    //         ERROR_THIS,
    //         ERROR_OTHER,
    //         UNEQUAL,
    //         EQUAL
    //     }
    // }
}
