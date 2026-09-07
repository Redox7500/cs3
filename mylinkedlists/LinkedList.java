package mylinkedlists;

import java.util.List;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Iterator;

public class LinkedList<E> implements List<E>
{
    Link<E> current = new Link<E>(true);
    Link<E> head = current;
    Link<E> tail = current;
    int size = 0;

    public LinkedList() {}



    public void moveTo(int index)
    {
        
    }

    public boolean add(E e)
    {
        tail = tail.next;
        tail.next = new Link<E>(e);

        return true;
    }

    public void add(int index, E element)
    {

    }

    public boolean addAll(Collection<? extends E> c)
    {

    }

    public boolean addAll(int index, Collection<? extends E> c)
    {

    }

    public void clear()
    {

    }

    public boolean contains(Object o)
    {

    }

    public boolean containsAll(Collection<?> c)
    {

    }

    public E get(int index)
    {

    }

    public boolean indexOf(Object o)
    {

    }

    public boolean isEmpty()
    {

    }

    public Iterator<E> iterator()
    {

    }

    public int lastIndexOf(Object o)
    {

    }

    public ListIterator<E> listIterator()
    {

    }

    public ListIterator<E> listIterator(int index)
    {

    }

    public boolean remove(int index)
    {

    }

    public boolean remove(Object o)
    {

    }

    public boolean removeAll(Collection<?> c)
    {

    }

    public boolean retainAll(Collection<?> c)
    {

    }

    public E set(int index, E element)
    {

    }

    public int size()
    {

    }

    public List<E> subList(int fromIndex, int toIndex)
    {

    }

    public Object[] toArray()
    {

    }

    public <T> T[] toArray(T[] a)
    {

    }
}