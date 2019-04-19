package com.sun.flower.hbase;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.AdvancedScanResultConsumer;
import org.apache.hadoop.hbase.client.AsyncConnection;
import org.apache.hadoop.hbase.client.AsyncTable;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.filter.ColumnPaginationFilter;
import org.apache.hadoop.hbase.filter.ColumnValueFilter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/18 10:12
 **/
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HBaseTest {

    private final String TABLE_NAME = "emp";

    @Autowired
    Configuration configuration;

    /**
     * 初始化测试数据表
     */
    @Test
    public void initTestTable() throws Exception {
        Connection connection = ConnectionFactory.createConnection(configuration);

        HBaseAdmin admin = (HBaseAdmin)connection.getAdmin();

        if (admin.tableExists(TableName.valueOf(TABLE_NAME))) {
            log.info("table is exists, do not create");
            return;
        }

        TableDescriptorBuilder descriptorBuilder = TableDescriptorBuilder.newBuilder(TableName.valueOf(TABLE_NAME));

        ColumnFamilyDescriptor familyDescriptor = ColumnFamilyDescriptorBuilder.of("cf");
        descriptorBuilder.setColumnFamily(familyDescriptor);

        TableDescriptor tableDescriptor = descriptorBuilder.build();
        admin.createTable(tableDescriptor);
        log.info("table is not exists, create success");
    }

    /**
     * 初始化测试数据
     * @throws Exception
     */
    @Test
    public void initTableData() throws Exception {
        try (Connection connection = ConnectionFactory.createConnection(configuration);
             Table table = connection.getTable(TableName.valueOf(TABLE_NAME))) {
            // 创建put对象
            Put put1 = new Put(Bytes.toBytes("r1"));
            put1.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("name"), Bytes.toBytes("test-1"));
            put1.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("sex"), Bytes.toBytes("N"));
            put1.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("age"), Bytes.toBytes("30"));

            Put put2 = new Put(Bytes.toBytes("r2"));
            put2.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("name"), Bytes.toBytes("test-2"));
            put2.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("sex"), Bytes.toBytes("Y"));

            Put put3 = new Put(Bytes.toBytes("r3"));
            put3.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("name"), Bytes.toBytes("test-3"));
            put3.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("sex"), Bytes.toBytes("N"));

            table.put(Lists.newArrayList(put1, put2, put3));
        }
    }


    /**
     * ColumnValueFilter 过滤，查询Column 为固定值
     * @throws Exception
     */
    @Test
    public void queryTest() throws Exception {
        Connection connection = ConnectionFactory.createConnection(configuration);

        TableName tableName = TableName.valueOf(TABLE_NAME);
        Table table = connection.getTable(tableName);

        // 查询过滤
        ColumnValueFilter columnValueFilter =
                new ColumnValueFilter("cf".getBytes(), "sex".getBytes(), CompareOperator.EQUAL, "N".getBytes());

        Scan scan = new Scan();
        scan.setFilter(columnValueFilter);

        ResultScanner results = table.getScanner(scan);

        results.forEach(result -> {
            for (Cell cell : result.listCells()) {
                System.out.print("qualifier: " + Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()));
                System.out.print("value: " + Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            }

            System.out.println("result = " + result.toString());
            System.out.println("row = " + new String(result.getRow()));
        });
    }

    /**
     * 根据主键rowkey查询
     * @throws Exception
     */
    @Test
    public void getByRowKeyTest() throws Exception {
        Connection connection = ConnectionFactory.createConnection(configuration);

        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

        Get get = new Get(Bytes.toBytes("r2"));

        Result result = table.get(get);

        System.out.println("row = " + new String(result.getValue("cf".getBytes(), "name".getBytes())));
        System.out.println("row = " + new String(result.getValue("cf".getBytes(), "sex".getBytes())));

        List<Cell> cells = result.listCells();
        cells.forEach(cell -> {
            log.info(" timestamp = {}", cell.getTimestamp());
        });
    }

    /**
     * PageFilter 一次查询的数量
     * @throws Exception
     */
    @Test
    public void queryByPageTest() throws Exception {
        CompletableFuture<AsyncConnection> connectionFuture = ConnectionFactory.createAsyncConnection(configuration);

        AsyncConnection asyncConnection = connectionFuture.get();

        AsyncTable<AdvancedScanResultConsumer> asyncTable = asyncConnection.getTable(TableName.valueOf(TABLE_NAME));

        PageFilter pageFilter = new PageFilter(3);

        Scan scan = new Scan();
        scan.setFilter(pageFilter);

        ResultScanner resultScanner = asyncTable.getScanner(scan);

        resultScanner.forEach(result -> {
            for (Cell cell : result.listCells()) {
                log.info(" qualifier = {}, value = {} ",
                        Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()),
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            }

            System.out.println(", row = " + new String(result.getRow()));
        });

    }

    /**
     * ColumnPaginationFilter 字段查询
     * @throws Exception
     */
    @Test
    public void queryByPagedTest() throws Exception {
        CompletableFuture<AsyncConnection> connectionFuture = ConnectionFactory.createAsyncConnection(configuration);

        AsyncConnection asyncConnection = connectionFuture.get();

        AsyncTable<AdvancedScanResultConsumer> asyncTable = asyncConnection.getTable(TableName.valueOf(TABLE_NAME));

        ColumnPaginationFilter pageFilter = new ColumnPaginationFilter(1, 0);

        Scan scan = new Scan();
        scan.setFilter(pageFilter);

        ResultScanner scanner = asyncTable.getScanner(scan);

        scanner.forEach(result -> {
            for (Cell cell : result.listCells()) {
                log.info(" qualifier = {}, value = {} ",
                        Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()),
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            }

            log.info(", row = {}", new String(result.getRow()));
        });

    }

}
