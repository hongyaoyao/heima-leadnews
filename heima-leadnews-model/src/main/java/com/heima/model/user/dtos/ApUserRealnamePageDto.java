package com.heima.model.user.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * @Author ItZheng
 * @Date 2023/6/20 16:48
 * @Version 1.5
 */
@Data
public class ApUserRealnamePageDto extends PageRequestDto {
    private Integer id;
    private String msg;//拒绝原因
    /**
     * 状态
     0 创建中
     1 待审核
     2 审核失败
     9 审核通过
     */
    private Short status;
}
