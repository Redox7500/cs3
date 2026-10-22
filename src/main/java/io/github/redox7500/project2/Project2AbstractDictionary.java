package io.github.redox7500.project2;

/** This is what my dictionaries will extend, which implements {@link io.github.redox7500.project2.Project2Dictionary Project2Dictionary}
 * <br>
 * Right now it only forces toString() implementations.
*/
public abstract class Project2AbstractDictionary<K extends Comparable<? super K>, V> implements Project2Dictionary<K, V>
{
    public abstract String toString();

    @Override
    public void printRelevantInfo(String name)
    {
        System.out.println(name + ": " + this);
    }

    @Override
    public EqualityTestResult equalityTest(Project2Dictionary<K, V> other)
    {
        // i'll fix this
        return EqualityTestResult.EQUAL;
    }
}
