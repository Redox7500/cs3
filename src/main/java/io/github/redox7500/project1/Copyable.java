package io.github.redox7500.project1;

public interface Copyable<T extends Copyable<T>>
{
    T copy();
}
