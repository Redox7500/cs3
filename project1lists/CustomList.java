package project1lists;

// add negative indices like in python maybe?
/** List ADT */
public interface CustomList<E, T extends CustomList<E, T>> // all this for the copy function is wild
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
    void moveCurrentIndexLeft();

    /** Move the current index one step right */
    void moveCurrentIndexRight();

    /** @return The number of elements in the list */
    int size();

    /** @return A copy of this list as far down as its elements (but elements in the copied array point to the same place as corresponding elements in this array) */
    T copy();

    /** String of the values in the list, comma separated, between square brackets */
    String toString();

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.moveCurrentIndexToStart moveCurrentIndexToStart()} method */
    default void moveToStart() {this.moveCurrentIndexToStart();}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.moveCurrentIndexToEnd moveCurrentIndexToEnd()} method */
    default void moveToEnd() {this.moveCurrentIndexToEnd();}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.moveCurrentIndexLeft moveCurrentIndexLeft()} method */
    default void prev() {this.moveCurrentIndexLeft();}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.moveCurrentIndexRight moveCurrentIndexRight()} method */
    default void next() {this.moveCurrentIndexRight();}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.size size()} method */
    default int length() {return this.size();}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.getCurrentIndex getCurrentIndex()} method */
    default int currPos() {return this.getCurrentIndex();}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.moveCurrentIndexTo moveCurrentIndexTo()} method
     * @param pos The position to make current
    */
    default void moveToPos(int pos) {this.moveCurrentIndexTo(pos);}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.getCurrentValue getCurrentValue()} method */
    default E getValue() {return this.getCurrentValue();}
}