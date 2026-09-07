package mylinkedlists;

public class Link<T>
{
    Link<T> next;
    T value;

    Link()                      {}
    Link(T value)               {this.value = value;}
    Link(Link<T> next)          {this.next = next;}
    Link(T value, Link<T> next) {this.value = value; this.next = next;}
    Link(boolean looping)       {if (looping) {this.next = this;}}
}