package com.heima.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ApUserCollectionService;
import com.heima.common.constants.BehaviorConstants;
import com.heima.common.constants.HotArticleConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.article.dtos.ApUserCollectionDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.mess.UpdateArticleMess;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ApUserCollectionServiceImpl implements ApUserCollectionService {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    /**
     * 用户收藏与取消收藏
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult collection(ApUserCollectionDto dto) {

        //条件判断
        if (dto == null || dto.getEntryId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //判断是否登录
        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //查询:是否已经收藏
        String collectionJson = (String) cacheService.hGet(BehaviorConstants.COLLECTION_BEHAVIOR + user.getId(),
                dto.getEntryId().toString());

        if (StringUtils.isNotBlank(collectionJson) && dto.getOperation() == 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "已收藏");
        }

        //创建数据对象
        UpdateArticleMess updateArticleMess = new UpdateArticleMess();
        updateArticleMess.setArticleId(dto.getEntryId());
        updateArticleMess.setType(UpdateArticleMess.UpdateArticleType.COLLECTION);


        //收藏
        if (dto.getOperation() == 0) {
            log.info("文章收藏，保存key:{},{},{}", dto.getEntryId(), user.getId().toString(), JSON.toJSONString(dto));
            cacheService.hPut(BehaviorConstants.COLLECTION_BEHAVIOR + user.getId(),
                    dto.getEntryId().toString(),
                    JSON.toJSONString(dto));

//            //对当前文章的 收藏字段 +1
//            ApArticle apArticle = apArticleMapper.selectById(dto.getEntryId());
//            if (apArticle.getCollection()==null){
//                apArticle.setCollection(0);
//            }
//            apArticle.setCollection(apArticle.getCollection() + 1);
//            apArticleMapper.updateById(apArticle);

            //设置参数
            updateArticleMess.setAdd(1);

        } else {
            //取消收藏
            log.info("文章收藏，删除key:{},{},{}", dto.getEntryId(), user.getId().toString(), JSON.toJSONString(dto));
            cacheService.hDelete(BehaviorConstants.COLLECTION_BEHAVIOR + user.getId(),
                    dto.getEntryId().toString());

            //对当前文章的 收藏字段 -1
//            ApArticle apArticle = apArticleMapper.selectById(dto.getEntryId());
//            apArticle.setCollection(apArticle.getCollection() - 1);
//            apArticleMapper.updateById(apArticle);

            //设置参数
            updateArticleMess.setAdd(-1);

        }

        kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC,JSON.toJSONString(updateArticleMess));

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
