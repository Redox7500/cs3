/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.redox7500.project3;

/**
 * ADT for binary tree nodes
 */
public interface BinNode<E> {

    /**
     * Get and set the element value
     */
    public E element();

    public void setElement(E v);

    /**
     * @return The left child
     */
    public BinNode<E> left();

    /**
     * @return The right child
     */
    public BinNode<E> right();

    /**
     * @return True if a leaf node, false otherwise
     */
    public boolean isLeaf();
}
