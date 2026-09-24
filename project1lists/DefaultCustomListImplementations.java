package project1lists;

/** All of the default implementations of the CustomList required methods except for Shaffer's aliases */
public abstract class DefaultCustomListImplementations<E, T extends DefaultCustomListImplementations<E, T>> implements CustomList<E, T>
{
    public void clear()
    {
        this.moveCurrentIndexToStart();
        for (int i = 0; i < this.size(); i++) this.remove();
    }
    
    public abstract void insert(E value);

    
    public abstract void append(E value);

    public abstract E remove();

    public abstract E getCurrentValue();

    public abstract void setCurrentValue(E value);
    
    public abstract int getCurrentIndex();
    
    public abstract void moveCurrentIndexTo(int index);
    
    public void moveCurrentIndexToStart() {this.moveCurrentIndexTo(0);}
    
    public void moveCurrentIndexToEnd() {this.moveCurrentIndexTo(Math.max(this.size() - 1, 0));}
    
    public void moveCurrentIndexLeft() {this.moveCurrentIndexTo(this.getCurrentIndex() - 1);}
    
    public void moveCurrentIndexRight() {this.moveCurrentIndexTo(this.getCurrentIndex() + 1);}
    
    public abstract int size();

    @SuppressWarnings("unchecked")
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