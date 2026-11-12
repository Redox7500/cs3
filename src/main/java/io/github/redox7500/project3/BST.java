package io.github.redox7500.project3;

import java.util.ArrayDeque;

// there are a bunch of methods that should be static or just removed completely, all the help stuff
/**
 * Binary Search Tree implementation for Dictionary ADT. 
 * 
 * @param <K> the type of this BST's keys
 * @param <V> the type of this BST's values
 */
class BST<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    /** Root of this BST. */
    private BSTNode<K, V> root;

    /** Number of nodes in this BST. */
    int nodecount;

    /** Initializes a BST with no nodes. */
    BST() {
        // Intentionally left blank
    }
    
    public void clear() {
        this.root = null;
        this.nodecount = 0;
    }

    public void insert(K key, V value) {
        this.root = inserthelp(this.root, key, value);
        this.nodecount++;
    }
    
    public V remove(K key) {
        V tempNode = findhelp(this.root, key); // First find it
        if (tempNode != null) {
            this.root = removehelp(this.root, key); // Now remove it
            this.nodecount--;
        }
        return tempNode;
    }
    
    public V removeAny() {
        if (this.root == null) {
            return null;
        }
        V temp = this.root.element();
        this.root = removehelp(this.root, this.root.key());
        this.nodecount--;
        return temp;
    }
    
    public V find(K key) {
        return findhelp(this.root, key);
    }
    
    public int size() {
        return this.nodecount;
    }
    
    /**
     * Get the root node of this tree.
     * 
     * @return the root node of this tree
     */
    public BSTNode<K, V> root() {
        return this.root;
    }

    /**
     * Get the value of the node in the given tree with the given key
     * 
     * @param root the root of the tree to find the node with the given key in
     * @param key  the key of the node to get the value of
     * @return the value of the node in the given tree with the given key, or {@code null} if it couldn't be found
     */
    private V findhelp(BSTNode<K, V> root, K key) {
        if (root == null) {
            return null;
        }
        if (root.key().compareTo(key) > 0) {
            return findhelp(root.left(), key);
        } else if (root.key().compareTo(key) == 0) {
            return root.element();
        } else {
            return findhelp(root.right(), key);
        }
    }

    /**
     * Get the current subtree modified to contain the new node containing the key {@code key} and the value {@code value}.
     * 
     * @param root  the subtree to insert the new node containing the key {@code key} and the value {@code value} into
     * @param key   the key of the new node to insert
     * @param value the value of the new node to insert
     * @return the current subtree modified to contain the new node containing the key {@code key} and the value {@code value}
     */
    private BSTNode<K, V> inserthelp(BSTNode<K, V> root, K key, V value) {
        if (root == null) {
            return new BSTNode<K, V>(key, value);
        }
        if (root.key().compareTo(key) > 0) {
            root.setLeft(inserthelp(root.left(), key, value));
        } else {
            root.setRight(inserthelp(root.right(), key, value));
        }
        return root;
    }

    /**
     * Remove a node with the key value given of the tree given.
     * 
     * @param root the root of the tree to remove from
     * @param key  the key of the node to be removed
     * @return the given tree with a node with the key given removed
     */
    private BSTNode<K, V> removehelp(BSTNode<K, V> root, K key) {
        if (root == null) {
            return null;
        }
        if (root.key().compareTo(key) > 0) {
            root.setLeft(removehelp(root.left(), key));
        } else if (root.key().compareTo(key) < 0) {
            root.setRight(removehelp(root.right(), key));
        } else {
            // Found it
            if (root.left() == null) {
                return root.right();
            } else if (root.right() == null) {
                return root.left();
            } else {
                // Two children
                BSTNode<K, V> temp = getmin(root.right());
                root.setElement(temp.element());
                root.setKey(temp.key());
                root.setRight(deletemin(root.right()));
            }
        }
        return root;
    }

    /**
     * Get the node with the least value in the given tree.
     * 
     * @param root the root of the tree to get the node with the least value of
     * @return the node with the least value in the given tree
     */
    private BSTNode<K, V> getmin(BSTNode<K, V> root) {
        if (root.left() == null) {
            return root;
        }
        return getmin(root.left());
    }

    /**
     * Get the tree given with its node with the minimum value removed.
     * 
     * @param root the root of the tree to delete the node with the minimum value from
     * @return the tree given, with the node with the minimum value removed
     */
    private BSTNode<K, V> deletemin(BSTNode<K, V> root) {
        if (root.left() == null) {
            return root.right();
        }
        root.setLeft(deletemin(root.left()));
        return root;
    }

    /** Schaffer is a very strange person. */
    private StringBuffer out;
    
    public String toString() {
        out = new StringBuffer(100);
        printhelp(root);
        return out.toString();
    }

    /**
     * Print all of the nodes of the tree given.
     * 
     * @param root the root of the tree to print
     */
    private void printhelp(BSTNode<K, V> root) {
        if (root == null) {
            return;
        }
        printhelp(root.left());
        printVisit(root.element());
        printhelp(root.right());
    }

    /** I genuinely have no idea why Schaffer is doing this. */
    private void printVisit(V it) {
        out.append(it + " ");
    }
    
    public void printHighToLow() {
        this.root.printHighToLow();
    }

    public int countNodes() {
        return this.root.countNodes();
    }

    public int countLeaves() {
        return this.root.countLeaves();
    }

    public int countParents() {
        return this.root.countParents();
    }

    // i should verify this one better
    /** Prints the node elements of the tree in-level order (prints the root node, then all the nodes in level 1 left-to-right, then all the nodes in level 2 left-to-right, and so on) */
    public void printInLevel() {
        StringBuilder toPrint = new StringBuilder();
        ArrayDeque<BSTNode<K, V>> layer = new ArrayDeque<>();
        layer.add(this.root);
        BSTNode<K, V> node;
        while ((node = layer.poll()) != null) {
            toPrint.append(node.element());
            toPrint.append(' ');
            if (node.left() != null) layer.add(node.left());
            if (node.right() != null) layer.add(node.right());
        }
        System.out.println(toPrint);
    }

    public void printRange(K low, K high) {
        this.root.printRange(low, high);
    }
}
