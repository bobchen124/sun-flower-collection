package com.sun.flower.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.AdvancedScanResultConsumer;
import org.apache.hadoop.hbase.client.AsyncConnection;
import org.apache.hadoop.hbase.client.AsyncTable;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.ColumnPaginationFilter;
import org.apache.hadoop.hbase.filter.ColumnValueFilter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
