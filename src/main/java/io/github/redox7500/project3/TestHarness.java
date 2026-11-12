package io.github.redox7500.project3;

import java.util.Random;

public class TestHarness {
    /** Hold the Random class object. */
    static private Random rndvalue = new Random();

    /**
     * Get a random integer (specifically for this test harness).
     * 
     * @param max the maximum value of the range of values that can be given
     * @param out the mutable object in which the random integer is stored
    */
    static void random(int max, int[] out) {
        out[0] = rndvalue.nextInt(max);
    }

    /**
     * Get a random character (specifically for this test harness).
     * 
     * @param out the mutable object in which the random character is stored
     */
    static void random(char[] out) {
        // 97 is the ascii value for a
        // and 122 is the ascii value for z
        out[0] = (char)rndvalue.nextInt(97, 123);
    }
    
    public static void main(String[] args) {
        BST<Integer, Integer> intBst = new BST<Integer, Integer>();
        int[] intTemp = new int[1];
        for (int i=1; i<=30; i++) {
            random(300, intTemp);
            intBst.insert(intTemp[0], intTemp[0]);
        }

        System.out.println("Tree: " + intBst);
        
        System.out.print("printHighToLow: ");
        intBst.printHighToLow();
        System.out.println();
        
        System.out.print("printRange: ");
        intBst.printRange(100, 150);
        System.out.println();

        System.out.print("sumNodes: ");
        System.out.println(sumNodes(intBst.root()));

        System.out.print("smallCount: ");
        System.out.println(smallCount(intBst.root(), 150));

        System.out.print("countNodes: ");
        System.out.println(intBst.countNodes());

        System.out.print("evenNodes: ");
        System.out.println(evenNodes(intBst.root()));

        System.out.print("sumChildrenOfEvens: ");
        System.out.println(sumChildrenOfEvens(intBst.root()));

        System.out.print("countLeaves: ");
        System.out.println(intBst.countLeaves());

        System.out.print("countParents: ");
        System.out.println(intBst.countParents());

        System.out.print("countRange: ");
        System.out.println(countRange(intBst.root(), 100, 150));

        System.out.print("printInLevel: ");
        intBst.printInLevel();

        System.out.println("\n");

        BST<Character, Character> charBst = new BST<>();
        char[] charTemp = new char[1];
        for (int i = 1; i <= 30; i++) {
            random(charTemp);
            charBst.insert(charTemp[0], charTemp[0]);
        }

        System.out.println("Tree: " + charBst);
        
        System.out.print("printHighToLow: ");
        charBst.printHighToLow();
        System.out.println();
        
        System.out.print("printRange: ");
        charBst.printRange((char)97, (char)109);
        System.out.println();

        System.out.print("smallCount: ");
        System.out.println(smallCount(charBst.root(), (char)109));

        System.out.print("countNodes: ");
        System.out.println(charBst.countNodes());

        System.out.print("countLeaves: ");
        System.out.println(charBst.countLeaves());

        System.out.print("countParents: ");
        System.out.println(charBst.countParents());

        System.out.print("countRange: ");
        System.out.println(countRange(charBst.root(), (char)97, (char)109));

        System.out.print("printInLevel: ");
        charBst.printInLevel();
    }

    /**
     * Returns the sum of the elements of all nodes in the tree given (of which this is the root).
     * 
     * @param root the root of the tree to sum the values of the nodes of
     * @return the sum of the elements of all nodes in the tree given
    */
    public static int sumNodes(BSTNode<?, Integer> root) {
        int toReturn = 0;
        if (root.element() != null) toReturn += root.element();
        if (root.left () != null) toReturn += sumNodes(root.left ());
        if (root.right() != null) toReturn += sumNodes(root.right());
        return toReturn;
    }
    
    public static <K extends Comparable<? super K>> int smallCount(BSTNode<K, ?> root, K key) {
        return root.smallCount(key);
    }

    /** 
     * Given a pointer to the root node of a BST storing integer elements in its nodes, returns the count of all the nodes with even elements in the tree.
     * 
     * @param root the root node of a BST storing integer elements in its nodes
     * @return the count of all the nodes with even elements in the tree
     */
    public static int evenNodes(BSTNode<?, Integer> root) {
        int toReturn = 0;
        if (root.element() % 2 == 0) toReturn++;
        if (root.left () != null) toReturn += evenNodes(root.left ());
        if (root.right() != null) toReturn += evenNodes(root.right());
        return toReturn;
    }

    /**
     * Given a pointer to the root node of a BST storing integer elements in its nodes, returns the sum of the elements of all direct children who have a parent with an element that is even.
     * 
     * @param root pointer to the root node of a BST storing integer elements in its ndoes
     * @return sum of the elements of all direct children who have a prent with an element that is even
     */
    public static int sumChildrenOfEvens(BSTNode<?, Integer> root) {
        int toReturn = 0;
        int multiplier = 1 - (root.element() % 2);
        if (root.left () != null) toReturn += multiplier * root.left ().element() + sumChildrenOfEvens(root.left ());
        if (root.right() != null) toReturn += multiplier * root.right().element() + sumChildrenOfEvens(root.right());
        return toReturn;
    }

    public static <K extends Comparable<? super K>> int countRange(BSTNode<K, ?> rt, K low, K high) {
        return rt.countRange(low, high);
    }
}
