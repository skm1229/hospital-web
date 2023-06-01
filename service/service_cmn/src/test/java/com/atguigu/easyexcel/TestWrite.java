package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestWrite {
    public static void main(String[] args) {
        List<UserData> list= new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            UserData userData = new UserData();
            userData.setUid(i);
            userData.setUserName("lucy" + i);
            list.add(userData);
        }
        //excel生成路径   设置文件路径和名称
        String fileName = "D:\\Excel生成\\01.xlsx";

        //调用方法执行写操作
        EasyExcel.write(fileName, UserData.class).sheet("用户信息").doWrite(list);


    }
}
