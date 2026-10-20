package io.github.redox7500.project1;

import java.util.ArrayList;

// honestly i could probably remove the dummy head link when there are no elements to save more, but idk, it does mess up the consistency
/** Linked list implementation */
public class Project1LinkedList<E> extends Project1AbstractList<E>
{
    /** Pointer to first link */
    protected final Project1Link<E> headLink = Project1Link.acquire(true);

    /** Pointer to second to last link */
    protected Project1Link<E> tailLink = this.headLink;

    /** Pointer to current link */
    private Project1Link<E> currentLink = this.headLink;

    /** Index of the current link in the list */
    private int currentIndex = 0;

    /** Size of list */
    private int elementCount = 0;

    // private boolean a = false;

    public ArrayList<String> history = new ArrayList<>();

    // private void log()
    // {
    //     if (this.tailLink.nextLink == null && !this.a)
    //     {
    //         this.a = true;
    //         System.out.println("\033[30;42mhere look here here here over here" + String.valueOf("\n").repeat(10) + "\033[0m");
    //     }
    // }

    @Override
    public void clear()
    {
        if (this.elementCount == 0) return;
        Project1Link<E> currentLink = this.headLink.nextLink;
        Project1Link<E> nextLink;
        while (currentLink != null)
        {
            nextLink = currentLink.nextLink;
            currentLink.release();
            currentLink = nextLink;
        }
        // for (Project1Link<E> tempLink = this.headLink.nextLink; tempLink != null; tempLink = tempLink.nextLink) tempLink.release();
        this.headLink.nextLink = this.headLink; // Drop access to links
        this.currentIndex = 0;
        this.elementCount = 0;

        // this.log();
        history.add("clear()");
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
            this.currentLink.nextLink = Project1Link.acquire(value, this.currentLink.nextLink);
            if (this.currentLink == this.tailLink) this.tailLink = this.tailLink.nextLink;
            this.elementCount++;
        }
        // this.log();
        history.add("insert(" + value + ")");
    }
    
    @Override
    public void append(E value)
    {
        this.tailLink = this.tailLink.nextLink;
        this.tailLink.nextLink = Project1Link.acquire(value);
        this.elementCount++;
        // this.log();
        history.add("append(" + value + ")");
    }
    
    @Override
    public E remove()
    {
        assert this.elementCount > 0 : "Index out of range";
        
        E value = this.currentLink.nextLink.value; // Remember value

        Project1Link<E> currentNextLink = this.currentLink.nextLink;
        this.currentLink.nextLink = currentNextLink.nextLink; // Remove from list
        currentNextLink.release();
        if (this.currentLink == this.tailLink) // Removed last
        {
            if (this.elementCount > 1)
            {
                this.moveCurrentIndexToPrevious();
                this.tailLink = this.currentLink;
            }
            else
            {
                // System.out.println("abc");
                this.headLink.nextLink = this.headLink;
            }
        }
        else if (this.tailLink == currentNextLink) this.tailLink = this.currentLink;
        // if (currentNextLink == this.tailLink)
        // {
        //     this.tailLink = this.currentLink;
        // }
        // if (this.elementCount == 1)
        // {
        //     this.headLink.nextLink = this.headLink;
        // }
        this.elementCount--;

        // this.log();
        history.add("remove()");
        return value;
    }

    @Override
    public E getCurrentValue() {assert this.elementCount > 0 : "Index out of range"; history.add("getCurrentValue()"); return this.currentLink.nextLink.value;}

    @Override
    public void setCurrentValue(E value) {if (this.elementCount > 0) this.currentLink.nextLink.value = value; else this.append(value); history.add("setCurrentValue()");}

    @Override
    public int getCurrentIndex() {history.add("getCurrentIndex()"); return this.currentIndex;}

    @Override
    public void moveCurrentIndexTo(int index)
    {
        assert (this.elementCount > 0)? index >= 0 && index < this.elementCount : index == 0 : "Index out of range";

        if (index < this.currentIndex)
        {
            this.currentLink = this.headLink;
            this.currentIndex = 0;
        }
        for (; this.currentIndex < index; this.currentIndex++) this.currentLink = this.currentLink.nextLink;
        // this.log();
        history.add("moveCurrentIndexTo(" + index + ")");
    }

    @Override
    public void moveCurrentIndexToStart() {this.currentLink = this.headLink; this.currentIndex = 0; history.add("moveCurrentIndexToStart()");}

    @Override
    public void moveCurrentIndexToEnd() {this.currentLink = this.tailLink; this.currentIndex = Math.max(this.elementCount - 1, 0); history.add("moveCurrentIndexToEnd()");}

    @Override
    public void moveCurrentIndexToPrevious()
    {
        assert this.currentIndex != 0 : "Index out of range";

        this.currentLink = this.headLink;
        int targetIndex = this.currentIndex - 1;
        for (this.currentIndex = 0; this.currentIndex < targetIndex; this.currentIndex++) this.currentLink = this.currentLink.nextLink;
        // this.log();
        history.add("moveCurrentIndexToPrevious()");
    }
    
    @Override
    public void moveCurrentIndexToNext() {assert this.currentIndex != Math.max(this.elementCount - 1, 0) : "Index out of range"; this.currentLink = this.currentLink.nextLink; this.currentIndex++; history.add("moveCurrentIndexToNext()");}

    @Override
    public int size() {history.add("size()"); return this.elementCount;}

    @Override
    public Project1LinkedList<E> copy()
    {
        Project1LinkedList<E> toReturn = new Project1LinkedList<>();
        if (this.elementCount > 0) for (Project1Link<E> tempLink = this.headLink; tempLink.nextLink != null; tempLink = tempLink.nextLink) toReturn.append(tempLink.nextLink.value);
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
            toReturn.append(this.headLink.nextLink.value);
            for (Project1Link<E> tempLink = this.headLink.nextLink; tempLink.nextLink != null; )
            {
                toReturn.append(", ");
                toReturn.append((tempLink = tempLink.nextLink).value);
            }
        }
        toReturn.append(']');

        return toReturn.toString();
    }
}
