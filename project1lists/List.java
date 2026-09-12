package project1lists;

// add negative indices like in python maybe?
/** List ADT */
public interface List<T>
{
    /** @return The number of elements in the list */
    public int size();

    /** @return The current index */
    public int getCurrentIndex();

    /** @param index The index to make current */
    public void moveCurrentIndexTo(int index);

    /** Set current index to 0 */
    public default void moveCurrentIndexToStart() {this.moveCurrentIndexTo(0);}

    /** Set current index to one less than the size of the list */
    public default void moveCurrentIndexToEnd() {this.moveCurrentIndexTo(this.size() - 1);}

    /** Move the current index one step left */
    public default void moveCurrentIndexLeft() {this.moveCurrentIndexTo(this.getCurrentIndex() - 1);}

    /** Move the current index one step right */
    public default void moveCurrentIndexRight() {this.moveCurrentIndexTo(this.getCurrentIndex() + 1);}

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

    /** Print the values in the list, comma separated, between square brackets*/
    public void print();

    /** Print the values in the list, comma separated, between square brackets, followed by a newline */
    public default void println() {this.print(); System.out.println();}

    /** Shaffer's name for my equivalent moveCurrentIndexToStart method */
    public default void moveToStart() {this.moveCurrentIndexToStart();}

    /** Shaffer's name for my equivalent moveCurrentIndexToEnd method */
    public default void moveToEnd() {this.moveCurrentIndexToEnd();}

    /** Shaffer's name for my equivalent moveCurrentIndexLeft method */
    public default void prev() {this.moveCurrentIndexLeft();}

    /** Shaffer's name for my equivalent moveCurrentIndexRight method */
    public default void next() {this.moveCurrentIndexRight();}

    /** Shaffer's name for my equivalent getCurrentIndex method */
    public default int currPos() {return this.getCurrentIndex();}

    /** Shaffer's name for my equivalent moveCurrentIndexTo method
     * @param pos The position to make current
    */
    public default void moveToPos(int pos) {this.moveCurrentIndexTo(pos);}

    /** Shaffer's name for my equivalent getCurrentValue method */
    public default T getValue() {return this.getCurrentValue();}
}