package io.github.redox7500.project2;

public abstract class Project2AbstractDictionary<K extends Comparable<? super K>, V> implements Project2Dictionary<K, V>
{
    public abstract String toString();
}
