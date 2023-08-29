package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;


@Data
public class WmChannelPageDto extends PageRequestDto {
    /**
     * 搜索频道时的关键字
     */
    private String name;
}
