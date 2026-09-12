package project1lists;

public class Main
{
    public static void main(String[] args)
    {
        List<Integer> list = new LinkedList<Integer>();
        list.insert(1);
        list.insert(2);
        list.moveCurrentIndexToEnd();
        System.out.println(list.getCurrentValue());
        list.println();
        // list.moveCurrentIndexLeft();
        // list.moveCurrentIndexLeft();
        list.remove();
        list.println();
        list.insert(3);
        System.out.println(list.getCurrentValue());
        list.println();
        list.moveCurrentIndexRight();
        System.out.println(list.getCurrentValue());
        list.remove();
        list.println();
        list.remove();
        list.println();
        list.append(4);
        list.println();
        System.out.println(list.getCurrentValue());
    }
}