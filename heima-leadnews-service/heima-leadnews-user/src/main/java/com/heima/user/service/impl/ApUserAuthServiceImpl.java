package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.apis.wemedia.IWemediaClient;
import com.heima.common.constants.ApUserAuth;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.ApUserAuthDto;
import com.heima.model.user.dtos.ApUserRealnamePageDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserRealname;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.user.mapper.ApUserAuthMapper;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;


@Service
@Slf4j
@Transactional
public class ApUserAuthServiceImpl extends ServiceImpl<ApUserAuthMapper, ApUserRealname> implements ApUserAuthService {

    @Autowired
    private ApUserMapper apUserMapper;

    @Autowired
    private IWemediaClient wemediaClient;

    /**
     * 分页查询审核列表：
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findAllAuth(ApUserRealnamePageDto dto) {
        //检查参数：
        dto.checkParam();

        //分页:
        IPage page = new Page(dto.getPage(), dto.getSize());

        //构建查询条件：
        if (dto.getStatus() != null) {
            LambdaQueryWrapper<ApUserRealname> lqw = Wrappers.<ApUserRealname>lambdaQuery()
                    .eq(ApUserRealname::getStatus, dto.getStatus())
                    .orderByDesc(ApUserRealname::getCreatedTime);

            //带条件分页查询
            page = page(page, lqw);
        } else {
            LambdaQueryWrapper<ApUserRealname> lqw = Wrappers.<ApUserRealname>lambdaQuery()
                    .orderByDesc(ApUserRealname::getCreatedTime);
            page = page(page, lqw);
        }

        //返回数据
        ResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        result.setData(page.getRecords());
        result.setCode(200);
        return result;
    }

    /**
     * 审核用户
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult updateAuth(ApUserAuthDto dto, Short status) {
        //1.检查参数
        if(dto == null || dto.getId() == null || status == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.封装参数
        ApUserRealname apUserRealname = new ApUserRealname();
        apUserRealname.setId(dto.getId());
        apUserRealname.setStatus(status);
        apUserRealname.setUpdatedTime(new Date());
        if(StringUtils.isNotBlank(dto.getMsg())){
            //不为空：则为拒绝，封装拒绝理由
            apUserRealname.setReason(dto.getMsg());
        }
        //修改：
        updateById(apUserRealname);

        //3.如果审核状态是9，就是成功，需要创建自媒体账户
        if(ApUserAuth.AP_USER_PASS_AUTH.equals(status)){
            ResponseResult responseResult = createWmUser(dto);
            if(responseResult != null){
                return responseResult;
            }
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 创建自媒体账户
     * @param dto
     * @return
     */
    private ResponseResult createWmUser(ApUserAuthDto dto) {
        //获取用户的ID:
        Integer userRealnameId = dto.getId();
        //查询用户认证信息
        ApUserRealname apUserRealname = getById(userRealnameId);
        if(apUserRealname == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //查询app端用户信息
        Integer userId = apUserRealname.getUserId();
        ApUser apUser = apUserMapper.selectById(userId);
        if(apUser == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }


        //创建自媒体账户
        //检查是否已经是自媒体人
        WmUser wmUser = wemediaClient.findWmUserByName(apUser.getName());
        if(wmUser == null){
            //封装参数
            wmUser= new WmUser();
            wmUser.setApUserId(apUser.getId());
            wmUser.setCreatedTime(new Date());
            wmUser.setName(apUser.getName());
            wmUser.setPassword(apUser.getPassword());
            wmUser.setSalt(apUser.getSalt());
            wmUser.setPhone(apUser.getPhone());
            wmUser.setStatus(9);
            wemediaClient.saveWmUser(wmUser);
        }

        //修改APuser 状态为 自媒体人
        apUser.setFlag((short)1);
        apUserMapper.updateById(apUser);

        return null;

    }




//    /**
//     * 通过用户审核
//     * @param dto
//     * @return
//     */
//    @Override
//    public ResponseResult authPassAuth(ApUserAuthDto dto ) {
//
//        //校验参数：
//        if (dto.getId() == null) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
//        }
//
//        Short status =9;
//        String msg = "人工审核通过";
//        ApUserRealname apUserRealname = adminAuth(dto.getId(),status, msg);
//
//        //修改 用户状态： 以身份认证， 用户类型改为 自媒体人
//        ApUser apUser = apUserMapper.selectById(dto.getId());
//        //用户类型：自媒体人
//        apUser.setFlag((short) 1);
//        //以身份认证
//        apUser.setIdentityAuthentication(true);
//        //修改
//        apUserMapper.updateById(apUser);
//
//
//        //修改
//        updateById(apUserRealname);
//
//        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
//    }
//
//
//    /**
//     * 拒绝用户审核
//     * @param dto
//     * @return
//     */
//    @Override
//    public ResponseResult authFailAuth(ApUserAuthDto dto) {
//
//        //校验参数：
//        if (dto.getId() == null) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
//        }
//
//        Short status =2;
//        ApUserRealname apUserRealname = adminAuth(dto.getId(),status, dto.getMsg());
//
//        //修改
//        updateById(apUserRealname);
//
//        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
//    }
//
//    /**
//     * 抽取用户审核：
//     * @return
//     */
//    private ApUserRealname adminAuth(Integer id,Short status,String msg){
//        //根据ID查询
//        ApUserRealname apUserRealname = getById(id);
//
//        //封装参数:
//        apUserRealname.setStatus(status);
//
//        apUserRealname.setReason(msg);
//        apUserRealname.setUpdatedTime(new Date());
//
//        return apUserRealname;
//    }
}
