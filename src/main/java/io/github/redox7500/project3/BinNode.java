package io.github.redox7500.project3;

// some things in BSTNode can go here i think, such as isLeaf or whatever that's called
/**
 * ADT for binary tree nodes.
 * <p>
 * Note: a node is considered a leaf node if both of its children are null.
 * 
 * @param <E> the type of value this node contains
 */
public interface BinNode<E> {

    /**
     * Get this node's value.
     * 
     * @return this node's value
     */
    E element();

    /**
     * Set this node's value.
     * 
     * @param element to set this node's value to
     */
    void setElement(E element);

    /**
     * Get this node's left child.
     * 
     * @return this node's left child
     */
    BinNode<E> left();

    /**
     * Get this node's right child.
     * 
     * @return this node's right child
     */
    BinNode<E> right();

    /**
     * Get whether or not this node is a leaf node. A leaf node's only requirement is to have {@code null} for both of its children.
     * 
     * @return {@code true} if this is a leaf node, and {@code false} otherwise
     */
    boolean isLeaf();
}
