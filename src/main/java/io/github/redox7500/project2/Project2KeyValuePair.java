package io.github.redox7500.project2;

// Container for a key-value pair
public record Project2KeyValuePair<K, V>(K key, V value)
{
    public Project2KeyValuePair() {this(null, null);}

    @Override
    public String toString() {return this.key + ": " + this.value;}
}
