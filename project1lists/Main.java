package project1lists;

public class Main
{
    public static void main(String[] args)
    {
        List<Integer> list = new AList<Integer>();
        list.append(1);
        list.append(2);
        list.moveIndexToEnd();
        list.append(3);
        list.print();
        System.out.println(list.getCurrentValue());
    }
}