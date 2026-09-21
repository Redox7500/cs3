package project1lists;

import java.util.function.Supplier;

public class Main
{
    public static void main(String[] args)
    {
        TestHarness.testCustomLists(new Supplier<?>[]{
            () -> new CustomArrayList       <Integer>(),
            () -> new CustomLinkedList      <Integer>(),
            () -> new CustomDoublyLinkedList<Integer>()
        }, new String[]{
            "CustomArrayList",
            "CustomLinkedList",
            "CustomDoublyLinkedList"
        });
    }
}