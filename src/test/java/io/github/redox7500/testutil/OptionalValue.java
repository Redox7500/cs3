package io.github.redox7500.testutil;

public record OptionalValue<T>(boolean empty, T value)
{
    public OptionalValue() {this(true, null);}

    public OptionalValue(T value) {this(false, value);}
}
