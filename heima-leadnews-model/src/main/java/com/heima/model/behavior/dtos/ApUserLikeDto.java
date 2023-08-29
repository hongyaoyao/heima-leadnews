package com.heima.model.behavior.dtos;

import com.heima.model.common.annotation.IdEncrypt;
import lombok.Data;


@Data
public class ApUserLikeDto {

    @IdEncrypt
    private Long articleId;
    /**
     * operation
     * <p>
     * 0 点赞   1 取消点赞
     */
    private Short operation;
    /**
     * 0文章  1动态   2评论
     */
    private Short type;
}
