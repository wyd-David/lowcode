package com.yabushan.web.test;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author yabushan
 * @Date 2021/5/21 20:16
 * @Version 1.0
 */
public class WriteTest {

    public static void main(String[] args) {

        List<ConverterData> list = new ArrayList<>();
        ConverterData d =new ConverterData();
        d.setDate(new Date());
        list.add(d);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write("D://12345.xlsx", ConverterData.class).sheet("模板").doWrite(list);
    }
}
