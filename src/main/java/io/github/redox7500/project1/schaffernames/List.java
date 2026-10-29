package io.github.redox7500.project1.schaffernames;

public interface List<E> extends io.github.redox7500.project1.Project1List<E>
{
    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.moveCurrentIndexToStart moveCurrentIndexToStart()} method */
    default void moveToStart() {this.moveCurrentIndexToStart();}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.moveCurrentIndexToEnd moveCurrentIndexToEnd()} method */
    default void moveToEnd() {this.moveCurrentIndexToEnd();}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.moveCurrentIndexToPrevious moveCurrentIndexToPrevious()} method */
    default void prev() {this.moveCurrentIndexToPrevious();}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.moveCurrentIndexToNext moveCurrentIndexToNext()} method */
    default void next() {this.moveCurrentIndexToNext();}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.size size()} method */
    default int length() {return this.size();}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.getCurrentIndex getCurrentIndex()} method */
    default int currPos() {return this.getCurrentIndex();}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.moveCurrentIndexTo moveCurrentIndexTo()} method
     * @param pos The position to make current
    */
    default void moveToPos(int pos) {this.moveCurrentIndexTo(pos);}

    /** Shaffer's name for my equivalent {@link #project1lists.CustomList.getCurrentValue getCurrentValue()} method */
    default E getValue() {return this.getCurrentValue();}
}
