package com.atguigu.yygh.user.api;

import com.atguigu.yygh.common.service.result.Result;
import com.atguigu.yygh.user.service.UserInfoService;
import com.atguigu.yygh.vo.user.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户手机号登录接口
     * @param loginVo
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        log.info("验证码： {}" , loginVo.getCode());
        log.info("手机号： {}" , loginVo.getPhone());
        Map<String, Object> info = userInfoService.loginUser(loginVo);

        return Result.ok(info);
    }
}
