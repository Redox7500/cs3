package io.github.redox7500.project2;

// Container for a key-value pair
public record MyKeyValuePair<K, V>(K key, V value)
{
    public MyKeyValuePair() {this(null, null);}

    @Override
    public String toString() {return this.key + ": " + this.value;}
}
