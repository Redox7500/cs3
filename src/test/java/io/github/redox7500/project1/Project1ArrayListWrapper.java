package io.github.redox7500.project1;

import java.util.ArrayList;

public class Project1ArrayListWrapper<E> extends Project1AbstractList<E>
{
    ArrayList<E> arrayList = new ArrayList<>();
    int currentIndex = 0;

    Project1ArrayListWrapper() {super();}

    @Override
    public void clear() {this.arrayList.clear(); this.currentIndex = 0;}

    @Override
    public void insert(E value) {if (this.size() != 0) this.arrayList.add(this.currentIndex, value); else this.append(value);}

    @Override
    public void append(E value) {this.arrayList.add(value);}

    @Override
    public E remove() {E toReturn = this.arrayList.remove(this.currentIndex); if (this.currentIndex == this.size() && this.currentIndex != 0) this.currentIndex--; return toReturn;}

    @Override
    public E getCurrentValue() {return this.arrayList.get(this.currentIndex);}

    @Override
    public void setCurrentValue(E value) {if (this.size() != 0) this.arrayList.set(this.currentIndex, value); else this.append(value);}

    @Override
    public int getCurrentIndex() {return this.currentIndex;}

    @Override
    public void moveCurrentIndexTo(int index) {assert (this.size() > 0)? index >= 0 && index < this.size() : index == 0 : "Index out of range"; this.currentIndex = index;}

    @Override
    public int size() {return this.arrayList.size();}

    @Override
    public Project1ArrayListWrapper<E> copy()
    {
        Project1ArrayListWrapper<E> toReturn = new Project1ArrayListWrapper<>();
        if (this.size() != 0) toReturn.arrayList = new ArrayList<>(this.arrayList); // i know this makes an extra array list shut up i'm lazy
        toReturn.currentIndex = this.currentIndex;

        return toReturn;
    }
}