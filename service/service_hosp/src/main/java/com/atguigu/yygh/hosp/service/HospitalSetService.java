package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.extension.service.IService;

public interface HospitalSetService extends IService<HospitalSet> {
    /**
     * 根据传递过来的医院编码 查询数据库 查询签名
     * @param hoscode     医院编码
     * @return
     */
    String getSignKey(String hoscode);
}
