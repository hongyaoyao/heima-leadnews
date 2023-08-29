package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.*;
import com.heima.model.wemedia.pojos.WmNews;
import org.springframework.web.bind.annotation.RequestBody;

public interface WmNewsService extends IService<WmNews> {

    /**
     * 条件查询文章列表
     * @param dto
     * @return
     */
    public ResponseResult findList(@RequestBody WmNewsPageReqDto dto);

    /**
     * 发布、修改文章或保存为草稿
     * @param dto
     * @return
     */
    public ResponseResult submitNews(@RequestBody WmNewsDto dto);

    /**
     * 文章的上下架
     * @param dto
     * @return
     */
    public ResponseResult downOrUp(WmNewsDownOrUpDto dto);

    /**
     * 后台管理端人工审核：分页查询
     * @param dto
     * @return
     */
    ResponseResult findAll(WmNewsScanPageDto dto);


    /**
     * 后台管理端查询文章详情
     * @param id
     * @return
     */
    ResponseResult findById(Integer id);


    /**
     * 后台管理端人工审核文章
     * @param dto
     * @param status
     * @return
     */
    ResponseResult updateAuth(WmNewsAdminAuthDto dto, short status);



//    /**
//     * 人工审核失败：
//     * @param dto
//     * @return
//     */
//    ResponseResult authFail(WmNewsAdminAuthDto dto);
//
//
//    /**
//     * 人工审核成功
//     * @param dto
//     * @return
//     */
//    ResponseResult authPass(WmNewsAdminAuthDto dto);
}
