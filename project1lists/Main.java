package project1lists;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.lang.InterruptedException;
import java.util.function.Supplier;

public class Main
{
    public static void main(String[] args)
    {
        System.out.print("\033[?25l");

        @SuppressWarnings("rawtypes") // i think that this is where it's supposed to go instead of at the top, i'll change that in the other files later
        Supplier[] constructors = new Supplier<?>[]{
            () -> new CustomArrayList       <Integer>(),
            () -> new CustomLinkedList      <Integer>(),
            () -> new CustomDoublyLinkedList<Integer>()
        };
        String[] names = new String[]{
            "CustomArrayList",
            "CustomLinkedList",
            "CustomDoublyLinkedList"
        };
        int maxNameLength = 0;
        for (String name : names) maxNameLength = Math.max(maxNameLength, name.length());
        for (String name : names) System.out.println(String.format("%-" + (maxNameLength + 2) + "s", name + ": ") + "...");

        System.out.print("\033[3A");
        AsyncSpinner.start();
        for (int i = 0; i < names.length; i++)
        {
            System.out.print("\033[25G   \033[25G");
            AsyncSpinner.start();

            long startTime = System.nanoTime();
            @SuppressWarnings("unchecked")
            boolean result = TestHarness.testCustomList(constructors[i], 7);
            float elapsedTime = (System.nanoTime() - startTime) / (float)1_000_000_000;

            AsyncSpinner.stop();
            if (result)
            {
                System.out.println("Success (" + elapsedTime + " seconds elapsed)");
            }
            else
            {
                System.out.println("\033[" + i + "A (" + elapsedTime + " seconds elapsed)");
                break;
            }
        }
        AsyncSpinner.stop();
        
        // System.out.println("CustomArrayList:        ...\nCustomLinkedList:       ...\nCustomDoublyLinkedList: ...");
        // boolean arrayListResult = TestHarness.testCustomList(() -> new CustomArrayList<Integer>(), 7);
        // System.out.println("\033[3A\033[25G" + ((TestHarness.testCustomList(() -> new CustomArrayList<Integer>(),        7))? "Success" : "")); // warning: depth 8 gave me a java heap space error with all of the custom list kinds
        // System.out.println("\033[25G" + ((TestHarness.testCustomList(() -> new CustomLinkedList<Integer>(),       7))? "Success" : ""));
        // System.out.println("\033[25G" + ((TestHarness.testCustomList(() -> new CustomDoublyLinkedList<Integer>(), 7))? "Success" : ""));
        // System.out.println("\nElapsed time: " + ((System.nanoTime() - startTime) / (float)1_000_000_000));
        AsyncSpinner.kill();
        System.out.print("\033[99999;99999H\033[1A\033[?25h");
    }

    private static class AsyncSpinner
    {
        static final char[] frames = {'|', '/', '-', '\\'};
        static ExecutorService executor;
        static Future<?> task;

        static void animate()
        {
            int frame = 0;
            while (!Thread.currentThread().isInterrupted())
            {
                System.out.print(frames[frame] + "\033[1D");
                frame = (frame + 1) % frames.length;
                try {Thread.sleep(100);}
                catch (InterruptedException _) {Thread.currentThread().interrupt();}
            }
        }

        static void start()
        {
            if (AsyncSpinner.executor == null) AsyncSpinner.executor = Executors.newSingleThreadExecutor();
            AsyncSpinner.task = executor.submit(AsyncSpinner::animate);
        }

        static void stop() {if (AsyncSpinner.task != null) AsyncSpinner.task.cancel(true);}

        static void kill() {if (AsyncSpinner.executor != null) AsyncSpinner.executor.shutdownNow();}
    }
}