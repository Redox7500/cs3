package io.github.redox7500.project2;

public abstract class MyAbstractDictionary<K extends Comparable<? super K>, V> implements MyDictionary<K, V>
{
    public abstract String toString();
}
