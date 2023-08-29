package com.heima.article.controller.v1;

import com.heima.article.service.ApUserCollectionService;
import com.heima.model.article.dtos.ApUserCollectionDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/collection_behavior")
public class ApUserCollectionController {
    @Autowired
    private ApUserCollectionService apUserCollectionService;


    /**
     * 用户收藏与取消收藏
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseResult collection(@RequestBody ApUserCollectionDto dto) {
        return apUserCollectionService.collection(dto);
    }
}
