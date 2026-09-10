package project1lists;

public class Main
{
    public static void main(String[] args)
    {
        List<Integer> list = new LList<Integer>();
        list.append(1);
        list.append(2);
        list.moveCurrentIndexToEnd();
        // list.moveCurrentIndexLeft();
        // list.moveCurrentIndexLeft();
        list.remove();
        list.println();
        list.append(3);
        list.moveCurrentIndexRight();
        list.remove();
        list.println();
        list.remove();
        list.println();
        list.append(4);
        list.println();
        System.out.println(list.getCurrentValue());
    }
}