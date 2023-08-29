package com.heima.model.wemedia.dtos;

import lombok.Data;

import java.util.Date;


@Data
public class WmChannelDto {
    private Date createdTime;
    private String description;
    private Integer id;
    private boolean isDefault; //是否默认频道
    private String name;
    private Integer ord;//排序
    private boolean status; // 0 禁用 1 启用

}
