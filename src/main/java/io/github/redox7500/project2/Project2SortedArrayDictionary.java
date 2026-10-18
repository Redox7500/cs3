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
    public void insert(K key, V value)
    {
        // Project2KeyValuePair<K, V> newPair = new Project2KeyValuePair<K, V>(key, value);

        // if (this.size() == 0) {this.arrayList.append(newPair); return;}

        // int low = 0;
        // int high = this.arrayList.size() - 1;
        // int mid;
        // while (low <= high)
        // {
        //     mid = (low + high) / 2;
        //     this.arrayList.moveCurrentIndexTo(mid);
        //     int comparison = this.arrayList.getCurrentValue().key().compareTo(key);
        //     if (comparison < 0) low = mid + 1;
        //     else if (comparison > 0) high = mid - 1;
        //     else {this.arrayList.setCurrentValue(newPair); return;} // setting like this is worse than setting just the value and keeping the old pair, but it's a record so idk // can break i guess
        // }
        
        // if (low < this.arrayList.size())
        // {
        //     this.arrayList.moveCurrentIndexTo(low);
        //     this.arrayList.insert(newPair);
        // }
        // else
        // {
        //     // System.out.println(key);
        //     this.arrayList.append(newPair);
        // }

        Project2KeyValuePair<K, V> newPair = new Project2KeyValuePair<>(key, value);

        int index = this.closestIndexOf(key);
        if (index < this.size())
        {
            this.arrayList.moveCurrentIndexTo(index);
            if (this.arrayList.getCurrentValue().key() == key) this.arrayList.setCurrentValue(newPair);
            else this.arrayList.insert(newPair);
        }
        else
        {
            this.arrayList.append(newPair);
        }
    }

    /** Use sequential search to find the element to remove */
    @Override
    public V remove(K key)
    {
        // V temp = find(key);
        // if (temp != null) this.arrayList.remove();

        // return temp;
        // now that i think about it the code above doesn't work with nulls, very terrible
        int index = this.closestIndexOf(key);
        if (index < this.size())
        {
            this.arrayList.moveCurrentIndexTo(index);
            if (this.arrayList.getCurrentValue().key() == key) return this.arrayList.remove().value();
        }
        return null;
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
        // this.arrayList.moveToStart();
        // int length = this.arrayList.length();
        // for (int i = 0; i < length; i++)
        // {
        //     Project2KeyValuePair<K, V> currentPair = this.arrayList.getValue();
        //     if (key == currentPair.key()) return currentPair.value();

        //     if (i == length - 1) break;

        //     this.arrayList.next();
        // }

        // return null; // "k" does not appear in dictionary

        int index = this.closestIndexOf(key);
        if (index < this.size())
        {
            this.arrayList.moveCurrentIndexTo(index);
            Project2KeyValuePair<K, V> pair = this.arrayList.getCurrentValue();
            if (pair.key() == key) return pair.value();
        }
        return null;
    }

    // Return list size
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

                // Project2KeyValuePair<K, V> currentPair = this.arrayList.getCurrentValue();
                toReturn.append(this.arrayList.getCurrentValue());
                // toReturn.append(currentPair.key());
                // System.out.println(currentPair.key());
                // System.out.println(currentPair.value());
                // toReturn.append(": ");
                // toReturn.append(currentPair.value());
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
            int comparison = this.arrayList.getCurrentValue().key().compareTo(key);
            if (comparison < 0) low = mid + 1;
            else if (comparison > 0) high = mid - 1;
            else {return mid;} // setting like this is worse than setting just the value and keeping the old pair, but it's a record so idk // can break i guess
        }
        return low;
    }
}
