package com.heima.model.user.dtos;

import lombok.Data;


@Data
public class ApUserAuthDto {
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
