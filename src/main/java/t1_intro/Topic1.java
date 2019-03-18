package t1_intro;

public class Topic1 {
    /*
        引用的问题
     */
    public static void main(String[] args) {
        //变量值 引用  常量池
        System.out.println("---------Integer/Long---------");
        Integer int1=60;
        Integer int2=60;
        System.out.println(int1==int2);
        Integer int3=128;
        Integer int4=128;
        System.out.println(int3==int4);
        System.out.println(int2==60);
        System.out.println(int3.equals(int4));


        System.out.println("-----------String------------");
        String str1 = "111";
        String str2 = "111";
        System.out.println(str1==str2);

        String str3=new String("111");
        String str4=new String("111");
        System.out.println(str1==str4);
        System.out.println(str3==str4);
        System.out.println(str1==str4.intern());

    }

}
