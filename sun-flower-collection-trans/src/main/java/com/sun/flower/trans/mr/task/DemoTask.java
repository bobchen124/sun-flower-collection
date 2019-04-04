package com.sun.flower.trans.mr.task;

/**
 * @Desc: 样例DEMO
 * @Author: chenbo
 * @Date: 2019/4/4 09:06
 **/
public class DemoTask implements Runnable {

    private String name;

    public DemoTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-------" + this.name);
    }

}
