package com.heima.behavior.controller;

import com.heima.behavior.service.ApUserReadService;
import com.heima.model.behavior.dtos.ApUserReadDto;
import com.heima.model.common.dtos.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ItZheng
 * @Date 2023/6/21 14:19
 * @Version 1.5
 */
@RestController
@RequestMapping("/api/v1/read_behavior")
@Slf4j
public class ApUserReadController {


    @Autowired
    private ApUserReadService apUserReadService;

    @PostMapping
    public ResponseResult readBehavior(@RequestBody ApUserReadDto dto) {
        return apUserReadService.readBehavior(dto);
    }

}
