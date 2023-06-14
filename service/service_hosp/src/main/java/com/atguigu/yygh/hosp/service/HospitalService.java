package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface HospitalService {
    /**
     * 将json数据封装的map集合保存为医院数据
     * @param paramMap
     */
    void save(Map<String, Object> paramMap);

    /**
     * 根据医院编号查询医院
     * @param hosCode
     * @return
     */
    Hospital getByHoscode(String hosCode);


    /**
     * 医院列表(条件查询分页)
     * @param page
     * @param limit
     * @param hospitalQueryVo
     * @return
     */
    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    /**
     * 更新医院上线状态
     * @param id
     * @param status
     */
    void updateStatus(String id, Integer status);

    /**
     * 医院详情信息
     * @param id
     * @return
     */
    Map<String, Object> getHospById(String id);

    /**
     * 获取医院名称
     * @param hoscode
     * @return
     */
    String getHospName(String hoscode);

    /**
     * 根据医院名称查询
     * @param hosname
     * @return
     */
    List<Hospital> findByHosname(String hosname);

    /**
     * 根据医院编号获取医院预约挂号详情
     * @param hoscode
     * @return
     */
    Map<String, Object> item(String hoscode);
}
