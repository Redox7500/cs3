package io.github.redox7500.project1;

/** Linked list implementation */
public class Project1DoublyLinkedList<E> extends Project1AbstractList<E>
{
    /** Pointer to first link */
    private Project1DoubleLink<E> headLink = null;

    /** Pointer to second to last link */
    private Project1DoubleLink<E> tailLink = null;

    /** Pointer to current link */
    private Project1DoubleLink<E> currentLink = null;

    /** Index of the current link in the list */
    private int currentIndex = 0;

    /** Size of list */
    private int elementCount = 0;

    @Override
    public void clear()
    {
        Project1DoubleLink<E> currentLink = this.headLink;
        Project1DoubleLink<E> nextLink;
        while (currentLink != null)
        {
            nextLink = currentLink.nextLink;
            currentLink.release();
            currentLink = nextLink;
        }
        // for (Project1DoubleLink<E> tempLink = this.headLink; tempLink != null; tempLink = tempLink.nextLink) tempLink.release();
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
                this.headLink.previousLink = Project1DoubleLink.acquire(value, null, this.headLink);
                this.headLink = this.currentLink = this.headLink.previousLink;
            }
            else
            {
                this.currentLink.previousLink = Project1DoubleLink.acquire(value, this.currentLink.previousLink, this.currentLink);
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
            this.headLink = this.currentLink = this.tailLink = Project1DoubleLink.acquire(value);
        }
        else
        {
            this.tailLink.nextLink = Project1DoubleLink.acquire(value, this.tailLink, null);
            this.tailLink = this.tailLink.nextLink;
        }
        this.elementCount++;
    }
    
    @Override
    public E remove()
    {
        assert this.elementCount > 0 : "Index out of range";
        
        E value = this.currentLink.value; // Remember value

        Project1DoubleLink<E> currentPreviousLink = this.currentLink.previousLink;
        Project1DoubleLink<E> currentNextLink = this.currentLink.nextLink;
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
    public E getCurrentValue() {assert this.elementCount > 0 : "Index out of range"; return this.currentLink.value;}

    @Override
    public void setCurrentValue(E value) {if (this.elementCount > 0) this.currentLink.value = value; else this.append(value);}

    @Override
    public int getCurrentIndex() {return this.currentIndex;}

    @Override
    public void moveCurrentIndexTo(int index)
    {
        assert (this.elementCount > 0)? index >= 0 && index < this.elementCount : index == 0 : "Index out of range";

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
    public void moveCurrentIndexToPrevious() {assert this.currentIndex != 0 : "Index out of range"; this.currentLink = this.currentLink.previousLink; this.currentIndex--;}
    
    @Override
    public void moveCurrentIndexToNext() {assert this.currentIndex != Math.max(this.elementCount - 1, 0) : "Index out of range"; this.currentLink = this.currentLink.nextLink; this.currentIndex++;}

    @Override
    public int size() {return this.elementCount;}

    @Override
    public Project1DoublyLinkedList<E> copy()
    {
        Project1DoublyLinkedList<E> toReturn = new Project1DoublyLinkedList<>();
        for (Project1DoubleLink<E> tempLink = this.headLink; tempLink != null; tempLink = tempLink.nextLink) toReturn.append(tempLink.value);
        if (this.currentIndex != 0) toReturn.moveCurrentIndexTo(this.currentIndex);

        return toReturn;
    }

    @Override
    public String toString()
    {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append('[');
        if (this.elementCount > 0)
        {
            toReturn.append(this.headLink.value);
            for (Project1DoubleLink<E> tempLink = this.headLink.nextLink; tempLink != null; tempLink = tempLink.nextLink)
            {
                toReturn.append(", ");
                toReturn.append(tempLink.value);
            }
        }
        toReturn.append(']');

        return toReturn.toString();
    }
}
