package com.heima.behavior.controller;

import com.heima.behavior.service.ApUserLikeService;
import com.heima.model.behavior.dtos.ApUserLikeDto;
import com.heima.model.common.dtos.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/likes_behavior")
@Slf4j
public class ApUserLikeController {

    @Autowired
    private ApUserLikeService apUserLikeService;

    @PostMapping
    public ResponseResult likeOrUnlike(@RequestBody ApUserLikeDto dto){

        return apUserLikeService.likeOrUnlike(dto);
    }

}
