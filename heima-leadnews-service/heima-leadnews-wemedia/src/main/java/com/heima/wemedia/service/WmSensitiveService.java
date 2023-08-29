package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmSensitiveDto;
import com.heima.model.wemedia.dtos.WmSensitivePageDto;
import com.heima.model.wemedia.pojos.WmSensitive;

public interface WmSensitiveService extends IService<WmSensitive> {

    /**
     * 新增敏感词
     * @return
     */
    ResponseResult saveSensitive(WmSensitiveDto dto);

    /**
     * 分页查询敏感词
     * @param dto
     * @return
     */
    ResponseResult findAllSensitive(WmSensitivePageDto dto);

    /**
     * 修改敏感词
     * @param dto
     * @return
     */
    ResponseResult updateSensitive(WmSensitiveDto dto);


    /**
     * 删除敏感词
     * @param id
     * @return
     */
    ResponseResult delSensitive(Integer id);
}
