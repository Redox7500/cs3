package project1lists;

public class Main
{
    public static void main(String[] args)
    {
        List<Integer> list = new LList<Integer>();
        list.append(1);
        list.append(2);
        System.out.println(list.getCurrentValue());
        list.remove();
        System.out.println(list.getCurrentValue());
        list.append(3);
        list.remove();
        list.remove();
        list.print();
        System.out.println(list.getCurrentValue());
    }
}