package io.github.redox7500.project1;

// add negative indices like in python maybe?
/** List ADT */
public interface Project1List<E> extends Project1TestHarness.Testable<Project1List<E>> // all this for the copy function is wild
{
    /** Remove all contents from the list, so it is once again empty. The client is responsible for reclaiming storage used by the list elements. */
    void clear();

    /** Insert an element at the current location with the specified value. The client is responsible for ensuring that the list’s capacity is not exceeded.
     * @param value The value to be inserted
     */
    void insert(E value);

    /** Append an element at the end of the list with the specified value. The client is responsible for ensuring that the list’s capacity is not exceeded.
     * @param value The value of the element to be appended
     */
    void append(E value);

    /** Remove the element at the current index and return its value
     * @return The value of the element that was removed
     */
    E remove();

    /** @return The value of the list at the current index */
    E getCurrentValue();

    /** @param value The value to set the list at the current index to */
    void setCurrentValue(E value);

    /** @return The current index */
    int getCurrentIndex();

    /** @param index The index to make current */
    void moveCurrentIndexTo(int index);

    /** Set current index to 0 */
    void moveCurrentIndexToStart();

    /** Set current index to one less than the size of the list */
    void moveCurrentIndexToEnd();

    /** Move the current index one step left */
    void moveCurrentIndexToPrevious();

    /** Move the current index one step right */
    void moveCurrentIndexToNext();

    /** @return The number of elements in the list */
    int size();
}
