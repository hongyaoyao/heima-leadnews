package com.heima.behavior.controller;

import com.heima.behavior.service.ApUserUnLikeService;
import com.heima.model.behavior.dtos.ApUserUnLikeDto;
import com.heima.model.common.dtos.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ItZheng
 * @Date 2023/6/21 14:53
 * @Version 1.5
 */
@RestController
@RequestMapping("/api/v1/un_likes_behavior")
@Slf4j
public class ApUserUnLikeController {

    @Autowired
    private ApUserUnLikeService apUserUnLikeService;

    @PostMapping
    public ResponseResult unLike(@RequestBody ApUserUnLikeDto dto) {
        return apUserUnLikeService.unLike(dto);
    }
}
