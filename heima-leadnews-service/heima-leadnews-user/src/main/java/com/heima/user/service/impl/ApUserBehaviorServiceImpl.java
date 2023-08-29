package com.heima.user.service.impl;

import com.heima.common.constants.BehaviorConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.ApUserBehaviorDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.service.ApUserBehaviorService;
import com.heima.utils.thread.AppThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class ApUserBehaviorServiceImpl implements ApUserBehaviorService {


    @Autowired
    private CacheService cacheService;

    /**
     * 用户关注行为（关注与取消关注）
     * operation : 0 关注  1 取消
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult ApUserFollow(ApUserBehaviorDto dto) {

        //校验参数：
        if (dto == null || dto.getOperation() != 0 && dto.getOperation() != 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        //获取当前登录用户的ID
        ApUser user = AppThreadLocalUtil.getUser();
        //判断是否为注册用户，游客无法进行关注操作
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH, "游客无法进行关注操作");
        }
        Integer userId = user.getId();

        /**
         * 一个用户可以多个关注
         * 一个博主可以有多个粉丝
         *
         * 思路：每个用户（博主）一个redis key相同 value不同， 加个字段做 排序
         */

        //判断是关注还是取关：  关注：将当前用户 写入作者 粉丝中 ，并且将 作者写入 当前用户关注中
        if (dto.getOperation() == 0) {
            //写入当前作者 到 当前用户的 关注中
            cacheService.zAdd(BehaviorConstants.APUSER_FOLLOW_RELATION + userId,
                    dto.getAuthorId().toString(), System.currentTimeMillis());
            //写入当前用户 到 当前作者的 粉丝中
            cacheService.zAdd(BehaviorConstants.APUSER_FANS_RELATION+dto.getAuthorId(),
                    userId.toString(),System.currentTimeMillis());

        } else {
            //将 作者粉丝中删除 该用户 ，该用户关注列表中删除 该作者
            //删除 粉丝 表
            cacheService.zRemove(BehaviorConstants.APUSER_FANS_RELATION+dto.getAuthorId(),
                    userId.toString());
            //删除关注表
            cacheService.zRemove(BehaviorConstants.APUSER_FOLLOW_RELATION + userId,
                    dto.getAuthorId().toString());
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
