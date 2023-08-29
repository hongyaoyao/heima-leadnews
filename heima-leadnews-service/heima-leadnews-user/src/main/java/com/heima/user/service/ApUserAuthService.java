package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.ApUserAuthDto;
import com.heima.model.user.dtos.ApUserRealnamePageDto;
import com.heima.model.user.pojos.ApUserRealname;


public interface ApUserAuthService extends IService<ApUserRealname> {

    /**
     * 分页查询审核列表：
     * @param dto
     * @return
     */
    ResponseResult findAllAuth(ApUserRealnamePageDto dto);

    /**
     * 审核用户
     * @param dto
     * @return
     */
    ResponseResult updateAuth(ApUserAuthDto dto, Short status);


//    /**
//     * 通过用户审核
//     * @param dto
//     * @return
//     */
//    ResponseResult authPassAuth(ApUserAuthDto dto );
//
//    /**
//     * 拒绝用户审核
//     * @param dto
//     * @return
//     */
//    ResponseResult authFailAuth(ApUserAuthDto dto);
}
