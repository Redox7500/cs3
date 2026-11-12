package io.github.redox7500.project3;

import java.io.OutputStream;
import java.io.PrintStream;

public class PrintInLevelTestHarness {
    private static class Interceptor extends PrintStream
    {
        private static String collect;
        public Interceptor(OutputStream out)
        {
            super(out, true);
            collect = "";
        }
        @Override
        public void print(String s)
        {
            collect += s;
            super.print(s);
        }
        @Override
        public void println(String s)
        {
            collect += s + "\n";
            super.println(s);
        }
        public String getCollect() {
            return collect;
        }
        public void resetCollect() {
            collect = "";
        }
    }

    public static void main(String[] args) {
        BST<Integer, Integer> bst = new BST<Integer, Integer>();
        int[] arr = {100, 55, 160, 75, 190, 25, 30, 85, 40, 110, 50, 5, 60, 95, 70, 15, 140, 35, 90, 65, 0, 45, 120, 130, 80, 150, 10, 170, 180, 20};
        for (int i = 0; i < 30; i++) {
            bst.insert(arr[i], arr[i]);
        }
        System.out.println("Tree: \n" + bst);

        PrintStream origOut = System.out;
        Interceptor interceptor = new Interceptor(origOut);
        System.setOut(interceptor);

        System.out.println("printInLevel (should be 100 55 160 25 75 110 190 5 30 60 85 140 170 0 15 40 70 80 95 120 150 180 10 20 35 50 65 90 130 45):");
        interceptor.resetCollect();
        bst.printInLevel();
        String test = interceptor.getCollect();
        System.out.println();
        if (!test.equals("100 55 160 25 75 110 190 5 30 60 85 140 170 0 15 40 70 80 95 120 150 180 10 20 35 50 65 90 130 45 ")) {
            System.out.println("\033[31mINCORRECT OUTPUT!\033[0m");
        }
    }
}
