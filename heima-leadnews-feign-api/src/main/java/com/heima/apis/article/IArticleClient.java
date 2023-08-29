package com.heima.apis.article;

import com.heima.apis.article.fallback.IArticleClientFallBack;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @PROJECT_NAME: heima-leadnews
 * @PACKAGE_NAME: com.heima.apis.article
 * @CLASS_NAME: IArticleClient
 * @USER: hongyaoyao
 * @DATETIME: 2023/8/22 15:06
 * @Emial: 1299694047@qq.com
 */
@FeignClient(value = "leadnews-article", fallback = IArticleClientFallBack.class)
public interface IArticleClient {

    @PostMapping("/api/v1/article/save")
    public ResponseResult saveArticle(ArticleDto dto);
}
