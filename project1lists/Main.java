package project1lists;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println((TestHarness.testCustomList(() -> new CustomDoublyLinkedList<Integer>(), 3))? "great" : "terrible");
    }
}