package com.easytest.controller;

import java.util.List;

import com.easytest.entity.enums.VerifyRegexEnum;
import com.easytest.entity.query.UserInfoQuery;
import com.easytest.entity.po.UserInfo;
import com.easytest.entity.vo.ResponseVO;
import com.easytest.exception.BusinessException;
import com.easytest.service.UserInfoService;
import com.easytest.utils.StringTools;
import com.easytest.utils.VerifyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户信息 Controller
 */
@RestController("userInfoController")
@RequestMapping("/userInfo")
public class UserInfoController extends ABaseController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 根据条件分页查询
     */
    @RequestMapping("/loadDataList")
    public ResponseVO loadDataList(UserInfoQuery query) {
        return getSuccessResponseVO(userInfoService.findListByPage(query));
    }

    /**
     * 批量新增
     */
    @RequestMapping("/addBatch")
    public ResponseVO addBatch(@RequestBody List<UserInfo> listBean) {
        userInfoService.addBatch(listBean);
        return getSuccessResponseVO(null);
    }

    /**
     * 批量新增/修改
     */
    @RequestMapping("/addOrUpdateBatch")
    public ResponseVO addOrUpdateBatch(@RequestBody List<UserInfo> listBean) {
        userInfoService.addBatch(listBean);
        return getSuccessResponseVO(null);
    }

    /**
     * 根据UserId查询对象
     */
    @RequestMapping("/getUserInfoByUserId")
    public ResponseVO getUserInfoByUserId(Integer userId) {
        return getSuccessResponseVO(userInfoService.getUserInfoByUserId(userId));
    }

    /**
     * 根据UserId修改对象
     */
    @RequestMapping("/updateUserInfoByUserId")
    public ResponseVO updateUserInfoByUserId(UserInfo bean, Integer userId) {
        userInfoService.updateUserInfoByUserId(bean, userId);
        return getSuccessResponseVO(null);
    }

    /**
     * 根据UserId删除
     */
    @RequestMapping("/deleteUserInfoByUserId")
    public ResponseVO deleteUserInfoByUserId(Integer userId) {
        userInfoService.deleteUserInfoByUserId(userId);
        return getSuccessResponseVO(null);
    }

    //@Jagger

    /**
     * 新增 或 修改
     */
    @RequestMapping("/saveUser")
    public ResponseVO saveUser(UserInfo userInfo) {
        if (userInfo.getUserId() == null && (StringTools.isEmpty(userInfo.getPassword()) || !VerifyUtils.verify(VerifyRegexEnum.PASSWORD, userInfo.getPassword()))) {
            throw new BusinessException("密码不能为空，只能是数字，字母，特殊字符，8-18位");
        }
        if (StringTools.isEmpty(userInfo.getUserName()) || userInfo.getUserName().length() > 10) {
            throw new BusinessException("姓名不能为空，且长度不能超过10");
        }
        if (StringTools.isEmpty(userInfo.getPhone()) || !VerifyUtils.verify(VerifyRegexEnum.PHONE, userInfo.getPhone())) {
            throw new BusinessException("手机号不能为空，且格式要正确");
        }
        if (userInfo.getBirthday() == null) {
            throw new BusinessException("出生日期不能为空");
        }
        if (userInfo.getSex() == null) {
            throw new BusinessException("性别不能为空");
        }
        if (userInfo.getPosition() == null) {
            throw new BusinessException("职位不能为空");
        }
        if (StringTools.isEmpty(userInfo.getRoles())) {
            throw new BusinessException("角色不能为空");
        }

        userInfoService.saveUserInfo(userInfo);

        return getSuccessResponseVO(null);
    }

    /**
     * 修改密码
     */
    @RequestMapping("/updatePassword")
    public ResponseVO updatePassword(String password, Integer userId) {
        if (StringTools.isEmpty(password) || !VerifyUtils.verify(VerifyRegexEnum.PASSWORD, password)) {
            throw new BusinessException("密码不能为空，只能是数字，字母，特殊字符，8-18位");
        }
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }

        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        if (userInfo == null) {
            throw new BusinessException("用户信息不存在");
        }

        userInfo = new UserInfo();
        userInfo.setPassword(password);
        userInfoService.updateUserInfoByUserId(userInfo, userId);
        return getSuccessResponseVO(null);
    }

    /**
     * 删除用户
     */
    @RequestMapping("/delUser")
    public ResponseVO delUser(Integer userId) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        if (userInfo == null) {
            throw new BusinessException("用户信息不存在");
        }
        userInfoService.deleteUserInfoByUserId(userId);
        return getSuccessResponseVO(null);
    }

}