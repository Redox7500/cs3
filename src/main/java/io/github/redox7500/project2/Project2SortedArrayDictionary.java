package io.github.redox7500.project2;

import io.github.redox7500.project1.Project1ArrayList;

/** Sorted array-based dictionary implementation */
public class Project2SortedArrayDictionary<K extends Comparable<? super K>, V> extends Project2AbstractDictionary<K, V>
{
    /** The default maximum size of this dictionary implementation */
    private static final int DEFAULT_SIZE = 10;

    /** The array of key-value pairs backing this dictionary */
    private Project1ArrayList<Project2KeyValuePair<K, V>> arrayList; // do i even need to keep on moving the index of this arraylist back to where it was? i don't think so, probably i don't have to

    public Project2SortedArrayDictionary() {this(DEFAULT_SIZE);}

    public Project2SortedArrayDictionary(int size) {this.arrayList = new Project1ArrayList<Project2KeyValuePair<K, V>>(size);}

    @Override
    public void clear() {this.arrayList.clear();}
    
    @Override
    public void insert(K key, V value)
    {
        Project2KeyValuePair<K, V> newPair = new Project2KeyValuePair<>(key, value);

        int index = this.closestIndexOf(key);
        if (index < this.size())
        {
            this.arrayList.moveCurrentIndexTo(index);
            Project2KeyValuePair<K, V> currentPair = this.arrayList.getCurrentValue();
            if (currentPair.key == key) currentPair.value = value;
            else this.arrayList.insert(newPair);
        }
        else
        {
            this.arrayList.append(newPair);
        }
    }
    
    @Override
    public V remove(K key)
    {
        // try
        // {
        int index = this.closestIndexOf(key);
        // System.out.println(index);
        if (index < this.size())
        {
            this.arrayList.moveCurrentIndexTo(index);
            if (this.arrayList.getCurrentValue().key == key) return this.arrayList.remove().value;
        }
        // }
        // catch (Throwable error)
        // {
        //     error.printStackTrace();
        // }
        return null;
    }
    
    @Override
    public V removeAny()
    {
        if (size() == 0) return null;
        
        this.arrayList.moveToEnd();
        if (this.arrayList.length() > 1) this.arrayList.prev();
        Project2KeyValuePair<K, V> e = this.arrayList.remove();

        return e.value;
    }
    
    @Override
    public V find(K key)
    {
        int index = this.closestIndexOf(key);
        if (index < this.size())
        {
            this.arrayList.moveCurrentIndexTo(index);
            Project2KeyValuePair<K, V> pair = this.arrayList.getCurrentValue();
            if (pair.key == key) return pair.value;
        }
        return null;
    }
    
    @Override
    public int size() {return this.arrayList.length();}

    @Override
    public String toString() // could make this print single line i guess but i'm not sure how, maybe a static boolean for this class?
    {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append('{');
        if (this.arrayList.size() > 0)
        {
            toReturn.append("\n\t");

            int currentIndex = this.arrayList.getCurrentIndex();
            this.arrayList.moveCurrentIndexToStart();

            toReturn.append(this.arrayList.getCurrentValue());

            for (int i = 1; i < this.arrayList.size(); i++)
            {
                toReturn.append(",\n\t");

                this.arrayList.moveCurrentIndexToNext();

                toReturn.append(this.arrayList.getCurrentValue());
            }
            toReturn.append("\n");

            this.arrayList.moveCurrentIndexTo(currentIndex);
        }
        toReturn.append('}');

        return toReturn.toString();
    }

    private int closestIndexOf(K key)
    {
        int low = 0;
        int high = this.arrayList.size() - 1;
        int mid;
        while (low <= high)
        {
            mid = (low + high) / 2;
            this.arrayList.moveCurrentIndexTo(mid);
            int comparison = this.arrayList.getCurrentValue().key.compareTo(key);
            if (comparison < 0) low = mid + 1;
            else if (comparison > 0) high = mid - 1;
            else {return mid;}
        }
        return low;
    }

    @Override
    public Project2SortedArrayDictionary<K, V> copy()
    {
        Project2SortedArrayDictionary<K, V> toReturn = new Project2SortedArrayDictionary<>();

        toReturn.arrayList = this.arrayList.copy(); // elements not copied but i'll fix that later

        return toReturn;
    }

    @Override
    public K[] getKeys()
    {
        int size = this.arrayList.size();

        @SuppressWarnings("unchecked") // As Schaffer would say, "For generic array"
        K[] toReturn = (K[])new Comparable[size];
        
        this.arrayList.moveCurrentIndexToStart();
        for (int i = 0; ; i++)
        {
            toReturn[i] = this.arrayList.getCurrentValue().key;

            if (i == size - 1) break;

            this.arrayList.moveCurrentIndexToNext();
        }

        return toReturn;
    }
}
