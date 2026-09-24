package project1lists;

/**  */
public abstract class DefaultCustomListImplementations<E, T extends DefaultCustomListImplementations<E, T>> implements CustomList<E, T>
{
    public void clear()
    {
        this.moveCurrentIndexToStart();
        for (int i = 0; i < this.size(); i++) this.remove();
    }

    /** Insert an element at the current location with the specified value. The client is responsible for ensuring that the list’s capacity is not exceeded.
     * @param value The value to be inserted
     */
    public abstract void insert(E value);

    /** Append an element at the end of the list with the specified value. The client is responsible for ensuring that the list’s capacity is not exceeded.
     * @param value The value of the element to be appended
     */
    public abstract void append(E value);

    /** Remove the element at the current index and return its value
     * @return The value of the element that was removed
     */
    public abstract E remove();

    /** @return The value of the list at the current index */
    public abstract E getCurrentValue();

    /** @param value The value to set the list at the current index to */
    public abstract void setCurrentValue(E value);

    /** @return The current index */
    public abstract int getCurrentIndex();

    /** @param index The index to make current */
    public abstract void moveCurrentIndexTo(int index);

    /** Set current index to 0 */
    public void moveCurrentIndexToStart() {this.moveCurrentIndexTo(0);}

    /** Set current index to one less than the size of the list */
    public void moveCurrentIndexToEnd() {this.moveCurrentIndexTo(Math.max(this.size() - 1, 0));}

    /** Move the current index one step left */
    public void moveCurrentIndexLeft() {this.moveCurrentIndexTo(this.getCurrentIndex() - 1);}

    /** Move the current index one step right */
    public void moveCurrentIndexRight() {this.moveCurrentIndexTo(this.getCurrentIndex() + 1);}

    /** @return The number of elements in the list */
    public abstract int size();

    /** @return A copy of this list as far down as its elements (but elements in the copied array point to the same place as corresponding elements in this array) */
    @SuppressWarnings("unchecked")
    public T copy()
    {
        T toReturn;
        try {toReturn = (T)this.getClass().getDeclaredConstructor().newInstance();}
        catch (Throwable _) {System.err.println("Class to copy has no visible parameterless constructor"); return null;}

        int size = this.size();
        if (size == 0) return toReturn;

        int currentIndex = this.getCurrentIndex();
        this.moveCurrentIndexToStart();
        for (int i = 0; ; i++)
        {
            toReturn.append(this.getCurrentValue());
            if (i == size - 1) break;
            this.moveCurrentIndexRight();
        }
        toReturn.append(this.getCurrentValue());

        this.moveCurrentIndexTo(currentIndex);
        toReturn.moveCurrentIndexTo(currentIndex);

        return toReturn;
    }

    /** String of the values in the list, comma separated, between square brackets */
    public String toString()
    {
        int currentIndex = 0;
        int size = this.size();

        StringBuilder toReturn = new StringBuilder();
        toReturn.append('[');
        if (size != 0)
        {
            this.moveCurrentIndexToStart();

            toReturn.append(this.getCurrentValue());
            for (int i = 0; i < size - 1; i++)
            {
                toReturn.append(", ");
                toReturn.append(this.getCurrentValue());

                this.moveCurrentIndexRight();
            }
        }
        toReturn.append(']');

        this.moveCurrentIndexTo(currentIndex);

        return toReturn.toString();
    }
}