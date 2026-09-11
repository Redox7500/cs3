package project1lists;

/** Linked list implementation */
class DoublyLinkedList<T> implements List<T>
{
    private static class Link<T>
    {
        /** Linked list of nodes that are not currently being used and can be reused
         * Note: Doubly linked free lists do not take advantage of the link's previousLink property, as there is no need.
        */
        @SuppressWarnings("rawtypes")
        private static Link freeList;

        /** Value for this node */
        private T value = null;

        /** Pointer to the previous node in the list */
        private Link<T> previousLink = null;

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

        private static <T> Link<T> acquire(T value)
        {
            Link<T> toReturn = Link.acquire();
            toReturn.value = value;
            toReturn.nextLink = null;

            return toReturn;
        }

        private static <T> Link<T> acquire(T value, Link<T> previousLink, Link<T> nextLink)
        {
            Link<T> toReturn = Link.acquire();
            toReturn.value = value;
            toReturn.previousLink = previousLink;
            toReturn.nextLink = nextLink;

            return toReturn;
        }

        @SuppressWarnings("unchecked")
        private void release()
        {
            this.value = null;
            this.previousLink = null;
            this.nextLink = Link.freeList;
            Link.freeList = this;
        }
    }

    /** Pointer to first link */
    private Link<T> headLink = null;

    /** Pointer to second to last link */
    private Link<T> tailLink = null;

    /** Pointer to current link */
    private Link<T> currentLink = null;

    /** Index of the current link in the list */
    private int currentIndex = 0;

    /** Size of list */
    private int elementCount = 0;

    DoublyLinkedList() {}

    @Override
    public int size() {return this.elementCount;}

    @Override
    public int getCurrentIndex() {return this.currentIndex;}

    @Override
    public void setCurrentIndex(int index)
    {
        assert index >= 0 && index < this.elementCount : "Index out of range";

        if (this.currentIndex >> 2 <= index)
        {
            for (; this.currentIndex >= index; this.currentIndex--) this.currentLink = this.currentLink.previousLink;
        }
        else
        {
            this.currentLink = this.headLink;
            for (this.currentIndex = 0; this.currentIndex <= index; this.currentIndex++) this.currentLink = this.currentLink.nextLink;
        }
    }

    @Override
    public void moveCurrentIndexToStart() {this.currentLink = this.headLink; this.currentIndex = 0;}

    @Override
    public void moveCurrentIndexToEnd() {this.currentLink = this.tailLink; this.currentIndex = this.elementCount - 1;}

    @Override
    public void moveCurrentIndexLeft() {assert this.currentLink != this.headLink : "Index out of range"; this.currentLink = this.currentLink.previousLink; this.currentIndex--;}
    
    @Override
    public void moveCurrentIndexRight() {assert this.currentLink != this.tailLink : "Index out of range"; this.currentLink = this.currentLink.nextLink; this.currentIndex++;}

    @Override
    public T getCurrentValue() {assert this.elementCount > 0 : "Index out of range"; return this.currentLink.value;}

    @Override
    public void setCurrentValue(T value) {assert this.elementCount > 0 : "Index out of range"; this.currentLink.value = value;}

    @Override
    public void clear()
    {
        for (Link<T> tempLink = this.headLink; tempLink != null; tempLink = tempLink.nextLink) tempLink.release();
        this.headLink = this.currentLink = this.tailLink = null; // Drop access to links
        this.currentIndex = 0;
        this.elementCount = 0;
    }

    @Override
    public void insert(T value)
    {
        if (this.currentLink != this.tailLink)
        {
            this.currentLink.previousLink.nextLink = Link.acquire(value, this.currentLink.previousLink, this.currentLink);
            this.currentLink.previousLink = this.currentLink.previousLink.nextLink;
            // this.currentLink = Link.acquire(value, this.currentLink.previousLink, this.currentLink);
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
        if (this.elementCount == 0)
        {
            this.headLink = this.currentLink = this.tailLink = Link.acquire(value);
        }
        else
        {
            this.tailLink.nextLink = Link.acquire(value, this.tailLink, null);
            this.tailLink = this.tailLink.nextLink;
        }
        this.elementCount++;
    }
    
    @Override
    public T remove()
    {
        assert this.elementCount > 0 : "Index out of range";
        
        T value = this.currentLink.value; // Remember value
        Link<T> currentPreviousLink = this.currentLink.previousLink;
        Link<T> currentNextLink = this.currentLink.nextLink;
        // this.currentLink.release();
        if (currentPreviousLink != null) currentPreviousLink.nextLink = currentNextLink;
        if (currentNextLink != null)
        {
            currentNextLink.previousLink = currentPreviousLink;
            this.currentLink = currentNextLink;
        }
        else
        {
            this.currentLink = currentPreviousLink;
            this.tailLink = this.currentLink;
        }
        // System.out.println(this.headLink.value);
        // System.out.println(this.currentLink.value);
        // System.out.println(this.tailLink.value);
        // System.out.println(this.headLink);
        // System.out.println(this.tailLink == this.headLink);
        // this.currentLink.previousLink.nextLink = this.currentLink.nextLink;
        // this.currentLink.nextLink.previousLink = this.currentLink.previousLink;
        // if (this.currentLink != this.headLink)
        // {
        //     if (this.currentLink != this.tailLink)
        //     {
        //         System.out.println("a");
        //         Link<T> currentNextLink = this.currentLink.nextLink;
        //         this.currentLink.previousLink.nextLink = currentNextLink;
        //         this.currentLink.release();
        //         this.currentLink = currentNextLink;
        //     }
        //     else
        //     {
        //         System.out.println("c");
        //         this.tailLink = this.tailLink.previousLink;
        //         this.currentLink.release();
        //         this.tailLink.nextLink = null;
        //         this.currentLink = this.tailLink;
        //     }
        // }
        // else if (this.elementCount > 1)
        // {
        //     System.out.println("b");
        //     this.headLink = this.currentLink.nextLink;
        //     this.currentLink.release();
        //     this.headLink.previousLink = null;
        //     this.currentLink = this.headLink;
        // }
        // else
        // {
        //     this.currentLink.release();
        //     this.headLink = this.currentLink = this.tailLink = null;
        // }
        this.elementCount--;

        return value;
    }

    @Override
    public void print()
    {
        System.out.print("[");
        if (this.elementCount > 0)
        {
            System.out.print(this.headLink.value);
            for (Link<T> tempLink = this.headLink.nextLink; tempLink != null; tempLink = tempLink.nextLink) System.out.print(", " + tempLink.value);
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
        for (Link<T> tempLink = headLink; tempLink != null; tempLink = tempLink.nextLink) if (tempLink.value == value) return true;
        return false;
    }

    @Override
    public boolean equals(List<T> otherList)
    {
        if (this.size() != otherList.size()) return false;

        otherList.moveCurrentIndexToStart();
        for (Link<T> tempLink = this.headLink; tempLink != null; tempLink = tempLink.nextLink, otherList.moveCurrentIndexRight()) if (tempLink.value != otherList.getCurrentValue()) return false;
        return true;
    }
}