package io.github.redox7500.project2;

import io.github.redox7500.project1.Project1LinkedList;
import io.github.redox7500.project1.Project1Link;

/** This is just Project1LinkedList but with extra methods.
 * @see io.github.redox7500.project1.Project1LinkedList
 */
public class Project2LinkedList<E> extends Project1LinkedList<E>
{
    // wait am i supposed to document generic parameters oh no
    /** Merge two sorted Project2LinkedLists of value type that extends Comparable. The output will be sortedsorted
     * @param list1 The first list to merge
     * @param list2 The second list to merge
     * @return The result of merging the first and second lists to merge
     */
    public static <E extends Comparable<? super E>> Project2LinkedList<E> mergeLists(Project2LinkedList<E> list1, Project2LinkedList<E> list2)
    {
        Project2LinkedList<E> toReturn = new Project2LinkedList<>();
        
        if (list1.size() == 0 && list2.size() == 0) return toReturn;

        Project1Link<E> list1CurrentLink = list1.headLink.nextLink;
        Project1Link<E> list2CurrentLink = list2.headLink.nextLink;

        while (list1CurrentLink != null || list2CurrentLink != null)
        {
            if (list1CurrentLink == null)
            {
                toReturn.append(list2CurrentLink.value);
                list2CurrentLink = list2CurrentLink.nextLink;
                continue;
            }
            if (list2CurrentLink == null)
            {
                toReturn.append(list1CurrentLink.value);
                list1CurrentLink = list1CurrentLink.nextLink;
                continue;
            }
            if (list1CurrentLink.value == null)
            {
                toReturn.append(list2CurrentLink.value);
                list2CurrentLink = list2CurrentLink.nextLink;
                continue;
            }
            if (list2CurrentLink.value == null)
            {
                toReturn.append(list1CurrentLink.value);
                list1CurrentLink = list1CurrentLink.nextLink;
                continue;
            }
            if (list1CurrentLink.value.compareTo(list2CurrentLink.value) > 0)
            {
                toReturn.append(list2CurrentLink.value);
                list2CurrentLink = list2CurrentLink.nextLink;
                continue;
            }
            else
            {
                toReturn.append(list1CurrentLink.value);
                list1CurrentLink = list1CurrentLink.nextLink;
                continue;
            }
        }

        return toReturn;
    }

    /** Reverse the order of the values in this list */
    public void reverseOrder()
    {
        int size = this.size();
        Project1Link<E> previousLink = null;
        Project1Link<E> currentLink = this.headLink.nextLink;
        Project1Link<E> nextLink = null;
        for (int i = 0; i < size; i++)
        {
            nextLink = currentLink.nextLink;
            currentLink.nextLink = previousLink;
            previousLink = currentLink;
            currentLink = nextLink;
        }
        this.headLink.nextLink = previousLink;
    }
}
