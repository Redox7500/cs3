package io.github.redox7500.project1;

/** All of the default implementations of the CustomList required methods except for Shaffer's aliases */
public abstract class DefaultCustomListImplementations<E> implements CustomList<E>
{
    @Override
    public void clear()
    {
        this.moveCurrentIndexToStart();
        for (int i = 0; i < this.size(); i++) this.remove();
    }
    
    @Override
    public void moveCurrentIndexToStart() {this.moveCurrentIndexTo(0);}
    
    @Override
    public void moveCurrentIndexToEnd() {this.moveCurrentIndexTo(Math.max(this.size() - 1, 0));}
    
    @Override
    public void moveCurrentIndexToPrevious() {this.moveCurrentIndexTo(this.getCurrentIndex() - 1);}
    
    @Override
    public void moveCurrentIndexToNext() {this.moveCurrentIndexTo(this.getCurrentIndex() + 1);}

    @SuppressWarnings("unchecked")
    @Override
    public CustomList<E> copy()
    {
        CustomList<E> toReturn;

        try {toReturn = this.getClass().getDeclaredConstructor().newInstance();}
        catch (Throwable _) {System.err.println("Class to copy has no visible parameterless constructor"); return null;}

        int size = this.size();
        if (size == 0) return toReturn;

        int currentIndex = this.getCurrentIndex();

        try
        {
            this.moveCurrentIndexToStart();

            for (int i = 0; ; i++)
            {
                toReturn.append(this.getCurrentValue());

                if (i == size - 1) break;

                this.moveCurrentIndexToNext();
            }
            toReturn.append(this.getCurrentValue());

            toReturn.moveCurrentIndexTo(currentIndex);
        }
        finally
        {
            this.moveCurrentIndexTo(currentIndex);
        }

        return toReturn;
    }
    
    @Override
    public boolean testEquality(CustomList<E> otherList) // only supports comparisons with other CustomLists
    {
        int size = this.size();
        if (size != otherList.size()) return false;
        if (size == 0) return true;

        int currentIndex = this.getCurrentIndex();
        if (currentIndex != otherList.getCurrentIndex()) return false;

        try
        {
            this.moveCurrentIndexToStart();
            otherList.moveCurrentIndexToStart();

            for (int i = 0; ; i++)
            {
                if (!(this.getCurrentValue().equals(otherList.getCurrentValue()))) return false;

                if (i == size - 1) break;

                this.moveCurrentIndexToNext();
                otherList.moveCurrentIndexToNext();
            }
        }
        finally
        {
            this.moveCurrentIndexTo(currentIndex);
            otherList.moveCurrentIndexTo(currentIndex);
        }
    }

    @Override
    public int hashCode() // apparently i need to do this if i want to override equals(Object)
    {
        int toReturn = 1;

        int size = this.size();
        int currentIndex = this.getCurrentIndex();

        if (this.size() > 0)
        {
            try
            {
                this.moveCurrentIndexToStart();

                for (int i = 0; ; i++)
                {
                    E currentValue = this.getCurrentValue();
                    toReturn = 31 * toReturn + ((currentValue == null)? 0 : currentValue.hashCode());

                    if (i == size - 1) break;

                    this.moveCurrentIndexToNext();
                }
            }
            finally
            {
                this.moveCurrentIndexTo(currentIndex);
            }
        }

        toReturn = 31 * toReturn + Integer.hashCode(size);
        toReturn = 31 * toReturn + Integer.hashCode(currentIndex);

        E currentValue = this.getCurrentValue();
        toReturn = 31 * toReturn + ((currentValue == null)? 0 : currentValue.hashCode());

        return toReturn;
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

                this.moveCurrentIndexToNext();
            }
        }
        toReturn.append(']');

        this.moveCurrentIndexTo(currentIndex);

        return toReturn.toString();
    }

    @Override
    public void printRelevantInfo(String name)
    {
        System.out.println(name + ": " + this);
        System.out.println(name + " size: " + this.size());
        System.out.println(name + " current value: " + this.getCurrentValue());
        System.out.println(name + " current index: " + this.getCurrentIndex());
    }
}
