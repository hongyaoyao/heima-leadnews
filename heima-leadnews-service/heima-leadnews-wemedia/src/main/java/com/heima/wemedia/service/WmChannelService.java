package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmChannelDto;
import com.heima.model.wemedia.dtos.WmChannelPageDto;
import com.heima.model.wemedia.pojos.WmChannel;

public interface WmChannelService extends IService<WmChannel> {

    /**
     * 查询所有频道
     * @return
     */
    public ResponseResult findAll();

    /**
     * 新增频道
     * @param dto
     * @return
     */
    ResponseResult saveChannel(WmChannelDto dto);

    /**
     * 频道分页查询
     * @param dto
     */
    ResponseResult channelPage(WmChannelPageDto dto);

    /**
     * 修改频道信息
     * @param dto
     * @return
     */
    ResponseResult updateChannel(WmChannelDto dto);

    /**
     * 删除频道
     * @param id
     * @return
     */
    ResponseResult delChannel(Integer id);
}