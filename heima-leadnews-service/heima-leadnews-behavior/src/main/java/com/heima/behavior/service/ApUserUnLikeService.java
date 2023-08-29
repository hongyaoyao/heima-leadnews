package com.heima.behavior.service;

import com.heima.model.behavior.dtos.ApUserUnLikeDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @Author ItZheng
 * @Date 2023/6/21 14:53
 * @Version 1.5
 */
public interface ApUserUnLikeService {
    /**
     * 不喜欢操作
     * @param dto
     * @return
     */
    ResponseResult unLike(ApUserUnLikeDto dto);
}
