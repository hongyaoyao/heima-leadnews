package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * @PROJECT_NAME: heima-leadnews
 * @PACKAGE_NAME: com.heima.model.wemedia.dtos
 * @CLASS_NAME: WmMaterialDto
 * @USER: hongyaoyao
 * @DATETIME: 2023/8/21 18:04
 * @Emial: 1299694047@qq.com
 */

@Data
public class WmMaterialDto extends PageRequestDto {

    /**
     *  1    收藏
     *  2   未收藏
     */
    private Short isCollection;
}
