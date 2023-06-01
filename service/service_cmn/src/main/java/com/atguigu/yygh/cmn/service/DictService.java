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
    public List<Dict> findChildData(Long id);

    /**
     * 导出数据字典接口
     * @param response
     */
    public void exportDictData(HttpServletResponse response);
    /**
     * 导入数据字典接口
     * @param file
     */
    public void importDictData(MultipartFile file);
}
