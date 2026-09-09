package project1lists;

/** List ADT */
public interface List<E>
{
    /** @return The number of elements in the list. */
    public int length();

    /** @return The index of the current element. */
    public int getCurrentIndex();

    /** Set current index.
        @param pos The index to make current. */
    public void setCurrentIndex(int index);

    /** @return The current element. */
    public E getCurrentElement();

    /** @param The element to set the value at currentIndex to. */
    public void setCurrentElement(E element);

    /** Remove all contents from the list, so it is once again
        empty. Client is responsible for reclaiming storage
        used by the list elements. */
    public void clear();

    /** Insert an element at the current location. The client
        is responsible for ensuring that the list’s capacity
        is not exceeded.
        @param item The element to be inserted. */
    public void insert(E element);

    /** Append an element at the end of the list. The client
        is responsible for ensuring that the list’s capacity
        is not exceeded.
        @param item The element to be appended. */
    public void append(E element);

    /** Remove and return the current element.
        @return The element that was removed. */
    public E remove();

    /** Set the current index to the start of the list */
    public void moveToStart();

    /** Set the current index to the end of the list */
    public void moveToEnd();

    /** Move the current index one step left. No change
        if already at beginning. */
    public void prev();

    /** Move the current index one step right. No change
        if already at end. */
    public void next();
}