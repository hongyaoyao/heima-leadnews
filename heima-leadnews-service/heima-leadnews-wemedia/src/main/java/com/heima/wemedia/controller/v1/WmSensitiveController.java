package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmSensitiveDto;
import com.heima.model.wemedia.dtos.WmSensitivePageDto;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.service.WmSensitiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/sensitive")
@Slf4j
public class WmSensitiveController {

    @Autowired
    private WmSensitiveService wmSensitiveService;

    /**
     * 新增敏感词
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public ResponseResult saveSensitive(@RequestBody WmSensitiveDto dto) {
        return wmSensitiveService.saveSensitive(dto);
    }


    /**
     * 分页查询敏感词
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult findAllSensitive(@RequestBody WmSensitivePageDto dto) {
        return wmSensitiveService.findAllSensitive(dto);
    }


    /**
     * 分页查询敏感词
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public ResponseResult updateSensitive(@RequestBody WmSensitiveDto dto) {
        return wmSensitiveService.updateSensitive(dto);
    }

    /**
     * 删除敏感词
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public ResponseResult delSensitive(@PathVariable("id") Integer id) {
        return wmSensitiveService.delSensitive(id);
    }

}
