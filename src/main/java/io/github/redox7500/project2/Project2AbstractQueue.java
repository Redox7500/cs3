package io.github.redox7500.project2;

/** This is what my dictionaries will extend, which implements {@link io.github.redox7500.project2.Project2Queue Project2Queue}
 * <br>
 * Right now it only forces toString() implementations.
*/
public abstract class Project2AbstractQueue<E> implements Project2Queue<E>
{
    public abstract String toString();
}
