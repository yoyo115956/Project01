package com.atguigu.java;

import com.atguigu.bean.Customer;

import java.util.ArrayList;

/**
 * @author YoyoLee
 * @create 2021-03-02-12:49
 */
public class TemplatesTest {
    //template 6: prsf:
    private static final Customer cust = new Customer();

    //变形：psf
    public static final int NUM = 1;

    //变形：psfi
    public static final int NUM2 = 2;

    //变形：psfs
    public static final String NATION = "china";

    //template 1: psvm
    public static void main(String[] args) {
        //template 2： sout
        System.out.println("hello!");
        //变形:soup / soutm / soutv / xxx.sout
        System.out.println("args = [" + args + "]");
        System.out.println("TemplatesTest.main");

        int num1 = 10;
        System.out.println("num1 = " + num1);

        int num2 = 20;
        System.out.println("num2 = " + num2);
        System.out.println(num1);

        //template: fori
        String[] arr = new String[]{"Tem", "Jerry", "HanMeimei", "Lilei"};
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        //变形：iter
        for (String s : arr) {
            System.out.println(s);
        }
        //变形：itar
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            System.out.println(s);
        }
        //template: list.for
        ArrayList list = new ArrayList();
        list.add(123);
        list.add(345);
        list.add(567);

        for (Object o : list) {
            
        }

        //变形：list.fori
        for (int i = 0; i < list.size(); i++) {
            
        }

        //变形：list.forr 倒序遍历
        for (int i = list.size() - 1; i >= 0; i--) {
            
        }
    }

    public void method(){
        System.out.println("TemplatesTest.method");

        ArrayList list = new ArrayList();
        list.add(123);
        list.add(345);
        list.add(567);

        //template 5:ifn
        if (list == null) {
            
        }

        //template 6:inn
        if (list != null) {
            
        }

        //变形：xxx.nn / xxx.null
        if (list != null) {

        }
        if (list == null) {

        }
    }
}
