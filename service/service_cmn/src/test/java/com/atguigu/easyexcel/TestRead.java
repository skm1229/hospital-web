package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

public class TestRead {
    public static void main(String[] args) {
        //excel生成路径   读取文件路径和名称
        String fileName = "D:\\Excel生成\\01.xlsx";
        //调用方法执行读操作
        EasyExcel.read(fileName, UserData.class,new ExcelListener()).sheet().doRead();
    }

}
