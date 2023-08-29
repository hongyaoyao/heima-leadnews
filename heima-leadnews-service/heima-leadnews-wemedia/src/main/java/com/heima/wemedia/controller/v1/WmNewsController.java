package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.*;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @PROJECT_NAME: heima-leadnews
 * @PACKAGE_NAME: com.heima.wemedia.controller.v1
 * @CLASS_NAME: WmNewsController
 * @USER: hongyaoyao
 * @DATETIME: 2023/8/21 21:21
 * @Emial: 1299694047@qq.com
 */
@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {

    @Autowired
    private WmNewsService wmNewsService;

    /**
     * 自媒体端分页查询自媒体文章
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody WmNewsPageReqDto dto){
        return wmNewsService.findList(dto);
    }

    /**
     * 自媒体端发布文章
     * @param dto
     * @return
     */
    @PostMapping("/submit")
    public ResponseResult submitNews(@RequestBody WmNewsDto dto){
        return wmNewsService.submitNews(dto);
    }

    /**
     * 自媒体端文章上下架
     * @param dto
     * @return
     */
    @PostMapping("/down_or_up")
    public ResponseResult downOrUp(@RequestBody WmNewsDownOrUpDto dto){
        return wmNewsService.downOrUp(dto);
    }

    /**
     * 后台管理端分页查询所有文章
     * @param dto
     * @return
     */
    @PostMapping("/list_vo")
    public ResponseResult findAll(@RequestBody WmNewsScanPageDto dto){
        return wmNewsService.findAll(dto);
    }

    /**
     * 后台管理端查询文章详情
     * @param
     * @return
     */
    @GetMapping("one_vo/{id}")
    public ResponseResult findById(@PathVariable("id") Integer id){
        return wmNewsService.findById(id);
    }

    /**
     * 后台管理端人工审核失败：
     * @param dto
     * @return
     */
    @PostMapping("/auth_fail")
    public ResponseResult authFail(@RequestBody WmNewsAdminAuthDto dto){
        return wmNewsService.updateAuth(dto, WmNews.Status.FAIL.getCode());
    }

    /**
     * 后台管理端人工审核成功：
     * @param dto
     * @return
     */
    @PostMapping("/auth_pass")
    public ResponseResult authPass(@RequestBody WmNewsAdminAuthDto dto){
        return wmNewsService.updateAuth(dto,WmNews.Status.ADMIN_SUCCESS.getCode());
    }
}
