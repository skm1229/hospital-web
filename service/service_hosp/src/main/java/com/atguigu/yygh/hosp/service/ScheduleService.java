package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.ScheduleOrderVo;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    /**
     * 上传排班接口
     * @param paramMap
     */
    void save(Map<String, Object> paramMap);

    /**
     * 查询排班接口
     * @param page
     * @param limit
     * @param scheduleQueryVo
     * @return
     */
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    /**
     * 删除排班接口
     * @param hoscode
     * @param hosScheduleId
     */
    void remove(String hoscode, String hosScheduleId);

    /**
     * 根据医院编号 和 科室编号 ，查询排班规则数据
     * @param page
     * @param limit
     * @param hoscode
     * @param depcode
     * @return
     */
    Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);

    /**
     * 根据医院编号 、科室编号和工作日期，查询排班详细信息
     * @param hoscode
     * @param depcode
     * @param workDate
     * @return
     */
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);

    /**
     * 获取可预约的排班数据
     * @param page
     * @param limit
     * @param hoscode
     * @param depcode
     * @return
     */
    Map<String,Object> getBookingScheduleRule(int page,int limit,String hoscode,String depcode);

    /**
     * 获取排班id获取排班数据
     * @param scheduleId
     * @return
     */
    Schedule getScheduleId(String scheduleId);

    /**
     * 根据排班id获取预约下单数据
     * @param scheduleId
     * @return
     */
    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    /**
     * 更新排班数据 用于mp
     * @param schedule
     */
    void update(Schedule schedule);
}
