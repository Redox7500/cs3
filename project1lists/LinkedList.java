package project1lists;

// i should change all of the this.currentLink == this.tailLink Index out of range things to this.currentIndex < this.elementCount - 1 or something
// same for DList
/** Linked list implementation */
class LinkedList<T> implements List<T>
{
    private static class Link<T>
    {
        /** Linked list of nodes that are not currently being used and can be reused */
        @SuppressWarnings("rawtypes")
        private static Link freeList;

        /** Value for this node */
        private T value = null;

        /** Pointer to next node in list */
        private Link<T> nextLink = null;

        Link() {}

        @SuppressWarnings({"rawtypes", "unchecked"})
        private static <T> Link<T> acquire()
        {
            if (freeList == null) return new Link<T>();
            
            Link toReturn = freeList;
            freeList = freeList.nextLink;

            return toReturn;
        }

        private static <T> Link<T> acquire(boolean looping)
        {
            Link<T> toReturn = Link.acquire();
            toReturn.nextLink = toReturn;

            return toReturn;
        }

        private static <T> Link<T> acquire(T value)
        {
            Link<T> toReturn = Link.acquire();
            toReturn.value = value;
            toReturn.nextLink = null;

            return toReturn;
        }

        private static <T> Link<T> acquire(T value, Link<T> nextLink)
        {
            Link<T> toReturn = Link.acquire();
            toReturn.value = value;
            toReturn.nextLink = nextLink;

            return toReturn;
        }

        @SuppressWarnings("unchecked")
        private void release()
        {
            this.value = null;
            this.nextLink = Link.freeList;
            Link.freeList = this;
        }
    }

    /** Pointer to first link */
    private Link<T> headLink = Link.acquire(true);

    /** Pointer to second to last link */
    private Link<T> tailLink = this.headLink;

    /** Pointer to current link */
    private Link<T> currentLink = this.headLink;

    /** Index of the current link in the list */
    private int currentIndex = 0;

    /** Size of list */
    private int elementCount = 0;

    LinkedList() {}

    @Override
    public int size() {return this.elementCount;}

    @Override
    public int getCurrentIndex() {return this.currentIndex;}

    @Override
    public void setCurrentIndex(int index)
    {
        assert index >= 0 && index < this.elementCount : "Index out of range";

        this.currentLink = this.headLink;
        for (this.currentIndex = 0; this.currentIndex < index; this.currentIndex++, this.currentLink = this.currentLink.nextLink);
    }

    @Override
    public void moveCurrentIndexToStart() {this.currentLink = this.headLink; this.currentIndex = 0;}

    @Override
    public void moveCurrentIndexToEnd() {this.currentLink = this.tailLink; this.currentIndex = this.elementCount - 1;}

    @Override
    public void moveCurrentIndexLeft()
    {
        assert this.currentLink != this.headLink : "Index out of range";

        this.currentLink = this.headLink;
        this.currentIndex--;
        for (int i = 0; i < this.currentIndex - 1; i++, this.currentLink = this.currentLink.nextLink);
    }
    
    @Override
    public void moveCurrentIndexRight() {assert this.currentLink != this.tailLink : "Index out of range"; this.currentLink = this.currentLink.nextLink; this.currentIndex++;}

    @Override
    public T getCurrentValue() {assert this.elementCount > 0 : "Index out of range"; return this.currentLink.nextLink.value;}

    @Override
    public void setCurrentValue(T value) {assert this.elementCount > 0 : "Index out of range"; this.currentLink.nextLink.value = value;}

    @Override
    public void clear()
    {
        for (Link<T> tempLink = this.headLink; tempLink != null; tempLink = tempLink.nextLink) tempLink.release();
        this.headLink.nextLink = this.headLink; // Drop access to links
        this.currentIndex = 0;
        this.elementCount = 0;
    }

    @Override
    public void insert(T value)
    {
        if (this.currentLink != this.tailLink)
        {
            this.currentLink.nextLink = Link.acquire(value, this.currentLink.nextLink);
            this.elementCount++;
        }
        else
        {
            this.append(value);
        }
    }
    
    @Override
    public void append(T value)
    {
        this.tailLink = this.tailLink.nextLink;
        this.tailLink.nextLink = Link.acquire(value);
        this.elementCount++;
    }
    
    @Override
    public T remove()
    {
        assert this.elementCount > 0 : "Index out of range";
        
        T value = this.currentLink.nextLink.value; // Remember value

        Link<T> currentNextLink = this.currentLink.nextLink;
        this.currentLink.nextLink = currentNextLink.nextLink; // Remove from list
        currentNextLink.release();
        if (this.currentLink == this.tailLink) // Removed last
        {
            if (this.elementCount > 1)
            {
                System.out.println(this.getCurrentValue());
                this.moveCurrentIndexLeft();
                this.tailLink = this.currentLink;
            }
            else
            {
                this.headLink.nextLink = this.headLink;
            }
        }
        this.elementCount--;

        return value;
    }

    @Override
    public void print()
    {
        System.out.print("[");
        if (this.elementCount > 0)
        {
            System.out.print(this.headLink.nextLink.value);
            for (Link<T> tempLink = this.headLink.nextLink; tempLink.nextLink != null; ) System.out.print(", " + (tempLink = tempLink.nextLink).value);
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
        for (Link<T> tempLink = headLink; tempLink != tailLink; ) if ((tempLink = tempLink.nextLink).value == value) return true;
        return false;
    }

    @Override
    public boolean equals(List<T> otherList)
    {
        if (this.size() != otherList.size()) return false;

        otherList.moveCurrentIndexToStart();
        for (Link<T> tempLink = this.headLink; tempLink != this.tailLink; tempLink = tempLink.nextLink, otherList.moveCurrentIndexRight()) if (tempLink.nextLink.value != otherList.getCurrentValue()) return false;
        return true;
    }
}