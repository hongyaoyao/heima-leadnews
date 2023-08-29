package com.heima.user.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.ApUserBehaviorDto;
import com.heima.user.service.ApUserBehaviorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class ApUserBehaviorController {

    @Autowired
    private ApUserBehaviorService apUserBehaviorService;

    /**
     * 用户关注行为（关注与取消关注）
     * operation : 0 关注  1 取消
     */
    @PostMapping("/user_follow")
    public ResponseResult ApUserFollow(@RequestBody ApUserBehaviorDto dto){
        return apUserBehaviorService.ApUserFollow(dto);
    }
}
