package project1lists;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println((TestHarness.testCustomList(() -> new CustomArrayList<Integer>(), 7))? "great" : "terrible"); // warning: depth 8 gave me a java heap space error with all of the custom list kinds
    }
}