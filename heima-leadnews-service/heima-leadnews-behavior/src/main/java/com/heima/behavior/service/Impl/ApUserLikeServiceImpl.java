package com.heima.behavior.service.Impl;

import com.alibaba.fastjson.JSON;
import com.heima.behavior.service.ApUserLikeService;
import com.heima.common.constants.BehaviorConstants;
import com.heima.common.constants.HotArticleConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.behavior.dtos.ApUserLikeDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.mess.UpdateArticleMess;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@Transactional
public class ApUserLikeServiceImpl implements ApUserLikeService {

    //注入Redis工具：
    @Autowired
    private CacheService cacheService;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    /**
     * 用户点赞或取消
     * @param dto
     * @return
     */
    @Override
    public ResponseResult likeOrUnlike(ApUserLikeDto dto) {
        //检查参数
        if (dto == null || dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        if (dto.getOperation() != 0 && dto.getOperation() != 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        if (dto.getType() != 0 && dto.getType() != 1 && dto.getType() != 2) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //判断当前是否是正常登录用户：
        Integer userId = AppThreadLocalUtil.getUser().getId();
        if (userId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }

        //创建数据对象
        UpdateArticleMess updateArticleMess = new UpdateArticleMess();
        updateArticleMess.setArticleId(dto.getArticleId());
        updateArticleMess.setType(UpdateArticleMess.UpdateArticleType.LIKES);

        //3.点赞  保存数据
        if (dto.getOperation() == 0) {
            Object obj = cacheService.hGet(BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(),
                   userId.toString());
            if (obj != null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "已点赞");
            }
            // 保存当前key
            log.info("保存当前key:{} ,{}, {}", dto.getArticleId(), userId, dto);
            cacheService.hPut(BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(),
                    userId.toString(), JSON.toJSONString(dto));

            //发送kafka消息，给article表，对当前文章喜欢字段 +1
            //sendKafka(dto.getArticleId(),1);

            //设置参数
            updateArticleMess.setAdd(1);

        } else {
            // 删除当前key
            log.info("删除当前key:{}, {}", dto.getArticleId(), userId);
            cacheService.hDelete(BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(),
                   userId.toString());
            //发送kafka消息，给article表，对当前文章喜欢字段 -1
            //sendKafka(dto.getArticleId(),-1);

            //设置参数
            updateArticleMess.setAdd(-1);
        }
        //发送信息
        kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC,JSON.toJSONString(updateArticleMess));

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
    //发kafka消息：
    private void sendKafka(Long articleId,Integer score){
        //发送kafka消息，给article表，对当前文章喜欢字段 +1
        Map map = new HashMap();
        map.put("articleId",articleId);
        map.put("likeScore",score);
        kafkaTemplate.send("ARTICLE_LIKE_TOPOC",JSON.toJSONString(map));
    }
     */
}
