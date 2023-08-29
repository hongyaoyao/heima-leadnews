package com.heima.model.wemedia.dtos;

import lombok.Data;

/**
 * @PROJECT_NAME: heima-leadnews
 * @PACKAGE_NAME: com.heima.model.wemedia.dtos
 * @CLASS_NAME: WmNewsDownOrUpDto
 * @USER: hongyaoyao
 * @DATETIME: 2023/8/24 20:07
 * @Emial: 1299694047@qq.com
 */
@Data
public class WmNewsDownOrUpDto {

    private Integer id;
    /**
     * 是否上架  0 下架  1 上架
     */
    private Short enable;
}
