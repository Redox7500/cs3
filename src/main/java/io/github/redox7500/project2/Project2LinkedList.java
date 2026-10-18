package io.github.redox7500.project2;

import io.github.redox7500.project1.Project1LinkedList;
import io.github.redox7500.project1.Project1Link;

public class Project2LinkedList<E> extends Project1LinkedList<E>
{
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
}
