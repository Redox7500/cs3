package project1lists;

/** Singly linked list node */
class Link<E>
{
    /** Value for this node */
    private E element;

    /** Pointer to next node in list */
    private Link<E> next;

    // Constructors
    Link(E it, Link<E> nextval) {element = it; next = nextval;}
    Link(Link<E> nextval) {next = nextval;}

    Link<E> next() {return next;}
    Link<E> setNext(Link<E> nextval) {return next = nextval;}
    E element() {return element;}
    E setElement(E it) {return element = it;}
}