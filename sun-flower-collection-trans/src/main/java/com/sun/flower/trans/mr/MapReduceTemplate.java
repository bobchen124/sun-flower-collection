package com.sun.flower.trans.mr;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 * @Desc: MapReduce模板
 * @Author: chenbo
 * @Date: 2019/4/3 16:59
 **/
public class MapReduceTemplate {

    /**
     * 批量执行异步任务，并判断是否都执行成功
     * @param executor 线程池
     * @param tasks 任务列表
     * @return false - 未完成；true - 完成
     */
    public boolean batchSubmitTaskAndIsCompleted(ExecutorService executor, List<Runnable> tasks) {
        long begin = System.currentTimeMillis();
        Integer ret = tasks.parallelStream().map((t) -> executor.submit(t)).reduce(new Integer(0),
                (r, future) -> {
                    do {
                        // 任务完成，返回1
                        r = 1;
                        break;
                    } while (future.isDone());

                    return r;
                }, (a, b) ->  a + b );

        System.out.println("---- time = " + (System.currentTimeMillis() - begin));
        return (ret.intValue() == tasks.size()) ? true : false;
    }

    public void syncTask(ExecutorService executor, List<Runnable> tasks) {
        Integer ret = tasks.parallelStream().map((task) -> executor.submit(task)).reduce(new Integer(0),
                new BiFunction<Integer, Future, Integer>() {
                    @Override
                    public Integer apply(Integer integers, Future future) {
                        do {
                            integers = 1;
                            System.out.println("=== isDone===" + integers);
                            break;
                            //System.out.println("=== isDone===");
                        } while (future.isDone());

                        return integers;
                    }
                }, new BinaryOperator<Integer>() {
                    @Override
                    public Integer apply(Integer integers, Integer integers2) {
                        //System.out.println("===" + integers);
                        //integers.addAll(integers2);
                        return integers + integers2;
                    }
                });

        //executor.shutdown();
        System.out.println("ret = " + ret);
        //return false;
    }

}
