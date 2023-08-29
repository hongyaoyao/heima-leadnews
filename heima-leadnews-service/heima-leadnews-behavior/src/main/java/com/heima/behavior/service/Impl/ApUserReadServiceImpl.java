package com.heima.behavior.service.Impl;

import com.alibaba.fastjson.JSON;
import com.heima.behavior.service.ApUserReadService;
import com.heima.common.constants.BehaviorConstants;
import com.heima.common.constants.HotArticleConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.behavior.dtos.ApUserReadDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ItZheng
 * @Date 2023/6/21 14:24
 * @Version 1.5
 */
@Service
@Slf4j
@Transactional
public class ApUserReadServiceImpl implements ApUserReadService {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 用户阅读
     * 需要记录当前用户查看的次数，
     * 阅读时长（非必要），
     * 阅读文章的比例（非必要），
     * 加载的时长（非必要）
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult readBehavior(ApUserReadDto dto) {

        //1.检查参数
        if (dto == null || dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.是否登录
        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //更新阅读次数
        String readBehaviorJson = (String) cacheService.hGet(BehaviorConstants.READ_BEHAVIOR + dto.getArticleId().toString(),
                user.getId().toString());
        if (StringUtils.isNotBlank(readBehaviorJson)) {
            ApUserReadDto apUserReadDto = JSON.parseObject(readBehaviorJson, ApUserReadDto.class);
            dto.setCount((short) (apUserReadDto.getCount() + dto.getCount()));
        }
        // 保存当前key
        log.info("保存当前key:{} {} {}", dto.getArticleId(), user.getId(), dto);
        cacheService.hPut(BehaviorConstants.READ_BEHAVIOR + dto.getArticleId().toString(),
                user.getId().toString(),
                JSON.toJSONString(dto));

        //创建数据对象
        UpdateArticleMess updateArticleMess = new UpdateArticleMess();
        updateArticleMess.setArticleId(dto.getArticleId());
        updateArticleMess.setType(UpdateArticleMess.UpdateArticleType.VIEWS);
        updateArticleMess.setAdd(1);
        //发送信息
        kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC,JSON.toJSONString(updateArticleMess));

/**
        //发送kafka消息，给article表，对当前文章阅读字段 +1
        Map map = new HashMap();
        map.put("articleId", dto.getArticleId());
        map.put("ReadScore", 1);
        kafkaTemplate.send("ARTICLE_READ_TOPOC", JSON.toJSONString(map));
*/
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
