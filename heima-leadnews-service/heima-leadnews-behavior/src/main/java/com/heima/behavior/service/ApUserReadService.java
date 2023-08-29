package com.heima.behavior.service;

import com.heima.model.behavior.dtos.ApUserReadDto;
import com.heima.model.common.dtos.ResponseResult;


public interface ApUserReadService {
    /**
     * 用户阅读
     * 需要记录当前用户查看的次数，
     * 阅读时长（非必要），
     * 阅读文章的比例（非必要），
     * 加载的时长（非必要）
     * @param dto
     * @return
     */
    ResponseResult readBehavior(ApUserReadDto dto);
}
