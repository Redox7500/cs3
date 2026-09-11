package project1lists;

/** Array-based list implementation */
class ArrayList<T> implements List<T>
{
    /** Maximum amount of elements AList is instantiated to contain by default */
    private static final int defaultSize = 10;

    /** Number of elements this list has */
    private int elementCount;

    /** Index of the current element */
    private int currentIndex;

    /** Array holding this instance's data */
    private T[] array;

    /** Create a new list object.
     * @param size Maximum amount of elements this instance should have
     */
    @SuppressWarnings("unchecked") // For generic array allocation
    ArrayList(int size)
    {
        this.elementCount = 0;
        this.currentIndex = 0;
        this.array = (T[])new Object[size];
    }

    /** Create a list with the default capacity. */
    ArrayList() {this(ArrayList.defaultSize);}

    @Override
    public int size() {return this.elementCount;}
    
    @Override
    public int getCurrentIndex() {return this.currentIndex;}

    @Override
    public void setCurrentIndex(int index) {assert index >= 0 && index < this.elementCount : "Index out of range"; this.currentIndex = index;}

    @Override
    public void moveCurrentIndexToStart() {this.currentIndex = 0;}

    @Override
    public void moveCurrentIndexToEnd() {this.currentIndex = elementCount - 1;}

    @Override
    public void moveCurrentIndexLeft() {assert this.currentIndex > 0 : "Index out of range"; this.currentIndex--;}
    
    @Override
    public void moveCurrentIndexRight() {assert this.currentIndex + 1 < this.elementCount : "Index out of range"; this.currentIndex++;}

    @Override
    public T getCurrentValue() {assert this.elementCount > 0 : "Index out of range"; return this.array[this.currentIndex];}

    @Override
    public void setCurrentValue(T value) {assert this.elementCount > 0: "Index out of range"; this.array[this.currentIndex] = value;}

    @Override
    public void clear() {this.elementCount = 0; this.currentIndex = 0;}

    @Override
    public void insert(T value)
    {
        assert this.elementCount + 1 < this.array.length : "List capacity exceeded";

        for (int i = this.elementCount; i > this.currentIndex; i--) this.array[i] = this.array[i - 1]; // Shift elements up to make room
        this.array[this.currentIndex] = value;
        this.elementCount++;
    }

    @Override
    public void append(T value)
    {
        assert this.elementCount + 1 < this.array.length : "List capacity exceeded";

        this.array[this.elementCount++] = value;
    }

    @Override
    public T remove()
    {
        T element = this.array[this.currentIndex]; // Copy the element

        for (int i = this.currentIndex; i < this.elementCount - 1; i++) this.array[i] = this.array[i + 1]; // Shift them down
        this.elementCount--; // Decrement size
        if (this.currentIndex == this.elementCount && this.currentIndex > 0) this.currentIndex--;

        return element;
    }

    @Override
    public void print()
    {
        System.out.print("[");
        if (this.elementCount > 0)
        {
            System.out.print(this.array[0]);
            for (int i = 1; i < this.elementCount; i++)
            {
                System.out.print(", ");
                System.out.print(this.array[i]);
            }
        }
        System.out.print("]");
    }

    @Override
    public void println()
    {
        this.print();
        System.out.println();
    }

    @Override
    public boolean contains(T value)
    {
        for (T v:this.array) if (v == value) return true;
        return false;
    }

    @Override
    public boolean equals(List<T> otherList)
    {
        if (this.size() != otherList.size()) return false;

        otherList.moveCurrentIndexToStart();
        for (T value:this.array)
        {
            if (value != otherList.getCurrentValue()) return false;
            otherList.moveCurrentIndexRight();
        }
        return true;
    }
}