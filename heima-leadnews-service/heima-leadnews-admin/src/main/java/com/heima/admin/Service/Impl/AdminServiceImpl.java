package com.heima.admin.Service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.Service.AdminService;
import com.heima.admin.mapper.AdminMapper;
import com.heima.model.admin.dtos.LoginDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, AdUser> implements AdminService {

    /**
     * 管理员登录
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(LoginDto dto) {

        //1:正常登录：
        //1.1:判断：传过来的 phone 和 password 是否为空 ：
        //StringUtils: lang3 中的 StringUtils
        if (StringUtils.isNotEmpty(dto.getName()) && StringUtils.isNotEmpty(dto.getPassword())) {
            //1.2： phone 和 password 不为空：
            //根据 Name 查询
            AdUser adUser = getOne(Wrappers.<AdUser>lambdaQuery().eq(AdUser::getName, dto.getName()));

            if (adUser == null) {
                //查询不到，返回：用户名不存在
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户名不存在");
                //自定义：返回的msg
            }

            //查询到 ： 获取对应的盐字段的值，并且：将传来的password与盐拼接，MD5加密后与数据库重的 password比对
            String md5Password = DigestUtils.md5DigestAsHex((dto.getPassword() + adUser.getSalt()).getBytes());

            if (adUser.getPassword().equals(md5Password)) {
                //密码匹配：使用JWT工具类：生成token， 返回用户信息和token 并封装进 Map
                String token = AppJwtUtil.getToken(Long.valueOf(adUser.getId()));
                Map result = new HashMap<>();
                result.put("token", token);
                //User封装：方法一：将 salt 和 password 设置为 null
                adUser.setSalt(null);
                adUser.setPassword(null);
                result.put("user",adUser);

                return ResponseResult.okResult(result);
            }
            //匹配失败：返回：密码错误：
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
    }
}
