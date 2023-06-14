package com.atguigu.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.listener.DictListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;

import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {


    /**
     * 根据数据id查询子数据列表
     * @param id  父id
     * @return
     */
    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long id) {
        //构造条件
        LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dict::getParentId,id);
        List<Dict> dictLsit = baseMapper.selectList(queryWrapper);
        //向list集合中的每个dict对象设置hasChildren
        dictLsit = dictLsit.stream().map((item) ->{
            Dict dict = new Dict();
            BeanUtils.copyProperties(item,dict);
            Long dictId = item.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
            return dict;
        }).collect(Collectors.toList());
        return dictLsit;
    }

    /**
     * 导出数据字典接口
     * @param response
     */
    @Override
    public void exportDictData(HttpServletResponse response) {
        OutputStream outputStream;
        //设置下载信息
        //传输类型
        response.setContentType("application/vnd.ms-excel");
        //编码格式
        response.setCharacterEncoding("utf-8");
        //文件名
        String fileName = "dict";
        //请求头
        response.setHeader("Content-disposition","attachment;filename=" + fileName + ".xlsx");
        //查询数据库
        List<Dict> dictList = baseMapper.selectList(null);
        //Dict -- DictEeVo
        List<DictEeVo> list = dictList.stream().map((item) ->{
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(item,dictEeVo);
            return dictEeVo;
        }).collect(Collectors.toList());

        //调用方法执行写操作
        try {
            outputStream = response.getOutputStream();
            EasyExcel.write(outputStream, DictEeVo.class).sheet("dict").doWrite(list);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 导入数据字典接口
     * @param file
     */
    @Override
    @CacheEvict(value = "dict",allEntries = true)
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class,new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断当前id下面是否还有子节点
     * @param id  当前dict的id
     * @return
     */
    private boolean isChildren(Long id) {
        LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dict::getParentId,id);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }


    /**
     * 根据dictcode和value查询
     * @param dictCode   医院类型
     * @param value      字典编码
     * @return
     */
    @Override
    public String getDictName(String dictCode, String value) {
        //条件构造器
        LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<>();
        //如果dictCode为空，直接根据value查询
        if(StringUtils.isEmpty(dictCode)) {
            //直接根据value查询
            queryWrapper.eq(Dict::getValue,value);
            Dict dict = baseMapper.selectOne(queryWrapper);
            return dict.getName();
        } else {//如果dictCode不为空，根据dictCode和value查询
            //根据dictcode查询dict对象，得到dict的id值
            Dict codeDict = this.getDictByDictCode(dictCode);
            Long parent_id = codeDict.getId();
            //根据parent_id和value进行查询
            queryWrapper.eq(Dict::getParentId, parent_id)
                        .eq(Dict::getValue, value);
            Dict finalDict = baseMapper.selectOne(queryWrapper);
            return finalDict.getName();
        }
    }

    /**
     * 根据dictCode获取下级节点
     * @param dictCode  字典编码
     * @return
     */
    @Override
    public List<Dict> findByDictCode(String dictCode) {
        //根据dictcode获取对应id
        Dict dict = this.getDictByDictCode(dictCode);
        //根据id获取子节点
        List<Dict> chlidData = this.findChildData(dict.getId());
        return chlidData;
    }
    /**
     * 根据dictCode获取dict对象
     * @param dictCode  字典编码
     * @return
     */
    private Dict getDictByDictCode(String dictCode) {
        LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dict::getDictCode,dictCode);
        Dict codeDict = baseMapper.selectOne(queryWrapper);
        return codeDict;
    }
}
