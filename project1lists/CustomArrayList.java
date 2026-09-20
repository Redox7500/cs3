package project1lists;

/** Array-based list implementation */
class CustomArrayList<E> implements CustomList<E, CustomArrayList<E>>
{
    /** Maximum amount of elements AList is instantiated to contain by default */
    private static final int DEFAULT_SIZE = 16;

    /** Number of elements this list has */
    private int elementCount;

    /** Index of the current element */
    private int currentIndex;

    /** Array holding this instance's data */
    private E[] array;

    /** Create a new list object.
     * @param size Maximum amount of elements this instance should have
     */
    @SuppressWarnings("unchecked") // For generic array allocation
    CustomArrayList(int size)
    {
        this.elementCount = 0;
        this.currentIndex = 0;
        this.array = (E[])new Object[size];
    }

    /** Create a list with the default capacity. */
    CustomArrayList() {this(CustomArrayList.DEFAULT_SIZE);}

    @Override
    public void clear() {this.elementCount = 0; this.currentIndex = 0;}

    @Override
    public void insert(E value)
    {
        assert this.elementCount < this.array.length : "List capacity exceeded";

        for (int i = this.elementCount; i > this.currentIndex; i--) this.array[i] = this.array[i - 1]; // Shift elements up to make room
        this.array[this.currentIndex] = value;
        this.elementCount++;
    }

    @Override
    public void append(E value)
    {
        assert this.elementCount < this.array.length : "List capacity exceeded";

        this.array[this.elementCount++] = value;
    }

    @Override
    public E remove()
    {
        assert this.elementCount > 0 : "Index out of range";

        E element = this.array[this.currentIndex]; // Copy the element

        for (int i = this.currentIndex; i < this.elementCount - 1; i++) this.array[i] = this.array[i + 1]; // Shift them down
        this.elementCount--; // Decrement size
        if (this.currentIndex == this.elementCount && this.currentIndex > 0) this.currentIndex--;

        return element;
    }

    @Override
    public E getCurrentValue() {assert this.elementCount > 0 : "Index out of range"; return this.array[this.currentIndex];}

    @Override
    public void setCurrentValue(E value) {assert this.array.length > 0: "List capacity exceeded"; if (this.elementCount != 0) this.array[currentIndex] = value; else this.append(value);}

    @Override
    public int getCurrentIndex() {return this.currentIndex;}

    @Override
    public void moveCurrentIndexTo(int index) {assert (this.elementCount > 0)? index >= 0 && index < this.elementCount : index == 0 : "Index out of range"; this.currentIndex = index;}

    @Override
    public int size() {return this.elementCount;}

    @Override
    public CustomArrayList<E> copy()
    {
        CustomArrayList<E> toReturn = new CustomArrayList<>(this.array.length);
        System.arraycopy(this.array, 0, toReturn.array, 0, this.elementCount);
        toReturn.elementCount = this.elementCount;
        toReturn.currentIndex = this.currentIndex;
        
        return toReturn;
    }
    
    @Override
    public String toString()
    {
        StringBuilder toReturn = new StringBuilder(this.elementCount * 3 + ((this.elementCount > 0)? 0 : 2));
        toReturn.append('[');
        if (this.elementCount > 0)
        {
            toReturn.append(this.array[0]);
            for (int i = 1; i < this.elementCount; i++)
            {
                toReturn.append(", ");
                toReturn.append(this.array[i]);
            }
        }
        toReturn.append(']');

        return toReturn.toString();
    }
}