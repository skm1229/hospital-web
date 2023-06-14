package com.atguigu.hospitalmanage.mapper;

import com.atguigu.hospitalmanage.model.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}
