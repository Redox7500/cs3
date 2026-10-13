package io.github.redox7500.project2;

class MySortedArrayDictionary<K extends Comparable<? super K>, V> implements MyDictionary<K, V>
{
    private static final int defaultSize = 10; // Default size
    private AList<KVpair<K, V>> list; // To store dictionary

    MySortedArrayDictionary() {this(defaultSize);}
    MySortedArrayDictionary(int size) {list = new AList<KVpair<K, V>>(sz);}

    public void clear() {list.clear();} // Reinitialize

    /** Insert an element: append to list */
    public void insert(K k, V e)
    {
        KVpair<K, V> temp = new KVpair<K, V>(k, e);
        list.append(temp);
    }

    /** Use sequential search to find the element to remove */
    public V remove(K k)
    {
        V temp = find(k);
        if (temp != null) list.remove();
        return temp;
    }

    /** Remove the last element */
    public V removeAny()
    {
        if (size() == 0) return null;
        
        list.moveToEnd();
        list.prev();
        KVpair<K, V> e = list.remove();
        return e.value();
    }

    // Find "k" using sequential search
    public V find(K k)
    {
        for(list.moveToStart(); list.currPos() < list.length(); list.next())
        {
            KVpair<K, V> temp = list.getValue();
            if (k == temp.key())
            return temp.value();
        }

        return null; // "k" does not appear in dictionary
    }

    // Return list size
    public int size() {return list.length();}
}