package com.heima.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.pojos.ApArticleContent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @PROJECT_NAME: heima-leadnews
 * @PACKAGE_NAME: com.heima.article.mapper
 * @CLASS_NAME: ApArticleContentMapper
 * @USER: hongyaoyao
 * @DATETIME: 2023/8/20 17:40
 * @Emial: 1299694047@qq.com
 */
@Mapper
@Repository
public interface ApArticleContentMapper extends BaseMapper<ApArticleContent> {
}
