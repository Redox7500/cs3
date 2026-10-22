package io.github.redox7500.project2;

import io.github.redox7500.project1.Project1TestHarness;

/** The Dictionary abstract class. */
public interface Project2Dictionary<K, V> extends Project1TestHarness.Testable<Project2Dictionary<K, V>>
{
    /** Reinitialize this dictionary */
    void clear();

    /**
     * Add a key-value pair to this dictionary
     * @param key The key of the key-value pair being added
     * @param value The value of the key-value pair being added
     */
    void insert(K key, V value);

    /**
     * Remove and return the value associated with the given key
     * <br>
     * Note: If multiple pairs match the given key, an arbitrary pair with the given key will be chosen
     * @param key The key of the key-value pair to be removed, paired with the returned value
     * @return The value associated with the key given, or {@code null} if the key is not found
     */
    V remove(K key);

    /**
     * Remove and return the value of an arbitrary key-value pair from this
     * <br>
     * Note: If this dictionary is empty, nothing will be removed and null will be returned
     * @return The value of the key-value pair removed
     */
    V removeAny();

    /** Get the value associated with the given key
     * <br>
     * Note: If multiple pairs match the given key, an arbitrary pair with the given key will be chosen
     * @param key The key associated with the value to be returned
     * @return The value associated with the given key, or {@code null} if the key is not found
     */
    V find(K key);

    /**
     * Get the size of this dictionary
     * @return the number of records in the dictionary.
     */
    int size();
};
