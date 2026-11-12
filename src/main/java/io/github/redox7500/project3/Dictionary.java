package io.github.redox7500.project3;

// maybe i should make all of the comments things like "adds x" instead of "add x"
// and should things after "Note: " be lowercase or capitalized, i wonder?
// also i have notes but then i put a little "or null blah blah blah" thing in the return, which might belong in the note instead
/** The Dictionary abstract class. */
public interface Dictionary<K, V> {
    /** Reinitialize this dictionary. */
    public void clear();

    /**
     * Add a key-value pair to this dictionary.
     * 
     * @param key   the key of the key-value pair to add
     * @param value the value of the key-value pair to add
     */
    public void insert(K key, V value);

    /**
     * Remove the key-value pair with the given key and get its value.
     * <p>
     * Note: If multiple pairs match the given key, any one of them could be chosen.
     * 
     * @param key the key of the key-value pair to be removed, paired with the returned value
     * @return the value associated with the key given, or {@code null} if the key is not found
     */
    public V remove(K key);

    /**
     * Remove any of this Dictionary's key-value pairs and get its value.
     * <p>
     * Note: If this dictionary is empty, nothing will be removed and {@code null} will be returned.
     * 
     * @return the value of the key-value pair removed
     */
    public V removeAny();

    /**
     * Get the value of the key-value pair with the given key.
     * <p>
     * Note: If multiple pairs match the given key, any one of them could be chosen.
     * 
     * @param key the key associated with the value to be returned
     * @return the value associated with the given key, or {@code null} if the key is not found
     */
    public V find(K key);

    /**
     * Get the size of this Dictionary.
     * 
     * @return the number of key-value pairs in this Dictionary
     */
    public int size();
};