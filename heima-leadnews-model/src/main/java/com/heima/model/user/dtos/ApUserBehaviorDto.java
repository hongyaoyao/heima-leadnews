package com.heima.model.user.dtos;

import com.heima.model.common.annotation.IdEncrypt;
import lombok.Data;

/**
 * @Author ItZheng
 * @Date 2023/6/20 21:27
 * @Version 1.5
 */
@Data
public class ApUserBehaviorDto {
    /**
     * 文章ID
     */
    @IdEncrypt
    private Long articleId;
    /**
     * 文章作者ID
     */
    @IdEncrypt
    private Integer authorId;
    /**
     * 是否关注：
     * 0:关注
     * 1：取消
     */
    private Short operation;
}
