package project1lists;

// honestly i could probably remove the dummy head link when there are no elements to save more, but idk, it does mess up the consistency
/** Linked list implementation */
class CustomLinkedList<E> implements CustomList<E, CustomLinkedList<E>>
{
    private static class Link<E>
    {
        /** Linked list of nodes that are not currently being used and can be reused */
        private static Link<?> freeList;

        /** Value for this node */
        private E value = null;

        /** Pointer to next node in list */
        private Link<E> nextLink = null;

        Link() {}

        @SuppressWarnings("unchecked")
        private static <E> Link<E> acquire()
        {
            if (freeList == null) return new Link<>();
            
            Link<E> toReturn = (Link<E>)freeList;
            freeList = freeList.nextLink;

            return toReturn;
        }

        private static <E> Link<E> acquire(boolean looping)
        {
            Link<E> toReturn = Link.acquire();
            if (looping) toReturn.nextLink = toReturn;

            return toReturn;
        }

        private static <E> Link<E> acquire(E value)
        {
            Link<E> toReturn = Link.acquire();
            toReturn.value = value;
            toReturn.nextLink = null;

            return toReturn;
        }

        private static <E> Link<E> acquire(E value, Link<E> nextLink)
        {
            Link<E> toReturn = Link.acquire();
            toReturn.value = value;
            toReturn.nextLink = nextLink;

            return toReturn;
        }

        @SuppressWarnings("unchecked")
        private void release()
        {
            this.value = null;
            this.nextLink = (Link<E>)Link.freeList;
            Link.freeList = this;
        }
    }

    /** Pointer to first link */
    private final CustomLinkedList.Link<E> headLink = Link.acquire(true);

    /** Pointer to second to last link */
    private CustomLinkedList.Link<E> tailLink = this.headLink;

    /** Pointer to current link */
    private CustomLinkedList.Link<E> currentLink = this.headLink;

    /** Index of the current link in the list */
    private int currentIndex = 0;

    /** Size of list */
    private int elementCount = 0;

    CustomLinkedList() {}

    @Override
    public int size() {return this.elementCount;}

    @Override
    public int getCurrentIndex() {return this.currentIndex;}

    @Override
    public void moveCurrentIndexTo(int index)
    {
        assert (this.elementCount > 0)? index >= 0 && index < this.elementCount : index == 0 : "Index out of range";

        this.currentLink = this.headLink;
        for (this.currentIndex = 0; this.currentIndex < index; this.currentIndex++) this.currentLink = this.currentLink.nextLink;
    }

    @Override
    public void moveCurrentIndexToStart() {this.currentLink = this.headLink; this.currentIndex = 0;}

    @Override
    public void moveCurrentIndexToEnd() {this.currentLink = this.tailLink; this.currentIndex = Math.max(this.elementCount - 1, 0);}

    @Override
    public void moveCurrentIndexLeft()
    {
        assert this.currentIndex != 0 : "Index out of range";

        this.currentLink = this.headLink;
        int targetIndex = this.currentIndex - 1;
        for (this.currentIndex = 0; this.currentIndex < targetIndex; this.currentIndex++) this.currentLink = this.currentLink.nextLink;
    }
    
    @Override
    public void moveCurrentIndexRight() {assert this.currentIndex != Math.max(this.elementCount - 1, 0) : "Index out of range"; this.currentLink = this.currentLink.nextLink; this.currentIndex++;}

    @Override
    public E getCurrentValue() {assert this.elementCount > 0 : "Index out of range"; return this.currentLink.nextLink.value;}

    @Override
    public void setCurrentValue(E value) {if (this.elementCount > 0) this.currentLink.nextLink.value = value; else this.append(value);}

    @Override
    public void clear()
    {
        if (this.elementCount == 0) return;
        for (CustomLinkedList.Link<E> tempLink = this.headLink.nextLink; tempLink != null; tempLink = tempLink.nextLink) tempLink.release();
        this.headLink.nextLink = this.headLink; // Drop access to links
        this.currentIndex = 0;
        this.elementCount = 0;
    }

    @Override
    public void insert(E value)
    {
        if (this.elementCount == 0)
        {
            this.append(value);
        }
        else
        {
            this.currentLink.nextLink = Link.acquire(value, this.currentLink.nextLink);
            if (this.currentLink == this.tailLink) this.tailLink = this.tailLink.nextLink;
            this.elementCount++;
        }
    }
    
    @Override
    public void append(E value)
    {
        this.tailLink = this.tailLink.nextLink;
        this.tailLink.nextLink = Link.acquire(value);
        this.elementCount++;
    }
    
    @Override
    public E remove()
    {
        assert this.elementCount > 0 : "Index out of range";
        
        E value = this.currentLink.nextLink.value; // Remember value

        CustomLinkedList.Link<E> currentNextLink = this.currentLink.nextLink;
        this.currentLink.nextLink = currentNextLink.nextLink; // Remove from list
        currentNextLink.release();
        if (this.currentLink == this.tailLink) // Removed last
        {
            if (this.elementCount > 1)
            {
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
    public CustomLinkedList<E> copy()
    {
        CustomLinkedList<E> toReturn = new CustomLinkedList<>();
        if (this.elementCount > 0) for (CustomLinkedList.Link<E> tempLink = this.headLink; tempLink.nextLink != null; tempLink = tempLink.nextLink) toReturn.append(tempLink.nextLink.value);
        if (this.currentIndex != 0) toReturn.moveCurrentIndexTo(this.currentIndex);

        return toReturn;
    }

    @Override
    public String toString()
    {
        StringBuilder toReturn = new StringBuilder(this.elementCount * 3 + ((this.elementCount > 0)? 0 : 2));
        toReturn.append('[');
        if (this.elementCount > 0)
        {
            toReturn.append(this.headLink.nextLink.value);
            for (CustomLinkedList.Link<E> tempLink = this.headLink.nextLink; tempLink.nextLink != null; )
            {
                toReturn.append(", ");
                toReturn.append((tempLink = tempLink.nextLink).value);
            }
        }
        toReturn.append(']');

        return toReturn.toString();
    }
}