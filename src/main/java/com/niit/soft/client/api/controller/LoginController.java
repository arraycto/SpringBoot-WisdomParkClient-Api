package com.niit.soft.client.api.controller;

import com.niit.soft.client.api.annotation.ControllerWebLog;
import com.niit.soft.client.api.common.ResponseResult;
import com.niit.soft.client.api.common.ResultCode;
import com.niit.soft.client.api.domain.dto.LoginDto;
import com.niit.soft.client.api.domain.dto.SmsPhoneDto;
import com.niit.soft.client.api.domain.dto.VerifyPhoneDto;
import com.niit.soft.client.api.domain.model.UserAccount;
import com.niit.soft.client.api.service.LoginDtoService;
import com.niit.soft.client.api.service.SendSmsService;
import com.niit.soft.client.api.service.UserAccountService;
import com.niit.soft.client.api.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/5/24 10:27
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping(value = "/user/")
@Api(value = "LoginController", tags = {"登录接口"})
public class LoginController {
    @Resource
    private LoginDtoService loginDtoService;
    @Resource
    private UserAccountService userAccountService;
    private SendSmsService sendSmsService;

    @Autowired
    public SendSmsService getSendSmsService() {
        return sendSmsService;
    }

    /**
     * 可以通过账号或学号或手机号 加 密码登录
     *
     * @param loginDto
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "登录", notes = "可以通过账号或学号或手机号加密码登录")
    @PostMapping("login")
    @ControllerWebLog(name = "login", isSaved = true)
    public ResponseResult login(@RequestBody LoginDto loginDto) throws UnsupportedEncodingException {
        log.info("访问login接口");
        log.info("loginDto{}", loginDto);
        //如果查到数据，返回用户数据
        Long id = loginDtoService.getIdByInfo(loginDto.getUserAccount(), loginDto.getPassword());
        if (id != 0) {
            log.info("登录成功");
            log.info(userAccountService.findUserAccountById(id).toString());
            //创建返回的数据
            Map map = new HashedMap();
            map.put("UserAccount", userAccountService.findUserAccountById(id));
            map.put("token", JwtUtil.sign(loginDto.getUserAccount(), loginDto.getPassword()));
            log.info("生成的token{}", map.get("token"));
            return ResponseResult.success(map);
        }
        return ResponseResult.failure(ResultCode.DATA_IS_WRONG);
    }

    @ControllerWebLog(name = "loginByPhone", isSaved = true)
    @ApiOperation(value = "手机验证码登录", notes = "请求参数为手机号 和  手机验证码 phoneNumber   verifyCode   ")
    @PostMapping("code/login")
    public ResponseResult loginByPhone(@RequestBody VerifyPhoneDto verifyPhone) throws UnsupportedEncodingException {
        log.info("访问code/login接口");
        //如果查到数据，返回用户数据
        if (sendSmsService.verify(verifyPhone)) {
            log.info("登录成功");
            UserAccount userAccount = userAccountService.findUserAccountByInfo(verifyPhone.getPhoneNumber());
            log.info(userAccount.toString());
            Map map = new HashedMap();
            map.put("UserAccount", userAccount);
            map.put("token", JwtUtil.sign(userAccount.getUserAccount(), userAccount.getPassword()));
            log.info("生成的token{}", map.get("token"));
            return ResponseResult.success(map);
        }
        return ResponseResult.failure(ResultCode.DATA_IS_WRONG);
    }

    @ControllerWebLog(name = "changePassword", isSaved = true)
    @ApiOperation(value = "修改密码", notes = "参数  手机号/学号/账号+新密码")
    @PutMapping("password")
    public ResponseResult changePassword(@RequestBody LoginDto loginDto) {
        log.info("访问user/password接口");
        //如果查到数据，返回用户数据
        return ResponseResult.success(userAccountService.updatePasswordByUserAccount(loginDto.getUserAccount(), loginDto.getPassword()));
    }

    /**
     * 1.获取token
     * 2.解析数据
     * 3.返回用户信息
     */
    @ControllerWebLog(name = "flushUserAccount", isSaved = true)
    @ApiOperation(value = "刷新用户信息", notes = "无参数，通过token解析")
    @PostMapping("flush")
    public ResponseResult flushUserAccount(@RequestBody SmsPhoneDto smsPhoneDto) {
        log.info("访问 /user/flush 刷新数据");
        UserAccount userAccount = userAccountService.findUserAccountByInfo(smsPhoneDto.getPhoneNumber());
        log.info(userAccount.toString());
        Map map = new HashedMap();
        map.put("UserAccount", userAccount);
        return ResponseResult.success(map);
    }


}
