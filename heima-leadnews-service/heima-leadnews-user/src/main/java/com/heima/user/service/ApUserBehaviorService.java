package com.heima.user.service;


import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.ApUserBehaviorDto;


public interface ApUserBehaviorService  {
    /**
     * 用户关注行为（关注与取消关注）
     * operation : 0 关注  1 取消
     * @param dto
     * @return
     */
    ResponseResult ApUserFollow(ApUserBehaviorDto dto);
}
