package com.atguigu.yygh.cmn.service;

import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
    /**
     * 根据数据id查询子数据列表
     * @param id  父id
     * @return
     */
    List<Dict> findChildData(Long id);

    /**
     * 导出数据字典接口
     * @param response
     */
    void exportDictData(HttpServletResponse response);
    /**
     * 导入数据字典接口
     * @param file
     */
    void importDictData(MultipartFile file);

    /**
     * 根据dictcode和value查询
     * @param dictCode   医院类型
     * @param value      字典编码
     * @return
     */
    String getDictName(String dictCode, String value);

    /**
     * 根据dictCode获取下级节点
     * @param dictCode  字典编码
     * @return
     */
    List<Dict> findByDictCode(String dictCode);
}
