package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    /**
     * 上传科室接口
     * @param paramMap
     */
    void save(Map<String, Object> paramMap);

    /**
     * 查询科室接口
     * @param page
     * @param limit
     * @param departmentQueryVo
     * @return
     */
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    /**
     * 删除科室接口
     * @param hoscode
     * @param depcode
     */
    void remove(String hoscode, String depcode);

    /**
     * 根据医院编号，查询医院所有科室列表
     * @param hoscode
     * @return
     */
    List<DepartmentVo> findDeptTree(String hoscode);

    /**
     * 根据科室编号，和医院编号，查询科室名称
     * @param hoscode
     * @param depcode
     * @return
     */
    String getDepName(String hoscode, String depcode);

    /**
     * 根据科室编号，和医院编号，查询科室
     * @param hoscode
     * @param depcode
     * @return
     */
    Department getDepartment(String hoscode, String depcode);
}
