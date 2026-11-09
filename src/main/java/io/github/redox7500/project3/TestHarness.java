/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.redox7500.project3;

import java.util.*;

public class TestHarness {
    static private Random rndvalue = new Random(); // Hold the Random class object
    // static int randomInt(int n) {
    //     return Math.abs(rndvalue.nextInt()) % n;
    // }
    // static void random(int low, int high, Object out) {
    //     switch (out) {
    //         case int [] i -> i[0] = rndvalue.nextInt(low, high);
    //         case char[] c -> c[0] = (char)rndvalue.nextInt(low, high);
    //         default -> System.out.println("a");
    //     }
    // }

    static void random(int n, int[] out) {
        out[0] = rndvalue.nextInt(n);
    }

    static void random(char[] out) {
        // 97 is the ascii value for a
        // and 122 is the ascii value for z
        out[0] = (char)rndvalue.nextInt(97, 123);
    }
    
    public static void main(String[] args) {
        // BST<Integer, Integer> bst = new BST<Integer, Integer>();
        // int temp;
        // for (int i=1; i<=30; i++) {
        //     temp = randomInt(300);
        //     bst.insert(temp, temp);
        // }
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

        System.out.print("countLeaves: ");
        System.out.println(intBst.countLeaves());

        System.out.print("countParents: ");
        System.out.println(intBst.countParents());

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
        System.out.println(charBst.root().smallCount((char)150));

        System.out.print("countNodes: ");
        System.out.println(charBst.countNodes());

        System.out.print("countLeaves: ");
        System.out.println(charBst.countLeaves());

        System.out.print("countParents: ");
        System.out.println(charBst.countParents());

        System.out.print("printInLevel: ");
        charBst.printInLevel();
    }
    
    // public static void printRange(BSTNode<Integer, Integer> rt, int low, int high) {
    //     rt.printRange(low, high);
    // }

    /**
     * Returns the sum of the elements of all nodes in the tree (of which this is the root)
     * @return The sum of the elements of all nodes in the tree
    */
    public static int sumNodes(BSTNode<Integer, Integer> rt) {
        int toReturn = 0;
        if (rt.element() != null) toReturn += rt.element();
        if (rt.left () != null) toReturn += sumNodes(rt.left ());
        if (rt.right() != null) toReturn += sumNodes(rt.right());
        return toReturn;
    }

    public static int smallCount(BSTNode<Integer, Integer> rt, Integer K) {
        return rt.smallCount(K);
    }

    /** 
     * Given a pointer to the root node of a BST storing integer elements in its nodes, returns the count of all the nodes with even elements in the tree
     * @param rt The root node of a BST storing integer elements in its nodes
     * @return The count of all the nodes with even elements in the tree
     */
    public static int evenNodes(BSTNode<?, Integer> rt) {
        int toReturn = 0;
        if (rt.element() % 2 == 0) toReturn++;
        if (rt.left () != null) toReturn += evenNodes(rt.left ());
        if (rt.right() != null) toReturn += evenNodes(rt.right());
        return toReturn;
    }
}
