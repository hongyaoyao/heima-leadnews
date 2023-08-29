package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmChannelDto;
import com.heima.model.wemedia.dtos.WmChannelPageDto;
import com.heima.wemedia.service.impl.WmChannelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @PROJECT_NAME: heima-leadnews
 * @PACKAGE_NAME: com.heima.wemedia.controller.v1
 * @CLASS_NAME: WmChannelController
 * @USER: hongyaoyao
 * @DATETIME: 2023/8/21 21:11
 * @Emial: 1299694047@qq.com
 */
@RestController
@RequestMapping("/api/v1/channel")
public class WmChannelController {

    @Autowired
    private WmChannelServiceImpl wmChannelService;

    @GetMapping("/channels")
    public ResponseResult findAll(){
        return wmChannelService.findAll();
    }

    /**
     * 新增频道
     *
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public ResponseResult saveChannel(@RequestBody WmChannelDto dto) {
        return wmChannelService.saveChannel(dto);
    }

    /**
     * 分页查询：频道列表
     */
    @PostMapping("/list")
    public ResponseResult channelPage(@RequestBody WmChannelPageDto dto) {
        return wmChannelService.channelPage(dto);
    }

    /**
     * 分页查询：频道列表
     */
    @PostMapping("/update")
    public ResponseResult updateChannel(@RequestBody WmChannelDto dto) {
        return wmChannelService.updateChannel(dto);
    }

    /**
     * 删除频道
     */
    @GetMapping("/del/{id}")
    public ResponseResult delChannel(@PathVariable("id") Integer id) {
        return wmChannelService.delChannel(id);
    }

}
