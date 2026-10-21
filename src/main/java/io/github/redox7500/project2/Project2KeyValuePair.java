package io.github.redox7500.project2;

/** Container for a key-value pair, specifically for dictionaries */
public class Project2KeyValuePair<K, V>
{
    /** The key of this key-value pair */
    final K key;
    
    /** The value of this key-value pair */
    V value; // could make this V value = null;

    public Project2KeyValuePair()
    {
        this.key = null;
        this.value = null;
    }

    public Project2KeyValuePair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {return this.key + ": " + this.value;}
}
