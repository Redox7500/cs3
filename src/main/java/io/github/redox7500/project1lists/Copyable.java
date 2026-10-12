package io.github.redox7500.project1lists;

public interface Copyable<T extends Copyable<T>>
{
    T copy();
}