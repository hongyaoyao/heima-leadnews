package com.heima.article.service;

import com.heima.model.article.dtos.ApUserCollectionDto;
import com.heima.model.common.dtos.ResponseResult;


public interface ApUserCollectionService {
    /**
     * 用户收藏与取消收藏
     * @param dto
     * @return
     */
    ResponseResult collection(ApUserCollectionDto dto);
}
