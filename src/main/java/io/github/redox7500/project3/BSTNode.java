package io.github.redox7500.project3;

// what on earth is schaffer's description for bstnode here
// like yes of course it has pointers to children we know
/**
 * Binary tree node implementation: Pointers to children.
 * 
 * @param <K> the type of this record's key
 * @param <V> the type of this record's value
 */
class BSTNode<K extends Comparable<? super K>, V> implements BinNode<V> {
    /** This node's key. */
    private K key;

    /** This node's value. */
    private V value;

    /** This node's left child */
    private BSTNode<K, V> left;

    /** This node's right child. */
    private BSTNode<K, V> right;
    
    /** Initializes a BSTNode with its key, value, and children all {@code null}. */
    public BSTNode() {
        // Intentionally left empty
    }

    /**
     * Initializes a BSTNode with the given key and value.
     * 
     * @param key   the key to initialize the BSTNode with
     * @param value the value to initialize the BSTNode with
     */
    public BSTNode(K key, V value) {
        this.left = this.right = null;
        this.key = key;
        this.value = value;
    }

    /**
     * Initializes a BSTNode with the given key, value, and left and right children.
     * 
     * @param key   the key value to initialize the BSTNode with
     * @param value the value to initialize the BSTNode with
     * @param left  the left child to initialize the BSTNode with
     * @param right the right child to initialize the BSTNode with
     */
    public BSTNode(K key, V value, BSTNode<K, V> left, BSTNode<K, V> right) {
        this.left = left;
        this.right = right;
        this.key = key;
        this.value = value;
    }

    /**
     * Get this node's key.
     * 
     * @return this node's key
     */
    public K key() {
        return this.key;
    }

    // there has to be a better way to phrase this, other than saying "the value"
    /**
     * Set this node's key.
     * 
     * @param key to set this node's key to
     */
    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public V element() {
        return this.value;
    }

    @Override
    public void setElement(V value) {
        this.value = value;
    }

    @Override
    public BSTNode<K, V> left() {
        return this.left;
    }

    /**
     * Set this node's left child.
     * 
     * @param left to set this node's left child to
     */
    public void setLeft(BSTNode<K, V> left) {
        this.left = left;
    }

    @Override
    public BSTNode<K, V> right() {
        return this.right;
    }

    /**
     * Set this node's right child.
     * 
     * @param right to set this node's right child to
     */
    public void setRight(BSTNode<K, V> right) {
        this.right = right;
    }

    /**
     * Get whether or not this node is a leaf node.
     * 
     * @return {@code true} if this node has only null children, and {@code false} otherwise
     */
    public boolean isLeaf() {
        return (left == null) && (right == null);
    }

    /** Prints out the node keys of the BST (of which this is the root) in reverse sorted order from highest to lowest. */
    public void printHighToLow() {
        if (this.right != null) this.right.printHighToLow();
        System.out.print(this.key + " ");
        if (this.left  != null) this.left .printHighToLow();
    }

    /**
     * Given a low key integer value and a high key integer value, prints in sorted order all records including and beneath this node whose key values fall between the two given keys, inclusive.
     * 
     * @param low  the lower bound for what keys to print
     * @param high the upper bound for what keys to print
     */
    public void printRange(K low, K high) {
        // maybe supposed to be non inclusive like < or > but i forgot if the insert does that or not
        // System.out.println(this.left != null && (Integer)this.left.key >= low);
        // System.out.println(this.right != null && (Integer)this.right.key <= high);
        if (this.left  != null && this.key.compareTo(low)  >= 0) this.left .printRange(low, high);
        if (this.key.compareTo(low) >= 0 && this.key.compareTo(high) <= 0) System.out.print(this.key + " ");
        if (this.right != null && this.key.compareTo(high) <= 0) this.right.printRange(low, high);
    }

    /**
     * Given a key value {@code key}, returns the number of nodes beneath and including this having key values less than or equal to {@code key}.
     * 
     * @param key the key value to return the number of nodes having key values less than or equal to
     * @return the number of nodes having key values less than or equal to {@code key}
     */
    public int smallCount(K key) {
        int toReturn = 0;
        if (this.key.compareTo(key) <= 0) {
            toReturn++;
            if (this.right != null) toReturn += this.right.smallCount(key);
        }
        if (this.left != null) toReturn += this.left.smallCount(key);
        return toReturn;
    }

    /**
     * Returns the count of all the nodes in the tree (of which this is the root).
     * 
     * @return the count of all the nodes in the tree (of which this is the root)
     */
    public int countNodes() {
        int toReturn = 1;
        if (this.left  != null) toReturn += this.left .countNodes();
        if (this.right != null) toReturn += this.right.countNodes();
        return toReturn;
    }

    /**
     * Returns the count of all the leaves (nodes with only null children) in the tree (of which this is the root).
     * 
     * @return the count of all the leaves (nodes with only null children) in the tree (of which this is the root)
     */
    public int countLeaves() {
        int toReturn = 0;
        if (this.isLeaf()) toReturn++;
        if (this.left  != null) toReturn += this.left .countLeaves();
        if (this.right != null) toReturn += this.right.countLeaves();
        return toReturn;
    }

    /**
     * Returns the count of all the parents (nodes with at least one non-null child) in the tree (of which this is the root).
     * 
     * @return the count of all the parents (nodes with at least one non-null child) in the tree (of which this is the root)
     */
    public int countParents() {
        // this feels like it's getting repetitive
        // also this specific method feels inefficient for some reason
        int toReturn = 0;
        if (!this.isLeaf()) toReturn++;
        if (this.left  != null) toReturn += this.left .countParents();
        if (this.right != null) toReturn += this.right.countParents();
        return toReturn;
    }

    /** 
     * Given a pointer to the root node of a BST storing integer values in its nodes and two key values low and high, returns the number of nodes having key values >= low and <= high.
     * 
     * @param low  the lower bound of nodes to count
     * @param high the upper bound of nodes to count
     * @return the number of nodes having key values within low and high, inclusive
     */
    public int countRange(K low, K high) {
        int toReturn = 0;
        if (this.key.compareTo(low) >= 0 && this.key.compareTo(high) <= 0) toReturn++;
        if (this.left  != null && this.key.compareTo(low)  >= 0) toReturn += this.left .countRange(low, high);
        if (this.right != null && this.key.compareTo(high) <= 0) toReturn += this.right.countRange(low, high);
        return toReturn;
    }
}
