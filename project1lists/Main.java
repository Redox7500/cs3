package project1lists;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        
        CustomList<Integer> list = new CustomLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.moveCurrentIndexToEnd();
        System.out.println(list.getCurrentValue());
        list.println();
        list.moveCurrentIndexLeft();
        list.remove();
        list.println();
        list.insert(3);
        System.out.println(list.getCurrentValue());
        list.println();
        list.moveCurrentIndexRight();
        System.out.println(list.getCurrentValue());
        list.remove();
        list.println();
        list.remove();
        list.println();
        list.append(4);
        list.println();
        System.out.println(list.getCurrentValue());
    }
}