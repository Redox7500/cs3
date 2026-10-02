package project1lists;

/** All of the default implementations of the CustomList required methods except for Shaffer's aliases */
public abstract class DefaultCustomListImplementations<E, T extends DefaultCustomListImplementations<E, T>> implements CustomList<E, T>
{
    @Override
    public void clear()
    {
        this.moveCurrentIndexToStart();
        for (int i = 0; i < this.size(); i++) this.remove();
    }
    
    @Override
    public abstract void insert(E value);
    
    @Override
    public abstract void append(E value);

    @Override
    public abstract E remove();

    @Override
    public abstract E getCurrentValue();

    @Override
    public abstract void setCurrentValue(E value);
    
    @Override
    public abstract int getCurrentIndex();
    
    @Override
    public abstract void moveCurrentIndexTo(int index);
    
    @Override
    public void moveCurrentIndexToStart() {this.moveCurrentIndexTo(0);}
    
    @Override
    public void moveCurrentIndexToEnd() {this.moveCurrentIndexTo(Math.max(this.size() - 1, 0));}
    
    @Override
    public void moveCurrentIndexLeft() {this.moveCurrentIndexTo(this.getCurrentIndex() - 1);}
    
    @Override
    public void moveCurrentIndexRight() {this.moveCurrentIndexTo(this.getCurrentIndex() + 1);}
    
    @Override
    public abstract int size();

    @SuppressWarnings("unchecked")
    @Override
    public T copy()
    {
        T toReturn;
        try {toReturn = (T)this.getClass().getDeclaredConstructor().newInstance();}
        catch (Throwable _) {System.err.println("Class to copy has no visible parameterless constructor"); return null;}

        int size = this.size();
        if (size == 0) return toReturn;

        int currentIndex = this.getCurrentIndex();
        this.moveCurrentIndexToStart();
        for (int i = 0; ; i++)
        {
            toReturn.append(this.getCurrentValue());

            if (i == size - 1) break;

            this.moveCurrentIndexRight();
        }
        toReturn.append(this.getCurrentValue());

        this.moveCurrentIndexTo(currentIndex);
        toReturn.moveCurrentIndexTo(currentIndex);

        return toReturn;
    }

    @Override
    public boolean equals(CustomList<E, ?> otherList)
    {
        int size = this.size();
        if (size != otherList.size()) return false;

        int currentIndex = this.getCurrentIndex();
        if (currentIndex != otherList.getCurrentIndex()) return false;

        this.moveCurrentIndexToStart();
        otherList.moveCurrentIndexToStart();

        for (int i = 0; ; i++)
        {
            if (this.getCurrentValue() != otherList.getCurrentValue()) return false;

            if (i == size - 1) break;

            this.moveCurrentIndexRight();
            otherList.moveCurrentIndexRight();
        }

        this.moveCurrentIndexTo(currentIndex);
        otherList.moveCurrentIndexTo(currentIndex);

        return true;
    }
    
    @Override
    public String toString()
    {
        int currentIndex = 0;
        int size = this.size();

        StringBuilder toReturn = new StringBuilder();
        toReturn.append('[');
        if (size != 0)
        {
            this.moveCurrentIndexToStart();

            toReturn.append(this.getCurrentValue());
            for (int i = 0; i < size - 1; i++)
            {
                toReturn.append(", ");
                toReturn.append(this.getCurrentValue());

                this.moveCurrentIndexRight();
            }
        }
        toReturn.append(']');

        this.moveCurrentIndexTo(currentIndex);

        return toReturn.toString();
    }
}