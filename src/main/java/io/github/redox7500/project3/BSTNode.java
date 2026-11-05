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

    public void printHighToLow() {
        if (this.right != null) this.right.printHighToLow();
        System.out.print(this.key + " ");
        if (this.left != null) this.left.printHighToLow();
    }

    // fix
    public void printRange(int low, int high) {
        // maybe supposed to be non inclusive like < or > but i forgot if the insert does that or not
        if (this.left != null && (Integer)this.left.key >= low) this.left.printRange(low, high);
        if (low <= (Integer)this.key && high >= (Integer)this.key) System.out.print(this.key + " ");
        if (this.right != null && (Integer)this.right.key <= high) this.right.printRange(low, high);
    }

    public int sumNodes() {
        int toReturn = 0;
        if (this.element != null) toReturn += (Integer)this.element;
        if (this.left  != null) toReturn += this.left .sumNodes();
        if (this.right != null) toReturn += this.right.sumNodes();
        return toReturn;
    }

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
