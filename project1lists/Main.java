package project1lists;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println((TestHarness.testCustomList(() -> new CustomArrayList<Integer>(), 7))? "great" : "terrible"); // warning: with doubly linked lists depth 8 gave me a java heap space error
    }
}