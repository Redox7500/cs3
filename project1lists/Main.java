package project1lists;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println(TestHarness.testCustomList(() -> new CustomArrayList<Integer>(), 2));
    }
}