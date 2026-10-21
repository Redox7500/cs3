package io.github.redox7500.project2;

/** This is what my dictionaries will extend, which implements {@link io.github.redox7500.project2.Project2Dictionary Project2Dictionary}
 * <br>
 * Right now it only forces toString() implementations.
*/
public abstract class Project2AbstractDictionary<K extends Comparable<? super K>, V> implements Project2Dictionary<K, V>
{
    public abstract String toString();
}
