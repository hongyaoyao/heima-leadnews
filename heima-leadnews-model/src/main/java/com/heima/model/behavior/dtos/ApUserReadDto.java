package com.heima.model.behavior.dtos;

import lombok.Data;


@Data
public class ApUserReadDto {
    // 文章、动态、评论等ID
    private Long articleId;

    /**
     * 阅读次数
     */
    private Short count;

    /**
     * 阅读时长（S)
     */
    private Integer readDuration;

    /**
     * 阅读百分比
     */
    private  Short percentage;

    /**
     * 加载时间
     */
    private Short loadDuration;
}
