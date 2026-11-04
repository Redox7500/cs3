package io.github.redox7500.testutil;

@FunctionalInterface
public interface TestFunction<T>
{
    OptionalValue<?> apply(T toTest, Object[] args);
}
