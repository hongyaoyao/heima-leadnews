package com.heima.model.article.dtos;

import lombok.Data;

import java.util.Date;

/**
 * @PROJECT_NAME: heima-leadnews
 * @PACKAGE_NAME: com.heima.model.article.dtos
 * @CLASS_NAME: ArticleHomeDto
 * @USER: hongyaoyao
 * @DATETIME: 2023/8/19 21:53
 * @Emial: 1299694047@qq.com
 */
@Data
public class ArticleHomeDto {

    // 最大时间
    Date maxBehotTime;
    // 最小时间
    Date minBehotTime;
    // 分页size
    Integer size;
    // 频道ID
    String tag;
}
