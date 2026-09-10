package project1lists;

public class Main
{
    public static void main(String[] args)
    {
        List<Integer> list = new LList<Integer>();
        list.append(1);
        list.append(2);
        list.append(3);
        list.print();
        System.out.println(list.getCurrentValue());
    }
}