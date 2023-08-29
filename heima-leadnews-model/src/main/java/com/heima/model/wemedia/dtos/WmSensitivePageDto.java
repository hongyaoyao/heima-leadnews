package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class WmSensitivePageDto extends PageRequestDto {
    /**
     * 要搜索的敏感词关键字
     */
    private String name;
}
