package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmChannelDto;
import com.heima.model.wemedia.dtos.WmChannelPageDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {

    @Autowired
    private WmNewsMapper wmNewsMapper;


    /**
     * 查询所有频道
     *
     * @return
     */
    @Override
    public ResponseResult findAll() {
        return ResponseResult.okResult(list());
    }

    /**
     * 新增频道
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveChannel(WmChannelDto dto) {

        //校验参数：
        if (dto == null || StringUtils.isEmpty(dto.getName())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        //判断当前频道名称是否存在
        WmChannel one = getOne(Wrappers.<WmChannel>lambdaQuery()
                .eq(WmChannel::getName,dto.getName()));
        if (one != null){
            //已存在
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }

        WmChannel wmChannel = new WmChannel();
        BeanUtils.copyProperties(dto, wmChannel);
        //补全字段：
        wmChannel.setIsDefault(true);
        wmChannel.setCreatedTime(new Date());

        //保存到数据库
        save(wmChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }


    /**
     * 频道分页查询
     *
     * @param dto
     */
    @Override
    public ResponseResult channelPage(WmChannelPageDto dto) {

        //检查参数：
        dto.checkParam();

        //分页:
        IPage page = new Page(dto.getPage(), dto.getSize());
        //构建查询条件：
        if (StringUtils.isNotEmpty(dto.getName())) {
            LambdaQueryWrapper<WmChannel> lqw = Wrappers.<WmChannel>lambdaQuery()
                    .like(WmChannel::getName, dto.getName())
                    .orderByDesc(WmChannel::getCreatedTime);

            //带条件分页查询
            page = page(page, lqw);
        } else {
            LambdaQueryWrapper<WmChannel> lqw = Wrappers.<WmChannel>lambdaQuery()
                    .orderByDesc(WmChannel::getCreatedTime);
            page = page(page, lqw);
        }
        //返回数据
        ResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        result.setData(page.getRecords());
        result.setCode(200);
        return result;
    }

    /**
     * 修改频道信息
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult updateChannel(WmChannelDto dto) {

        //校验参数：
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        //判断当前是否有文章使用该频道
        List<WmNews> wmNewsList = wmNewsMapper.selectList(Wrappers.<WmNews>lambdaQuery()
                .eq(WmNews::getChannelId, dto.getId()));
        if (wmNewsList!=null && wmNewsList.size()>0){
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH,"当前有文章引用该频道");
        }

        //封装修改的参数
        WmChannel wmChannel = new WmChannel();
        BeanUtils.copyProperties(dto, wmChannel);
        wmChannel.setIsDefault(true);
        wmChannel.setCreatedTime(new Date());

        //修改
        updateById(wmChannel);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 删除频道
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult delChannel(Integer id) {
        //校验参数：
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        //查询当前频道
        WmChannel wmChannel = getById(id);
        //判断当前频道是否为 启用：
        if (wmChannel.getStatus()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH, "启用频道不可删除");
        } else {
            removeById(id);
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}