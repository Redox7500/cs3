package io.github.redox7500.project2;

import java.util.HashMap;
import java.util.Map;

public class Project2HashMapWrapper<K extends Comparable<? super K>, V> extends Project2AbstractDictionary<K, V>
{
    Map<K, V> hashMap = new HashMap<>();

    // clear, insert, remove, removeAny, find, size, copy

    @Override
    public void clear() {this.hashMap.clear();}

    @Override
    public void insert(K key, V value) {this.hashMap.put(key, value);}

    @Override // i kinda wanna change these to not return null but error, that would allow for nulls and make people aware that it errored i think
    public V remove(K key) {return this.hashMap.remove(key);}

    @Override
    public V removeAny() {return (this.hashMap.size() > 0)? this.hashMap.remove(this.hashMap.keySet().iterator().next()) : null;}

    @Override
    public V find(K key) {return this.hashMap.get(key);}

    @Override
    public int size() {return this.hashMap.size();}

    @Override
    public Project2HashMapWrapper<K, V> copy()
    {
        Project2HashMapWrapper<K, V> toReturn = new Project2HashMapWrapper<>();

        toReturn.hashMap = new HashMap<>(this.hashMap); // fix later, this is shallow i think but it should be at least 2 layers deep, maybe infinitely deep if i feel extra cool

        return toReturn;
    }

    @Override
    public String toString() {return this.hashMap.toString();} // this does a slightly different formatting thing than what i do but it works i guess

    @Override
    @SuppressWarnings("unchecked") // idk if this is the right ordering of these two annotations
    public K[] getKeys() {return (K[])this.hashMap.keySet().toArray(new Comparable[0]);} // people say to not use this overload of toArray but i think it's fine specifically for here
}
