package project1lists;

/** Linked list implementation */
class CustomDoublyLinkedList<E> implements CustomList<E, CustomDoublyLinkedList<E>>
{
    private static class Link<E>
    {
        /** Linked list of nodes that are not currently being used and can be reused
         * <p>
         * Note: Doubly linked free lists do not take advantage of the link's previousLink property, as there is no need.
        */
        private static Link<?> freeList;

        /** Value for this node */
        private E value = null;

        /** Pointer to the previous node in the list */
        private Link<E> previousLink = null;

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

        private static <E> Link<E> acquire(E value)
        {
            Link<E> toReturn = Link.acquire();
            toReturn.value = value;
            toReturn.nextLink = null;

            return toReturn;
        }

        private static <E> Link<E> acquire(E value, Link<E> previousLink, Link<E> nextLink)
        {
            Link<E> toReturn = Link.acquire();
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
            this.nextLink = (Link<E>)Link.freeList;
            Link.freeList = this;
        }
    }

    /** Pointer to first link */
    private Link<E> headLink = null;

    /** Pointer to second to last link */
    private Link<E> tailLink = null;

    /** Pointer to current link */
    private Link<E> currentLink = null;

    /** Index of the current link in the list */
    private int currentIndex = 0;

    /** Size of list */
    private int elementCount = 0;

    CustomDoublyLinkedList() {}

    @Override
    public int size() {return this.elementCount;}

    @Override
    public int getCurrentIndex() {return this.currentIndex;}

    @Override
    public void moveCurrentIndexTo(int index)
    {
        assert index >= 0 && ((this.elementCount > 0)? index < this.elementCount : index == 0) : "Index out of range";

        if (this.currentIndex < index)
        {
            for (; this.currentIndex < index; this.currentIndex++) this.currentLink = this.currentLink.nextLink;
        }
        else if (this.currentIndex >> 1 < index)
        {
            for (; this.currentIndex > index; this.currentIndex--) this.currentLink = this.currentLink.previousLink;
        }
        else
        {
            this.currentLink = this.headLink;
            for (this.currentIndex = 0; this.currentIndex < index; this.currentIndex++) this.currentLink = this.currentLink.nextLink;
        }
    }

    @Override
    public void moveCurrentIndexToStart() {this.currentLink = this.headLink; this.currentIndex = 0;}

    @Override
    public void moveCurrentIndexToEnd() {this.currentLink = this.tailLink; this.currentIndex = Math.max(this.elementCount - 1, 0);}

    @Override
    public void moveCurrentIndexLeft() {assert this.currentIndex != 0 : "Index out of range"; this.currentLink = this.currentLink.previousLink; this.currentIndex--;}
    
    @Override
    public void moveCurrentIndexRight() {assert this.currentIndex != Math.max(this.elementCount - 1, 0) : "Index out of range"; this.currentLink = this.currentLink.nextLink; this.currentIndex++;}

    @Override
    public E getCurrentValue() {assert this.elementCount > 0 : "Index out of range"; return this.currentLink.value;}

    @Override
    public void setCurrentValue(E value) {if (this.elementCount > 0) this.currentLink.value = value; else this.append(value);}

    @Override
    public void clear()
    {
        for (Link<E> tempLink = this.headLink; tempLink != null; tempLink = tempLink.nextLink) tempLink.release();
        this.headLink = this.currentLink = this.tailLink = null; // Drop access to links
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
            if (this.currentLink == this.headLink)
            {
                this.headLink.previousLink = Link.acquire(value, null, this.headLink);
                this.headLink = this.currentLink = this.headLink.previousLink;
            }
            else
            {
                this.currentLink.previousLink = Link.acquire(value, this.currentLink.previousLink, this.currentLink);
                this.currentLink = this.currentLink.previousLink;
                this.currentLink.previousLink.nextLink = this.currentLink;
            }
            this.elementCount++;
        }
    }
    
    @Override
    public void append(E value)
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
    public E remove()
    {
        assert this.elementCount > 0 : "Index out of range";
        
        E value = this.currentLink.value; // Remember value

        Link<E> currentPreviousLink = this.currentLink.previousLink;
        Link<E> currentNextLink = this.currentLink.nextLink;
        this.currentLink.release();
        if (this.currentLink != this.headLink) currentPreviousLink.nextLink = currentNextLink;
        if (this.currentLink != this.tailLink)
        {
            currentNextLink.previousLink = currentPreviousLink;
            this.currentLink = currentNextLink;
            if (this.currentIndex == 0) this.headLink = this.currentLink;
        }
        else
        {
            this.currentLink = currentPreviousLink;
            this.tailLink = this.currentLink;
            if (this.elementCount == 1) this.headLink = null;
            else this.currentIndex--;
        }
        this.elementCount--;

        return value;
    }

    @Override
    public CustomDoublyLinkedList<E> copy()
    {
        CustomDoublyLinkedList<E> toReturn = new CustomDoublyLinkedList<>();
        for (Link<E> tempLink = this.headLink; tempLink != null; tempLink = tempLink.nextLink) toReturn.append(tempLink.value);
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
            toReturn.append(this.headLink.value);
            for (Link<E> tempLink = this.headLink.nextLink; tempLink != null; tempLink = tempLink.nextLink)
            {
                toReturn.append(", ");
                toReturn.append(tempLink.value);
            }
        }
        toReturn.append(']');

        return toReturn.toString();
    }
}