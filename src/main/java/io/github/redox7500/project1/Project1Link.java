package io.github.redox7500.project1;

public class Project1Link<E>
{
    /** Linked list of nodes that are not currently being used and can be reused */
    private static Project1Link<?> freeList;

    /** Value for this node */
    public E value = null;

    /** Pointer to next node in list */
    public Project1Link<E> nextLink = null;

    public static <E> Project1Link<E> acquire()
    {
        if (freeList == null) return new Project1Link<>();
        
        @SuppressWarnings("unchecked")
        Project1Link<E> toReturn = (Project1Link<E>)freeList;
        freeList = freeList.nextLink;

        return toReturn;
    }

    public static <E> Project1Link<E> acquire(boolean looping)
    {
        Project1Link<E> toReturn = Project1Link.acquire();
        if (looping) toReturn.nextLink = toReturn;

        return toReturn;
    }

    public static <E> Project1Link<E> acquire(E value) // could make this acquire(value, null) instead to save room?
    {
        Project1Link<E> toReturn = Project1Link.acquire();
        toReturn.value = value;
        toReturn.nextLink = null;

        return toReturn;
    }

    public static <E> Project1Link<E> acquire(E value, Project1Link<E> nextLink)
    {
        Project1Link<E> toReturn = Project1Link.acquire();
        toReturn.value = value;
        toReturn.nextLink = nextLink;

        return toReturn;
    }

    @SuppressWarnings("unchecked")
    public void release()
    {
        this.value = null;
        this.nextLink = (Project1Link<E>)Project1Link.freeList;
        Project1Link.freeList = this;
    }
}
