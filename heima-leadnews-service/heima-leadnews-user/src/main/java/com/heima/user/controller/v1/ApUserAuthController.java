package com.heima.user.controller.v1;

import com.heima.common.constants.ApUserAuth;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.ApUserAuthDto;
import com.heima.model.user.dtos.ApUserRealnamePageDto;
import com.heima.user.service.ApUserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class ApUserAuthController {

    @Autowired
    private ApUserAuthService apUserAuthService;

    /**
     * 分页查询用户申请
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult findAllAuth(@RequestBody ApUserRealnamePageDto dto) {
        return apUserAuthService.findAllAuth(dto);
    }

    /**
     * 驳回用户申请
     */
    @PostMapping("/authFail")
    public ResponseResult authFailAuth(@RequestBody ApUserAuthDto dto) {
        return apUserAuthService.updateAuth(dto, ApUserAuth.AP_USER_FAIL_AUTH);
    }

    /**
     * 通过用户申请
     */
    @PostMapping("/authPass")
    public ResponseResult authPassAuth(@RequestBody ApUserAuthDto dto) {
        return apUserAuthService.updateAuth(dto, ApUserAuth.AP_USER_PASS_AUTH);
    }
}
