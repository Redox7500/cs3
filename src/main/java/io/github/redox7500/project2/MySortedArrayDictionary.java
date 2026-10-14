package io.github.redox7500.project2;

class MySortedArrayDictionary<K extends Comparable<? super K>, V> implements MyDictionary<K, V>
{
    // Default size
    private static final int defaultSize = 10;

    // To store dictionary
    private AList<KeyValuePair<K, V>> arrayList;

    MySortedArrayDictionary() {this(defaultSize);}
    MySortedArrayDictionary(int size) {this.arrayList = new AList<KeyValuePair<K, V>>(size);}

    // Reinitialize
    public void clear() {this.arrayList.clear();}

    /** Insert an element: append to list */
    public void insert(K key, V value) {this.arrayList.append(new KeyValuePair<K, V>(key, value));}

    /** Use sequential search to find the element to remove */
    public V remove(K key)
    {
        V temp = find(key);
        if (temp != null) this.arrayList.remove();

        return temp;
    }

    /** Remove the last element */
    public V removeAny()
    {
        if (size() == 0) return null;
        
        this.arrayList.moveToEnd();
        if (this.arrayList.length() > 1) this.arrayList.prev();
        KeyValuePair<K, V> e = this.arrayList.remove();

        return e.value();
    }

    // Find "k" using sequential search
    public V find(K key)
    {
        this.arrayList.moveToStart();
        int length = this.arrayList.length();
        for (int i = 0; i < length; i++)
        {
            KeyValuePair<K, V> currentPair = this.arrayList.getValue();
            if (key == currentPair.key()) return currentPair.value();

            if (i == length - 1) break;

            this.arrayList.next();
        }

        return null; // "k" does not appear in dictionary
    }

    // Return list size
    public int size() {return this.arrayList.length();}
}
