package io.github.redox7500.project1;

public class Project1DoubleLink<E>
{
    /** Linked list of nodes that are not currently being used and can be reused
     * <p>
     * Note: Doubly linked free lists do not take advantage of the link's previousLink property, as there is no need.
    */
    private static Project1DoubleLink<?> freeList;

    /** Value for this node */
    public E value = null;

    /** Pointer to the previous node in the list */
    public Project1DoubleLink<E> previousLink = null;

    /** Pointer to next node in list */
    public Project1DoubleLink<E> nextLink = null;

    public static <E> Project1DoubleLink<E> acquire()
    {
        if (freeList == null) return new Project1DoubleLink<>();
        
        @SuppressWarnings("unchecked")
        Project1DoubleLink<E> toReturn = (Project1DoubleLink<E>)freeList;
        freeList = freeList.nextLink;

        return toReturn;
    }

    public static <E> Project1DoubleLink<E> acquire(E value)
    {
        Project1DoubleLink<E> toReturn = Project1DoubleLink.acquire();
        toReturn.value = value;
        toReturn.nextLink = null;

        return toReturn;
    }

    public static <E> Project1DoubleLink<E> acquire(E value, Project1DoubleLink<E> previousLink, Project1DoubleLink<E> nextLink)
    {
        Project1DoubleLink<E> toReturn = Project1DoubleLink.acquire();
        toReturn.value = value;
        toReturn.previousLink = previousLink;
        toReturn.nextLink = nextLink;

        return toReturn;
    }

    @SuppressWarnings("unchecked")
    public void release()
    {
        this.value = null;
        this.previousLink = null;
        this.nextLink = (Project1DoubleLink<E>)Project1DoubleLink.freeList;
        Project1DoubleLink.freeList = this;
    }
}
