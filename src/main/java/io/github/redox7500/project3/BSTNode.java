/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.redox7500.project3;

/**
 * Binary tree node implementation: Pointers to children
 *
 * @param E The data element
 * @param Key The associated key for the record
 */
class BSTNode<Key, E> implements BinNode<E> {

    private Key key;              // Key for this node
    private E element;            // Element for this node
    private BSTNode<Key, E> left;  // Pointer to left child
    private BSTNode<Key, E> right; // Pointer to right child

    /**
     * Constructors
     */
    public BSTNode() {
        left = right = null;
    }

    public BSTNode(Key k, E val) {
        left = right = null;
        key = k;
        element = val;
    }

    public BSTNode(Key k, E val, BSTNode<Key, E> l, BSTNode<Key, E> r) {
        left = l;
        right = r;
        key = k;
        element = val;
    }

    /**
     * Get and set the key value
     */
    public Key key() {
        return key;
    }

    public void setKey(Key k) {
        key = k;
    }

    /**
     * Get and set the element value
     */
    public E element() {
        return element;
    }

    public void setElement(E v) {
        element = v;
    }

    /**
     * Get and set the left child
     */
    public BSTNode<Key, E> left() {
        return left;
    }

    public void setLeft(BSTNode<Key, E> p) {
        left = p;
    }

    /**
     * Get and set the right child
     */
    public BSTNode<Key, E> right() {
        return right;
    }

    public void setRight(BSTNode<Key, E> p) {
        right = p;
    }

    /**
     * @return True if a leaf node, false otherwise
     */
    public boolean isLeaf() {
        return (left == null) && (right == null);
    }

    /** Prints out the node keys of the BST (of which this is the root) in reverse sorted order from highest to lowest */
    public void printHighToLow() {
        if (this.right != null) this.right.printHighToLow();
        System.out.print(this.key + " ");
        if (this.left != null) this.left.printHighToLow();
    }

    /**
     * Given a low key integer value and a high key integer value, prints in sorted order all records including and beneath this node whose key values fall between the two given keys, inclusive
     * @param low The lower bound for what keys to print
     * @param high The upper bound for what keys to print
     */
    public void printRange(int low, int high) {
        // maybe supposed to be non inclusive like < or > but i forgot if the insert does that or not
        // System.out.println(this.left != null && (Integer)this.left.key >= low);
        // System.out.println(this.right != null && (Integer)this.right.key <= high);
        if (this.left  != null && (Integer)this.key  >= low)  this.left .printRange(low, high);
        if (low <= (Integer)this.key && high >= (Integer)this.key) System.out.print((Integer)this.key + " ");
        if (this.right != null && (Integer)this.key <= high) this.right.printRange(low, high);
    }

    /** Returns the sum of the elements of all nodes in the tree (of which this is the root)
     * @return The sum of the elements of all nodes in the tree
    */
    public int sumNodes() {
        int toReturn = 0;
        if (this.element != null) toReturn += (Integer)this.element;
        if (this.left  != null) toReturn += this.left .sumNodes();
        if (this.right != null) toReturn += this.right.sumNodes();
        return toReturn;
    }

    /** Given a key value K, returns the number of nodes beneath and including this having key values less than or equal to K
     * @param K Key value to return the number of nodes having key values less than or equal to
     * @return The number of nodes having key values less than or equal to K
     */
    public int smallCount(Integer K) {
        int toReturn = 0;
        if ((Integer)this.key <= K) {
            toReturn++;
            if (this.right != null) toReturn += this.right.smallCount(K);
        }
        if (this.left != null) toReturn += this.left.smallCount(K);
        return toReturn;
    }
}
