package com.sun.flower.trans.mr;

import com.google.common.collect.Lists;
import com.sun.flower.trans.BaseTest;
import com.sun.flower.trans.mr.task.DemoTask;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/4 10:32
 **/
public class MrTest extends BaseTest {

    @Test
    public void batchTask2() throws Exception {
        ExecutorService executorService = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        long begin = System.currentTimeMillis();

        List<Runnable> demoTasks = Lists.newArrayList(new DemoTask("task1"), new DemoTask("task2"),new DemoTask("task3"));
        List<Future<?>> futures = Lists.newArrayList();

        for (Runnable task : demoTasks) {
            Future<?> future = executorService.submit(task);
            futures.add(future);
        }

        System.out.println("===== bool finish ======");
        int num = 0;
        for (Future future : futures) {
            //System.out.println("---" + future.get() + "---" + future.isDone());
            do {
                num += 1;
                break;
            } while (future.isDone());

            System.out.println("---====" + future.get());
        }

        boolean bool = (num == demoTasks.size()) ? true : false;
        System.out.println("===== bool end ======" + bool);

        System.out.println("===== middle22222 ======");

        executorService.submit(new DemoTask("---tttt2222---"));

        System.out.println("===== end2 ======" + (System.currentTimeMillis() - begin));

    }

    @Test
    public void batchTask() {
        ExecutorService executorService = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        long begin = System.currentTimeMillis();
        List<Runnable> demoTasks = Lists.newArrayList(new DemoTask("task1"), new DemoTask("task2"),new DemoTask("task3"));

        MapReduceTemplate mapReduceTemplate = new MapReduceTemplate();
        boolean bool = mapReduceTemplate.batchSubmitTaskAndIsCompleted(executorService, demoTasks);
        System.out.println("===== bool 1111 ======" + bool);

        System.out.println("===== middle ======");

        //List<Runnable> demoTasks2 = Lists.newArrayList(new DemoTask("task11"), new DemoTask("task31"));
        //bool = mapReduceTemplate.batchSubmitTaskAndIsCompleted(executorService, demoTasks2);
        //System.out.println("===== bool 2222 ======" + bool);

        executorService.submit(new DemoTask("---tttt---"));

        System.out.println("===== end1 ======" + (System.currentTimeMillis() - begin));

    }
}
