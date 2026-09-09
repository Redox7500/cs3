package project1lists;

/** Array-based list implementation */
class AList<E> implements List<E>
{
    /** Maximum amount of elements AList is instantiated to contain by default */
    private static final int defaultSize = 10;

    /** Number of elements this instance has */
    private int elementCount;

    /** Index of the current element */
    private int currentIndex;

    /** Array holding this instance's data */
    private E[] array;

    /** Create a new list object.
     * @param size Maximum amount of elements this instance should have
     */
    @SuppressWarnings("unchecked") // For generic array allocation
    AList(int size)
    {
        this.elementCount = 0;
        this.currentIndex = 0;
        this.array = (E[])new Object[size];
    }

    /** Create a list with the default capacity. */
    AList() {this(AList.defaultSize);}

    public int length() {return this.elementCount;}
    
    public int getCurrentIndex() {return this.currentIndex;}

    public void setCurrentIndex(int index)
    {
        assert index >= 0 && index < this.elementCount : "Pos out of range";
        this.currentIndex = index;
    }

    public E getCurrentElement() {return this.array[this.currentIndex];}

    public void setCurrentElement(E element) {this.array[this.currentIndex] = element;}

    public void clear() {this.elementCount = 0; this.currentIndex = 0;}

    public void insert(E element)
    {
        assert this.elementCount + 1 < this.array.length : "List capacity exceeded";

        for (int i = this.elementCount; i > this.currentIndex; i--) this.array[i] = this.array[i - 1]; // Shift elements up to make room
        this.array[this.currentIndex] = element;
        this.elementCount++;
    }

    public void append(E element)
    {
        assert this.elementCount + 1 < this.array.length : "List capacity exceeded";

        this.array[this.elementCount++] = element;
    }
    
    public void moveToStart() {this.currentIndex = 0;}

    public E remove()
    {
        E element = this.array[this.currentIndex]; // Copy the element
        for (int i = this.currentIndex; i < this.elementCount - 1; i++) this.array[i] = this.array[i + 1]; // Shift them down
        this.elementCount--; // Decrement size
        return element;
    }

    public void moveToEnd() {this.currentIndex = this.elementCount - 1;}

    public void prev() {if (this.currentIndex != 0) this.currentIndex--;}
    
    public void next() {if (this.currentIndex + 1 < this.elementCount) this.currentIndex++;}
}