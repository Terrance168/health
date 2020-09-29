package com.itheima.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Terrance
 * @Date: 2020-09-24 10:07
 *
 * 任务类：注册到spring容器
 */
public class MyJob {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void abc(){
        String name = Thread.currentThread().getName();
        System.out.println(name + "=======" + sdf.format(new Date()));
    }

    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");

        List<String> list2 = new ArrayList<>();
        list2.add("3");
        list2.add("5");

        //list1 - list2 = 1,2,4
        list1.removeAll(list2);

        for (String s : list1) {
            System.out.println(s);
        }
    }
}
