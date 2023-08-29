package com.heima.model.article.dtos;

import com.heima.model.common.annotation.IdEncrypt;
import lombok.Data;

import java.util.Date;


@Data
public class ApUserCollectionDto {
    // 文章、动态ID
    @IdEncrypt
    private Long entryId;
    /**
     * 收藏内容类型
     * 0文章
     * 1动态
     */
    private Short type;

    /**
     * 操作类型
     * 0收藏
     * 1取消收藏
     */
    private Short operation;

    private Date publishedTime;

}
