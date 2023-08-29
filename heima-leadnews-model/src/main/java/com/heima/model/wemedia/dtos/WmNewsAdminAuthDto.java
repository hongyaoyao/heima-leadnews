package com.heima.model.wemedia.dtos;

import lombok.Data;


@Data
public class WmNewsAdminAuthDto {
    private Integer id;
    //原因（失败，成功）
    private String msg;
}
