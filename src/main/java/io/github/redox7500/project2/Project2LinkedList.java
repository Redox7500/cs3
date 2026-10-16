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
        Project1Link<E> nextLink = null; // = this.headLink.nextLink;
        for (int i = 0; i < size; i++)
        {
            // System.out.println("previousLink: " + ((previousLink == null)? null : previousLink.value));
            // System.out.println("currentLink: " + ((currentLink == null)? null : currentLink.value));
            // System.out.println("nextLink: " + ((nextLink == null)? null : nextLink.value));
            nextLink = currentLink.nextLink;
            currentLink.nextLink = previousLink;
            previousLink = currentLink;
            currentLink = nextLink;
        }
        this.headLink.nextLink = previousLink;
    }

    // public Project2LinkedList<E> mergeLists(Project2LinkedList<E> otherList)
    // {
    //     assert this.headLink.value instanceof Comparable : "This list's values are not Comparable";
    //     assert otherList.headLink.value instanceof Comparable : "Other list's values are not Comparable";
        
    //     Project1Link<E> thisCurrentLink = this.headLink;
    //     Project1Link<E> otherCurrentLink = otherList.headLink;

    //     Project2LinkedList<E> toReturn = new Project2LinkedList<>();

    //     while (thisCurrentLink != null && otherCurrentLink != null)
    //     {
    //         if (thisCurrentLink.value.compareTo(otherCurrentLink) > 0)
    //         {
    //             toReturn.append(thisCurrentLink);
    //         }

    //         if (thisCurrentLink != null) thisCurrentLink = thisCurrentLink.nextLink;
    //         if (otherCurrentLink != null) otherCurrentLink = otherCurrentLink.nextLink;
    //     }
    // }

    public static <E extends Comparable<? super E>> Project2LinkedList<E> mergeLists(Project2LinkedList<E> list1, Project2LinkedList<E> list2)
    {
        Project2LinkedList<E> toReturn = new Project2LinkedList<>();

        // if (list1.size() == 0)
        // {
        //     for (Project1Link<E> currentLink = list2.headLink; currentLink != list2.tailLink; ) toReturn.append((currentLink = currentLink.nextLink).value);
        //     return toReturn;
        // }
        // if (list2.size() == 0)
        // {
        //     for (Project1Link<E> currentLink = list1.headLink; currentLink != list1.tailLink; ) toReturn.append((currentLink = currentLink.nextLink).value);
        //     return toReturn;
        // }
        if (list1.size() == 0 && list2.size() == 0) return toReturn; // could do list1.size() + list2.size() == 0

        Project1Link<E> list1CurrentLink = list1.headLink.nextLink;
        Project1Link<E> list2CurrentLink = list2.headLink.nextLink;

        while (list1CurrentLink != null || list2CurrentLink != null)
        {
            System.out.println(list1CurrentLink);
            System.out.println(list2CurrentLink);
            // System.out.println(list1CurrentLink);
            // System.out.println(list2CurrentLink);
            // if (list1CurrentLink != null) System.out.println(list1CurrentLink.value);
            // if (list2CurrentLink != null) System.out.println(list2CurrentLink.value);
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
            // if (list1CurrentLink.value == null)
            // toReturn.append(((list1CurrentLink != null && list1CurrentLink.value != null && (list2CurrentLink == null || list2CurrentLink.value == null || list1CurrentLink.value.compareTo(list2CurrentLink.value) > 0))? list1CurrentLink : list2CurrentLink).value);
            // if (list1CurrentLink.value.compareTo(list2CurrentLink.value) > 0)
            // {
            //     toReturn.append(list1CurrentLink.value);
            // }
            // else
            // {
            //     toReturn.append(list2CurrentLink.value);
            // }

            // if (list1CurrentLink != null) list1CurrentLink = list1CurrentLink.nextLink;
            // if (list2CurrentLink != null) list2CurrentLink = list2CurrentLink.nextLink;
        }

        return toReturn;
    }
}
