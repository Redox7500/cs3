package project1lists;

public class Main
{
    public static void main(String[] args)
    {
        List<Integer> list = new ArrayList<Integer>();
        list.append(1);
        list.append(2);
        list.moveCurrentIndexToEnd();
        System.out.println(list.getCurrentValue());
        // list.moveCurrentIndexLeft();
        // list.moveCurrentIndexLeft();
        list.remove();
        list.println();
        list.append(3);
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