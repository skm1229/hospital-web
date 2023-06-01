package com.atguigu.hospitalmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jw.hmanage.hospitalmanage.model.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}
