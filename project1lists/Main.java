package project1lists;

public class Main
{
    public static void main(String[] args)
    {
        long startTime = System.nanoTime();
        System.out.println((TestHarness.testCustomList(() -> new CustomArrayList<Integer>(), 7))? "Success!" : "Terrible!!!"); // warning: depth 8 gave me a java heap space error with all of the custom list kinds
        System.out.println("Elapsed time: " + ((System.nanoTime() - startTime) / (float)1_000_000_000));
    }
}