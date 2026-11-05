/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.redox7500.project3;

/**
 * The Dictionary abstract class.
 */
public interface Dictionary<Key, E> {

    /**
     * Reinitialize dictionary
     */
    public void clear();

    /**
     * Insert a record
     *
     * @param k The key for the record being inserted.
     * @param e The record being inserted.
     */
    public void insert(Key k, E e);

    /**
     * Remove and return a record.
     *
     * @param k The key of the record to be removed.
     * @return A matching record. If multiple records match "k", remove an
     * arbitrary one. Return null if no record with key "k" exists.
     */
    public E remove(Key k);

    /**
     * Remove and return an arbitrary record from dictionary.
     *
     * @return the record removed, or null if none exists.
     */
    public E removeAny();

    /**
     * @return A record matching "k" (null if none exists). If multiple records
     * match, return an arbitrary one.
     * @param k The key of the record to find
     */
    public E find(Key k);

    /**
     * @return The number of records in the dictionary.
     */
    public int size();
};