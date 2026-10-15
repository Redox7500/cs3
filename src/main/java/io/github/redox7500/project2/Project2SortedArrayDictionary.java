package io.github.redox7500.project2;

import io.github.redox7500.project1.Project1ArrayList;

class Project2SortedArrayDictionary<K extends Comparable<? super K>, V> extends Project2AbstractDictionary<K, V>
{
    // Default size
    private static final int defaultSize = 10;

    // To store dictionary
    private Project1ArrayList<Project2KeyValuePair<K, V>> arrayList;

    public Project2SortedArrayDictionary() {this(defaultSize);}
    public Project2SortedArrayDictionary(int size) {this.arrayList = new Project1ArrayList<Project2KeyValuePair<K, V>>(size);}

    // Reinitialize
    @Override
    public void clear() {this.arrayList.clear();}

    /** Insert an element: append to list */
    @Override
    public void insert(K key, V value) {this.arrayList.append(new Project2KeyValuePair<K, V>(key, value));}

    /** Use sequential search to find the element to remove */
    @Override
    public V remove(K key)
    {
        V temp = find(key);
        if (temp != null) this.arrayList.remove();

        return temp;
    }

    /** Remove the last element */
    @Override
    public V removeAny()
    {
        if (size() == 0) return null;
        
        this.arrayList.moveToEnd();
        if (this.arrayList.length() > 1) this.arrayList.prev();
        Project2KeyValuePair<K, V> e = this.arrayList.remove();

        return e.value();
    }

    // Find "k" using sequential search
    @Override
    public V find(K key)
    {
        this.arrayList.moveToStart();
        int length = this.arrayList.length();
        for (int i = 0; i < length; i++)
        {
            Project2KeyValuePair<K, V> currentPair = this.arrayList.getValue();
            if (key == currentPair.key()) return currentPair.value();

            if (i == length - 1) break;

            this.arrayList.next();
        }

        return null; // "k" does not appear in dictionary
    }

    // Return list size
    @Override
    public int size() {return this.arrayList.length();}

    @Override
    public String toString()
    {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append('{');
        if (this.arrayList.size() > 0)
        {
            toReturn.append("\n");

            int currentIndex = this.arrayList.getCurrentIndex();
            this.arrayList.moveCurrentIndexToStart();

            for (int i = 1; i < this.arrayList.size(); i++)
            {
                toReturn.append(",\n");

                this.arrayList.moveCurrentIndexToNext();

                Project2KeyValuePair<K, V> currentPair = this.arrayList.getCurrentValue();
                toReturn.append(currentPair.key());
                toReturn.append(": ");
                toReturn.append(currentPair.value());
            }
            toReturn.append("\n");

            this.arrayList.moveCurrentIndexTo(currentIndex);
        }
        toReturn.append('}');

        return toReturn.toString();
    }
}
