package com.sun.flower.spark;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/29 15:45
 **/
public class JavaSparkPi {

    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder().appName("JavaSparkPi").getOrCreate();

        JavaSparkContext sc = new JavaSparkContext(sparkSession.sparkContext());

        int slices = 2;
        int n = 1000 * slices;

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i< n; i++) {
            list.add(i);
        }

        JavaRDD<Integer> dataSet = sc.parallelize(list, slices);
        int count = dataSet.map(i -> {
           double x = Math.random() * 2 - 1;
           double y = Math.random() * 2 - 1;

           return (x * x + y * y <= 1) ? 1 : 0;
        }).reduce((m, k) -> m + k);

        System.out.println("pi is = " + 4.0 * count / n);

        sparkSession.stop();
    }

}
