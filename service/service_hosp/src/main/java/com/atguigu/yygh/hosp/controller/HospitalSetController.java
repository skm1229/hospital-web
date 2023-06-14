package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.common.service.result.Result;
import com.atguigu.yygh.common.service.utils.MD5;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(value = "医院设置管理")
//@CrossOrigin     //运行跨越访问
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    //查询所有医院
    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("/findAll")
    public Result findAllHospSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }
    //根据id逻辑删除医院
    @ApiOperation(value = "逻辑删除医院设置")
    @DeleteMapping("/{id}")
    public Result removeHospSet(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if(flag) {
            return Result.ok();
        }
        return Result.fail();

    }

    /**
     * 分页查询医院信息
     * @param current             当前页
     * @param limit               每页记录数
     * @param hospitalSetQueryVo  医院名称 & 医院编号
     * @return
     */
    @PostMapping("/findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current,limit);
        //构建条件
        LambdaQueryWrapper<HospitalSet> queryWrapper = new LambdaQueryWrapper<>();
        //医院名称
        queryWrapper.like(!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())
                ,HospitalSet::getHosname,hospitalSetQueryVo.getHosname());
        //医院编号
        queryWrapper.eq(!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())
                ,HospitalSet::getHoscode,hospitalSetQueryVo.getHoscode());

        //调用方法实现分页查询
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, queryWrapper);

        return Result.ok(hospitalSetPage);
    }

    /**
     * 添加医院设置
     * @param hospitalSet   医院信息
     * @return
     */
    @PostMapping("/saveHospSet")
    public Result saveHospSet(@RequestBody HospitalSet hospitalSet) {
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //前面密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        //调用service
        boolean save = hospitalSetService.save(hospitalSet);
        if(save) {
            return Result.ok();
        }
        return Result.fail();

    }

    /**
     * 根据id获取医院设置
     * @param id
     * @return
     */
    @GetMapping("/getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    /**
     * 修改医院设置
     * @param hospitalSet
     * @return
     */
    @PostMapping("/updateHospSet")
    public Result updateHospSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if(flag) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 批量删除医院设置
     * @param idList
     * @return
     */
    @DeleteMapping("/batchRemoveHospSet")
    public Result batchRemoveHospSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    /**
     * 医院设置锁定和解锁
     * @param id        医院id
     * @param status    医院状态
     * @return
     */
    @PutMapping("/lockHospSet/{id}/{status}")
    public Result lockHospSet(@PathVariable Long id,@PathVariable Integer status) {
        LambdaUpdateWrapper<HospitalSet> updateWrapper = new LambdaUpdateWrapper<>();
        //医院编号
        updateWrapper.set(HospitalSet::getStatus,status);
        //医院状态
        updateWrapper.eq(HospitalSet::getId,id);
        //调用service
        hospitalSetService.update(updateWrapper);
        return Result.ok();
    }

    /**
     * 发送签名密钥
     * @param id
     * @return
     */
    @PutMapping("/sendKey/{id}")
    public Result sendKeyHospSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String hoscode = hospitalSet.getHoscode();
        String signKey = hospitalSet.getSignKey();
        return Result.ok();
    }
}
