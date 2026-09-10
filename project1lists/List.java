package project1lists;

/** List ADT */
public interface List<T>
{
    /** @return The number of elements in the list */
    public int length();

    /** @return The current index */
    public int getCurrentIndex();

    /** @param index The index to make current */
    public void setCurrentIndex(int index);

    /** Set current index to 0 */
    public void moveIndexToStart();

    /** Set current index to one less than the size of the list */
    public void moveIndexToEnd();

    /** Move the current index one step left */
    public void moveIndexLeft();

    /** Move the current index one step right */
    public void moveIndexRight();

    /** @return The value of the list at the current index */
    public T getCurrentValue();

    /** @param value The value to set the list at the current index to */
    public void setCurrentValue(T value);

    /** Remove all contents from the list, so it is once again empty. The client is responsible for reclaiming storage used by the list elements. */
    public void clear();

    /** Insert an element at the current location with the specified value. The client is responsible for ensuring that the list’s capacity is not exceeded.
     * @param value The value to be inserted
     */
    public void insert(T value);

    /** Append an element at the end of the list with the specified value. The client is responsible for ensuring that the list’s capacity is not exceeded.
     * @param value The value of the element to be appended
     */
    public void append(T value);

    /** Remove the element at the current index and return its value
     * @return The value of the element that was removed
     */
    public T remove();

    /** Print the values in the list */
    public void print();

    /** @param value The value to search the list for
     * @return Boolean representing whether or not the list has the specified value
     */
    public boolean contains(T value);

    /** Check whether or not two lists have the same values and the same element count. Implementations may affect the current index of this list and/or otherList.
     * @param otherList The list to compare to
     */
    public boolean equals(List<T> otherList);
}