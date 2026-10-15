package io.github.redox7500.project1;

public class Project1Main
{
    public static void main(String[] args)
    {
        System.out.println(Project1TestHarness.test(() -> new Project1ArrayList<Integer>(), 7));
    }
}
