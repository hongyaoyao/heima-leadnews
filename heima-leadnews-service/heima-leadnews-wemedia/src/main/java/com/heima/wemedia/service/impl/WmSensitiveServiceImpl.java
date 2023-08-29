package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmSensitiveDto;
import com.heima.model.wemedia.dtos.WmSensitivePageDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.mapper.WmSensitiveMapper;
import com.heima.wemedia.service.WmSensitiveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class WmSensitiveServiceImpl extends ServiceImpl<WmSensitiveMapper, WmSensitive> implements WmSensitiveService {
    /**
     * 新增敏感词
     *
     * @return
     */
    @Override
    public ResponseResult saveSensitive(WmSensitiveDto dto) {

        //校验参数：
        if (dto == null || StringUtils.isEmpty(dto.getSensitives())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        //判断当前敏感词是否存在：
        WmSensitive one = getOne(Wrappers.<WmSensitive>lambdaQuery()
                .eq(WmSensitive::getSensitives, dto.getSensitives()));
        if (one != null){
            //已存在
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }

        //封装参数：
        WmSensitive wmSensitive = new WmSensitive();
        BeanUtils.copyProperties(dto, wmSensitive);
        wmSensitive.setCreatedTime(new Date());
        //添加：
        save(wmSensitive);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 分页查询敏感词
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findAllSensitive(WmSensitivePageDto dto) {
        //检查参数：
        dto.checkParam();

        //分页:
        IPage page = new Page(dto.getPage(), dto.getSize());
        //构建查询条件：
        if (StringUtils.isNotEmpty(dto.getName())) {
            LambdaQueryWrapper<WmSensitive> lqw = Wrappers.<WmSensitive>lambdaQuery()
                    .like(WmSensitive::getSensitives,dto.getName())
                    .orderByDesc(WmSensitive::getCreatedTime);

            //带条件分页查询
            page = page(page, lqw);
        } else {
            LambdaQueryWrapper<WmSensitive> lqw = Wrappers.<WmSensitive>lambdaQuery()
                    .orderByDesc(WmSensitive::getCreatedTime);
            page = page(page, lqw);
        }
        //返回数据
        ResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        result.setData(page.getRecords());
        result.setCode(200);
        return result;
    }

    /**
     * 修改敏感词
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult updateSensitive(WmSensitiveDto dto) {

        //校验参数：
        if (dto == null || StringUtils.isEmpty(dto.getSensitives())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE, "请输入更改的敏感词信息");
        }

        //判断当前敏感词是否存在：
        WmSensitive wmSensitive1 = getOne(Wrappers.<WmSensitive>lambdaQuery()
                .eq(WmSensitive::getSensitives, dto.getSensitives()));
        if (wmSensitive1 != null){
            //已存在
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }


        //封装修改的参数
        WmSensitive wmSensitive = new WmSensitive();
        BeanUtils.copyProperties(dto, wmSensitive);
        wmSensitive.setCreatedTime(new Date());

        //修改
        updateById(wmSensitive);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }


    /**
     * 删除敏感词
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult delSensitive(Integer id) {
        //校验参数：
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        //删除
        removeById(id);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
