package com.heima.behavior.service;

import com.heima.model.behavior.dtos.ApUserLikeDto;
import com.heima.model.common.dtos.ResponseResult;


public interface ApUserLikeService {
    /**
     * 用户点赞或取消
     * @param dto
     * @return
     */
    ResponseResult likeOrUnlike(ApUserLikeDto dto);
}
